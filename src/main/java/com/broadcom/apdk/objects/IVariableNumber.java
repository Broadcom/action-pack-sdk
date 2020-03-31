package com.broadcom.apdk.objects;

public interface IVariableNumber extends IVariable {
	
	public String getDataTypeName();
	
	public void limitTextLength(Boolean flag);
	
	public Boolean isTextLengthLimited();
	
	public void forceUpperCase(Boolean flag);
	
	public Boolean isUpperCaseForced();

}
