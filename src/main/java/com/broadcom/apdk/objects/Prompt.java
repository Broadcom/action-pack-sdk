package com.broadcom.apdk.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import com.broadcom.apdk.helpers.VariableHelper;

@XmlTransient
public abstract class Prompt<T> implements IPrompt<T> {

	static final String INITVALUE = "initvalue";
	static final String TEXT = "text";
	static final String ID = "id";
	static final String ENABLED = "enabled";
	static final String FOCUS = "focus";
	static final String TOOLTIP = "tooltip";
	static final String REFERENCE = "reference";
	static final String CUSTOMFIELD = "customfield";
	
	private List<PromptCommand> commands;
	private Map<String, PropertyValue> properties;
	private String variableName;
	private String promptSetName;
	
	public Prompt() {
		initPrompt(null, null, null);
	}
	
	public Prompt(String variableName, String label, String tooltip) {
		initPrompt(variableName, label, tooltip);
	}
	
	public Prompt(String variableName, String label, String tooltip, String refType, 
			String dataReference, String listParam, T initValue) {	
		initPrompt(variableName, label, tooltip);
		Map<String, PropertyValue> propertiesMap = this.getProperties();
		PropertyValue value = new PropertyValue(dataReference);
		value.setRefType(refType);
		value.setListParam(listParam);
		propertiesMap.put(REFERENCE, value);
		setProperties(propertiesMap);
		setValue(initValue);
	}
	
	private void initPrompt(String variableName, String label, String tooltip) {
		this.variableName = VariableHelper.getSanitizedValue(variableName);
		
		Map<String, PropertyValue> properties = new HashMap<String, PropertyValue>();
		properties.put(TEXT, new PropertyValue(label));
		properties.put(ID, new PropertyValue(this.variableName));
		properties.put(ENABLED, new PropertyValue("0"));
		properties.put(FOCUS, new PropertyValue("0"));
		properties.put(TOOLTIP, new PropertyValue(tooltip));
		properties.put(CUSTOMFIELD, new PropertyValue(null));
		properties.put(INITVALUE, new PropertyValue(null));
		properties.put("xmlkey", new PropertyValue(null));
		properties.put("xmlpath", new PropertyValue(null));
		properties.put("modifiable", new PropertyValue(null));
		properties.put("onchangereset", new PropertyValue(null));
		properties.put("resetable", new PropertyValue(null));
		setProperties(properties);		
	}
	
	@XmlTransient
	public String getValueAsString() {
		if (properties.containsKey(INITVALUE)) {
			PropertyValue returnValue = properties.get(INITVALUE);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;		
	}

	@XmlTransient
	public String getCustomField() {
		if (properties.containsKey(CUSTOMFIELD)) {
			PropertyValue returnValue = properties.get(CUSTOMFIELD);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;
	}
	
	public void setCustomField(String customField) {
		if (properties == null) {
			properties = new HashMap<String, PropertyValue>();		
		}
		properties.put(CUSTOMFIELD, customField != null ? new PropertyValue(customField) : null);	
	}
	
	public void setLabel(String label) {
		if (properties == null) {
			properties = new HashMap<String, PropertyValue>();		
		}
		properties.put(TEXT, label != null ? new PropertyValue(label) : null);		
	}
	
	@XmlAttribute(name = "text")
	public String getLabel() {
		if (properties.containsKey(TEXT)) {
			PropertyValue returnValue = properties.get(TEXT);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;			
	}
	
	public void setVariableName(String variableName) {
		if (properties == null) {
			properties = new HashMap<String, PropertyValue>();		
		} 
		properties.put(ID, variableName != null ? new PropertyValue(VariableHelper.getSanitizedValue(variableName)) : null);		
	}
	
	@XmlAttribute(name = "id")
	public String getId() {
		return this.variableName;			
	}

	@XmlTransient
	public String getVariableName() {
		if (properties.containsKey(ID)) {		
			PropertyValue returnValue = properties.get(ID);
			return returnValue != null ? VariableHelper.getOriginalValue(returnValue.getValue()) : null;
		}
		return null;			
	}
	
	public void setTooltip(String tooltip) {
		if (properties == null) {
			properties = new HashMap<String, PropertyValue>();		
		}
		properties.put(TOOLTIP, tooltip != null ? new PropertyValue(tooltip) : null);
	}
	
	@XmlAttribute(name = "tooltip")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getTooltip() {
		if (properties.containsKey(TOOLTIP)) {
			PropertyValue returnValue = properties.get(TOOLTIP);
			return returnValue != null ? returnValue.getValue() : null;

		}
		return null;		
	}
	
	public void setReadOnly(Boolean readonly) {
		if (properties == null) {
			properties = new HashMap<String, PropertyValue>();		
		}
		properties.put(ENABLED, new PropertyValue(readonly ? "1" : "0"));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@XmlTransient
	public Boolean getReadOnly() {
		if (properties.containsKey(ENABLED)) {
			return properties.get(ENABLED).equals("1") ? true : false;
		}
		return false;
	}
	
	public void setFocus(Boolean focus) {
		if (properties == null) {
			properties = new HashMap<String, PropertyValue>();		
		}
		properties.put(FOCUS, new PropertyValue(focus ? "1" : "0"));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@XmlTransient
	public Boolean getFocus() {
		if (properties.containsKey(FOCUS)) {
			return properties.get(FOCUS).equals("1") ? true : false;
		}
		return false;
	}
	
	public void setDataReference(String dataReference) {
		if (properties == null) {
			properties = new HashMap<String, PropertyValue>();		
		}
		properties.put(REFERENCE, dataReference != null ? new PropertyValue(dataReference) : null);	
	}

	@XmlTransient
	public String getDataReference() {
		if (properties.containsKey(REFERENCE)) {
			PropertyValue returnValue = properties.get(REFERENCE);
			return returnValue != null ? returnValue.getValue() : null;

		}
		return null;
	}
	
	public void setPromptSetName(String promptSetName) {
		this.promptSetName = promptSetName;
	}
	
	@XmlTransient
	public String getPromptSetName() {
		return promptSetName;
	}
	
	// Non-Public API
	
	@XmlPath("properties/text()")
	@XmlJavaTypeAdapter(PropertyAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Map<String, PropertyValue> getProperties() {
		if (properties != null) {
			for (String key : properties.keySet()) {
				if (properties.get(key) != null) {
					properties.get(key).setParentPrompt(this);
				}
			}
		}
		return properties;
	}

	protected void setProperties(Map<String, PropertyValue> properties) {
		this.properties = properties;
	}
	
	protected List<PromptCommand> getCommands() {
		return commands;	
	}
	
	protected void setCommands(List<PromptCommand> commands) {
		this.commands = commands;
	}	
	
	@XmlAttribute(name = "nl")
	String getNl() {
		return "1";
	}
	
	@XmlAttribute(name = "fill")
	String getFill() {
		return "b";
	}
	
	@XmlAttribute(name = "focus")
	String getFocusAttr() {
		return "0";
	}
	
	@XmlAttribute(name = "enabled")
	String getEnabled() {
		return "1";
	}
	
	@XmlAttribute(name = "alt")
	String getAlt() {
		return "1";
	}
	
	void initProperties() {
		if (getProperties() == null) {
			setProperties(new HashMap<String, PropertyValue>());		
		}
	}

}
