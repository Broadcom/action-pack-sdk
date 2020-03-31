package com.broadcom.apdk.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "PRPTS")
class PromptSetDialogData {
	
	private List<PromptSetDataEntry> data;
	
	PromptSetDialogData() {}
	
	PromptSetDialogData(List<PromptSetDataEntry> data) {
		this.setData(data);
	}
	
	@XmlPath("PRPTBOX")
	@XmlAnyElement(lax = true)
	@XmlJavaTypeAdapter(PromptSetDataEntryAdapter.class)
	List<PromptSetDataEntry> getData() {
		return data;
	}

	void setData(List<PromptSetDataEntry> data) {
		this.data = data;
	}
	
	@XmlAttribute(name = "ontop")
	String getOnTop() {
		return "1";
	}

}
