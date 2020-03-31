package com.broadcom.apdk.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.broadcom.apdk.api.ParamAdapter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ActionParamAdapter {

	public Class<? extends ParamAdapter<?>> value();

}
