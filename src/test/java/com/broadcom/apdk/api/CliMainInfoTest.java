package com.broadcom.apdk.api;

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

public class CliMainInfoTest extends ApiTest {
	
	private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static PrintStream originalOut = System.out;
	
	@TempDir
	static File tempDir;
	
	@BeforeAll
	public static void redirectStreams() {
	    System.setOut(new PrintStream(outContent, true));
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
	public void testInfoOneActionPack() {	
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("TestActionPack1.java");
		compile(sourceFileNames, tempDir, true);
		
		try {
			CLI.main(new String[] {"-i"});
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals("[ActionPack] com.broadcom.TestActionPack1 (PCK.CUSTOM_APDK_TEST1)\n", 
				outContent.toString().replace("\r\n", "\n"));
	}

	@Test
	public void testInfoMultipleActionPacks() {	
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("TestActionPack1.java");
		sourceFileNames.add("TestActionPack2.java");
		compile(sourceFileNames, tempDir, true);
		
		try {
			CLI.main(new String[] {"-i"});
		} 
		catch (ExportException e) {
			e.printStackTrace();
		}

		assertEquals("[ActionPack] com.broadcom.TestActionPack1 (PCK.CUSTOM_APDK_TEST1)\n" +
				"[ActionPack] com.broadcom.TestActionPack2 (PCK.CUSTOM_APDK_TEST2)\n", 
				outContent.toString().replace("\r\n", "\n"));
	}
	
	@Test
	public void testInfoActionPacksWithAction() {	
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("TestActionPack1.java");
		sourceFileNames.add("TestAction1.java");
		compile(sourceFileNames, tempDir, true);
		
		try {
			CLI.main(new String[] {"-i"});
		} 
		catch (ExportException e) {
			e.printStackTrace();
		}

		assertEquals("[ActionPack] com.broadcom.TestActionPack1 (PCK.CUSTOM_APDK_TEST1)\n" +
				"[Action]     com.broadcom.TestAction1 (ACTION1)\n", 
				outContent.toString().replace("\r\n", "\n"));
	}

	@Test
	public void testInfoMultipleActionPacksWithActions() {		
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("TestActionPack1.java");
		sourceFileNames.add("TestActionPack2.java");
		sourceFileNames.add("TestAction2.java");
		sourceFileNames.add("TestAction3.java");
		compile(sourceFileNames, tempDir, true);
		
		try {
			CLI.main(new String[] {"-i"});
		} 
		catch (ExportException e) {
			e.printStackTrace();
		}
		
		assertEquals("[ActionPack] com.broadcom.TestActionPack1 (PCK.CUSTOM_APDK_TEST1)\n" +
				"[ActionPack] com.broadcom.TestActionPack2 (PCK.CUSTOM_APDK_TEST2)\n" +
				"[Action]     com.broadcom.TestAction2 (ACTION2)\n" +
				"[Action]     com.broadcom.TestAction3 (ACTION3)\n", 
				outContent.toString().replace("\r\n", "\n"));
	}

	@Test
	public void testInfoMultipleActionPacksWithActionsSpecific() {	
		List<String> sourceFileNames = new ArrayList<String>();
		sourceFileNames.add("TestActionPack1.java");
		sourceFileNames.add("TestActionPack2.java");
		sourceFileNames.add("TestAction2.java");
		sourceFileNames.add("TestAction3.java");
		compile(sourceFileNames, tempDir, true);
		
		try {
			CLI.main(new String[] {"-i com.broadcom.TestActionPack1"});
		} 
		catch (ExportException e) {
			e.printStackTrace();
		}
		
		assertEquals("[ActionPack] com.broadcom.TestActionPack1 (PCK.CUSTOM_APDK_TEST1)\n" +
				"[Action]     com.broadcom.TestAction2 (ACTION2)\n", 
				outContent.toString().replace("\r\n", "\n"));
	}

}
