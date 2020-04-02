package com.broadcom.apdk.api;

public interface IAction {
	
	String getName();

	void setName(String name);
	
	String getTitle();

	void setTitle(String title);
	
	String getPath();
	
	void setPath(String path);
	
	void run();

}
