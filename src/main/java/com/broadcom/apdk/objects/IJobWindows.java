package com.broadcom.apdk.objects;

public interface IJobWindows extends IJob {
	
	void setWorkingDirectory(String directory);
	
	String getWorkingDirectory();
	
	void setInterpreterType(InterpreterType type);
	
	InterpreterType getInterpreterType();
	
	void setLogonAsBatchUser(Boolean logonAsBatchUser);
	
	Boolean getLogonAsBatchUser();
	
	void setScriptedReport(Boolean scriptedReport);
	
	Boolean getScriptedReport();
	
	void setUseWinOsJobObject(UseWinOsJobObject useWinOsJobObject);
	
	UseWinOsJobObject getUseWinOsJobObject();
	
	void setViewJobOnDesktop(ViewJobOnDesktop view);
	
	ViewJobOnDesktop getViewJobOnDesktop();
	
	void setReportToDatabase(Boolean database);
	
	Boolean getReportToDatabase();
	
	void setReportToFile(Boolean file);
	
	Boolean getReportToFile();
	
	void setReportTrigger(Boolean trigger);
	
	Boolean getReportTrigger();
	
}
