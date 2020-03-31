package com.broadcom.apdk.objects;

public interface IJobUnix extends IJob {
	
	public void setJobType(JobType jobType);
	
	public JobType getJobType();
	
	public void setShellOptions(String options);
	
	public String getShellOptions();
	
	public void setShell(Shell shell);
	
	public Shell getShell();
	
	public void setReportToDatabase(Boolean database);
	
	public Boolean getReportToDatabase();
	
	public void setReportToFile(Boolean file);
	
	public Boolean getReportToFile();
	
	public void setReportTrigger(Boolean trigger);
	
	public Boolean getReportTrigger();

}
