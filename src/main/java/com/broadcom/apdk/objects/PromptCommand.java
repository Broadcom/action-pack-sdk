package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;

class PromptCommand {
	
	private String targetAction;
	private String targetParam;
	private String target;
	private String request;
	private String ownerAction;
	private String ownerParam;
	private String owner;
	private String wait;
	
	@XmlAttribute(name = "targetaction")
	String getTargetAction() {
		return targetAction;
	}
	
	void setTargetAction(String targetAction) {
		this.targetAction = targetAction;
	}
	
	@XmlAttribute(name = "targetparam")
	String getTargetParam() {
		return targetParam;
	}
	
	void setTargetParam(String targetParam) {
		this.targetParam = targetParam;
	}
	
	@XmlAttribute
	String getTarget() {
		return target;
	}
	
	void setTarget(String target) {
		this.target = target;
	}
	
	@XmlAttribute
	String getRequest() {
		return request;
	}
	
	void setRequest(String request) {
		this.request = request;
	}
	
	@XmlAttribute(name = "owneraction")
	String getOwnerAction() {
		return ownerAction;
	}
	
	void setOwnerAction(String ownerAction) {
		this.ownerAction = ownerAction;
	}
	
	@XmlAttribute(name = "ownerparam")
	String getOwnerParam() {
		return ownerParam;
	}
	
	void setOwnerParam(String ownerParam) {
		this.ownerParam = ownerParam;
	}
	
	@XmlAttribute
	String getOwner() {
		return owner;
	}
	
	void setOwner(String owner) {
		this.owner = owner;
	}
	
	@XmlAttribute
	String getWait() {
		return wait;
	}
	
	void setWait(String wait) {
		this.wait = wait;
	}

}
