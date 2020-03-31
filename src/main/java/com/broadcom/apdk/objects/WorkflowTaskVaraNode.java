package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

public class WorkflowTaskVaraNode extends VaraNode implements IWorkflowTaskVaraPromptNode {
	
	private WorkflowTaskVaraNode() {
		super("Variables", "VALUE", null, "VALUE", new ArrayList<VariableEntry>());
	}

	WorkflowTaskVaraNode(String name, String id, String parent, String type, List<VariableEntry> variables) {
		super(name, id, parent, type, variables == null ? new ArrayList<VariableEntry>() : variables);
		setState(2);
	}

}
