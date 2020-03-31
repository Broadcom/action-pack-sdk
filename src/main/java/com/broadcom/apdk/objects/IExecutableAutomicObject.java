package com.broadcom.apdk.objects;

import java.util.List;

public interface IExecutableAutomicObject extends IAutomicObject {
	
	public void setScript(String script);
	
	public String getScript();
	
	public void setQueue(String queue);
	
	public String getQueue();
	
	public void setErrorFreeStatus(String status);
	
	public String getErrorFreeStatus();
	
	public void setDeactivationDelay(Integer delay);
	
	public Integer getDeactivationDelay();
	
	public void setDeactivateOnFinish(DeactivationOnFinish deactivationOnFinish);
	
	public DeactivationOnFinish getDeactivateOnFinish();
	
	public void setMaxSimultaneousExec(Integer maxExecutions);
	
	public Integer getMaxSimultaneousExec();
	
	public void setPriority(Integer priority);
	
	public Integer getPriority();
	
	public void setTimezone(String timezoneObjectName);
	
	public String getTimezone();
	
	public void setGenerateAtRuntime(Boolean generateAtRuntime);
	
	public Boolean getGenerateAtRuntime();
	
	public void setWaitForRemainingTasks(Boolean waitForRemainingTasks);
	
	public Boolean isWaitForRemainingTasks();
	
	public void setJobGroup(String jobGroup);
	
	public String getJobGroup();
	
	public void setVariables(List<VariableEntry> variables);
	
	public List<VariableEntry> getVariables();
	
	public void setPromptSets(List<IPromptSet> promptSets);
	
	public List<IPromptSet> getPromptSets();

	public void setMaxReturnCode(Integer maxReturnCode);
	
	public Integer getMaxReturnCode();
	
	public void setExecuteObjectIfAboveMaxReturnCode(String objectName);
	
	public String getExecuteObjectIfAboveMaxReturnCode();
	
	public void setERTMethod(ERTMethod method);
	
	public ERTMethod getERTMethod();
	
	public void setERT(Integer seconds);
	
	public Integer getERT();
	
	public void setFixedERT(Integer seconds);
	
	public Integer getFixedERT();
	
	public void setERTNumberOfPastRuns(Integer numberOfPastRuns);
	
	public Integer getERTNumberOfPastRuns();
	
	public void setERTCorrection(Integer correction);
	
	public Integer getERTCorrection();
	
	public void setERTIgnoreDeviations(Boolean ignoreDeviations);
	
	public Boolean isERTIgnoreDeviations();
	
	public void setERTDeviationExtent(Integer deviationExntent);
	
	public Integer getERTDeviationExtent();
	
	public void setERTMinimumRuns(Integer minimumRuns);
	
	public Integer getERTMinimumRuns();
	
	public void setForecastEndStatus(String status);
	
	public String getForecastEndStatus();
	
	public void setSRTMethod(SRTMethod method);
	
	public SRTMethod getSRTMethod();
	
	public void setSRTERT(Integer negativeDuration);
	
	public Integer getSRTERT();
	
	public void setFixedSRT(Integer seconds);
	
	public Integer getFixedSRT();
	
	public void setExecuteObjectIfRuntimDeviation(String objectName);
	
	public String getExecuteObjectIfRuntimDeviation();
	
	public void setCancelIfRuntimDeviation(Boolean cancel);
	
	public Boolean isCancelIfRuntimDeviation();
	
	public void setMRTMethod(MRTMethod method);
	
	public MRTMethod getMRTMethod();
	
	public void setFixedDuration(Integer duration);
	
	public Integer getFixedDuration();
	
	public void setAdditionalDuration(Integer duration);
	
	public Integer getAdditionalDuration();
	
	public void setAdditionalDurationDays(Integer days);
	
	public Integer getAdditionalDurationDays();
	
	public void setFinishTime(String hhmm);
	
	public String getFinishTime();
	
	public void setFinishTimeTZ(String timezone);
	
	public String getFinishTimeTZ();
	
}
