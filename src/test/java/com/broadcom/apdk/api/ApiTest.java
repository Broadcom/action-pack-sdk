package com.broadcom.apdk.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

abstract class ApiTest {
	
	enum Output {
		INPUT_PARAMS,
		OUTPUT_PARAMS,
		OUTPUT
	}
	
	void extractPackage(File rootFolder) {
		
	}
	
	List<File> getExtractedFiles(File rootFolder) {
		return null;
	}
	
	void compile(List<String> sourceFileNames, File rootFolder, boolean clean) {
		if (sourceFileNames != null && !sourceFileNames.isEmpty() && 
				rootFolder != null && rootFolder.isDirectory()) {
			if (clean) {
				clean(rootFolder);
			}
			try {				
				ResourceCompiler.compile(sourceFileNames, rootFolder);
				// Load compiled classes 
				URLClassLoader classLoader = new URLClassLoader(
				        new URL[] {rootFolder.toURI().toURL()});
				Thread.currentThread().setContextClassLoader(classLoader);//((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs()
			} 
			catch (Exception e) {} 			
		}
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
	
	String getOutput(ByteArrayOutputStream output) {
		List<String> lines = getOutput(output, Output.OUTPUT);
		if (lines != null && !lines.isEmpty()) {
			return String.join("\n", 
					lines.toArray(new String[lines.size()]));			
		}
		return null;
	}
	
	List<String> getInputParams(ByteArrayOutputStream output) {
		List<String> lines = getOutput(output, Output.INPUT_PARAMS);
		if (lines != null && !lines.isEmpty()) {
			return lines;		
		}
		return null;		
	}
	
	List<String> getOutputParams(ByteArrayOutputStream output) {
		List<String> lines = getOutput(output, Output.OUTPUT_PARAMS);
		if (lines != null && !lines.isEmpty()) {
			return lines;		
		}
		return null;		
	}
	
	private List<String> getOutput(ByteArrayOutputStream output, Output outputType) {
		if (output != null && outputType != null) {
			String origOutput = output.toString().replace("\r\n", "\n");
			String[] origLines = origOutput.split("\n");
			List<String> lines = new ArrayList<String>(); 
			String separator = "---------------------------------------------------------------------";
			String prevLine = null;
			boolean resetBuffer = false;
			boolean insideInputsSection = false;
			boolean insideActionOutput = false;
			boolean insideOutputsSection = false;
			for (String line : origLines) {
				if (resetBuffer) {
					lines.clear();	
					resetBuffer = false;
				}
				if (line.equals(separator) && insideActionOutput) {
					insideActionOutput = false;
					if (Output.OUTPUT.equals(outputType)) {
						return lines;
					}
				}
				if (line.equals(separator) && insideInputsSection) {
					resetBuffer = true;
					insideInputsSection = false;	
					insideActionOutput = true;
					if (Output.INPUT_PARAMS.equals(outputType)) {
						return lines;
					}				
				}
				if (line.equals(separator) && insideOutputsSection) {
					resetBuffer = true;
					insideOutputsSection = false;	
					if (Output.OUTPUT_PARAMS.equals(outputType)) {
						return lines;
					}				
				}
				if (line.equals(separator) && prevLine != null && 
						prevLine.equals("ACTION INPUT")) {
					resetBuffer = true;
					insideInputsSection = true;
				}
				if (line.equals(separator) && prevLine != null && 
						prevLine.equals("ACTION OUTPUT")) {
					resetBuffer = true;
					insideOutputsSection = true;
				}
				lines.add(line.trim());
				prevLine = line;
			}
			if (Output.OUTPUT.equals(outputType) && !lines.isEmpty()) {
				return lines;
			}
		}
		return null;
	}
	
}
