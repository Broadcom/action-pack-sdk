package com.broadcom.apdk.objects;

public interface IVariableText extends IVariable {
	
	public String getDataTypeName();
	
	public void limitTextLength(Boolean limitTextLength);
	
	public Boolean isTextLengthLimited();
	
	public void setMaxTextLength(Integer maxTextLength);
	
	public Integer getMaxTextLength();
	
	public void forceUpperCase(Boolean forceUpperCase);
	
	public Boolean isUpperCaseForced();

}
