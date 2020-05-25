package com.broadcom.apdk.api;

public interface IAction extends IPromptSet {
	
	String getPath();
	
	void setPath(String path);
	
	void run();
	
	String getDocumentation();
	
	void setDocumentation(String documentation);

}
