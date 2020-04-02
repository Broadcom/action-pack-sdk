package com.broadcom.apdk.objects;

import java.time.LocalDate;

public interface IPromptDate extends IPrompt<LocalDate> {
	
	void setMinimum(String minimum);
	
	String getMinimum();
	
	void setMaximum(String maximum);
	
	String getMaximum();
	
	void setCalendar(String calendar);
	
	String getCalendar();

}
