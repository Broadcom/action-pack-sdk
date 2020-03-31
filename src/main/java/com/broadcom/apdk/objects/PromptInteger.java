package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

@XmlRootElement(name = "integer")
@PropertyOrder(keys = {"reference", "text", "min", "max", "quotes", "id", "enabled", 
		"focus", "tooltip", "customfield", "modifiable", "initvalue"})
public class PromptInteger extends Prompt<Integer> implements IPromptInteger {
	
	private final String MINIMUM = "min";
	private final String MAXIMUM = "max";
	private final String MODIFIABLE = "modifiable";
	private final String QUOTES = "quotes";	
	
	public PromptInteger() {
		super();
		initPrompt();
	}
	
	public PromptInteger(String variableName, String label, String tooltip) {
		super(variableName, label, tooltip, "STATIC", "UC_DATATYPE_NUMERIC", "N", null);
		initPrompt();
	}
	
	public PromptInteger(String variableName, String label, String tooltip, Integer initValue) {
		super(variableName, label, tooltip, "STATIC", "UC_DATATYPE_NUMERIC", "N", initValue);
		initPrompt();
	}
	
	private void initPrompt() {	
		initProperties();
		getProperties().put(MODIFIABLE, new PropertyValue("1"));
		getProperties().put(QUOTES, new PropertyValue(null));
		getProperties().put(MINIMUM, new PropertyValue(null));
		getProperties().put(MAXIMUM, new PropertyValue(null));
	}

	public void setMinimum(Integer minimum) {
		initProperties();
		getProperties().put(MINIMUM, minimum != null ? 
				new PropertyValue(Integer.toString(minimum)) : new PropertyValue(null));	
	}

	@XmlAttribute(name = "min")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getMinimum() {
		if (getProperties().containsKey(MINIMUM)) {
			PropertyValue returnValue = getProperties().get(MINIMUM);
			return returnValue != null && returnValue.getValue() != null ? 
					Integer.parseInt(returnValue.getValue()) : null;
		}
		return null;
	}

	public void setMaximum(Integer maximum) {
		initProperties();
		getProperties().put(MAXIMUM, maximum != null ? 
				new PropertyValue(Integer.toString(maximum)) : new PropertyValue(null));	
	}

	@XmlAttribute(name = "max")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getMaximum() {
		if (getProperties().containsKey(MAXIMUM)) {
			PropertyValue returnValue = getProperties().get(MAXIMUM);
			return returnValue != null && returnValue.getValue() != null ? 
					Integer.parseInt(returnValue.getValue()) : null;
		}
		return null;
	}

	@Override
	public void setValue(Integer value) {
		initProperties();
		getProperties().put(Prompt.INITVALUE, value != null ? 
				new PropertyValue(Integer.toString(value)) : new PropertyValue(null));		
	}

	@Override
	@XmlTransient
	public Integer getValue() {
		if (getProperties().containsKey(Prompt.INITVALUE)) {
			PropertyValue returnValue = getProperties().get(Prompt.INITVALUE);
			return returnValue != null && returnValue.getValue() != null ?
					Integer.parseInt(returnValue.getValue()) : null;
		}
		return null;
	}
}
