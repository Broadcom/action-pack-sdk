package com.broadcom.apdk.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "uc-export")
public class ExportPackage {
	
	private FolderStructure folderStructure;
	private List<IAutomicObject> objects;
	private String clientvers;
	
	public ExportPackage() {}
	
	public ExportPackage(FolderStructure folderStructure, List<IAutomicObject> objects) {
		this.folderStructure = folderStructure;
		this.objects = objects;
	}
	
	@XmlElement(name = "FolderStruct")
	public FolderStructure getFolderStructure() {
		return folderStructure;
	}

	public void setFolderStructure(FolderStructure folderStructure) {
		this.folderStructure = folderStructure;
	}

	@XmlAnyElement(lax = true)
	@XmlJavaTypeAdapter(AutomicObjectAdapter.class)
	public List<IAutomicObject> getObjects() {
		return objects;
	}

	public void setObjects(List<IAutomicObject> objects) {
		this.objects = objects;
	}

	@XmlAttribute
	public String getClientvers() {
		return clientvers;
	}

	public void setClientvers(String clientvers) {
		this.clientvers = clientvers;
	}

}
