package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "PRPT")
@XmlType (propOrder={"title", "archiveKey1", "archiveKey2", "type", "OHSubType", "designer", 
		"dialog", "dialogData", "documentation"})
public class PromptSet extends NonExecutableAutomicObject implements IPromptSet {

	private PromptSetDialog dialog;
	private PromptSetDialogData dialogData;
	private List<IPrompt<?>> prompts;
	
	public PromptSet() {}
	
	public PromptSet(String name) {
		super(name);
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
		addPromptSetNameToPrompts();
	}

	public PromptSet(String name, List<IPrompt<?>> prompts) {
		super(name);
		setPrompts(prompts);
	}
	
	public PromptSet(String name, String dialogTitle, List<IPrompt<?>> prompts) {
		super(name);
		setPrompts(prompts, dialogTitle);
	}
	
	public void setPrompts(List<IPrompt<?>> prompts) {
		setPrompts(prompts, "PRPT");
	}
	
	public void setPrompts(List<IPrompt<?>> prompts, String dialogTitle) {
		this.prompts = prompts;
		addPromptSetNameToPrompts();
		
		this.dialog = new PromptSetDialog(dialogTitle, prompts);
		
		List<PromptSetDataEntry> data = new ArrayList<PromptSetDataEntry>();
		if (prompts != null) {
			for (IPrompt<?> prompt : prompts) {
				data.add(new PromptSetDataEntry(prompt.getVariableName(), prompt.getValueAsString()));
			}
		}
		this.dialogData = new PromptSetDialogData(data);
	}
	
	@XmlTransient
	public List<IPrompt<?>> getPrompts() {
		return prompts;
	}
	
	// Non-Public API
	
	@XmlPath("PROMPTSETXUI[@state='1']/XUIEDITOR/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	@XmlJavaTypeAdapter(DialogAdapter.class)
	@XmlCDATA
	PromptSetDialog getDialog() {
		return this.dialog;
	}
	
	@XmlPath("PROMPTSETDATA[@state='1']/DATAEDITOR/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	@XmlJavaTypeAdapter(DialogDataAdapter.class)
	@XmlCDATA
	PromptSetDialogData getDialogData() {
		return this.dialogData;
	}
	
	@XmlPath("PROMPTDESIGNER[@state='1']/DESIGNER/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getDesigner() {
		return null;
	}
	
	private void addPromptSetNameToPrompts() {
		if (prompts != null) {
			List<IPrompt<?>> newPrompts = new ArrayList<IPrompt<?>>();
			for (IPrompt<?> prompt : prompts) {
				prompt.setPromptSetName(getName());
				newPrompts.add(prompt);
			}
			prompts = newPrompts;
		}
	}
	
}
