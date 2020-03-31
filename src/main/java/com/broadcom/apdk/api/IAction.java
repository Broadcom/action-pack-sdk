package com.broadcom.apdk.api;

public interface IAction {
	
	public String getName();

	public void setName(String name);
	
	public String getTitle();

	public void setTitle(String title);
	
	public String getPath();
	
	public void setPath(String path);
	
	public void run();

}
