package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

class DocumentationAttr  {
	
	private DocumentationType type;
	private String documentation;
	
	public DocumentationAttr() {}
	
	public DocumentationAttr(String name, String documentation) {
		this.setType(DocumentationType.TEXT);
		this.setDocumentation(documentation);
	}

	public DocumentationAttr(DocumentationType type, String documentation) {
		this.setType(type);
		this.setDocumentation(documentation);
	}
	
	@XmlAttribute
	public DocumentationType getType() {
		return type;
	}

	public void setType(DocumentationType type) {
		this.type = type;
	}

	@XmlPath("DOC/text()")
	@XmlCDATA
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
}
