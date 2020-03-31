package com.broadcom.apdk.api;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.google.common.io.Files;

public class ResourceCompiler {
	
	public static void compile(List<String> sourceFileNames, File directory) throws Exception {
		if (sourceFileNames != null && !sourceFileNames.isEmpty() && 
				directory != null && directory.isDirectory()) {		
		    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		    DiagnosticCollector<JavaFileObject> diagnostics = 
		    		new DiagnosticCollector<JavaFileObject>();
		    StandardJavaFileManager fileManager = compiler.getStandardFileManager(
		    		diagnostics, Locale.getDefault(), null);
			
			// Copy sources from resource directory to the specified directory
			List<File> files = copySources(sourceFileNames, directory);
			List<JavaFileObject> javaFiles = new ArrayList<JavaFileObject>();
			for (File file : files) {
				javaFiles.add(readJavaObject(file, fileManager));
			}
			
			// Check if there are actually sourced to compile
			if (!javaFiles.isEmpty()) {
			    String[] compileOptions = new String[]{"-d", directory.getAbsolutePath()} ;
			    Iterable<String> compilationOptions = Arrays.asList(compileOptions);

			    CompilationTask compilerTask = compiler.getTask(null, fileManager, 
			    		diagnostics, compilationOptions, null, javaFiles) ;

			    if (!compilerTask.call()) {
			        for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
			            System.err.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic);
			        }
			        throw new Exception("Could not compile project");
			    }
			}	
			
			fileManager.close();
		}
	}
	
	private static JavaFileObject readJavaObject(File file, StandardJavaFileManager fileManager) {
	    Iterable<? extends JavaFileObject> javaFileObjects = fileManager.getJavaFileObjects(file);
	    Iterator<? extends JavaFileObject> it = javaFileObjects.iterator();
	    if (it.hasNext()) {
	        return it.next();
	    }
	    throw new RuntimeException("Could not load " + file.getAbsolutePath() + " java file object");
	}

	private static List<File> copySources(List<String> sourceFileNames, File rootFolder) {
		List<File> files = new ArrayList<File>();
		if (sourceFileNames != null && !sourceFileNames.isEmpty() && 
				rootFolder != null && rootFolder.isDirectory()) {
			for (String filename : sourceFileNames) {
				File originalFile = new File(ApiTest.class.getClassLoader().getResource(filename).getFile());
				if (originalFile.getName().toLowerCase().endsWith(".java")) {
					File file = getDestFile(originalFile, rootFolder);
					if (file != null) {
						try {
							Files.createParentDirs(file);
							Files.copy(originalFile, file);
							files.add(file);
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return files;
	}
	
	private static File getDestFile(File sourceFile, File rootFolder) {
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
						return new File(rootFolder.getPath() + separator + 
										packagePath + separator + sourceFile.getName());
					}
				}
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
