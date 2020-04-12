package com.broadcom.apdk.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.broadcom.apdk.cli.CLI;
import com.broadcom.apdk.cli.ExportException;

public class CliMainRunTest extends ApiTest {
	
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
	public void testRunAction() {	
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("TestAction1.java");
		compile(sourceFileNames, tempDir, true);
		
		try {
			CLI.main(new String[] {"-r com.broadcom.TestAction1"});
		} 
		catch (ExportException e) {
			e.printStackTrace();
		}

		assertEquals("com.broadcom.TestAction1\n", 
				outContent.toString().replace("\r\n", "\n"));
	}
	
	@Test
	public void testRunActionWithInputParams() {	
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("TestAction4.java");
		compile(sourceFileNames, tempDir, true);
		
		try {
			CLI.main(new String[] {"-r com.broadcom.TestAction4", 
					"shortPrimitive=1", 
					"shortWrapper=2",
					"bytePrimitive=3",
					"byteWrapper=4",
					"charPrimitive=t",
					"charWrapper=f", 
					"intPrimitive=777", 
					"intWrapper=87",
					"floatPrimitive=4.3",
					"floatWrapper=4078.387",
					"longPrimitive=686",
					"longWrapper=6534",
					"doublePrimitive=87.87",
					"doubleWrapper=103.43",
					"booleanPrimitive=false",
					"booleanWrapper=true",
					"string=Description",
					"date=1980-03-04",
					"datetime=1980-03-04 14:08",
					"time=14:08"});
		} 
		catch (ExportException e) {
			e.printStackTrace();
		}

		assertEquals("date=1980-03-04\n" +
				"datetime=1980-03-04T14:08\n" +
				"time=14:08\n" +
				"string=\"Description\"\n" +
				"floatPrimitive=4.3\n" +
				"floatWrapper=4078.387\n" +
				"bytePrimitive=3\n" +
				"byteWrapper=4\n" +
				"intPrimitive=777\n" +
				"intWrapper=87\n" +
				"charPrimitive=t\n" +
				"charWrapper=f\n" +
				"longPrimitive=686\n" +
				"longWrapper=6534\n" +
				"booleanPrimitive=false\n" +
				"booleanWrapper=true\n" +
				"shortPrimitive=1\n" +
				"shortWrapper=2\n" +
				"doublePrimitive=87.87\n" +
				"doubleWrapper=103.43",
				getOutput(outContent));
	}
	
	@Test
	public void testRunActionWithOutputParams() {	
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("TestAction5.java");
		compile(sourceFileNames, tempDir, true);
		
		try {
			CLI.main(new String[] {"-r com.broadcom.TestAction5"});
		} 
		catch (ExportException e) {
			e.printStackTrace();
		}
		
		List<String> expectedLines = new ArrayList<String>();
		expectedLines.add("DATE=\"1980-03-04\"");
		expectedLines.add("DATETIME=\"1980-03-04 14:08\"");
		expectedLines.add("TIME=\"14:08\"");
		expectedLines.add("STRING=\"Description\"");
		expectedLines.add("FLOATPRIMITIVE=\"4.3\"");
		expectedLines.add("FLOATWRAPPER=\"4078.387\"");
		expectedLines.add("BYTEPRIMITIVE=\"3\"");
		expectedLines.add("BYTEWRAPPER=\"4\"");
		expectedLines.add("INTPRIMITIVE=\"777\"");
		expectedLines.add("INTWRAPPER=\"87\"");
		expectedLines.add("CHARPRIMITIVE=\"t\"");
		expectedLines.add("CHARWRAPPER=\"f\"");
		expectedLines.add("LONGPRIMITIVE=\"686\"");
		expectedLines.add("LONGWRAPPER=\"6534\"");
		expectedLines.add("BOOLEANPRIMITIVE=\"false\"");
		expectedLines.add("BOOLEANWRAPPER=\"true\"");
		expectedLines.add("SHORTPRIMITIVE=\"1\"");				
		expectedLines.add("SHORTWRAPPER=\"2\"");
		expectedLines.add("DOUBLEPRIMITIVE=\"87.87\"");
		expectedLines.add("DOUBLEWRAPPER=\"103.43\"");
		
		assertThat(expectedLines, containsInAnyOrder(getOutputParams(outContent).toArray()));
	}
	 
	@Test
	public void testRunActionWithCustomTypeParams() {	
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("Employee.java");
		sourceFileNames.add("EmployeeAdapter.java");
		sourceFileNames.add("TestAction6.java");
		compile(sourceFileNames, tempDir, true);
		
		try {
			CLI.main(new String[] {"-r com.broadcom.TestAction6", 
					"candidate=\"Michael\",\"Grath\""});
		} 
		catch (ExportException e) {
			e.printStackTrace();
		}
		
		List<String> expectedLines = new ArrayList<String>();
		expectedLines.add("EMPLOYEE=\"\"Grath\",\"Michael\"\"");
		
		assertThat(expectedLines, containsInAnyOrder(getOutputParams(outContent).toArray()));
	}
}
