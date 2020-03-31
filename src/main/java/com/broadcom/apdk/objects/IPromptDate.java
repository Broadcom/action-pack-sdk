package com.broadcom.apdk.objects;

import java.time.LocalDate;

public interface IPromptDate extends IPrompt<LocalDate> {
	
	public void setMinimum(String minimum);
	
	public String getMinimum();
	
	public void setMaximum(String maximum);
	
	public String getMaximum();
	
	public void setCalendar(String calendar);
	
	public String getCalendar();

}
