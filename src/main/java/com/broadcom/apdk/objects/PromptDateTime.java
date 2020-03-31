package com.broadcom.apdk.objects;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

import com.broadcom.apdk.helpers.DateTimeHelper;

@XmlRootElement(name = "timestamp")
@PropertyOrder(keys = {"reference", "calendar", "key", "text", "tsmin", "tsmax", 
		"tsoutputformat", "id", "enabled", "focus", "tooltip", "customfield", 
		"modifiable", "xmlkey", "xmlpath", "initvalue"})
public class PromptDateTime extends Prompt<LocalDateTime> implements IPromptDateTime {
	
	private final String DATETIME_MINIMUM = "tsmin";
	private final String DATETIME_MAXIMUM = "tsmax";
	private final String CALENDAR = "calendar";
	private final String KEY = "key";
	private final String OUTPUT_FORMAT = "tsoutputformat";
	private final String OUTPUT_FORMAT_VALUE = "yyyy-mm-dd hh:mm:ss";
	private final String MODIFIABLE = "modifiable";
	
	public PromptDateTime() {
		super();
		initPrompt();
	}
	
	public PromptDateTime(String variableName, String label, String tooltip) {
		super(variableName, label, tooltip, "STATIC", "UC_DATATYPE_TIMESTAMP", "D", null);
		initPrompt();
	}
	
	public PromptDateTime(String variableName, String label, String tooltip, LocalDateTime initValue) {
		super(variableName, label, tooltip, "STATIC", "UC_DATATYPE_TIMESTAMP", "D", initValue);
		initPrompt();
	}
	
	private void initPrompt() {		
		initProperties();
		getProperties().put(MODIFIABLE, new PropertyValue("1"));
		getProperties().put(DATETIME_MINIMUM, new PropertyValue(null, "min"));
		getProperties().put(DATETIME_MAXIMUM, new PropertyValue(null, "max"));
		getProperties().put(CALENDAR, new PropertyValue(null));
		getProperties().put(OUTPUT_FORMAT, new PropertyValue(OUTPUT_FORMAT_VALUE));
		getProperties().put(KEY, new PropertyValue(null));
	}
	
	@Override
	public void setValue(LocalDateTime value) {
		initProperties();
		getProperties().put(Prompt.INITVALUE, value != null ? 
				new PropertyValue(DateTimeHelper.toString(value)) : new PropertyValue(null));
	}

	@Override
	@XmlTransient
	public LocalDateTime getValue() {
		if (getProperties().containsKey(Prompt.INITVALUE)) {
			PropertyValue returnValue = getProperties().get(Prompt.INITVALUE);
			return returnValue != null && returnValue.getValue() != null ? 
					LocalDateTime.parse(returnValue.getValue()) : null;
		}
		return null;
	}
	
	public void setMinimum(String minimum) {
		initProperties();
		getProperties().put(DATETIME_MINIMUM, minimum != null ? 
				new PropertyValue(minimum) : new PropertyValue(null));		
	}
	
	@XmlAttribute(name = "min")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getMinimum() {
		if (getProperties().containsKey(DATETIME_MINIMUM)) {
			PropertyValue returnValue = getProperties().get(DATETIME_MINIMUM);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;			
	}
	
	public void setMaximum(String maximum) {
		initProperties();
		getProperties().put(DATETIME_MAXIMUM, maximum != null ? 
				new PropertyValue(maximum) : new PropertyValue(null));	
	}
		
	@XmlAttribute(name = "max")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getMaximum() {
		if (getProperties().containsKey(DATETIME_MAXIMUM)) {
			PropertyValue returnValue = getProperties().get(DATETIME_MAXIMUM);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;
	}
	
	public void setCalendar(String calendar) {
		initProperties();
		getProperties().put(CALENDAR, calendar != null ? 
				new PropertyValue(calendar) : new PropertyValue(null));	
	}
		
	@XmlTransient
	public String getCalendar() {
		if (getProperties().containsKey(CALENDAR)) {
			PropertyValue returnValue = getProperties().get(CALENDAR);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;
	}
	
	// Non-Public API

	@XmlAttribute(name = "strict")
	String getStrict() {
		return "1";
	}
	
	@XmlAttribute(name = "mode")
	String getMode() {
		return "timestamp";
	}
	
	@XmlTransient
	String getKey() {
		if (getProperties().containsKey(KEY)) {
			PropertyValue returnValue = getProperties().get(KEY);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;
	}
	
	@XmlTransient
	String getOutputFormat() {
		if (getProperties().containsKey(OUTPUT_FORMAT)) {
			PropertyValue returnValue = getProperties().get(OUTPUT_FORMAT);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;
	}
		
}
