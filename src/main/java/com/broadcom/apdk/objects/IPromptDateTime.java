package com.broadcom.apdk.objects;

import java.time.LocalDateTime;

public interface IPromptDateTime extends IPrompt<LocalDateTime> {
	
	void setMinimum(String minimum);
	
	String getMinimum();
	
	void setMaximum(String maximum);
	
	String getMaximum();
	
	void setCalendar(String calendar);
	
	String getCalendar();

}
