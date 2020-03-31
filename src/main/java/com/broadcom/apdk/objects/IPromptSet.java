package com.broadcom.apdk.objects;

import java.util.List;

public interface IPromptSet extends IAutomicObject {
	
	public void setPrompts(List<IPrompt<?>> prompts);
	
	public List<IPrompt<?>> getPrompts();

}
