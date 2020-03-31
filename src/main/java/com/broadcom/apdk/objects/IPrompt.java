package com.broadcom.apdk.objects;

public interface IPrompt<T> {

	public void setDataReference(String dataReference);
	
	public String getDataReference();
	
	public void setLabel(String label);
	
	public String getLabel();
	
	public void setVariableName(String variableName);
	
	public String getVariableName();
	
	public void setTooltip(String tooltip);
	
	public String getTooltip();
	
	public void setReadOnly(Boolean readonly);
	
	public Boolean getReadOnly();
	
	public void setFocus(Boolean focus);
	
	public Boolean getFocus();
	
	public void setCustomField(String customField);
	
	public String getCustomField();
	
	public void setPromptSetName(String promptSetName);
	
	public String getPromptSetName();
	
	public void setValue(T value);
	
	public T getValue();
	
	public String getValueAsString();
	
}
