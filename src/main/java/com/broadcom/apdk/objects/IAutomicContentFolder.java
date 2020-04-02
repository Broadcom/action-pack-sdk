package com.broadcom.apdk.objects;

import java.util.List;

public interface IAutomicContentFolder extends IAutomicContent {
	
	String getName();
	
	void setName(String name);
	
	String getTitle();
	
	void setTitle(String name);

	List<IAutomicContent> getObjects();
	
	void setObjects(List<IAutomicContent> objects);
	
}
