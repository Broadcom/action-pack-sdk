package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "row")
public class OutputScanFilterObject {
	
	private Integer lnr;
	private String icon;
	private String filterObject;
	private Boolean applyFilterCriteria;
	private Integer returnCode;
	private String statusText;
	private String executeObject;
	
	@SuppressWarnings("unused")
	private OutputScanFilterObject() {}
	
	public OutputScanFilterObject(String filterObject, Boolean applyFilterCriteria, 
			Integer returnCode, String statusText, String executeObject) {
		initObject(null, filterObject, applyFilterCriteria, returnCode, statusText, executeObject);		
	}
	
	public OutputScanFilterObject(Integer lnr, String filterObject, Boolean applyFilterCriteria, 
			Integer returnCode, String statusText, String executeObject) {
		initObject(lnr, filterObject, applyFilterCriteria, returnCode, statusText, executeObject);
	}
	
	private void initObject(Integer lnr, String filterObject, Boolean applyFilterCriteria, 
			Integer returnCode, String statusText, String executeObject) {
		this.lnr = lnr;
		this.filterObject = filterObject;
		this.applyFilterCriteria = applyFilterCriteria;
		this.returnCode = returnCode;
		this.statusText = statusText;
		this.executeObject = executeObject;
		this.icon = "FILTER";
	}
	
	@XmlAttribute(name = "lnr")
	public Integer getLineNumber() {
		return lnr;
	}
	
	@XmlAttribute(name = "Icon")
	public String getIcon() {
		return icon;
	}
	
	@XmlTransient
	public Boolean isApplyFilterCriteria() {
		return applyFilterCriteria != null ? applyFilterCriteria : false;
	}
	
	@XmlAttribute(name = "condition")
	public String isCondition() {
		return applyFilterCriteria != null ? (applyFilterCriteria ? "true" : "false"): "false";
	}
	
	@XmlAttribute(name = "return")
	public Integer getReturnCode() {
		return returnCode;
	}
	
	@XmlAttribute(name = "statustext")
	public String getStatusText() {
		return statusText;
	}
	
	@XmlAttribute(name = "execute")
	public String getExecuteObject() {
		return executeObject;
	}

	@XmlAttribute(name = "filter")
	public String getFilterObject() {
		return filterObject;
	}
}
