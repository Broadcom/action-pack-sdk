package com.broadcom.apdk.api;

public interface IParamAdapter<T> {
	
	public String convertToString(T value);
	
	public T convertToType(String value);

}
