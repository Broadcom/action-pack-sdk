package com.broadcom.apdk.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.broadcom.apdk.api.IAction;
import com.broadcom.apdk.api.IActionPack;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.apdk.api.annotations.CustomType;
import com.broadcom.apdk.api.annotations.EnumValue;
import com.broadcom.apdk.helpers.ActionHelper;
import com.broadcom.apdk.helpers.FolderHelper;
import com.broadcom.apdk.objects.Documentation;
import com.broadcom.apdk.objects.ExportPackage;
import com.broadcom.apdk.objects.FileType;
import com.broadcom.apdk.objects.Folder;
import com.broadcom.apdk.objects.FolderStructure;
import com.broadcom.apdk.objects.IAutomicObject;
import com.broadcom.apdk.objects.IJob;
import com.broadcom.apdk.objects.IJobUnix;
import com.broadcom.apdk.objects.IPrompt;
import com.broadcom.apdk.objects.IPromptSet;
import com.broadcom.apdk.objects.IStorage;
import com.broadcom.apdk.objects.IVariable;
import com.broadcom.apdk.objects.IWorkflow;
import com.broadcom.apdk.objects.IWorkflowTask;
import com.broadcom.apdk.objects.JobUnix;
import com.broadcom.apdk.objects.JobWindows;
import com.broadcom.apdk.objects.KeyValueGroup;
import com.broadcom.apdk.objects.Link;
import com.broadcom.apdk.objects.PromptCombo;
import com.broadcom.apdk.objects.PromptDate;
import com.broadcom.apdk.objects.PromptDateTime;
import com.broadcom.apdk.objects.PromptInteger;
import com.broadcom.apdk.objects.PromptSet;
import com.broadcom.apdk.objects.PromptText;
import com.broadcom.apdk.objects.PromptTime;
import com.broadcom.apdk.objects.StatusMismatchOrTimeFailure;
import com.broadcom.apdk.objects.Storage;
import com.broadcom.apdk.objects.StoredFile;
import com.broadcom.apdk.objects.VariableEntry;
import com.broadcom.apdk.objects.VariableText;
import com.broadcom.apdk.objects.Workflow;
import com.broadcom.apdk.objects.WorkflowEndTask;
import com.broadcom.apdk.objects.WorkflowStartTask;
import com.broadcom.apdk.objects.WorkflowTask;
import com.broadcom.apdk.objects.WorkflowTaskPredecessor;
import com.google.common.io.Files;

class ExportService {
	
	class PromptSetEntry {
		
		private IPromptSet promptSet;
		private boolean shared;
		
		PromptSetEntry(IPromptSet promptSet, boolean shared) {
			this.promptSet = promptSet;
			this.shared = shared;
		}
		
		boolean isShared() {
			return shared;
		}
		
		IPromptSet getPromptSet() {
			return promptSet;
		}

	}
	
	class PromptSetsAndVariables {
		
		private List<PromptSetEntry> promptSets;
		private List<IVariable> variables;
		
		public PromptSetsAndVariables(List<PromptSetEntry> promptSets, List<IVariable> variables) {
			this.promptSets = promptSets;
			this.variables = variables;
		}
		
		public List<PromptSetEntry> getPromptSets() {
			return promptSets;
		}
		
		public List<IVariable> getVariables() {
			return variables;
		}	

	}
	
	private final static Logger LOGGER = Logger.getLogger("APDK");
	private final String CLIENTVERSION = "12.3.1+build.1570622767455";
	
	public void export(IActionPack actionPack, List<IAction> actions) throws ExportException {
		export(actionPack, actions, null);
	}
	
	public void export(IActionPack actionPack, List<IAction> actions, Path destinationFolder) throws ExportException {
		
		if (actionPack != null) {
			// Get Action Pack name
			String actionPackName = toValidName(getActionPackName(actionPack));
			
			// Define destination folder
			destinationFolder = getDestinationFolder(destinationFolder);
			String contentXML = destinationFolder + destinationFolder.getFileSystem().getSeparator() + "CONTENT.xml";
			LOGGER.info("Destination folder: \"" + destinationFolder + "\"");
			
			// Define name of zip file
			String version = this.getClass().getPackage().getImplementationVersion();
			if (version != null) {
				version = "-" + version;
			}
			String zipFilename = destinationFolder + destinationFolder.getFileSystem().getSeparator() + 
					actionPackName + (version != null ? version : "") + ".zip";
			LOGGER.info("Destination file: \"" + zipFilename + "\"");	
			
			// Create basic Folder structure
			String[] folderStructure = new String[9];
			folderStructure[0] = "ACTIONS";
			folderStructure[1] = "CONFIG";
			folderStructure[2] = "DOCUMENTATION";
			folderStructure[3] = "RESOURCES";
			folderStructure[4] = "RESOURCES/INTERPRETERS";
			folderStructure[5] = "RESOURCES/LIBS";
			folderStructure[6] = "RESOURCES/SCRIPTS";
			folderStructure[7] = "SOURCE";
			folderStructure[8] = "TEMPLATES";
			
			Folder rootFolder = getInitialFolderStructure(actionPack, folderStructure);
			
			// Define the list of files that should be added to the ZIP package
			// Key refers to the file and the value indicates if it should appear in a folder
			Map<Path, String> files = new HashMap<Path, String>();
			files.put(Paths.get("CONTENT.xml"), "");
			
			// Create VARA objects for Action Pack meta data
			VariableText metaDataVARA = getMetaDataVARA(actionPack);
			LOGGER.info("Create object \"" + metaDataVARA.getName() + "\"");
			rootFolder = (Folder) FolderHelper.insertObject(rootFolder, null, metaDataVARA);
			rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "CONFIG", getPromptExternalMapVARA(actionPack, actions));
			export(metaDataVARA, destinationFolder);
			files.put(Paths.get(metaDataVARA.getName() + ".xml"), "");
			
			// Create DOCU objects
			Documentation publicDocu = getPublicDOCU(actionPack);
			Documentation licensesDocu = getLicensesDOCU(actionPack);
			rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "DOCUMENTATION", publicDocu);
			LOGGER.info("Create object \"" + publicDocu.getName() + "\"");
			rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "DOCUMENTATION", licensesDocu);
			LOGGER.info("Create object \"" + licensesDocu.getName() + "\"");
			export(publicDocu, destinationFolder);
			export(licensesDocu, destinationFolder);
			files.put(Paths.get(publicDocu.getName() + ".xml"), "DOCUMENTATION");
			files.put(Paths.get(licensesDocu.getName() + ".xml"), "DOCUMENTATION");
			
			// Create STORAGE object that contains CustomTypes
			IStorage customTypes = getCustomTypeSTORAGE(actionPack);
			if (customTypes != null) {
				LOGGER.info("Create object \"" + customTypes.getName() + "\"");
				rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "RESOURCES", customTypes);
			}
			
			// Create STORAGE object that contains the Action Pack binaries
			IStorage apjar = getBinarySTORAGE(actionPack);
			LOGGER.info("Create object \"" + apjar.getName() + "\"");
			rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "RESOURCES/LIBS", apjar);

			// Create Actions
			for (IAction action : actions) {
				rootFolder = addActionWorkflow(rootFolder, actionPackName, apjar, action);
			}

			List<IAutomicObject> list = FolderHelper.getAutomicObjects(rootFolder);
			ExportPackage exportPackage = new ExportPackage(new FolderStructure(rootFolder), list);
			exportPackage.setClientvers(CLIENTVERSION);
			
			LOGGER.info("Export to \"" + contentXML + "\"");
			try {
		        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        DocumentBuilder db = dbf.newDocumentBuilder();
				Document exportDocument = db.newDocument();
				
				JAXBContext context = JAXBContext.newInstance(exportPackage.getClass());
		        Marshaller m = context.createMarshaller();
		        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);	
				m.marshal(exportPackage, exportDocument);
				
				DOMSource source = new DOMSource(exportDocument);
				Writer writer = new OutputStreamWriter(new FileOutputStream(contentXML), StandardCharsets.UTF_8);
			    StreamResult result = new StreamResult(writer);

			    
			    TransformerFactory factory = TransformerFactory.newInstance();
			    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
			    Transformer transformer = factory.newTransformer();
			    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			    transformer.transform(source, result);		
			    
			    writer.close();
			} 
			catch (JAXBException | TransformerException | ParserConfigurationException | IOException e) {
				throw new ExportException(e.getClass().getName() + (e.getMessage() != null ? ": " + e.getMessage() : ""));
			} 

			// Retrieve list of files that must be added to the ZIP file
			List<String> storedFiles = FolderHelper.getFilenames(actionPackName, rootFolder);
			for (String storedFile : storedFiles) {
				files.put(Paths.get(storedFile), "");
				LOGGER.info("Add stored file \"" + storedFile + "\" to zip-file");
			}
			
			// Create ZIP file
			files = getFileWithDestinationPath(files, destinationFolder);
			LOGGER.info("Created \"" + zipFilename + "\"");
			createZipFile(zipFilename, files);
			
			// Delete files that were added to the ZIP file
			for (Path file : files.keySet()) {
				file.toFile().delete();
			}
		}
	}
	
	public void export(IAutomicObject object) throws ExportException {
		export(object, object.getName(), null);
	}
	
	public void export(IAutomicObject object, Path destinationFolder) throws ExportException {
		export(object, object.getName(), getDestinationFolder(destinationFolder));
	}

	private void export(IAutomicObject object, String name, Path destinationFolder) throws ExportException {
		destinationFolder = getDestinationFolder(destinationFolder);
		String filename = destinationFolder + destinationFolder.getFileSystem().getSeparator() + toValidName(name) + ".xml";
		LOGGER.info("Export to \"" + filename + "\"");		
		
		try {
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.newDocument();
			
			JAXBContext context = JAXBContext.newInstance(object.getClass());
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(object, document);
			
			DOMSource source = new DOMSource(wrapDocumentForExport(document));		
			
			Writer writer = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8);
		    StreamResult result = new StreamResult(writer);

		    TransformerFactory factory = TransformerFactory.newInstance();
		    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
		    Transformer transformer = factory.newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.transform(source, result);
		    
		    writer.close();
		} 
		catch (IOException | TransformerException | ParserConfigurationException | JAXBException e) {
			throw new ExportException(e.getClass().getName() + ": " + e.getMessage());
		} 
	}
	
	@SuppressWarnings("unchecked")
	private PromptSetsAndVariables getPromptSetsAndVariables(String actionPackName, Class<? extends com.broadcom.apdk.api.IPromptSet> actionClass) {
		List<PromptSetEntry> promptSets = new ArrayList<PromptSetEntry>();
		List<IPrompt<?>> prompts = new ArrayList<IPrompt<?>>();
		List<IVariable> variables = new ArrayList<IVariable>();
		if (actionClass != null) {
			Class<?> parentClass = actionClass.getSuperclass();
			Field[] fields = actionClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(ActionInputParam.class)) {
					ActionInputParam annotation = field.getAnnotation(ActionInputParam.class);
					String variableName = "&" + field.getName().toUpperCase() + "#";
					if (!annotation.name().trim().isEmpty()) {
						variableName = annotation.name().trim().toUpperCase();
						if (!variableName.startsWith("&")) {
							variableName = "&" + variableName;
						}
						if (!variableName.endsWith("#")) {
							variableName = variableName + "#";
						}
					}
					String label = annotation.label().isEmpty() ? field.getName() : annotation.label(); 
					boolean required = annotation.required();
					String tooltip = annotation.tooltip(); 
					String refType = annotation.refType();
					String dataReference = annotation.dataReference();
					boolean password = annotation.password();
					if (field.getType().equals(Float.class) || field.getType().equals(Float.TYPE) ||
							field.getType().equals(Double.class) || field.getType().equals(Double.TYPE)) {
						PromptText promptFloatOrDouble = new PromptText(variableName, label, tooltip, refType,
								dataReference != null && !dataReference.isEmpty() ? dataReference : "UC_DATATYPE_STRING");
						promptFloatOrDouble.setRegEx("[-+]?\\d*\\.?\\d*");
						promptFloatOrDouble.setRequired(required);
						prompts.add(promptFloatOrDouble);
					}
					else if (field.getType().equals(Integer.class) || field.getType().equals(Integer.TYPE) ||
							field.getType().equals(Short.class) || field.getType().equals(Short.TYPE) ||
							field.getType().equals(Byte.class) || field.getType().equals(Byte.TYPE)) {
						PromptInteger promptInteger = new PromptInteger(variableName, label, tooltip);
						prompts.add(promptInteger);
					}
					else if (field.getType().equals(Long.class) || field.getType().equals(Long.TYPE)) {
						PromptText promptLong = new PromptText(variableName, label, tooltip, refType, 
								dataReference != null && !dataReference.isEmpty() ? dataReference : "UC_DATATYPE_STRING");
						promptLong.setRegEx("[-+]?\\d*");
						promptLong.setRequired(required);
						prompts.add(promptLong);
					}
					else if (field.getType().equals(Boolean.class) || field.getType().equals(Boolean.TYPE)) {
						prompts.add(new PromptCombo(variableName, label, tooltip, refType, "PCK.ITPA_SHARED.PRV.VARA.TRUE_FALSE"));	
					}
					else if (field.getType().equals(String.class) && dataReference != null && !dataReference.isEmpty()) {
						prompts.add(new PromptCombo(variableName, label, tooltip, refType, dataReference));
					}
					else if (field.getType().equals(LocalTime.class)) {
						prompts.add(new PromptTime(variableName, label, tooltip));
					}
					else if (field.getType().equals(LocalDate.class)) {
						prompts.add(new PromptDate(variableName, label, tooltip));
					}
					else if (field.getType().equals(LocalDateTime.class)) {
						prompts.add(new PromptDateTime(variableName, label, tooltip));
					}
					else if (Enum.class.isAssignableFrom(field.getType())) {
						IVariable variable = getVariableFromEnum(actionPackName, (Class<? extends Enum<?>>) field.getType());
						if (variable != null) {
							variables.add(variable);	
							prompts.add(new PromptCombo(variableName, label, tooltip, refType, variable.getName()));
						}
					}
					else if (field.getType().equals(String.class) && password) {
						PromptText promptText = new PromptText(variableName, label, 
								tooltip, "STATIC", "UC_DATATYPE_STRING");
						promptText.setShowPassword(true);
						promptText.setRequired(required);
						prompts.add(promptText);
					}
					else {
						PromptText promptText = new PromptText(variableName, label, tooltip, refType, 
								dataReference != null && !dataReference.isEmpty() ? dataReference : "UC_DATATYPE_STRING");	
						promptText.setRequired(required);
						prompts.add(promptText);
					}
				}
				if (com.broadcom.apdk.api.IPromptSet.class.isAssignableFrom(field.getType())) {
					PromptSetsAndVariables promptsAndVariables = getPromptSetsAndVariables(
							actionPackName, (Class<? extends com.broadcom.apdk.api.IPromptSet>) field.getType());
					List<PromptSetEntry> referencedPromptSets = promptsAndVariables.getPromptSets();
					for (PromptSetEntry parentPromptSet : referencedPromptSets) {
						promptSets.add(new PromptSetEntry(parentPromptSet.getPromptSet(), true));
					}
					variables.addAll(promptsAndVariables.getVariables());						
				}
			} 
			if (!prompts.isEmpty()) {
				String promptSetName = getPromptSetName((Class<? extends com.broadcom.apdk.api.IPromptSet>) actionClass);	
				String promptSetTitle = getPromptSetTitle((Class<? extends com.broadcom.apdk.api.IPromptSet>) actionClass);
				if (promptSetName != null) {
					promptSets.add(new PromptSetEntry(new PromptSet(actionPackName.toUpperCase() + 
							".PRV.PROMPTSET." + promptSetName, promptSetTitle, prompts), false));	
				}
			}
			// Recursively check parent classes
			if (IAction.class.isAssignableFrom(parentClass) && !parentClass.isInterface()) {
				PromptSetsAndVariables promptsAndVariablesFromParent = getPromptSetsAndVariables(
						actionPackName, (Class<? extends com.broadcom.apdk.api.IPromptSet>) parentClass);
				List<PromptSetEntry> parentPromptSets = promptsAndVariablesFromParent.getPromptSets();
				for (PromptSetEntry parentPromptSet : parentPromptSets) {
					promptSets.add(new PromptSetEntry(parentPromptSet.getPromptSet(), true));
				}
				variables.addAll(promptsAndVariablesFromParent.getVariables());
			}
			return new PromptSetsAndVariables(promptSets, variables);
		}
		return null;
	}
	
	private static IVariable getVariableFromEnum(String actionPackName, Class<? extends Enum<?>> enumeration) {
		if (enumeration != null) {		
			List<KeyValueGroup<String>> values = new ArrayList<>();
			Field[] fields = enumeration.getDeclaredFields();
			if (fields != null) {
				for (Field field : fields) {
					if (field.isEnumConstant()) {
						String value = field.getName();
						if (field.isAnnotationPresent(EnumValue.class)) {
							EnumValue annotation = field.getAnnotation(EnumValue.class);
							value = annotation.value();
						}
						values.add(new KeyValueGroup<String>(field.getName(), value));
					}
				}
			}
			VariableText variable = new VariableText(actionPackName.toUpperCase() + ".PRV.VARA." + 
					enumeration.getSimpleName().toUpperCase());
			variable.setValues(values);
			return variable;
		}
		return null;
	}

	private String getPromptSetTitle(Class<? extends com.broadcom.apdk.api.IPromptSet> actionOrPromptClass) {
		if (actionOrPromptClass != null &&
				actionOrPromptClass.isAnnotationPresent(com.broadcom.apdk.api.annotations.PromptSet.class)) {
			com.broadcom.apdk.api.annotations.PromptSet annotation = 
					actionOrPromptClass.getAnnotation(com.broadcom.apdk.api.annotations.PromptSet.class);
			if (!annotation.title().isEmpty()) {
				return annotation.title();
			}
		}
		return "Input";
	}
	
	private String getPromptSetName(Class<? extends com.broadcom.apdk.api.IPromptSet> actionOrPromptClass) {
		if (actionOrPromptClass != null) {
			if (actionOrPromptClass.isAnnotationPresent(com.broadcom.apdk.api.annotations.PromptSet.class)) {
				com.broadcom.apdk.api.annotations.PromptSet annotation = 
						actionOrPromptClass.getAnnotation(com.broadcom.apdk.api.annotations.PromptSet.class);
				if (!annotation.name().isEmpty()) {
					return annotation.name().replace(" ", "_").toUpperCase();
				}
			}
			try {
				com.broadcom.apdk.api.IPromptSet object = actionOrPromptClass.getDeclaredConstructor().newInstance();
				return object.getName().replace(" ", "_").toUpperCase();
			} 
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException | 
					InvocationTargetException | NoSuchMethodException | SecurityException e) {} 
			return actionOrPromptClass.getSimpleName().toUpperCase();
		}
		return null;
	}
	
	private String getActionName(Class<? extends IAction> actionClass) {
		if (actionClass != null) {
			try {
				IAction action = (IAction) actionClass.getDeclaredConstructor().newInstance();
				return action.getName().replace(" ", "_").toUpperCase();
			} 
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException | 
					InvocationTargetException | NoSuchMethodException | SecurityException e) {} 
			return actionClass.getSimpleName().toUpperCase();
		}
		return null;
	}
	
	private Map<Path, String> getFileWithDestinationPath(Map<Path, String> files, Path destinationFolder) {
		Map<Path, String> filesWithDestPath = new HashMap<Path, String>();
		for (Map.Entry<Path, String> entry : files.entrySet()) {
			String storedFile = entry.getKey().toString();
			Path key = Paths.get(destinationFolder + destinationFolder.getFileSystem().getSeparator() + storedFile);
			filesWithDestPath.put(key, entry.getValue());
		}
		return filesWithDestPath;
	}
	
	
	private void createZipFile(String zipFilePath, Map<Path, String> files) throws ExportException {
		try {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zipOS = new ZipOutputStream(fos);
            for (Map.Entry<Path, String> entry : files.entrySet()) {
            	String fullPath = entry.getKey().toString();
            	
            	String folder = "";
            	if (entry.getValue() != null && !entry.getValue().isEmpty()) {
            		folder = entry.getValue() + "/";	
            	}
            	String filename = folder + entry.getKey().getFileName().toString();
            	
                File f = new File(fullPath);
                FileInputStream fis = new FileInputStream(f);
                ZipEntry zipEntry = new ZipEntry(filename);
                zipOS.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                	zipOS.write(bytes, 0, length);
                }

                zipOS.closeEntry();
                fis.close();          	
            }
            zipOS.close();
            fos.close();
		} 
		catch (IOException e) {
			throw new ExportException(e.getClass().getName() + (e.getMessage() != null ? ": " + e.getMessage() : ""));
		}
	}
	
	private Path getDestinationFolder(Path destinationFolder) throws ExportException {
		if (destinationFolder == null) {
			Path jarFilePath = getJAR();
			destinationFolder = jarFilePath.getParent();
		}
		return destinationFolder;
	}
	
	private Storage getCustomTypeSTORAGE(IActionPack actionPack) throws ExportException {
		if (actionPack != null) {
			Class<?> actionPackClass = actionPack.getClass();
			CustomType[] customTypeAnnotations = actionPackClass.getAnnotationsByType(CustomType.class);
			if (customTypeAnnotations.length > 0) {
				String actionPackName = toValidName(getActionPackName(actionPack));
				Storage store = new Storage(actionPackName + ".PRV.CUSTOM_TYPES");
				List<StoredFile> storedFiles = new ArrayList<StoredFile>();
				Path jarFilePath = getJAR();
				for (CustomType customType : customTypeAnnotations) {
					if (customType.filename() != null && !customType.filename().isEmpty()) {
						String filename = customType.filename();
						if (filename.lastIndexOf("/") > -1 && filename.lastIndexOf("/") < filename.length()) {
							filename = filename.substring(filename.lastIndexOf("/") + 1);
						}
						try {	
							if (filename.toUpperCase().endsWith(".XML")) {
								InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(customType.filename());
								if (inputStream != null) {
									byte[] buffer = new byte[inputStream.available()];
									inputStream.read(buffer);
									File targetFile = new File(jarFilePath.getParent().toString() + 
											jarFilePath.getFileSystem().getSeparator() + filename);
									Files.write(buffer, targetFile);
									String customTypeName = !customType.name().isEmpty() ? customType.name() : 
											filename.substring(0, filename.lastIndexOf("."));
									String order = customType.order() > 0 ? "#" + customType.order() : "";
									String storedFileName = customType.type().name().toUpperCase() + "#" + 
											customTypeName.replace(" ", "_").toUpperCase() + order;		
									StoredFile storedFile = new StoredFile(storedFileName, targetFile.toPath(), FileType.TEXT, 
											!customType.version().isEmpty() ? customType.version() : actionPack.getVersion());
									storedFiles.add(storedFile);	
									String newName = jarFilePath.getParent().toString() + File.separator + 
											store.getName() + "-" + storedFile.getName() + "-ALL-ALL-ALL";	
									targetFile.renameTo(new File(newName));
									LOGGER.info("Found custom type resource \"" + filename + "\"");	
								}
								else {
									throw new ExportException("Failed to find resource \"" + filename + "\"");
								}
							}
							else {
								throw new ExportException("Resource \"" + filename + "\" is not a XML file");
							}
						}
						catch (IOException e) {
							throw new ExportException(e.getClass().getName() + ": " + e.getMessage());	
						}
					}
				}
				store.setStoredFiles(storedFiles);
				return !storedFiles.isEmpty() ? store : null;
			}
		}
		return null;
	}
	
	private Storage getBinarySTORAGE(IActionPack actionPack) throws ExportException {
		String actionPackName = toValidName(getActionPackName(actionPack));
		Storage store = new Storage(actionPackName + ".PRV.STORE");
		List<StoredFile> storedFiles = new ArrayList<StoredFile>();
		String version = actionPack.getVersion();
		Path jarFilePath = getJAR();		
		StoredFile storedFile = new StoredFile("APJAR", jarFilePath, FileType.BINARY, version);
		storedFiles.add(storedFile);
		String destFile = jarFilePath.getParent().toString() + jarFilePath.getFileSystem().getSeparator() + 
				store.getName() + "-" + storedFile.getName() + "-ALL-ALL-ALL";	
		try {
			Files.copy(new File(jarFilePath.toString()), new File(destFile));
		} 
		catch (IOException e) {
			throw new ExportException(e.getClass().getName() + ": " + e.getMessage());
		}
		store.setStoredFiles(storedFiles);
		return store;
	}
	
	private Path getJAR() throws ExportException {
		try {
			String jar = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
			Path jarFilePath = Paths.get(jar);
			if (!jarFilePath.getFileName().toString().toUpperCase().endsWith(".JAR")) {
				throw new ExportException("Running file isn't a valid JAR file");
			}
			return jarFilePath;	
		} 
		catch (URISyntaxException e) {
			throw new ExportException(e.getClass().getName() + ": " + e.getMessage());
		} 
	}
	
	private Documentation getLicensesDOCU(IActionPack actionPack) {
		String actionPackName = toValidName(getActionPackName(actionPack));
		Documentation docu = new Documentation(actionPackName + ".PUB.LICENSES");
		docu.setDocumentation(actionPack.getLicenses());
		return docu;
	}
	
	private Documentation getPublicDOCU(IActionPack actionPack) {
		String actionPackName = toValidName(getActionPackName(actionPack));
		Documentation docu = new Documentation(actionPackName + ".PUB.DOC");
		docu.setTitle("Pack Documentation");
		docu.setDocumentation(actionPack.getDocumentation());
		return docu;		
	}
	
	private VariableText getPromptExternalMapVARA(IActionPack actionPack, List<IAction> actions) {
		String actionPackName = toValidName(getActionPackName(actionPack));
		List<KeyValueGroup<String>> values = new ArrayList<KeyValueGroup<String>>();
		VariableText vara = new VariableText(actionPackName + ".PUB.PROMPT_EXTERNAL_MAP");
		// Insert LOGIN and AGENT mappings
		for (IAction action : actions) {
			values.add(new KeyValueGroup<String>(actionPackName + ".PUB.ACTION." + 
					getActionName(action.getClass()) + "/AGENT#", "&AGENT#"));		
			values.add(new KeyValueGroup<String>(actionPackName + ".PUB.ACTION." + 
					getActionName(action.getClass()) + "/LOGIN#", "&LOGIN#"));	
		}
		// Check CDA mappings
		Map<String, String> mappings = ActionHelper.getCDAMappings(actions); 
		if (!mappings.isEmpty()) {
			for (String key : mappings.keySet()) {
				values.add(new KeyValueGroup<String>(actionPackName + ".PUB.ACTION." + key, mappings.get(key)));
			}
		}
		if (!values.isEmpty()) {
			vara.setValues(values);
		}
		return vara;
	}
	
	private VariableText getMetaDataVARA(IActionPack actionPack) {
		String actionPackName = toValidName(getActionPackName(actionPack));
		VariableText varaMetaData = new VariableText(actionPackName + ".PUB.VAR.METADATA");
		List<KeyValueGroup<String>> actionPackAttributes = new ArrayList<KeyValueGroup<String>>();
		String dependencies = actionPack.getDependencies();
		if (dependencies != null && !dependencies.isEmpty()) {
			if (!dependencies.contains("PCK.ITPA_SHARED")) {
				dependencies = "PCK.ITPA_SHARED ~> 1.2," + dependencies;
			}
		}
		else {
			dependencies = "PCK.ITPA_SHARED ~> 1.2";
		}
		actionPackAttributes.add(new KeyValueGroup<String>("Company", actionPack.getCompany()));
		actionPackAttributes.add(new KeyValueGroup<String>("Categories", actionPack.getCategory()));
		actionPackAttributes.add(new KeyValueGroup<String>("Dependencies", dependencies));	
		actionPackAttributes.add(new KeyValueGroup<String>("Description", actionPack.getDescription()));	
		actionPackAttributes.add(new KeyValueGroup<String>("Homepage", actionPack.getHomepage()));
		actionPackAttributes.add(new KeyValueGroup<String>("License", actionPack.getLicense()));
		actionPackAttributes.add(new KeyValueGroup<String>("Name", actionPackName));
		actionPackAttributes.add(new KeyValueGroup<String>("Package Format Version", actionPack.getPackageFormatVersion()));
		actionPackAttributes.add(new KeyValueGroup<String>("Title", actionPack.getTitle()));
		actionPackAttributes.add(new KeyValueGroup<String>("Version", actionPack.getVersion()));
		varaMetaData.setValues(actionPackAttributes);
		return varaMetaData;		
	}
	
	private String getActionPackName(IActionPack actionPack) {
		return toValidName(actionPack.getName() != null ? 
				actionPack.getName() : actionPack.getClass().getSimpleName());		
	}
	
	private Folder getInitialFolderStructure(IActionPack actionPack, String[] folderStructure) {
		Folder rootFolder = null;
		if (actionPack != null) {
			rootFolder = new Folder(toValidName(getActionPackName(actionPack)));
			rootFolder.setTitle(actionPack.getTitle());
			List<String> folders = new ArrayList<String>(Arrays.asList(folderStructure));
			Collections.sort(folders);
			for (String folder : folders) {
				String[] folderItems = folder.split("/");
				String parentFolderPath = "";
				if (folderItems.length > 0) {
					Folder newFolder = new Folder(toValidName(folderItems[folderItems.length - 1]));
					for (int idx = 0; idx < folderItems.length - 1; idx++) {
						parentFolderPath += folderItems[idx];
						if (idx < folderItems.length - 2) {
							parentFolderPath += "/";
						}
					}
					rootFolder = (Folder) FolderHelper.insertObject(rootFolder, parentFolderPath, newFolder);
				}	
			}
		}
		return rootFolder;
	}
	
	private String toValidName(String name) {
		if (name != null) {
			char[] characters = name.toCharArray();
			String validName = "";
			for (char character : characters) {
				if (Character.isLetterOrDigit(character) || character == '.' || character == '_') {
					validName += character;
				}
				if (Character.isWhitespace(character)) {
					validName += "_";
				}
			}
			return validName.toUpperCase();
		}
		return null;
	}
	
	private Document wrapDocumentForExport(Document document) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document newDocument = builder.newDocument();
			Element root = newDocument.createElement("uc-export");
			root.setAttribute("clientvers", CLIENTVERSION);
			newDocument.appendChild(root);

			Node copy = newDocument.importNode(document.getDocumentElement(), true);
			root.appendChild(copy);
			return newDocument;
		} 
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	private Folder addActionWorkflow(Folder rootFolder, String actionPackName, IStorage jarFile, IAction action) {
		
		String actionName = getActionName(action.getClass());
		List<IPromptSet> promptSets = new ArrayList<IPromptSet>();
		PromptSetsAndVariables promptsAndVariables = getPromptSetsAndVariables(actionPackName, (Class<? extends com.broadcom.apdk.api.IPromptSet>) action.getClass());
		
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE", new Folder(actionName));
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/" + actionName, new Folder("INTERNAL"));
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/" + actionName + "/INTERNAL", new Folder("INCLUDES"));
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/" + actionName + "/INTERNAL", new Folder("JOBS"));
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/" + actionName + "/INTERNAL", new Folder("PROMPTSETS"));
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/" + actionName + "/INTERNAL", new Folder("ROLLBACK"));
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/" + actionName + "/INTERNAL", new Folder("VARIABLES"));
		
		if (promptsAndVariables != null) {
			// Generate PromptSets
			List<PromptSetEntry> allPromptSets = promptsAndVariables.getPromptSets();
			if (!allPromptSets.isEmpty()) {
				for (PromptSetEntry promptSetEntry : allPromptSets) {
					IPromptSet promptSet = promptSetEntry.getPromptSet();
					LOGGER.info("Create object \"" + promptSet.getName() + "\"");
					promptSets.add(promptSet);			
					if (!promptSetEntry.isShared()) {
						rootFolder = (Folder) FolderHelper.insertObject(rootFolder, 
								"SOURCE/" + actionName + "/INTERNAL/PROMPTSETS", promptSet);	
					}
					else {
						rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED", new Folder("INCLUDES"));
						rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED", new Folder("JOBS"));
						rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED", new Folder("ROLLBACK"));
						rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED", new Folder("VARIABLES"));
						rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED/PROMPTSETS", promptSet);
					}
				}
			}
			// Generate Variables
			List<IVariable> allVariables = promptsAndVariables.getVariables();
			for (IVariable variable : allVariables) {
				LOGGER.info("Create object \"" + variable.getName() + "\"");
				rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED", new Folder("INCLUDES"));
				rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED", new Folder("JOBS"));
				rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED", new Folder("ROLLBACK"));
				rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED/VARIABLES", variable);
				rootFolder = (Folder) FolderHelper.insertObject(rootFolder, "SOURCE/SHARED", new Folder("PROMPTSETS"));
			}
		}
		
		// Create ITPA_SHARED PromptSet for overwriting the Agent information
		IPromptSet overwriteAgentPromptSet = getOverwriteAgentPromptSet();
		LOGGER.info("Create object \"" + overwriteAgentPromptSet.getName() + "\"");
		promptSets.add(overwriteAgentPromptSet);		
		
		// Create Jobs
		String jarFilename = getFilename(jarFile, "APJAR");
		JobWindows jobWin = (JobWindows) createActionJob(action, actionPackName, jarFilename, 
				actionPackName + ".PRV.JOB." + actionName + "@WINDOWS", JobWindows.class);
		LOGGER.info("Create object \"" + jobWin.getName() + "\"");
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, 
				"SOURCE/" + actionName + "/INTERNAL/JOBS", jobWin);
		
		JobUnix jobUnix = (JobUnix) createActionJob(action, actionPackName, jarFilename,
				actionPackName + ".PRV.JOB." + actionName + "@UNIX", JobUnix.class);
		LOGGER.info("Create object \"" + jobUnix.getName() + "\"");
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, 
				"SOURCE/" + actionName + "/INTERNAL/JOBS", jobUnix);
		
		// Create Workflow
		Workflow actionWorkflow = new Workflow(actionPackName + ".PUB.ACTION." + actionName);
		actionWorkflow.setTitle(getActionName(action.getClass()));
		actionWorkflow.setChildQueue("*OWN");
		actionWorkflow.setErrorFreeStatus("ANY_OK");
		actionWorkflow.setGenerateAtRuntime(true);
		actionWorkflow.setPromptSets(promptSets);
		actionWorkflow.setDocumentation(action.getDocumentation());
		 
		WorkflowStartTask start = new WorkflowStartTask(2);		
		List<WorkflowTaskPredecessor> predecessors = new ArrayList<WorkflowTaskPredecessor>();
		predecessors.add(new WorkflowTaskPredecessor(1, "ANY_OK", 1));
		WorkflowTask jobWIN = new WorkflowTask(2, jobWin.getName(),
				"JOBS", 1, 2, predecessors);
		jobWIN.setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.ABORT_TASK);
		WorkflowTask jobUNIX = new WorkflowTask(3, jobUnix.getName(),
				"JOBS", 3, 2, predecessors);
		jobUNIX.setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.ABORT_TASK);
		List<WorkflowTaskPredecessor> endPredecessors = new ArrayList<WorkflowTaskPredecessor>();
		endPredecessors.add(new WorkflowTaskPredecessor(1, "ANY_OK", 3));
		endPredecessors.add(new WorkflowTaskPredecessor(2, "ANY_OK", 2));
		WorkflowEndTask end = new WorkflowEndTask(4, 2, 3, endPredecessors);
		end.setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.ABORT_TASK);
		
		List<IWorkflowTask> tasks = new ArrayList<IWorkflowTask>();
		tasks.add(start);
		tasks.add(jobWIN);
		tasks.add(jobUNIX);
		tasks.add(end);
		actionWorkflow.setTasks(tasks);	
		
		// Inject initilized values of inputVariables to promptsets assigned to the workflow
		actionWorkflow.setPromptSets(getInitializedPromptSets(actionWorkflow, action));
		
		// Add Variables for defined output parameters
		Map<String, Field> outputParams = ActionHelper.getActionOutputParams(action);
		if (!outputParams.isEmpty()) {
			List<VariableEntry> variables = new ArrayList<VariableEntry>();
			for (String paramName : outputParams.keySet()) {
				Field field = outputParams.get(paramName);
				if (field != null) {
					String variableName = ActionHelper.getVariableNameFromParam(field, ActionOutputParam.class);
					variables.add(new VariableEntry(variableName, null));
				}
			}
			actionWorkflow.setVariables(variables);
		}
		
		LOGGER.info("Create object \"" + actionWorkflow.getName() + "\"");
		
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, 
				"SOURCE/" + actionName, actionWorkflow);
		
		// Adjust path according to the passed categories
		String pathToActionLink = "ACTIONS";
		if (action.getPath() != null && !action.getPath().isEmpty()) {
			String[] pathFragments = action.getPath().split("/");
			for (String pathFragment : pathFragments) {
				pathToActionLink += "/" + toValidName(pathFragment);
			}
		}
		rootFolder = (Folder) FolderHelper.insertObject(rootFolder, 
				pathToActionLink, new Link(actionWorkflow));
		
		return rootFolder;
	}
	
	private List<IPromptSet> getInitializedPromptSets(IWorkflow workflow, IAction action) {
		Map<String, Object[]> initValues = ActionHelper.getActionInputParamValues(action);
		List<IPromptSet> initializedPromptSets = new ArrayList<IPromptSet>();
		for (IPromptSet promptSet : workflow.getPromptSets()) {
			List<IPrompt<?>> initializedPrompts = new ArrayList<IPrompt<?>>();
			for (IPrompt<?> prompt : promptSet.getPrompts()) {
				if (initValues.containsKey(prompt.getVariableName())) {
					Object[] initValueArray = initValues.get(prompt.getVariableName());
					Object initValue = initValueArray[0];
					for (Method method : prompt.getClass().getDeclaredMethods()) {
						if (method.getParameterCount() == 1 && method.getName().equals("setValue")) {
							try {
								Class<?> paramType = null;
								Class<?>[] paramClasses = method.getParameterTypes();
								if (paramClasses.length == 1) {
									paramType = paramClasses[0];	
								}
								// If Prompt accepts String but value isn't a String then trigger a conversion
								if (!initValue.getClass().equals(Object.class) && 
										!initValue.getClass().equals(String.class) && 
										paramType.equals(String.class)) {
									method.invoke(prompt, initValueArray[1]);
								}
								else if (initValue.getClass().equals(paramType)) {
									method.invoke(prompt, initValue);
								}
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
				initializedPrompts.add(prompt);
			}
			promptSet.setPrompts(initializedPrompts);
			initializedPromptSets.add(promptSet);
		}		
		return initializedPromptSets;
	}
	
	private String getFilename(IStorage jarFile, String name) {
		if (jarFile != null && name != null && !name.isEmpty()) {
			for (StoredFile storedFile : jarFile.getStoredFiles()) {
				if (name.equals(storedFile.getName())) {
					return storedFile.getFilename();
				}
			}
		}
		return name;
	}
	
	private <T extends IJob> IJob createActionJob(IAction action, String actionPackName, String jarFilename,
			String actionName, Class<T> jobType) {
		if (IJob.class.isAssignableFrom(jobType)) {
			try {
				Class<?> jobClass = Class.forName(jobType.getName());
				Constructor<?> constructor = jobClass.getConstructor(String.class);
				IJob job = (IJob) constructor.newInstance(actionName);
				job.setPreScript(":INCLUDE PCK.ITPA_SHARED.PRV.INCLUDE.PREPARE_JOB");
				String arguments = String.join(" ", ActionHelper.getArguments(action, jobType));
				String checkCmd = ":INCLUDE PCK.ITPA_SHARED.PRV.INCLUDE.CHECK_CMDLINE_CMD@WINDOWS";
				String attachITPATool = "";
				if (ActionHelper.hasPasswordParameters(action)) {
					attachITPATool = ":ATTACH_RES \"PCK.ITPA_SHARED.PRV.STORE\", \"ITPATOOL.JAR\", C, N\n";
				} 		
				if (IJobUnix.class.isAssignableFrom(job.getClass())) {
					checkCmd = ":INCLUDE PCK.ITPA_SHARED.PRV.INCLUDE.CHECK_SHELL_CMD@UNIX";
				}
				job.setScript(
						":ATTACH_RES \"" +  actionPackName + ".PRV.STORE\", \"APJAR\", C, N\n" +
						attachITPATool +
						checkCmd + "\n" +
						":INCLUDE PCK.ITPA_SHARED.PRV.INCLUDE.CHANGE_DIRECTORY_TO_AGENT_BIN\n" +
						":JCL_CONCAT_CHAR \"?\"\n" + 
						"java -jar \"&$AGENT_RESOURCES_CLIENT#" + jarFilename + "\" -r " + action.getClass().getName() + 
						(!arguments.isEmpty() ? (" ?\n" + arguments + "\n") : "") +
						":JCL_CONCAT_CHAR\n" + 
						checkCmd);	
				Map<String, Field> outputParams = ActionHelper.getActionOutputParams(action);
				if (!outputParams.isEmpty()) {
					String postScript =
							":SET &INSIDE_OUTPUT_SECTION# = \"N\"\n" +
							":SET &HND#=PREP_PROCESS_REPORT(\"JOBS\",,\"REP\")\n" +
							":PROCESS &HND#\n" +
							":  SET &RET# = GET_PROCESS_LINE(&HND#)\n" +
							":  IF &INSIDE_OUTPUT_SECTION# EQ \"Y\"\n" +
							":    SET &POS# = STR_FIND(&RET#,\"=\")\n" +
							":    IF &POS# <> 0\n" +
							":      SET &POS# = SUB(&POS#,1)\n" +
							":      SET &PARAM_NAME# = SUBSTR(&RET#,1,&POS#)\n" +
							":      SET &PARAM_NAME# = STR_TRIM(&PARAM_NAME#)\n" +
							":      SET &POS# = ADD(&POS#,2)\n" +
							":      SET &PARAM_VALUE# = STR_CUT(&RET#,&POS#)\n" +
							":      SET &PARAM_VALUE# = STR_TRIM(&PARAM_VALUE#)\n" +
							":      SET &VALIDSTART# = STR_STARTS_WITH(&PARAM_VALUE#, \"\"\"\")\n" +
							":      IF &VALIDSTART# EQ \"Y\"\n" +
							":        SET &VALIDEND# = STR_ENDS_WITH(&PARAM_VALUE#, \"\"\"\")\n" +
							":        IF &VALIDEND# EQ \"Y\"\n" +
							":          SET &LASTPOS# = STR_LENGTH(&PARAM_VALUE#)\n" +
							":          SET &LASTPOS# = SUB(&LASTPOS#,2)\n" +
							":          SET &PARAM_VALUE# = SUBSTR(&PARAM_VALUE#,2,&LASTPOS#)\n" +
							":        ENDIF\n" +
							":      ENDIF\n";
					for (String paramName : outputParams.keySet()) {
						Field field = outputParams.get(paramName);
						if (field != null) {
							String variableName = ActionHelper.getVariableNameFromParam(field, ActionOutputParam.class);
							String pureName = variableName;
							if (pureName != null && !pureName.isEmpty()) {
								if (pureName.startsWith("&")) {
									pureName = pureName.substring(1);
								}
								if (pureName.endsWith("#")) {
									pureName = pureName.substring(0, pureName.length() - 1);	
								}
							}
							postScript +=
									":      IF &PARAM_NAME# EQ \"" + pureName + "\"\n" +
									":        PSET " + variableName + " = &PARAM_VALUE#\n" +
									":      ENDIF\n";
						}
					}
					postScript +=
							":    ENDIF\n" +
							":  ENDIF\n" +
							":  IF &INSIDE_OUTPUT_SECTION# EQ \"N\"\n" +
							":    SET &INSIDE_OUTPUT_SECTION# = STR_STARTS_WITH(&RET#, \"ACTION OUTPUT\")\n" +
							":  ENDIF\n" +
							":ENDPROCESS\n" +
							":CLOSE_PROCESS &HND#\n";
					job.setPostScript(postScript);
				}
				return job;
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private PromptSet getOverwriteAgentPromptSet() {
		PromptSet overwriteAgentPromptSet = new PromptSet("PCK.ITPA_SHARED.PRV.PROMPTSET.OVERWRITE_AGENT");
		overwriteAgentPromptSet.setTitle("Overwrite Agent");
		
		IPrompt<String> overwriteAgentCombo = new PromptCombo(
				"&AGENT#", 
				"Overwrite Agent",
				"Overwrites the default agent",
				"STATIC",
				"PCK.ITPA_SHARED.PRV.VARA.AGENTS");
		overwriteAgentCombo.setValue("&AGENT#");
		IPrompt<String> overwriteAgentLoginCombo = new PromptCombo(
				"&LOGIN#",
				"Overwrite Login",
				"Overwrites the default login",
				"STATIC",
				"PCK.ITPA_SHARED.PRV.VARA.LOGINS");
		overwriteAgentLoginCombo.setValue("&LOGIN#");
		
		List<IPrompt<?>> prompts = new ArrayList<IPrompt<?>>();
		prompts.add(overwriteAgentCombo);
		prompts.add(overwriteAgentLoginCombo);
		overwriteAgentPromptSet.setPrompts(prompts, "Overwrite Settings");
		return overwriteAgentPromptSet;
	}
}
