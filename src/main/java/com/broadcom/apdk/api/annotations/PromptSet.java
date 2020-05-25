package com.broadcom.apdk.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PromptSet {

	public String name() default "";
	
	public String title() default "Inputs";
	
}

