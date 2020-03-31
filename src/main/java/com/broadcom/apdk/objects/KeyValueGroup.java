package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

public class KeyValueGroup<T> {
	
	private String key;
	private T[] values;
	
	@SuppressWarnings("unchecked")
	public KeyValueGroup() {
		Object[] objectArray = new Object[5];
		values = (T[]) objectArray;
	}
	
	@SuppressWarnings("unchecked")
	public KeyValueGroup(String key, T value) {
		this.key = key;
		Object[] objectArray = new Object[5];
		this.values = (T[]) objectArray;
		this.values[0] = value;
	}
	
	@SuppressWarnings("unchecked")
	public KeyValueGroup(String key, T[] values) {
		this.key = key;
		Object[] objectArray = new Object[5];
		this.values = (T[]) objectArray;
		if (values.length > 0) {
			for (int i = 0; i < 5; i++) {
				this.values[i] = values[i];
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public KeyValueGroup(String key, T value, T value1, T value2, T value3, T value4) {
		this.key = key;
		Object[] objectArray = new Object[5];
		this.values = (T[]) objectArray;
		this.values[0] = value;
		this.values[1] = value1;
		this.values[2] = value2;
		this.values[3] = value3;
		this.values[4] = value4;
	}
	
	public void setValue(T value) {
		this.values[0] = value;
	}

	public void setValue1(T value) {
		this.values[1] = value;
	}
	
	public void setValue2(T value) {
		this.values[2] = value;
	}
	
	public void setValue3(T value) {
		this.values[3] = value;
	}
	
	public void setValue4(T value) {
		this.values[4] = value;
	}
	
	@XmlAttribute(name = "Name")
	public String getKey() {
		return this.key;
	}
	
	@XmlTransient
	public T[] getValues() {
		return this.values;
	}
	
	@XmlAttribute(name = "Value")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public T getValue() {
		return this.values[0];
	}
	
	@XmlAttribute(name = "Value1")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public T getValue1() {
		return this.values[1];
	}
	
	@XmlAttribute(name = "Value2")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public T getValue2() {
		return this.values[2];
	}
	
	@XmlAttribute(name = "Value3")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public T getValue3() {
		return this.values[3];
	}
	
	@XmlAttribute(name = "Value4")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public T getValue4() {
		return this.values[4];
	}
}
