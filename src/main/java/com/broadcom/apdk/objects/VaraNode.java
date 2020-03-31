package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlType (propOrder={"variables", "mode"})
class VaraNode extends VaraPromptNode {
	
	private List<VariableEntry> variables;
	private Integer state = 1;
	
	private VaraNode() {
		super("Variables", "VALUE", null, "VALUE");
		variables = new ArrayList<VariableEntry>();
	}
	
	VaraNode(List<VariableEntry> variables) {
		super("Variables", "VALUE", null, "VALUE");
		setVariables(variables);
	}
	
	VaraNode(String name, String id, String parent, String type, List<VariableEntry> variables) {
		super(name, id, parent, type);
		setVariables(variables);
	}

	@XmlAnyElement(lax = true)	
	@XmlElementWrapper
	@XmlPath("VALUE/Values")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	List<VariableEntry> getVariables() {
		return variables;
	}
	
	void setVariables(List<VariableEntry> variables) {
		if (variables == null) {
			variables = new ArrayList<VariableEntry>();	
		}
		else {
			this.variables = variables;
		}
	}

	@XmlPath("VALUE/Mode/text()")
	String getMode() {
		return "0";
	}
	
	void setState(Integer state) {
		this.state = state;
	}
	
	@XmlPath("VALUE/@state")
	Integer getState() {
		return state;
	}
}
