package com.broadcom.apdk.objects;

public interface IAutomicObject extends IAutomicContent {
	
	public void setName(String name);
	
	public String getName();
	
	public void setTitle(String title);
	
	public String getTitle();
	
	public void setType(ObjectType type);
	
	public ObjectType getType();
	
	public void setArchiveKey1(String archiveKey1);
	
	public String getArchiveKey1();
	
	public void setArchiveKey2(String archiveKey2);
	
	public String getArchiveKey2();
	
	public void setDocumentation(String documentation);
	
	public String getDocumentation();
	
}
