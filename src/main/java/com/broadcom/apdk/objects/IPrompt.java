package com.broadcom.apdk.objects;

public interface IPrompt<T> {

	void setDataReference(String dataReference);
	
	String getDataReference();
	
	void setLabel(String label);
	
	String getLabel();
	
	void setVariableName(String variableName);
	
	String getVariableName();
	
	void setTooltip(String tooltip);
	
	String getTooltip();
	
	void setReadOnly(Boolean readonly);
	
	Boolean getReadOnly();
	
	void setFocus(Boolean focus);
	
	Boolean getFocus();
	
	void setCustomField(String customField);
	
	String getCustomField();
	
	void setPromptSetName(String promptSetName);
	
	String getPromptSetName();
	
	void setValue(T value);
	
	T getValue();
	
	String getValueAsString();
	
}
