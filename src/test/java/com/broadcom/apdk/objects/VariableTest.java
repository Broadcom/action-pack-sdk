package com.broadcom.apdk.objects;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.xmlunit.matchers.CompareMatcher;

public class VariableTest {
	
	@Test
	public void testVARA_Text() {
		String expectedXML = XmlHelper.getXMLFragmentAsString("VARA.STATIC.TEST.PLAIN.xml", "uc-export/VARA");
		String testXML = XmlHelper.getXMLAsString(new VariableText("VARA.STATIC.TEST.PLAIN"));	
		assertThat(testXML, CompareMatcher.isIdenticalTo(expectedXML));
	} 

}
