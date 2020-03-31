package com.broadcom.apdk.objects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

public class FileHelper {
	
	static String getChecksum(File file) throws IOException {	
		HashCode checksum = Files.asByteSource(file).hash(Hashing.sha256());
		return checksum.toString().toUpperCase();
	}

	static Path getResource(String filename) {
		ClassLoader classLoader = FileHelper.class.getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		return Paths.get(file.getAbsolutePath());
	}
}
