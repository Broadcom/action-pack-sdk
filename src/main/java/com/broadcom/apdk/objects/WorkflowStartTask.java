package com.broadcom.apdk.objects;

import java.util.List;

public class WorkflowStartTask extends WorkflowTask {
	
	public WorkflowStartTask() {
		super(1, "START", "<START>", 1, 1, null);
		setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.SKIP_TASK);
	}
	
	public WorkflowStartTask(List<IPromptSet> promptSets) {
		super(1, "START", "<START>", 1, 1, null, promptSets);
		setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.SKIP_TASK);
	}
	
	public WorkflowStartTask(Integer row) {
		super(1, "START", "<START>", row, 1, null);
		setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.SKIP_TASK);
	}

}
