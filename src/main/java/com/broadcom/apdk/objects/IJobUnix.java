package com.broadcom.apdk.objects;

public interface IJobUnix extends IJob {
	
	void setJobType(JobType jobType);
	
	JobType getJobType();
	
	void setShellOptions(String options);
	
	String getShellOptions();
	
	void setShell(Shell shell);
	
	Shell getShell();
	
	void setReportToDatabase(Boolean database);
	
	Boolean getReportToDatabase();
	
	void setReportToFile(Boolean file);
	
	Boolean getReportToFile();
	
	void setReportTrigger(Boolean trigger);
	
	Boolean getReportTrigger();

}
