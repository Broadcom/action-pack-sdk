package com.broadcom.apdk.objects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

public class StoredFile {
	
	private String name;
	private String version;
	private FileType fileType = FileType.BINARY;
	private String filename;
	private String checksum;
	private Long size; 
	private OperatingSystem os = OperatingSystem.ALL;
	private Platform platform = Platform.ALL;
	private Architecture architecture = Architecture.ALL;
	private String storageName;
	
	@SuppressWarnings("unused")
	private StoredFile() {}
	
	public StoredFile(String name, Path filepath, FileType fileType, String version) {
		File file = new File(filepath.toString());
		this.checksum = getChecksum(file);
		this.size = file.length();
		this.filename = filepath.getFileName().toString();
		this.name = name;
		this.fileType = fileType;
		this.version = version;
	}

	@XmlAttribute(name = "name")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "version")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@XmlAttribute(name = "fileType")
	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	@XmlAttribute(name = "filename")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@XmlAttribute(name = "checksum")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	@XmlAttribute(name = "sw")
	public OperatingSystem getOs() {
		return os;
	}

	public void setOs(OperatingSystem os) {
		this.os = os;
	}

	@XmlAttribute(name = "platform")
	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	@XmlAttribute(name = "hw")
	public Architecture getArchitecture() {
		return architecture;
	}

	public void setArchitecture(Architecture architecture) {
		this.architecture = architecture;
	}

	@XmlAttribute(name = "size")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
	// Non-Public API
	
	void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	
	@XmlTransient
	String getStorageName() {
		return storageName;
	}
	
	@XmlAttribute(name = "id")
	String getId() {
		return getName() + "-" + getStoredFileProperties();
	}
	
	@XmlAttribute(name = "type")
	Integer getType() {
		return 3;
	}	
	
	@XmlAttribute(name = "resname")
	String getResname() {
		return getStorageName() + "-" + getName() + "-" + getStoredFileProperties();
	}
	
	private String getStoredFileProperties() {
		String osStr = os.toString();
		String platformStr = platform.toString();
		String architectureStr =  architecture.toString();
		return osStr + "-" + platformStr + "-" + architectureStr;
	}
	
	private String getChecksum(File file) {	
		try {
			HashCode checksum = Files.asByteSource(file).hash(Hashing.sha256());
			return checksum.toString().toUpperCase();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
