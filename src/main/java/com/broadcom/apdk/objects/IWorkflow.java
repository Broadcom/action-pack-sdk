package com.broadcom.apdk.objects;

import java.util.List;

public interface IWorkflow extends IExecutableAutomicObject {

	public void setScript(String script);
	
	public String getScript();
	
	public void setInternalAccount(String internalAccount);
	
	public String getInternalAccount();
	
	public void setExtendedReport(ExtendedReport extendedReport);
	
	public ExtendedReport getExtendedReport();
	
	public void setResultEvalOkStatus(String okStatus);
	
	public String getResultEvalOkStatus();
	
	public void setResultEvalExecIfNotOk(String executableObjectName);
	
	public String getResultEvalExecIfNotOk();
	
	public void setPassPriority(Boolean passPriority);
	
	public Boolean isPassPriority();
	
	public void setChildQueue(String childQueue);
	
	public String getChildQueue();
	
	public void setTasks(List<IWorkflowTask> tasks);
	
	public List<IWorkflowTask> getTasks();
	
}
