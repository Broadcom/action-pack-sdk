package com.broadcom.apdk.objects;

import java.util.List;

public interface IPromptSet extends IAutomicObject {
	
	void setPrompts(List<IPrompt<?>> prompts);
	
	List<IPrompt<?>> getPrompts();

}
