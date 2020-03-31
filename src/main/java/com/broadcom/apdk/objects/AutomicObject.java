package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlTransient
public abstract class AutomicObject implements IAutomicObject {
	
	private String name;
	private String documentation;
	
	public AutomicObject() {}
	
	public AutomicObject(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public String getName() {
		return this.name;
	}
	
	public void setDocumentation(String documentation) {
		this.documentation = documentation;	
	}
	
	@XmlPath("DOCU_Docu[@state='1']/text()")
	@XmlJavaTypeAdapter(DocumentationAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getDocumentation() {
		return documentation;
	}
	
	@XmlPath("DOCU_Docu[@state='1']/@type")
	String getDocumentationType() {
		return "text";
	}
	
}
