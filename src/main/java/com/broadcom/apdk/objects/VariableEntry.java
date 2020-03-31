package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

@XmlRootElement(name = "row")
public class VariableEntry {
	
	private String name;
	private String value;
	private Boolean ERTusage;
	
	public VariableEntry() {
		setERTusage(false);
	}
	
	public VariableEntry(String name, String value) {
		setName(name);
		setValue(value);
		setERTusage(false);
	}

	@XmlAttribute(name ="Name")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "Value")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute(name = "ERTUsage")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isERTusage() {
		return ERTusage;
	}

	public void setERTusage(Boolean eRTusage) {
		ERTusage = eRTusage;
	}

}
