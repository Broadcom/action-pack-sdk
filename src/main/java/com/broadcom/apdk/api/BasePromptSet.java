package com.broadcom.apdk.api;

import java.util.logging.Logger;

import com.broadcom.apdk.api.annotations.PromptSet;

public abstract class BasePromptSet implements IPromptSet {
	
	protected final static Logger LOGGER = Logger.getLogger("APDK");
	
	private String name;
	private String title;
	
	public BasePromptSet() {
		PromptSet actionAnnotation = this.getClass().getAnnotation(PromptSet.class);
		if (actionAnnotation != null) {
			setName(!actionAnnotation.name().isEmpty() ? 
					actionAnnotation.name() : 
					getClass().getSimpleName().toUpperCase());
			setTitle(!actionAnnotation.title().isEmpty() ? 
					actionAnnotation.title() : null);
		}
		else {
			setName(getClass().getSimpleName().toUpperCase());
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

}
