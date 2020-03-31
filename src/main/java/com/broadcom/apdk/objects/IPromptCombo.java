package com.broadcom.apdk.objects;

public interface IPromptCombo extends IPrompt<String> {
	
	public void setDynamic(Boolean dynamic);
	
	public Boolean getDynamic();
	
	public void setQuotes(String quotes);
	
	public String getQuotes();

}
