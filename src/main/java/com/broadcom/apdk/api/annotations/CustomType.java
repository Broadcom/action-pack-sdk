package com.broadcom.apdk.api.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import com.broadcom.apdk.objects.MainType;

@Repeatable(CustomType.List.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomType {
	
	public String filename();
	
	public MainType type();
	
	public int order() default 0;
	
	public String name() default "";
	
	public String version() default "";
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @interface List {
        CustomType[] value();
    }
}
