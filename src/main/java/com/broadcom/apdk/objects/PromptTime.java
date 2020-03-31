package com.broadcom.apdk.objects;

import java.time.LocalTime;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

import com.broadcom.apdk.helpers.DateTimeHelper;

@XmlRootElement(name = "time")
@PropertyOrder(keys = {"reference", "text", "timin", "timax", "id", "enabled", "focus", 
		"tooltip", "customfield", "modifiable", "initvalue"})
public class PromptTime extends Prompt<LocalTime> implements IPromptTime {
	
	private final String TIME_MINIMUM = "timin";
	private final String TIME_MAXIMUM = "timax";
	private final String MODIFIABLE = "modifiable";
	
	public PromptTime() {
		super();
		initPrompt();
	}
	
	public PromptTime(String variableName, String label, String tooltip) {
		super(variableName, label, tooltip, "STATIC", "UC_DATATYPE_TIME", "TI", null);
		initPrompt();
	}
	
	public PromptTime(String variableName, String label, String tooltip, LocalTime initValue) {
		super(variableName, label, tooltip, "STATIC", "UC_DATATYPE_TIME", "TI", initValue);
		initPrompt();
	}
	
	private void initPrompt() {	
		initProperties();
		getProperties().put(MODIFIABLE, new PropertyValue("1"));
		getProperties().put(TIME_MINIMUM, new PropertyValue(null, "min"));
		getProperties().put(TIME_MAXIMUM, new PropertyValue(null, "max"));
	}
	
	@Override
	public void setValue(LocalTime value) {
		initProperties();
		getProperties().put(Prompt.INITVALUE, value != null ? 
				new PropertyValue(DateTimeHelper.toString(value)) : new PropertyValue(null));
	}

	@Override
	@XmlTransient
	public LocalTime getValue() {
		if (getProperties().containsKey(Prompt.INITVALUE)) {
			PropertyValue returnValue = getProperties().get(Prompt.INITVALUE);
			return returnValue != null && returnValue.getValue() != null ? 
					LocalTime.parse(returnValue.getValue()) : null;
		}
		return null;
	}
	
	public void setMinimum(String minimum) {
		initProperties();
		getProperties().put(TIME_MINIMUM, minimum != null ? 
				new PropertyValue(minimum) : new PropertyValue(null));		
	}
	
	@XmlAttribute(name = "min")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getMinimum() {
		if (getProperties().containsKey(TIME_MINIMUM)) {
			PropertyValue returnValue = getProperties().get(TIME_MINIMUM);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;			
	}
	
	public void setMaximum(String maximum) {
		initProperties();
		getProperties().put(TIME_MAXIMUM, maximum != null ? 
				new PropertyValue(maximum) : new PropertyValue(null));	
	}
		
	@XmlAttribute(name = "max")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getMaximum() {
		if (getProperties().containsKey(TIME_MAXIMUM)) {
			PropertyValue returnValue = getProperties().get(TIME_MAXIMUM);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;
	}
	
}
