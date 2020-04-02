package com.broadcom.apdk.objects;

public interface IVariableText extends IVariable {
	
	String getDataTypeName();
	
	void limitTextLength(Boolean limitTextLength);
	
	Boolean isTextLengthLimited();
	
	void setMaxTextLength(Integer maxTextLength);
	
	Integer getMaxTextLength();
	
	void forceUpperCase(Boolean forceUpperCase);
	
	Boolean isUpperCaseForced();

}
