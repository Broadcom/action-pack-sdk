package com.broadcom.apdk.objects;

import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xmlunit.matchers.CompareMatcher;

public class PromptSetTest {
	
	private static String expectedXML;
	private static String testXML;
	
	@BeforeAll
	private static void init() {
		PromptSet promptSet = new PromptSet("PRPT.TEST.PROMPTS");
		List<IPrompt<?>> prompts = new ArrayList<IPrompt<?>>();
		
		prompts.add(new PromptText(
				"&TEXT1#",
				"Text Field",	
				null,
				"STATIC",
				"UC_DATATYPE_STRING",
				"my default value"));
		
		prompts.add(new PromptCombo(
				"&COMBOBOX1#",
				"Combobox",
				null,
				"STATIC",
				"PCK_DB.PRV.INSTALLED_PACKAGES"));
		
		prompts.add(new PromptDate(
				"&DATE#",
				"Date",
				null,
				LocalDate.of(2020, 3, 4)));
		
		prompts.add(new PromptDateTime(
				"&DATETIME#",
				"Time/Date",
				null,
				LocalDateTime.of(2020, 3, 4, 14, 8)));
		
		prompts.add(new PromptTime(
				"&TIME#",
				"Time",
				null,
				LocalTime.of(14, 8)));

		promptSet.setPrompts(prompts);
		
		expectedXML = XmlHelper.getXMLFragmentAsString("PRPT.TEST.PROMPTS.xml", "uc-export/PRPT");
		testXML = XmlHelper.getXMLAsString(promptSet);		
		
		// Remove attributes 'height', 'width', 'top' and 'left'
		expectedXML = expectedXML.replaceAll("( height=\"| width=\"| top=\"| left=\")[0-9]*\"", "");
		testXML = testXML.replaceAll("( height=\"| width=\"| top=\"| left=\")[0-9]*\"", "");
	}
	
	@Test
	public void testPRPT() {
		String expectedXML = XmlHelper.getXMLFragmentAsString("PRPT.TEST.PLAIN.xml", "uc-export/PRPT");
		String testXML = XmlHelper.getXMLAsString(new PromptSet("PRPT.TEST.PLAIN"));			
		assertThat(testXML, CompareMatcher.isIdenticalTo(expectedXML));
	} 

	@Test
	public void testPRPT_Dialog() {
		// Check XML in CDATA section of element <XUIEDITOR>
		String expectedDialogXML = getSubstring(expectedXML, "<dialog.*?</dialog>");
		String testDialogXML     = getSubstring(testXML, "<dialog.*?</dialog>");
		
		// Remove values of "initvalue" property entry as this may not be consistent
		expectedDialogXML = expectedDialogXML.replaceAll("\\<entry name=\"initvalue\"\\>[\\w\\s:-]*\\<\\/entry\\>", 
				"<entry name=\"initvalue\"/\\>");
		testDialogXML = testDialogXML.replaceAll("\\<entry name=\"initvalue\"\\>[\\w\\s:-]*\\<\\/entry\\>", 
				"<entry name=\"initvalue\"/\\>");
		
		// Remove values of "doutputformat" property entry as this may not be consistent
		expectedDialogXML = expectedDialogXML.replaceAll("\\<entry name=\"doutputformat\"\\>[\\w\\s:-]*\\<\\/entry\\>", 
				"<entry name=\"doutputformat\"/\\>");
		testDialogXML = testDialogXML.replaceAll("\\<entry name=\"doutputformat\"\\>[\\w\\s:-]*\\<\\/entry\\>", 
				"<entry name=\"doutputformat\"/\\>");
		
		// Remove values of "tsoutputformat" property entry as this may not be consistent
		expectedDialogXML = expectedDialogXML.replaceAll("\\<entry name=\"tsoutputformat\"\\>[\\w\\s:-]*\\<\\/entry\\>", 
				"<entry name=\"tsoutputformat\"/\\>");
		testDialogXML = testDialogXML.replaceAll("\\<entry name=\"tsoutputformat\"\\>[\\w\\s:-]*\\<\\/entry\\>", 
				"<entry name=\"tsoutputformat\"/\\>");
		
		assertThat(testDialogXML, CompareMatcher.isIdenticalTo(expectedDialogXML));
	}
	
	@Test
	public void testPRPT_Data() {	
		// Check XML in CDATA section of element <DATAEDITOR>
		String expectedDataXML = getSubstring(expectedXML, "<PRPTS.*?</PRPTS>");
		String testDataXML     = getSubstring(testXML, "<PRPTS.*?</PRPTS>");
		assertThat(testDataXML, CompareMatcher.isIdenticalTo(expectedDataXML));		
	}	
	
	@Test
	public void testPRPT_Skeleton() {	
		// Check XML skeleton without CDATA sections
		String expectedSkeletonXML = expectedXML.replaceAll("<!\\[CDATA\\[(.*?)]]>", "");
		String testSkeletonXML = testXML.replaceAll("<!\\[CDATA\\[(.*?)]]>", "");
		assertThat(testSkeletonXML, CompareMatcher.isIdenticalTo(expectedSkeletonXML));
	} 
	
	private String getSubstring(String data, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		if (matcher.find())	{
			return matcher.group(0);
		}
		return null;
	}
}
