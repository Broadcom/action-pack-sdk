package com.broadcom.apdk.objects;

import java.util.List;

public interface IWorkflow extends IExecutableAutomicObject {

	void setScript(String script);
	
	String getScript();
	
	void setInternalAccount(String internalAccount);
	
	String getInternalAccount();
	
	void setExtendedReport(ExtendedReport extendedReport);
	
	ExtendedReport getExtendedReport();
	
	void setResultEvalOkStatus(String okStatus);
	
	String getResultEvalOkStatus();
	
	void setResultEvalExecIfNotOk(String executableObjectName);
	
	String getResultEvalExecIfNotOk();
	
	void setPassPriority(Boolean passPriority);
	
	Boolean isPassPriority();
	
	void setChildQueue(String childQueue);
	
	String getChildQueue();
	
	void setTasks(List<IWorkflowTask> tasks);
	
	List<IWorkflowTask> getTasks();
	
}
