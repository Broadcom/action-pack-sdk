package com.broadcom.apdk.objects;

public interface IVariableNumber extends IVariable {
	
	String getDataTypeName();
	
	void limitTextLength(Boolean flag);
	
	Boolean isTextLengthLimited();
	
	void forceUpperCase(Boolean flag);
	
	Boolean isUpperCaseForced();

}
