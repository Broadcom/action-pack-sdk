package com.broadcom.apdk.api;

abstract public class ParamAdapter<T> implements IParamAdapter<T> {
	
	abstract public String convertToString(T value);
	
	abstract public T convertToType(String value);

}
