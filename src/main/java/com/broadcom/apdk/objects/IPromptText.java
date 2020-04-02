package com.broadcom.apdk.objects;

public interface IPromptText extends IPrompt<String> {
	
	void setRequired(Boolean required);
	
	Boolean isRequired();
	
	void setInputAssistance(Boolean assistant);
	
	Boolean isInputAssistance();	
	
	void setMultiline(Boolean multiline);
	
	Boolean isMultiline();	
	
	void setUpperCase(Boolean uppercase);
	
	Boolean isUpperCase();
	
	void setMultiselect(Boolean multiselect);
	
	Boolean isMultiselect();
	
	void setRegEx(String regex);
	
	String getRegEx();
	
	public void setMaxLength(Integer length);
	
	public Integer getMaxLength();
	
	public void setQuotes(String quotes);
	
	public String getQuotes();

}