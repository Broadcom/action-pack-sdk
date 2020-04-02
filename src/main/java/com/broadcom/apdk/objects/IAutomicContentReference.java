package com.broadcom.apdk.objects;

public interface IAutomicContentReference extends IAutomicContent {

	String getName();
	
	void setName(String name);
	
	Boolean isLink();

	void setLink(Boolean isLink);
	
}
