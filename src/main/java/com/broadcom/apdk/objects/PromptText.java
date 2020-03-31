package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

@XmlRootElement(name = "text")
@XmlType (propOrder={"commands", "properties"})
@PropertyOrder(keys = {"reference", "text", "multiselect", "separator", "len", "quotes", 
		"id", "inputassistance", "enabled", "upper", "focus", "showaspassword", "tooltip", 
		"customfield", "xmlkey", "xmlpath", "modifiable", "multiline", "multilineIcon", 
		"regex", "required", "initvalue", "onchangereset", "resetable"})
public class PromptText extends Prompt<String> implements IPromptText {
	
	private final String REQUIRED = "required";
	private final String SEPARATOR = "separator";
	private final String MULTILINE = "multiline";
	private final String MULTISELECT = "multiselect";
	private final String ASSISTANCE = "inputassistance";
	private final String UPPER = "upper";
	private final String REGEX = "regex";
	private final String MAXLENGTH = "len";
	private final String PASSWORD = "showaspassword";
	private final String QUOTES = "quotes";
	
	public PromptText() {
		super();
		initPrompt();
	}
	
	public PromptText(String variableName, String label, String tooltip) {
		super(variableName, label, tooltip);
		initPrompt();
	}
	
	public PromptText(String variableName, String label, String tooltip, String refType, 
			String dataReference) {
		super(variableName, label, tooltip, refType, dataReference, "C", null);
		initPrompt();
	}
	
	public PromptText(String variableName, String label, String tooltip, String refType, 
			String dataReference, String initValue) {
		super(variableName, label, tooltip, refType, dataReference, "C", initValue);
		initPrompt();
	}
	
	private void initPrompt() {
		initProperties();		
		getProperties().put("resetable", new PropertyValue("1"));
		getProperties().put("modifiable", new PropertyValue("1"));
		getProperties().put("multilineIcon", new PropertyValue("0"));
		getProperties().put(REQUIRED, new PropertyValue("0"));
		getProperties().put(SEPARATOR, new PropertyValue(";"));
		getProperties().put(MULTILINE, new PropertyValue("0"));
		getProperties().put(MULTISELECT, new PropertyValue("0"));
		getProperties().put(ASSISTANCE, new PropertyValue("0"));
		getProperties().put(UPPER, new PropertyValue("0"));
		getProperties().put(REGEX, new PropertyValue(null));
		getProperties().put(MAXLENGTH, new PropertyValue(null));
		getProperties().put(PASSWORD, new PropertyValue("0"));
		getProperties().put(QUOTES, new PropertyValue(null));
		
		List<PromptCommand> commands = new ArrayList<PromptCommand>();
		
		PromptCommand c1 = new PromptCommand();
		c1.setTarget("_view");
		c1.setTargetAction("setAttribute");
		c1.setTargetParam("promptname|@id");
		c1.setRequest("_internal");
		commands.add(c1);
		
		PromptCommand c2 = new PromptCommand();
		c2.setTarget("_view");
		c2.setTargetAction("setAttribute");
		c2.setTargetParam("promptvalue|@value");
		c2.setRequest("_internal");
		commands.add(c2);
		
		PromptCommand c3 = new PromptCommand();
		c3.setOwner("_promptsetcontainer");
		c3.setOwnerAction("getData");
		c3.setTarget("_chainwindow");
		c3.setRequest("getpromptinputassistance");
		commands.add(c3);
		
		this.setCommands(commands);
	}
	
	@Override
	public void setValue(String value) {
		initProperties();
		getProperties().put(Prompt.INITVALUE, value != null ? 
				new PropertyValue(value) : new PropertyValue(null));	
	}

	@Override
	@XmlTransient
	public String getValue() {
		if (getProperties().containsKey(Prompt.INITVALUE)) {
			PropertyValue returnValue = getProperties().get(Prompt.INITVALUE);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;
	}

	public void setMultiselect(Boolean multiselect) {
		setBooleanPropertyValue(MULTISELECT, multiselect);
	}

	@XmlTransient
	public Boolean isMultiselect() {
		return getBooleanPropertyValue(MULTISELECT);
	}
	
	public void setInputAssistance(Boolean inputAssistance) {
		setBooleanPropertyValue(ASSISTANCE, inputAssistance);
	}

	@XmlAttribute(name = "inputassistance")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isInputAssistance() {
		return getBooleanPropertyValue(ASSISTANCE);
	}
	
	public void setMultiline(Boolean multiline) {
		setBooleanPropertyValue(MULTILINE, multiline);
	}

	@XmlAttribute
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isMultiline() {
		return getBooleanPropertyValue(MULTILINE);
	}
	
	@XmlTransient
	public String getQuotes() {
		if (getProperties().containsKey(QUOTES)) {
			PropertyValue returnValue = getProperties().get(QUOTES);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;
	}

	public void setQuotes(String quotes) {
		if (getProperties() == null) {
			setProperties(new HashMap<String, PropertyValue>());		
		}
		getProperties().put(QUOTES, quotes != null ? 
				new PropertyValue(quotes) : new PropertyValue(null));	
	}
	
	public void setUpperCase(Boolean upper) {
		setBooleanPropertyValue(UPPER, upper);
	}

	@XmlAttribute(name = "upper")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isUpperCase() {
		return getBooleanPropertyValue(UPPER);
	}
	
	public void setShowPassword(Boolean showPassword) {
		setBooleanPropertyValue(PASSWORD, showPassword);
	}

	@XmlAttribute(name = "showaspassword")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isShowPassword() {
		return getBooleanPropertyValue(PASSWORD);
	}

	public void setRequired(Boolean required) {
		setBooleanPropertyValue(REQUIRED, required);
	}

	@XmlAttribute(name = "required")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isRequired() {
		return getBooleanPropertyValue(REQUIRED);
	}
	
	@XmlAttribute(name = "separator")
	String getSeparator() {
		return ";";
	}
	
	public void setRegEx(String regex) {
		setStringPropertyValue(REGEX, regex);
	}
	
	@XmlAttribute(name = "regex")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getRegEx() {
		return getStringPropertyValue(REGEX);
	}
	
	public void setMaxLength(Integer length) {
		if (length != null) {
			setStringPropertyValue(MAXLENGTH, Integer.toString(length.intValue()));	
		}
	}
	
	@XmlAttribute(name = "len")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getMaxLength() {
		String propertyValue = getStringPropertyValue(MAXLENGTH);
		if (propertyValue != null) {
			return Integer.parseInt(getStringPropertyValue(MAXLENGTH));	
		}
		return null;
	}
	
	// Non-Public API
	
	@XmlAttribute(name = "multilineIcon")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isMultiLineIcon() {
		return false;
	}
	
	@Override
	@XmlElement(name = "command")
	@XmlElementWrapper(name = "oninputassistant")
	protected List<PromptCommand> getCommands() {
		return super.getCommands();
	}
	
	private String getStringPropertyValue(String propertyName) {
		if (getProperties().containsKey(propertyName)) {
			PropertyValue returnValue = getProperties().get(propertyName);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;		
	}
	
	private void setStringPropertyValue(String propertyName, String value) {
		if (getProperties() == null) {
			setProperties(new HashMap<String, PropertyValue>());		
		}
		getProperties().put(propertyName, value != null ? 
				new PropertyValue(value) : new PropertyValue(null));		
	}
	
	@SuppressWarnings("unlikely-arg-type")
	private Boolean getBooleanPropertyValue(String propertyName) {
		if (getProperties().containsKey(propertyName)) {
			return getProperties().get(propertyName).equals("1") ? true : false;
		}
		return false;		
	}
	
	private void setBooleanPropertyValue(String propertyName, Boolean value) {
		if (getProperties() == null) {
			setProperties(new HashMap<String, PropertyValue>());		
		}
		getProperties().put(propertyName, new PropertyValue(value ? "1" : "0"));		
	}

}
