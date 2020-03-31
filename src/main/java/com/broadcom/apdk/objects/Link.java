package com.broadcom.apdk.objects;

public class Link implements IAutomicContentLink {
	
	private IAutomicObject object;
	
	public Link() {}
	
	public Link(IAutomicObject object) {
		this.object = object;
	}

	public IAutomicObject getObject() {
		return object;
	}

	public void setObject(IAutomicObject object) {
		this.object = object;
	}
	
}
