package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

class PromptSetNode extends VaraPromptNode {
	
	private List<IPrompt<?>> prompts;

	private PromptSetNode() {
		super(null, null, "PRPTS", "PROMPTSET");
		prompts = new ArrayList<IPrompt<?>>();
	}
	
	PromptSetNode(String name , List<IPrompt<?>> prompts) {
		super(name, name, "PRPTS", "PROMPTSET");
		setPrompts(prompts);
	}
	
	@XmlPath("PROMPTSET[@ontop='1']/PRPTBOX[@prptmode='1']")
	@XmlAnyElement(lax = true)
	@XmlJavaTypeAdapter(PromptSetNodeAdapter.class)
	List<IPrompt<?>> getPrompts() {
		return prompts;
	}
		
	void setPrompts(List<IPrompt<?>> prompts) {
		if (prompts == null) {
			prompts = new ArrayList<IPrompt<?>>();	
		}
		else {
			this.prompts = prompts;
		}
	}
	
	@XmlPath("PROMPTSET[@ontop='1']/@name")
	String getPromptSetName() {
		return getName();
	}
	
	@XmlPath("PROMPTSET[@ontop='1']/PRPTBOX[@prptmode='1']/@promptset")
	String getPrptBoxName() {
		return getName();
	}
	
	@XmlPath("PROMPTSET[@ontop='1']/PRPTBOX[@prptmode='1']/element")
	NoPrompt getEmptyPrompt() {
		if (prompts == null || prompts.isEmpty()) {
			return new NoPrompt(false, getName());
		}
		return null;
	}

}
