package com.broadcom.apdk.objects;

public interface IAutomicObject extends IAutomicContent {
	
	void setName(String name);
	
	String getName();
	
	void setTitle(String title);
	
	String getTitle();
	
	void setType(ObjectType type);
	
	ObjectType getType();
	
	void setArchiveKey1(String archiveKey1);
	
	String getArchiveKey1();
	
	void setArchiveKey2(String archiveKey2);
	
	String getArchiveKey2();
	
	void setDocumentation(String documentation);
	
	String getDocumentation();
	
}
