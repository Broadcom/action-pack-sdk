package com.broadcom.apdk.objects;

import java.time.LocalTime;

public interface IPromptTime extends IPrompt<LocalTime> {
	
	public void setMinimum(String minimum);
	
	public String getMinimum();
	
	public void setMaximum(String maximum);
	
	public String getMaximum();

}
