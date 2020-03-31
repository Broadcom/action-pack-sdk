package com.broadcom.apdk.objects;

public class WorkflowTaskParentPromptSetsNode extends VaraPromptNode implements IWorkflowTaskVaraPromptNode {

	WorkflowTaskParentPromptSetsNode() {
		super("Parent PromptSets", "PPRPTS", null, "PPROMPTSET");
		setContent(false);
	}

}
