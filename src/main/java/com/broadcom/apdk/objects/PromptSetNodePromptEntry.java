package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

class PromptSetNodePromptEntry {
	
	private String initValue;
	
	PromptSetNodePromptEntry() {}
	
	PromptSetNodePromptEntry(String initValue) {
		this.initValue = initValue;
	}
	
	@XmlAttribute(name = "haslist")
	String getHasList() {
		return "0";
	}
	
	@XmlAttribute(name = "altview")
	String getAltView() {
		return initValue != null && initValue.startsWith("&") && initValue.endsWith("#") ? "1" : "0";
	}
	
	@XmlValue
	String getInitValue() {
		return initValue;
	}

}
