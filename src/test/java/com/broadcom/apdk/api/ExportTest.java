package com.broadcom.apdk.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.maven.shared.utils.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.broadcom.apdk.objects.XmlHelper;
import com.google.common.io.Files;

abstract class ExportTest {
	
	Map<String, File> getFilesFromZip(File zipFile, File tempFolder) {
		Map<String, File> extractedFiles = new HashMap<String, File>();
		if (zipFile != null && zipFile.isFile() &&
				tempFolder != null && tempFolder.isDirectory()) {
			try {
				byte[] buffer = new byte[1024];
				ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
				ZipEntry zipEntry = zis.getNextEntry();
				while (zipEntry != null) {
					File newFile = createFile(tempFolder, zipEntry); 
					Files.createParentDirs(newFile);
					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					extractedFiles.put(newFile.getName(), newFile);
					fos.close();
					zipEntry = zis.getNextEntry();
				}
				zis.closeEntry();
				zis.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return extractedFiles;
	}
	
    private File createFile(File folder, ZipEntry zipEntry) throws IOException {
        File destFile = new File(folder, zipEntry.getName());
        String destFilePath = destFile.getCanonicalPath();   
        if (!destFilePath.startsWith(folder + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }    
        return destFile;
    }
	
	void compileWithFramework(List<String> sourceFileNames, File tempFolder) throws Exception {	
		if (sourceFileNames != null && !sourceFileNames.isEmpty()) {
			clean(tempFolder);
			File sourceFolder = getSourceFolder(sourceFileNames.get(0));
			copyFramework(sourceFolder, tempFolder);
			File pomFile = createParentPOM(sourceFolder, tempFolder);
			File frameworkPomFile = modifyFrameworkPOM(sourceFolder, tempFolder);
			File projectPomFile = copySources(sourceFileNames, tempFolder);
			modifyProjectPOM(projectPomFile, frameworkPomFile);
			try {	
				InvocationRequest request = new DefaultInvocationRequest();
				request.setPomFile(pomFile);
				request.setGoals(Collections.singletonList("clean package"));
				 
				Invoker invoker = new DefaultInvoker();
				invoker.execute(request);
			} 
			catch (MavenInvocationException e) {
				throw new Exception("Failed to run maven build");
			}
		}
	}
	
	private void modifyProjectPOM(File projectPOM, File frameworkPOM) throws Exception {
		if (frameworkPOM != null && frameworkPOM.exists() &&
				projectPOM != null && projectPOM.exists()) {
			String artifactId = XmlHelper.getXMLFragmentAsString(
					frameworkPOM, "project/artifactId/text()");
			String groupId = XmlHelper.getXMLFragmentAsString(
					frameworkPOM, "project/groupId/text()");
			String version = XmlHelper.getXMLFragmentAsString(
					frameworkPOM, "project/version/text()");		
			try {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document document = docBuilder.parse(projectPOM);

				Element newDependencyElement = document.createElement("dependency");
				Element groupIdElement = document.createElement("groupId");
				groupIdElement.setTextContent(groupId);
				Element artifactIdElement = document.createElement("artifactId");
				artifactIdElement.setTextContent(artifactId);	
				Element versionElement = document.createElement("version");
				versionElement.setTextContent(version);	
				newDependencyElement.appendChild(groupIdElement);
				newDependencyElement.appendChild(artifactIdElement);
				newDependencyElement.appendChild(versionElement);
				Node dependenciesNode = document.getElementsByTagName("dependencies").item(0);
				NodeList dependenyNodes = dependenciesNode.getChildNodes();
				if (dependenyNodes.getLength() > 0) {
					for (int idx = 0; idx < dependenyNodes.getLength(); idx++) {
						Node dependencyNode = dependenyNodes.item(idx);
						NodeList dependencyAttrs = dependencyNode.getChildNodes();
						if (dependencyAttrs.getLength() > 0) {
							for (int attrIdx = 0; attrIdx < dependencyAttrs.getLength(); attrIdx++) {
								String nodeName = dependencyAttrs.item(attrIdx).getNodeName();
								String content = dependencyAttrs.item(attrIdx).getTextContent();
								if (nodeName.equals("artifactId") && content.equals("action-pack-sdk")) {
									dependenciesNode.replaceChild(newDependencyElement, dependencyNode);	
								}
							}
						}
					}
				}
				
		        TransformerFactory factory = TransformerFactory.newInstance();
		        Transformer transformer = factory.newTransformer();
		        DOMSource domSource = new DOMSource(document);
		        StreamResult streamResult = new StreamResult(projectPOM);
				transformer.transform(domSource, streamResult);
			} 
			catch (TransformerException | ParserConfigurationException | SAXException | IOException e) {
				throw new Exception("Failed to modify the pom file of the project");
			}
		}		
	}
	
	File getTargetFolder(File tempFolder) {
		if (tempFolder != null && tempFolder.isDirectory()) {
			return new File(tempFolder.getAbsolutePath() + File.separator + "test-project" + 
					File.separator + "target");
		}
		return null;
	}
	
	File getJarFile(File tempFolder) {
		File targetFolder = getTargetFolder(tempFolder);
		return getFileWithExtendsion(targetFolder, "jar");
	}
	
	File getZipFile(File tempFolder) {
		File targetFolder = getTargetFolder(tempFolder);
		return getFileWithExtendsion(targetFolder, "zip");
	}
	
	String getClientVersion(String xmlContent) {
		Pattern clientVersionPattern = Pattern.compile("clientvers=\"[0-9\\.\\+a-zA-Z]*\"");
		Matcher m = clientVersionPattern.matcher(xmlContent);
		while (m.find()) {
			return m.group(0);
		}
		return null;	
	}
	
	String getSize(String xmlContent) {
		Pattern sizePattern = Pattern.compile("size=\"[0-9]*\"");
		Matcher m = sizePattern.matcher(xmlContent);
		while (m.find()) {
			return m.group(0);
		}
		return null;			
	}
	
	String getChecksum(String xmlContent) {
		Pattern checksumPattern = Pattern.compile("checksum=\"[0-9A-Z]*\"");
		Matcher m = checksumPattern.matcher(xmlContent);
		while (m.find()) {
			return m.group(0);
		}
		return null;			
	}
	
	private File getFileWithExtendsion(File folder, String extension) {
		if (folder != null && folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File file : files) {
				if (file.isFile() && file.getName().toLowerCase().endsWith("." + extension)) {
					return file;
				}
			}
		}
		return null;		
	}
	
	
	private void clean(File rootFolder) {
		if (rootFolder != null && rootFolder.isDirectory()) {
			try {
				java.nio.file.Files.list(rootFolder.toPath())
					.forEach(path -> {
						try {
							java.nio.file.Files.walk(path)
							  .sorted(Comparator.reverseOrder())
							  .map(Path::toFile)
							  .forEach(File::delete);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private File copySources(List<String> sourceFileNames, File tempFolder) throws Exception {
		File pomFile = null;
		if (sourceFileNames != null && !sourceFileNames.isEmpty() && 
				tempFolder != null && tempFolder.isDirectory()) {
			for (String filename : sourceFileNames) {
				try {
					File originalFile = new File(ApiTest.class.getClassLoader().getResource(filename).getFile());
					File file = null;
					if (originalFile.getName().toLowerCase().endsWith(".java")) {
						file = getDestFile(originalFile, tempFolder);
	
					}
					if (originalFile.getName().toLowerCase().endsWith("pom.xml")) {
						file = new File(tempFolder.getAbsolutePath() + File.separator + 
								"test-project" + File.separator + "pom.xml");	
						pomFile = file;
					}
					if (file != null) {
						Files.createParentDirs(file);
						Files.copy(originalFile, file);
	
					}
				} 
				catch (Exception e) {
					throw new Exception("Failed to copy test sources");
				}
			}
		}
		return pomFile;
	}
	
	private File getDestFile(File sourceFile, File rootFolder) throws Exception {
		if (sourceFile != null && sourceFile.isFile() &&
				rootFolder != null && rootFolder.isDirectory()) {
			try {
				String separator = File.separator;
				List<String> lines = Files.readLines(sourceFile, Charset.defaultCharset());
				for (String line : lines) {
					if (line.trim().startsWith("package ") 
							&& line.trim().endsWith(";") && line.trim().length() > 9) {
						String packageName = line.trim().substring(8, line.trim().length() - 1);
						String packagePath = packageName.replace(".", separator);
						return new File(rootFolder.getPath() + separator + "test-project" + 
								separator + "src" + separator + "main" + separator + "java" +
								separator + packagePath + separator + sourceFile.getName());
					}
				}
				return null;
			} catch (IOException e) {
				throw new Exception("Failed to create destination file");
			}
		}
		return null;
	}
	
	private File createParentPOM(File sourceFolder, File tempFolder) throws Exception {
		if (sourceFolder != null && sourceFolder.isDirectory() &&
				tempFolder != null && tempFolder.isDirectory()) {
			String frameworkProjectName = sourceFolder.getAbsolutePath().
					substring(sourceFolder.getAbsolutePath().lastIndexOf(File.separator) + 1);
			File pomFile = new File(tempFolder.getAbsolutePath() + File.separator + "pom.xml");		
			String pomContent = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" " +
					"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
					"xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
					"  <modelVersion>4.0.0</modelVersion>\n" +
					"  <groupId>com.broadcom</groupId>\n" +
					"  <artifactId>parent-project</artifactId>\n" +
					"  <packaging>pom</packaging>\n" +
					"  <version>1.0-SNAPSHOT</version>\n" +
					"  <name>parent-project</name>\n" +
					"  <url>http://maven.apache.org</url>\n" +
					"  <modules>\n" +
					"    <module>" + frameworkProjectName + "</module>\n" +
					"    <module>test-project</module>\n" +
					"  </modules>\n" +
					"</project>";
			try {
				FileUtils.fileWrite(pomFile.getAbsolutePath(), pomContent);
				return pomFile;
			} 
			catch (IOException e) {
				throw new Exception("Failed to create parent pom file");
			}
		}
		return null;
	}
	
	private void copyFramework(File source, File destination) throws Exception {
		if (source != null && source.isDirectory() && 
				destination != null && destination.isDirectory()) {
			Iterable<File> sourceFiles = Files.fileTraverser().depthFirstPreOrder(source);
			Iterator<File> sourceFileIterator = sourceFiles.iterator();
			while (sourceFileIterator.hasNext()) {
				File sourceFile = sourceFileIterator.next();
				File destinationFile = new File(sourceFile.getAbsolutePath().replace(
						source.getAbsolutePath().substring(0, source.getAbsolutePath().lastIndexOf(File.separator)),
						destination.getAbsolutePath()));
				if (!sourceFile.isDirectory() &&
						sourceFile.getAbsolutePath().startsWith(source.getAbsolutePath() + 
						File.separator + "src" + File.separator + "main") ||
						sourceFile.getAbsolutePath().endsWith("pom.xml")) {
					try {
						Files.createParentDirs(destinationFile);
						Files.copy(sourceFile, destinationFile);	
					} 
					catch (IOException e) {
						throw new Exception("Failed to copy framework sources");
					}
				}
			}
		}
	}
	
	private File modifyFrameworkPOM(File sourceFolder, File tempFolder) throws Exception {
		if (sourceFolder != null && sourceFolder.isDirectory() &&
				tempFolder != null && tempFolder.isDirectory()) {
			String frameworkProjectName = sourceFolder.getAbsolutePath().
					substring(sourceFolder.getAbsolutePath().lastIndexOf(File.separator) + 1);
			File pomFile = new File(tempFolder.getAbsolutePath() + File.separator + 
					frameworkProjectName + File.separator + "pom.xml");	
			if (pomFile.exists()) {
				try {
					DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
					Document document = docBuilder.parse(pomFile);
					
					Element root = document.getDocumentElement();
					Element parentElement = document.createElement("parent");
					Element groupIdElement = document.createElement("groupId");
					groupIdElement.setTextContent("com.broadcom");
					Element artifactIdElement = document.createElement("artifactId");
					artifactIdElement.setTextContent("parent-project");	
					Element versionElement = document.createElement("version");
					versionElement.setTextContent("1.0-SNAPSHOT");	
					parentElement.appendChild(groupIdElement);
					parentElement.appendChild(artifactIdElement);
					parentElement.appendChild(versionElement);
					root.insertBefore(parentElement, 
							document.getElementsByTagName("dependencies").item(0));
					
			        TransformerFactory factory = TransformerFactory.newInstance();
			        Transformer transformer = factory.newTransformer();
			        DOMSource domSource = new DOMSource(document);
			        StreamResult streamResult = new StreamResult(pomFile);
					transformer.transform(domSource, streamResult);
				} 
				catch (TransformerException | ParserConfigurationException | SAXException | IOException e) {
					throw new Exception("Failed to modify the pom file of the framework");
				}
				return pomFile;
			}
		}
		return null;
	}
	
	private File getSourceFolder(String resourceName) {
		String sourceDir = System.getProperty("sourcedir");
		if (sourceDir == null) {
			File originalFile = new File(ApiTest.class.getClassLoader().
					getResource(resourceName).getFile());
			sourceDir = originalFile.getPath();
			sourceDir = sourceDir.substring(0, sourceDir.indexOf(
					File.separator + "target" + 
					File.separator + "test-classes" + 
					File.separator + resourceName));
		}
		return Paths.get(sourceDir).toFile();
	}
	
	
}
