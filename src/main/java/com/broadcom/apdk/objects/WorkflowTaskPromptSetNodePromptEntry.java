package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

class WorkflowTaskPromptSetNodePromptEntry {
	
	private String variableName;
	private String promptSetName;
	
	@SuppressWarnings("unused")
	private WorkflowTaskPromptSetNodePromptEntry() {};
	
	WorkflowTaskPromptSetNodePromptEntry(String variableName, String promptSetName) {
		this.variableName = variableName;
		this.promptSetName = promptSetName;
	}
	
	@XmlAttribute(name = "haslist")
	String getHasList() {
		return "0";
	}
	
	@XmlAttribute(name = "altview")
	String getAltView() {
		return "0";
	}
	
	@XmlAttribute(name = "ReadFromTable")
	String getReadFromTable() {
		return "OPUD";
	}
	
	@XmlAttribute(name = "promptname")
	String getPromptName() {
		if (variableName != null && variableName.startsWith("&")) {
			return variableName.substring(1);
		}
		return variableName;
	}
	
	@XmlAttribute(name = "promptsetname")
	String getPromptSetName() {
		return promptSetName;
	}
	
	@XmlAttribute(name = "msgnr")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getMsgNr() {
		return null;
	}

	@XmlAttribute(name = "msginsert")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getMsgInsert() {
		return null;
	}
}
