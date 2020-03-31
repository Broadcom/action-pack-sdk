package com.broadcom.apdk.objects;

public interface IJobWindows extends IJob {
	
	public void setWorkingDirectory(String directory);
	
	public String getWorkingDirectory();
	
	public void setInterpreterType(InterpreterType type);
	
	public InterpreterType getInterpreterType();
	
	public void setLogonAsBatchUser(Boolean logonAsBatchUser);
	
	public Boolean getLogonAsBatchUser();
	
	public void setScriptedReport(Boolean scriptedReport);
	
	public Boolean getScriptedReport();
	
	public void setUseWinOsJobObject(UseWinOsJobObject useWinOsJobObject);
	
	public UseWinOsJobObject getUseWinOsJobObject();
	
	public void setViewJobOnDesktop(ViewJobOnDesktop view);
	
	public ViewJobOnDesktop getViewJobOnDesktop();
	
	public void setReportToDatabase(Boolean database);
	
	public Boolean getReportToDatabase();
	
	public void setReportToFile(Boolean file);
	
	public Boolean getReportToFile();
	
	public void setReportTrigger(Boolean trigger);
	
	public Boolean getReportTrigger();
	
}
