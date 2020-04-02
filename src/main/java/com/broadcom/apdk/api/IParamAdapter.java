package com.broadcom.apdk.api;

public interface IParamAdapter<T> {
	
	String convertToString(T value);
	
	T convertToType(String value);

}
