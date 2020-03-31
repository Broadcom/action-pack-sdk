package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

public abstract class VaraPromptNode implements IVaraPromptNode {
	
	private String name;
	private String id;
	private String parent;
	private String type;
	private Boolean content;
	
	@SuppressWarnings("unused")
	private VaraPromptNode() {}
	
	public VaraPromptNode(String name, String id, String parent, String type) {
		this.name = name;
		this.id = id;
		this.parent = parent;
		this.type = type;
		this.content = true;
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getName() {
		return name;
	}
	
	@XmlAttribute
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isContent() {
		return content;
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getId() {
		return id;
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getParent() {
		return parent;
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getType() {
		return type;
	}

	// Non-Public API
	
	protected void setContent(Boolean content) {
		this.content = content;
	}
}
