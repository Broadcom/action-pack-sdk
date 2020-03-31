package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "STORE")
@XmlType (propOrder={
		"title", "archiveKey1", "archiveKey2", "type", "OHSubType", 
		"hostType", "storedFiles", "noStoredFiles", "documentation"})
public class Storage extends NonExecutableAutomicObject implements IStorage {
	
	private List<StoredFile> storedFiles;
	
	public Storage() {	
		super();
	}

	public Storage(String name) {
		super(name);
	}

	public void setStoredFiles(List<StoredFile> storedFiles) {
		List<StoredFile> storedFileList = new ArrayList<StoredFile>();
    	if (storedFiles != null) {
    		for (StoredFile storedFile : storedFiles) {
    			storedFile.setStorageName(getName());
    			storedFileList.add(storedFile);
    		}
    		this.storedFiles = storedFileList;
    	}
    	else {
    		this.storedFiles = storedFiles;
    	}
	}
	
    @XmlPath("STORE/resourceList/row")
	@XmlElement(name = "row")	
	public List<StoredFile> getStoredFiles() {
		return storedFiles;
	}
    
	// Non-Public API
    
	// Hack to ensure that an empty <Variables> element will be marshalled
	@XmlPath("STORE/resourceList/text()")
	String getNoStoredFiles() {
		if (this.storedFiles == null) {
			return "";
		}
		return null;
	}
	
	@XmlPath("STORE/HostType/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getHostType() {
		return null;
	}

}
