package com.broadcom.apdk.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "VARA")
@XmlType (propOrder={
		// <HEADER>
		"title", "archiveKey1", "archiveKey2", "type", "OHSubType", 
		// <ATTR_VARA>
		"dataTypeName", "lblSource", "source", "VRName", "keyNotFoundAction", "shareN", 
		"shareL", "shareR", "maxTextLength", "textLengthLimited",
		"upperCaseForced", "sortColumn", "sortDirection", "hostType",
		// <VARIABLES>
		"values", "noValues",
		// <DOCU>
		"documentation"})
public class VariableText extends Variable<String> implements IVariableText {
	
	private String dataTypeName = "C|Text";
	private Boolean limitTextLength = false;
	private Integer maxTextLength = 1024;
	private Boolean forceUpperCase = false;
	
	public VariableText() {
		super();
		initVariable();
	}
	
	public VariableText(String name) {
		super(name);
		initVariable();
	}
	
	private void initVariable() {
		setOHSubType("STATIC");
		setSource("STATIC");
		setLblSource("Static");
	}
	
	@XmlPath("ATTR_VARA[@state='1']/Type/text()")
	public String getDataTypeName() {
		return dataTypeName;
	}

	public void limitTextLength(Boolean limitTextLength) {
		this.limitTextLength = limitTextLength;
	}

	@XmlPath("ATTR_VARA[@state='1']/CheckMax_C/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isTextLengthLimited() {
		return limitTextLength;
	}

	public void forceUpperCase(Boolean forceUpperCase) {
		this.forceUpperCase = forceUpperCase;
	}

	@XmlPath("ATTR_VARA[@state='1']/uppercase/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isUpperCaseForced() {
		return forceUpperCase;
	}

	public void setMaxTextLength(Integer maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	@XmlPath("ATTR_VARA[@state='1']/MaxValue_C/text()")
	public Integer getMaxTextLength() {
		return maxTextLength;
	}
	
    public void setValues(List<KeyValueGroup<String>> values) {
        super.setValues(values);
    }
	
    @SuppressWarnings("unchecked")
	@XmlPath("VARA[@state='1']/Variables/row")
	public List<KeyValueGroup<String>> getValues() {
        return super.getValues();
    }

}
