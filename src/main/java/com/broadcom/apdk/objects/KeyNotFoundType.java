package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType (propOrder={"returnError", "returnInitialValue"})
class KeyNotFoundType {
	
	private Boolean returnError;
	private Boolean returnInitialValue;
	
	public KeyNotFoundType() {}

	public KeyNotFoundType(boolean returnError, boolean returnInitialValue) {
		this.setReturnError(returnError);
		this.setReturnInitialValue(returnInitialValue);
	}

	@XmlElement(name = "NotFoundErr")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getReturnError() {
		return returnError;
	}

	public void setReturnError(Boolean returnError) {
		this.returnError = returnError;
	}

	@XmlElement(name = "NotFoundDef")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getReturnInitialValue() {
		return returnInitialValue;
	}

	public void setReturnInitialValue(Boolean returnInitialValue) {
		this.returnInitialValue = returnInitialValue;
	}
}
