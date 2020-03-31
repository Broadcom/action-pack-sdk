package com.broadcom.apdk.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.broadcom.apdk.api.IActionPack;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActionPacks {
	
	public Class<? extends IActionPack>[] value();

}
