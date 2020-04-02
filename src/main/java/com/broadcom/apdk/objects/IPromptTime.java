package com.broadcom.apdk.objects;

import java.time.LocalTime;

public interface IPromptTime extends IPrompt<LocalTime> {
	
	void setMinimum(String minimum);
	
	String getMinimum();
	
	void setMaximum(String maximum);
	
	String getMaximum();

}
