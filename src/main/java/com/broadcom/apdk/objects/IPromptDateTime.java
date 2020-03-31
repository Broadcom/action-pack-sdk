package com.broadcom.apdk.objects;

import java.time.LocalDateTime;

public interface IPromptDateTime extends IPrompt<LocalDateTime> {
	
	public void setMinimum(String minimum);
	
	public String getMinimum();
	
	public void setMaximum(String maximum);
	
	public String getMaximum();
	
	public void setCalendar(String calendar);
	
	public String getCalendar();

}
