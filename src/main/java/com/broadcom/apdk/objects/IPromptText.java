package com.broadcom.apdk.objects;

public interface IPromptText extends IPrompt<String> {
	
	public void setRequired(Boolean required);
	
	public Boolean isRequired();
	
	public void setInputAssistance(Boolean assistant);
	
	public Boolean isInputAssistance();	
	
	public void setMultiline(Boolean multiline);
	
	public Boolean isMultiline();	
	
	public void setUpperCase(Boolean uppercase);
	
	public Boolean isUpperCase();
	
	public void setMultiselect(Boolean multiselect);
	
	public Boolean isMultiselect();
	
	public void setRegEx(String regex);
	
	public String getRegEx();
	
	public void setMaxLength(Integer length);
	
	public Integer getMaxLength();
	
	public void setQuotes(String quotes);
	
	public String getQuotes();

}