package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;


public class Property {

	private String key;
	private PropertyValue value;
	private String refType;
	private String listParam;
	private String alias;

	public Property() {} 

	public Property(String key, String value, String alias) {
	    this.setKey(key);
	    this.setValue(new PropertyValue(value, alias));
	    this.setAlias(alias);
	}
	
	public Property(String key, String value, String refType, String listParam, String alias) {
	    this.setKey(key);
	    this.setValue(new PropertyValue(value, alias));
	    this.setAlias(alias);
	    if (refType != null) {
	    	this.setRefType(refType);
	    	this.setListParam(listParam != null ? listParam : "C");
	    }
	}

	@XmlAttribute(name = "name")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@XmlTransient
	public PropertyValue getValue() {
		return value;
	}

	@XmlValue
	public String getValueStr() {
		if (value != null) {
			return value.getValue();
		}
		return null;
	}
	
	public void setValue(PropertyValue value) {
		this.value = value;
	}
	
	@XmlAttribute(name = "reftype")
	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	@XmlAttribute(name = "listparam")
	public String getListParam() {
		return listParam;
	}

	public void setListParam(String listParam) {
		this.listParam = listParam;
	}

	@XmlAttribute(name = "alias")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
