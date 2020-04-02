package com.broadcom.apdk.objects;

public interface IPromptInteger extends IPrompt<Integer> {
	
	Integer getMinimum();
	
	void setMinimum(Integer minimum);
	
	Integer getMaximum();
	
	void setMaximum(Integer maximum);

}