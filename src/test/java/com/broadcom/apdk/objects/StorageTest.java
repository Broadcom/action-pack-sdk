package com.broadcom.apdk.objects;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.xmlunit.matchers.CompareMatcher;

public class StorageTest {
	
	@Test
	public void testSTORE() {
		String expectedXML = XmlHelper.getXMLFragmentAsString("STORE.TEST.PLAIN.xml", "uc-export/STORE");
		String testXML = XmlHelper.getXMLAsString(new Storage("STORE.TEST.PLAIN"));	
		assertThat(testXML, CompareMatcher.isIdenticalTo(expectedXML));
	} 
	
	@Test
	public void testSTORE_Content() {		
		Storage storage = new Storage("STORE.TEST.AUTOMIC_PNG");
		List<StoredFile> storedFiles = new ArrayList<StoredFile>();
		storedFiles.add(new StoredFile(
				"AUTOMIC",
				FileHelper.getResource("automic.png"),
				FileType.BINARY, 
				null));
		storage.setStoredFiles(storedFiles);
				
		String expectedXML = XmlHelper.getXMLFragmentAsString("STORE.TEST.AUTOMIC_PNG.xml", "uc-export/STORE");
		String testXML = XmlHelper.getXMLAsString(storage);	
				
		assertThat(testXML, CompareMatcher.isIdenticalTo(expectedXML));
	} 

}
