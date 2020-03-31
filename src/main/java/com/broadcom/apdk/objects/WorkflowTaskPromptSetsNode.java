package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class WorkflowTaskPromptSetsNode extends VaraPromptNode implements IWorkflowTaskVaraPromptNode {

	WorkflowTaskPromptSetsNode() {
		super("PromptSets", "PRPTS", null, "PROMPTSET");
	}
	
	@XmlAttribute(name = "pref")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isPRef() {
		return true;
	}

}
