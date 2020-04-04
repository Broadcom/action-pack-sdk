package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class AutomicContentReference implements IAutomicContentReference {
	
	private Boolean link = false;
	private String name;
	
	public AutomicContentReference() {}
	
	public AutomicContentReference(String name, Boolean isLink) {
		this.setName(name);
		this.setLink(isLink);
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isLink() {
		return link;
	}

	public void setLink(Boolean isLink) {
		this.link = isLink;
	}

}
