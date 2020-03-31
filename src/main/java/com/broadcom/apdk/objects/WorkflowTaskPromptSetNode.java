package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

class WorkflowTaskPromptSetNode extends VaraPromptNode implements IWorkflowTaskVaraPromptNode {
	
	private List<IPrompt<?>> prompts;

	private WorkflowTaskPromptSetNode() {
		super(null, null, "PRPTS", "PROMPTSET");
		prompts = new ArrayList<IPrompt<?>>();
	}
	
	WorkflowTaskPromptSetNode(String name , List<IPrompt<?>> prompts, boolean inherited) {
		super(name, name, 
				inherited ? "PPRPTS" : "PRPTS", 
				inherited ? "PPROMPTSET" : "PROMPTSET");
		setPrompts(prompts);
	}
	
	@XmlPath("PROMPTSET/PRPTBOX")
	@XmlAnyElement(lax = true)
	@XmlJavaTypeAdapter(WorkflowTaskPromptSetNodeAdapter.class)
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
	
	@XmlPath("PROMPTSET/@name")
	String getPromptSetName() {
		return getName();
	}
	
	@XmlPath("PROMPTSET/PRPTBOX/@promptset")
	String getPrptBoxName() {
		return getName();
	}
	
	@XmlPath("PROMPTSET/PRPTBOX/ELEMENT")
	NoPrompt getEmptyPrompt() {
		if (prompts == null || prompts.isEmpty()) {
			return new NoPrompt(true, getName());
		}
		return null;
	}

}
