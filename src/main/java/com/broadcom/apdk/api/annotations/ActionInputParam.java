package com.broadcom.apdk.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If your class implements the {@link com.broadcom.apdk.api.IAction IAction} interface 
 * this annotation may be used to mark certain fields of the class as input parameters
 * for your action. In addition to primitive data types and their corresponding wrapper 
 * classes and enums the following classes are supported by the framework:
 * <ul><li>{@link java.lang.String String}</li>
 * <li>{@link java.time.LocalTime LocalTime}</li>
 * <li>{@link java.time.LocalDate LocalDate}</li>
 * <li>{@link java.time.LocalDateTime LocalDateTime}</li></ul>
 * For all other classes you may have to implement an adapter that extends
 * {@link com.broadcom.apdk.api.ParamAdapter ParamAdapter} and reference it
 * using the {@link com.broadcom.apdk.api.annotations.ActionParamAdapter ActionParamAdapter}
 * annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ActionInputParam {
	
	public String name() default "";
	
	public String label() default "";
	
	public String tooltip() default "";	
	
	public String refType() default "STATIC";	
	
	public String dataReference() default "";
	
	public boolean required() default false;
	
	public boolean password() default false;

	public String cdaMapping() default "";	

}
