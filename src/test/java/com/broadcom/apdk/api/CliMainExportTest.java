package com.broadcom.apdk.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.xmlunit.matchers.CompareMatcher;

import com.broadcom.apdk.objects.XmlHelper;

public class CliMainExportTest extends ExportTest {
	
	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static PrintStream originalOut = System.out;
	
	@TempDir
	static File tempDir;
	
	@BeforeAll
	public static void redirectStreams() {
	    System.setOut(new PrintStream(outContent));
	}
	
	@AfterAll
	public static void restoreStreams() {
	    System.setOut(originalOut);
	}
	
	@AfterEach
	public void resetStreams() {
		outContent.reset();
	}
	
	@Test
	public void testExportEmptyActionPack() {	
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("TestActionPack1.java");
		sourceFileNames.add("pom.xml");
		
		try {
			compileWithFramework(sourceFileNames, tempDir);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		File targetFolder = getTargetFolder(tempDir);
		assumeTrue(targetFolder.exists(), "Target folder for binaries does not exist");

		File jarFile = getJarFile(tempDir);
		assumeTrue(jarFile.exists(), "jar file does not exist");
		
		File zipFile = getZipFile(tempDir);
		assumeTrue(zipFile.exists(), "zip file does not exist");
		
		List<String> expectedFiles = new ArrayList<String>();
		expectedFiles.add("PCK.CUSTOM_APDK_TEST1.PUB.LICENSES.xml");
		expectedFiles.add("PCK.CUSTOM_APDK_TEST1.PUB.VAR.METADATA.xml");
		expectedFiles.add("CONTENT.xml");
		expectedFiles.add("PCK.CUSTOM_APDK_TEST1.PRV.STORE-APJAR-ALL-ALL-ALL");
		expectedFiles.add("PCK.CUSTOM_APDK_TEST1.PUB.DOC.xml");
		Map<String, File> extractedFiles = getFilesFromZip(zipFile, tempDir);	
		assertThat(expectedFiles, containsInAnyOrder(extractedFiles.keySet().toArray()));
		
		String testVaraXML = XmlHelper.getXMLFragmentAsString(
				extractedFiles.get("PCK.CUSTOM_APDK_TEST1.PUB.VAR.METADATA.xml"), 
				"uc-export/VARA");
		String expectedVaraXML = XmlHelper.getXMLFragmentAsString(
				"PCK.CUSTOM_APDK_TEST1.PUB.VAR.METADATA_01.xml", "uc-export/VARA");
		assertThat(testVaraXML, CompareMatcher.isIdenticalTo(expectedVaraXML));
		
		checkContentXML("CONTENT_01.xml", extractedFiles.get("CONTENT.xml"));

	}
	
	private void checkContentXML(String expectedXMLfilename, File actualContentXML) {
		String testContentXML = XmlHelper.getXMLFragmentAsString(
				actualContentXML, "uc-export");
		String actualSize = getSize(XmlHelper.getXMLFragmentAsString(
				actualContentXML, "uc-export/STORE/STORE/resourceList"));
		String actualClientVersion = getClientVersion(testContentXML);
		String actualChecksum = getChecksum(testContentXML);
		String expectedContentXML = XmlHelper.getXMLFragmentAsString(
				expectedXMLfilename, "uc-export");
		String expectedSize = getSize(XmlHelper.getXMLFragmentAsString(
				expectedXMLfilename, "uc-export/STORE/STORE/resourceList"));
		String expectedClientVersion = getClientVersion(expectedContentXML);
		String expectedChecksum = getChecksum(expectedContentXML);
		expectedContentXML = expectedContentXML.replace(expectedClientVersion, actualClientVersion);
		expectedContentXML = expectedContentXML.replace(expectedChecksum, actualChecksum);
		expectedContentXML = expectedContentXML.replace(expectedSize, actualSize);
		assertThat(testContentXML, CompareMatcher.isIdenticalTo(expectedContentXML));		
	}

}
