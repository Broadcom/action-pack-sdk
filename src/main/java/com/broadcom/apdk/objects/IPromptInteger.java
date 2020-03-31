package com.broadcom.apdk.objects;

public interface IPromptInteger extends IPrompt<Integer> {
	
	public Integer getMinimum();
	
	public void setMinimum(Integer minimum);
	
	public Integer getMaximum();
	
	public void setMaximum(Integer maximum);

}