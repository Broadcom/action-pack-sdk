package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

@XmlRootElement(name = "combo")
@XmlType (propOrder={"commands", "properties"})
@PropertyOrder(keys = {"reference", "text", "quotes", "id", "enabled", "focus", 
		"tooltip", "dynamic", "customfield", "xmlkey", "xmlpath", "modifiable", 
		"initvalue", "onchangereset", "resetable"})
public class PromptCombo extends Prompt<String> implements IPromptCombo {
	
	private final String DYNAMIC = "dynamic";
	private final String QUOTES = "quotes";
	private final String RESETABLE = "resetable";
	private final String MODIFIABLE = "modifiable";
	
	public PromptCombo() {
		super();
		initPrompt();
	}
	
	public PromptCombo(String variableName, String label, String tooltip) {
		super(variableName, label, tooltip);
		initPrompt();
	}
	
	public PromptCombo(String variableName, String label, String tooltip, String refType, 
			String dataReference) {
		super(variableName, label, tooltip, refType, dataReference, "C", null);
		initPrompt();
	}
	
	public PromptCombo(String variableName, String label, String tooltip, String refType, 
			String dataReference, String initValue) {
		super(variableName, label, tooltip, refType, dataReference, "C", initValue);
		initPrompt();
	}
	
	private void initPrompt() {
		initProperties();
		getProperties().put(RESETABLE, new PropertyValue("1"));
		getProperties().put(MODIFIABLE, new PropertyValue("1"));
		getProperties().put(DYNAMIC, new PropertyValue("0"));
		getProperties().put(QUOTES, new PropertyValue(null));
		
		List<PromptCommand> commands = new ArrayList<PromptCommand>();
		
		PromptCommand c1 = new PromptCommand();
		c1.setTarget("_view");
		c1.setTargetAction("setAttribute");
		c1.setTargetParam("promptname|@id");
		c1.setRequest("_internal");
		commands.add(c1);
		
		PromptCommand c2 = new PromptCommand();
		c2.setOwner("_self");
		c2.setOwnerAction("setsource");
		c2.setOwnerParam("src|_dialog@src");
		commands.add(c2);
		
		PromptCommand c3 = new PromptCommand();
		c3.setOwner("_promptsetcontainer");
		c3.setOwnerAction("getData");
		c3.setTarget("_self");
		c3.setTargetAction("setlist");
		c3.setRequest("getpromptcombolist");
		c3.setWait("0");
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
	
	public void setDynamic(Boolean dynamic) {
		initProperties();
		getProperties().put(DYNAMIC, dynamic != null ? 
				new PropertyValue(dynamic ? "1" : "0") : new PropertyValue(null));		
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@XmlAttribute(name = "dynamic")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getDynamic() {
		if (getProperties().containsKey(DYNAMIC)) {
			return getProperties().get(DYNAMIC).equals("1") ? true : false;
		}
		return null;			
	}
		
	@XmlAttribute(name = "quotes")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getQuotes() {
		if (getProperties().containsKey(QUOTES)) {
			PropertyValue returnValue = getProperties().get(QUOTES);
			return returnValue != null ? returnValue.getValue() : null;
		}
		return null;
	}

	public void setQuotes(String quotes) {
		initProperties();
		getProperties().put(QUOTES, quotes != null ? 
				new PropertyValue(quotes) : new PropertyValue(null));	
	}
	
	@Override
	@XmlElement(name = "command")
	protected List<PromptCommand> getCommands() {
		return super.getCommands();
	}
	
	// Non-Public API

	@XmlAttribute(name = "strict")
	String getStrict() {
		return "1";
	}
	
	@XmlAttribute(name = "vtype")
	String getVType() {
		return "i";
	}
	
	@XmlAttribute(name = "filter")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getFilter() {
		return false;
	}

}
