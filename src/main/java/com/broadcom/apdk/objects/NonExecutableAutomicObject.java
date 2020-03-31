package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlTransient
public abstract class NonExecutableAutomicObject extends AutomicObject {
	
	private String title;
	private ObjectType type;
	private String archiveKey1;
	private String archiveKey2;
	private String ohSubType;

	public NonExecutableAutomicObject() {
		super();
	}
	
	public NonExecutableAutomicObject(String name) {
		super(name);
	}
		
	public void setTitle(String title) {
		this.title = title;
	}

	@XmlPath("HEADER[@state='1']/Title/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getTitle() {
		return this.title;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}
	
	@XmlPath("HEADER[@state='1']/OH_SubType/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public ObjectType getType() {
		return this.type;
	}

	public void setArchiveKey1(String archiveKey1) {
		this.archiveKey1 = archiveKey1;
	}

	@XmlPath("HEADER[@state='1']/ArchiveKey1/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getArchiveKey1() {
		return this.archiveKey1;
	}

	public void setArchiveKey2(String archiveKey2) {
		this.archiveKey2 = archiveKey2;
	}

	@XmlPath("HEADER[@state='1']/ArchiveKey2/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getArchiveKey2() {
		return this.archiveKey2;
	}
	
	// Non-Public API
	
	protected void setOHSubType(String ohSubType) {
		this.ohSubType = ohSubType;
	}
	
	@XmlPath("HEADER[@state='1']/OH_SubType/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	protected String getOHSubType() {
		return ohSubType;
	}

}