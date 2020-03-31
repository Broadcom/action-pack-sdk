package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;

class PromptSetNodePromptEntry {
	
	PromptSetNodePromptEntry() {}
	
	@XmlAttribute(name = "haslist")
	String getHasList() {
		return "0";
	}
	
	@XmlAttribute(name = "altview")
	String getAltView() {
		return "0";
	}

}
