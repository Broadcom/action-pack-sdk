package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

class PromptSetDataEntry {
	
	private String defaultValue;
	private String promptName;
	private String variableName;
	
	PromptSetDataEntry() {}
	
	PromptSetDataEntry(String variableName, String defaultValue) {
		this.setVariableName(variableName);
		if (variableName != null) {
			if (variableName.startsWith("&")) {
				this.promptName = variableName.substring(1);	
			}
			else {
				this.promptName = variableName;
			}
		}
		this.setDefaultValue(defaultValue);
	}
	
	@XmlTransient
	String getVariableName() {
		return variableName;
	}

	void setVariableName(String variableName) {
		this.variableName = variableName;
		if (variableName != null) {
			if (variableName.startsWith("&")) {
				this.promptName = variableName.substring(1);	
			}
			else {
				this.promptName = variableName;
			}
		}
	}
	
	@XmlAttribute(name = "promptname")
	String getPromptName() {
		return promptName;
	}
	
	@XmlValue
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getDefaultValue() {
		return defaultValue;
	}

	void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@XmlAttribute(name = "ReadFromTable")
	String getReadFromTable() {
		return "OPSE"; 
	}
	
	@XmlAttribute(name = "haslist")
	String getHasList() {
		return "0"; 
	}
	
	@XmlAttribute(name = "msgnr")
	String getMsgNr() {
		return "-1"; 
	}
	
	@XmlAttribute(name = "msginsert")
	String getMsgInsert() {
		return ""; 
	}
	
	@XmlAttribute(name = "altview")
	String getAltView() {
		return "0"; 
	}

}
