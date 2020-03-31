package com.broadcom.apdk.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;

@XmlRootElement(name = "FOLD")
public class Folder implements IAutomicContentFolder {
	
	private String name;
	private String title;
	
	private List<IAutomicContent> objects;
	
	public Folder() {}
	
	public Folder(String name) {
		this.setName(name);
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlAnyElement(lax = true)
	@XmlJavaTypeAdapter(AutomicContentAdapter.class)
	public List<IAutomicContent> getObjects() {
		return objects;
	}

	public void setObjects(List<IAutomicContent> objects) {
		this.objects = objects;
	}
	
}
