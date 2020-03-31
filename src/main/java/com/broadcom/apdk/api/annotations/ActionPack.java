package com.broadcom.apdk.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActionPack {

	public String name() default "";
	
	public String title() default "";
	
	public String category() default "";
	
	public String buildNumber() default "";
	
	public String company() default "";
	
	public String dependencies() default "";
	
	public String description() default "";
	
	public String homepage() default "";
	
	public String license() default "";
	
	public String packageFormatVersion() default "";
	
	public String version() default "";	
	
}