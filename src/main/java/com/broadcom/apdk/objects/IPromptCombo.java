package com.broadcom.apdk.objects;

public interface IPromptCombo extends IPrompt<String> {
	
	void setDynamic(Boolean dynamic);
	
	Boolean getDynamic();
	
	void setQuotes(String quotes);
	
	String getQuotes();

}
