package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "FolderStruct")
public class FolderStructure {
	
	private Boolean includeExternalObjects;
	private Folder rootFolder;
	
	public FolderStructure() {
		setIncludeExternalObjects(false);
	}
	
	public FolderStructure(Folder rootFolder) {
		this.includeExternalObjects = false;
		this.rootFolder = rootFolder;
	}

	@XmlElement
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getIncludeExternalObjects() {
		return includeExternalObjects;
	}

	public void setIncludeExternalObjects(Boolean includeExternalObjects) {
		this.includeExternalObjects = includeExternalObjects;
	}
	
	@XmlElement(name = "FOLD")
	public Folder getRootFolder() {
		return rootFolder;
	}

	public void setRootFolder(Folder rootFolder) {
		this.rootFolder = rootFolder;
	}

}
