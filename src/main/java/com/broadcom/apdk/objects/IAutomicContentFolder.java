package com.broadcom.apdk.objects;

import java.util.List;

public interface IAutomicContentFolder extends IAutomicContent {
	
	public String getName();
	
	public void setName(String name);
	
	public String getTitle();
	
	public void setTitle(String name);

	public List<IAutomicContent> getObjects();
	
	public void setObjects(List<IAutomicContent> objects);
	
}
