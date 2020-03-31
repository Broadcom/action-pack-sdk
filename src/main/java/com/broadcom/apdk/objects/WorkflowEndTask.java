package com.broadcom.apdk.objects;

import java.util.List;

public class WorkflowEndTask extends WorkflowTask {
	
	public WorkflowEndTask() {
		super(2, "END", "<END>", 1, 4, null, null);	
		setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.SKIP_TASK);
	}
	
	public WorkflowEndTask(Integer number, Integer row, Integer column, 
			List<WorkflowTaskPredecessor> predecessors) {
		super(number, "END", "<END>", row, column, predecessors, null);
		setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.SKIP_TASK);
	}

}
