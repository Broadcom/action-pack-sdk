package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

class NoPrompt {
	
	private boolean taskContext;
	private String promptSetName;
	
	@SuppressWarnings("unused")
	private NoPrompt() {}
	
	NoPrompt(boolean taskContext, String promptSetName) {
		this.taskContext = taskContext;
		this.promptSetName = promptSetName;
	}
	
	@XmlAttribute(name = "promptname")
	String getPromptName() {
		return taskContext ? "ELEMENT" : "OPSE_Name";
	}
	
	@XmlAttribute(name = "msgnr")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getMsgNr() {
		return taskContext ? null : "!NOP|";
	}
	
	@XmlAttribute(name = "msginsert")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getMsgInsert() {
		return taskContext ? null : "!NOP|";
	}
	
	@XmlAttribute(name = "haslist")
	String getHasList() {
		return taskContext ? "0" : "OPSE_HasList";
	}
	
	@XmlAttribute(name = "altview")
	String getAltView() {
		return taskContext ? "0" : "!PROMPTVALUE|OPSE_HasList|OPSE_AltView,OPUD_AltView|OPSE_AltView,OPUD_AltView";
	}
	
	@XmlPath("values/value/text()")
	String getValue() {
		return taskContext ? null : "!PROMPTVALUE|OPSE_HasList|OPSEA_Value,OPUDA_Value|OPSE_Value,OPUD_Value";
	}
	
	@XmlAttribute(name = "promptsetname")
	String getPromptSetName() {
		return taskContext ? promptSetName : null; 
	}
	
	@XmlAttribute(name = "ReadFromTable")
	String getReadFromTable() {
		return taskContext ? "OPUD" : null; 
	}

}
