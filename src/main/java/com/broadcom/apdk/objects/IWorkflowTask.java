package com.broadcom.apdk.objects;

import java.util.List;

public interface IWorkflowTask {
	
	public void setParentObject(String parentObject);
	
	public String getParentObject();
	
	public void setParentAlias(String parentAlias);
	
	public String getParentAlias();
	
	public void setObject(String name); 
	
	public String getObject();
	
	public void setObjectType(String objectType);
	
	public String getObjectType();
	
	public void setNumber(Integer number);
	
	public Integer getNumber();
	
	public void setRow(Integer column);
	
	public Integer getRow();
	
	public void setColumn(Integer column);
	
	public Integer getColumn();
	
	public void setBranchType(String branchType);
	
	public String getBranchType();
	
	public void setAlias(String alias);
	
	public String getAlias();
	
	public void setPredecessors(List<WorkflowTaskPredecessor> predecessors);
	
	public List<WorkflowTaskPredecessor> getPredecessors();
	
	public void setPromptSets(List<IPromptSet> promptSets);
	
	public List<IPromptSet> getPromptSets();
	
	public void setWorkflow(IWorkflow workflowName);
	
	public IWorkflow getWorkflow();

}
