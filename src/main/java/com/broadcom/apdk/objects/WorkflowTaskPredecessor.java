package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;

public class WorkflowTaskPredecessor {
	
	private Integer number;
	private String ifStatus;
	private Integer predecessorNumber;
	
	public WorkflowTaskPredecessor() {}
	
	public WorkflowTaskPredecessor(Integer number, String ifStatus, Integer predecessorNumber) {
		setNumber(number);
		setIfStatus(ifStatus);
		setPredecessorNumber(predecessorNumber);
	}

	@XmlAttribute(name = "Lnr")
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@XmlAttribute(name = "When")
	public String getIfStatus() {
		return ifStatus;
	}

	public void setIfStatus(String ifStatus) {
		this.ifStatus = ifStatus;
	}

	@XmlAttribute(name = "PreLnr")
	public Integer getPredecessorNumber() {
		return predecessorNumber;
	}

	public void setPredecessorNumber(Integer predecessorNumber) {
		this.predecessorNumber = predecessorNumber;
	}
	
	@XmlAttribute(name = "type")	
	String getType() {
		return "container";
	}

	@XmlAttribute(name = "BranchType")
	String getBranchType() {
		return "0";
	}

}
