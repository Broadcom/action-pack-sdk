package com.broadcom.apdk.objects;

import java.util.List;

public interface IExecutableAutomicObject extends IAutomicObject {
	
	void setScript(String script);
	
	String getScript();
	
	void setQueue(String queue);
	
	String getQueue();
	
	void setErrorFreeStatus(String status);
	
	String getErrorFreeStatus();
	
	void setDeactivationDelay(Integer delay);
	
	Integer getDeactivationDelay();
	
	void setDeactivateOnFinish(DeactivationOnFinish deactivationOnFinish);
	
	DeactivationOnFinish getDeactivateOnFinish();
	
	void setMaxSimultaneousExec(Integer maxExecutions);
	
	Integer getMaxSimultaneousExec();
	
	void setPriority(Integer priority);
	
	Integer getPriority();
	
	void setTimezone(String timezoneObjectName);
	
	String getTimezone();
	
	void setGenerateAtRuntime(Boolean generateAtRuntime);
	
	Boolean getGenerateAtRuntime();
	
	void setWaitForRemainingTasks(Boolean waitForRemainingTasks);
	
	Boolean isWaitForRemainingTasks();
	
	void setJobGroup(String jobGroup);
	
	String getJobGroup();
	
	void setVariables(List<VariableEntry> variables);
	
	List<VariableEntry> getVariables();
	
	void setPromptSets(List<IPromptSet> promptSets);
	
	List<IPromptSet> getPromptSets();

	void setMaxReturnCode(Integer maxReturnCode);
	
	Integer getMaxReturnCode();
	
	void setExecuteObjectIfAboveMaxReturnCode(String objectName);
	
	String getExecuteObjectIfAboveMaxReturnCode();
	
	void setERTMethod(ERTMethod method);
	
	ERTMethod getERTMethod();
	
	void setERT(Integer seconds);
	
	Integer getERT();
	
	void setFixedERT(Integer seconds);
	
	Integer getFixedERT();
	
	void setERTNumberOfPastRuns(Integer numberOfPastRuns);
	
	Integer getERTNumberOfPastRuns();
	
	void setERTCorrection(Integer correction);
	
	Integer getERTCorrection();
	
	void setERTIgnoreDeviations(Boolean ignoreDeviations);
	
	Boolean isERTIgnoreDeviations();
	
	void setERTDeviationExtent(Integer deviationExntent);
	
	Integer getERTDeviationExtent();
	
	void setERTMinimumRuns(Integer minimumRuns);
	
	Integer getERTMinimumRuns();
	
	void setForecastEndStatus(String status);
	
	String getForecastEndStatus();
	
	void setSRTMethod(SRTMethod method);
	
	SRTMethod getSRTMethod();
	
	void setSRTERT(Integer negativeDuration);
	
	Integer getSRTERT();
	
	void setFixedSRT(Integer seconds);
	
	Integer getFixedSRT();
	
	void setExecuteObjectIfRuntimDeviation(String objectName);
	
	String getExecuteObjectIfRuntimDeviation();
	
	void setCancelIfRuntimDeviation(Boolean cancel);
	
	Boolean isCancelIfRuntimDeviation();
	
	void setMRTMethod(MRTMethod method);
	
	MRTMethod getMRTMethod();
	
	void setFixedDuration(Integer duration);
	
	Integer getFixedDuration();
	
	void setAdditionalDuration(Integer duration);
	
	Integer getAdditionalDuration();
	
	void setAdditionalDurationDays(Integer days);
	
	Integer getAdditionalDurationDays();
	
	void setFinishTime(String hhmm);
	
	String getFinishTime();
	
	void setFinishTimeTZ(String timezone);
	
	String getFinishTimeTZ();
	
}
