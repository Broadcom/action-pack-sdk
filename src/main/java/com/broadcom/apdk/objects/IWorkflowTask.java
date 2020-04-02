package com.broadcom.apdk.objects;

import java.util.List;

public interface IWorkflowTask {
	
	void setParentObject(String parentObject);
	
	String getParentObject();
	
	void setParentAlias(String parentAlias);
	
	String getParentAlias();
	
	void setObject(String name); 
	
	String getObject();
	
	void setObjectType(String objectType);
	
	String getObjectType();
	
	void setNumber(Integer number);
	
	Integer getNumber();
	
	void setRow(Integer column);
	
	Integer getRow();
	
	void setColumn(Integer column);
	
	Integer getColumn();
	
	void setBranchType(String branchType);
	
	String getBranchType();
	
	void setAlias(String alias);
	
	String getAlias();
	
	void setPredecessors(List<WorkflowTaskPredecessor> predecessors);
	
	List<WorkflowTaskPredecessor> getPredecessors();
	
	void setPromptSets(List<IPromptSet> promptSets);
	
	List<IPromptSet> getPromptSets();
	
	void setWorkflow(IWorkflow workflowName);
	
	IWorkflow getWorkflow();

}
