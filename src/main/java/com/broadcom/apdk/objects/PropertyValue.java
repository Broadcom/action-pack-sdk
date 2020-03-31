package com.broadcom.apdk.objects;

class PropertyValue {
	
	private IPrompt<?> parentPrompt;
	private String refType;
	private String listParam;
	private String value;
	private String alias;
	
	PropertyValue() {}
	
	PropertyValue(String value) {
		setValue(value);
	}
	
	PropertyValue(String value, String alias) {
		setValue(value);
		setAlias(alias);
	}
	
	void setValue(String value) {
		this.value = value;
	}

	String getValue() {
		return value;
	}

	String getRefType() {
		return refType;
	}

	void setRefType(String refType) {
		this.refType = refType;
	}

	String getListParam() {
		return listParam;
	}

	void setListParam(String listParam) {
		this.listParam = listParam;
	}
	
	IPrompt<?> getParentPrompt() {
		return parentPrompt;
	}
	
	void setParentPrompt(IPrompt<?> parentPrompt) {
		this.parentPrompt = parentPrompt;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
