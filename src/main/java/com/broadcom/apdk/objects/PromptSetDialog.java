package com.broadcom.apdk.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "dialog")
@XmlType (propOrder={"properties", "prompts"})
class PromptSetDialog {
	
	private final String ID = "PRPTS";
	private final String ICON = "PRPT";
	
	private String title;
	private List<IPrompt<?>> prompts;
	
	private Integer left;
	private Integer top;
	private Integer height;
	private Integer width;
	private String id;
	private String icon;
	private Map<String, PropertyValue> properties;
	
	PromptSetDialog() {
		initDialog(null);
	}
	
	PromptSetDialog(String title) {
		initDialog(title);
	}
	
	PromptSetDialog(String title, List<IPrompt<?>> prompts) {
		initDialog(title);
		setPrompts(prompts);
	}
	
	void initDialog(String title) {
		setTitle(title);
		setId(ID);
		setIcon(ICON);
		setWidth(657);
		setLeft(279);
		setHeight(114);
		setTop(4);
		
		Map<String, PropertyValue> properties = new HashMap<String, PropertyValue>();
		properties.put("text", new PropertyValue(title != null ? title : "PRPT"));
		properties.put("modifiable", new PropertyValue("0"));
		setProperties(properties);
	}
	
	void setTitle(String title) {
		this.title = title;
	}

	@XmlPath("readpanel[@id='PRPTBOX']/@text")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getTitle() {
		return this.title != null ? this.title : "PRPT";
	}

	void setPrompts(List<IPrompt<?>> prompts) {
		this.prompts = prompts;
	}

	@XmlPath("readpanel[@id='PRPTBOX']")
	@XmlAnyElement(lax = true)
	@XmlJavaTypeAdapter(PromptAdapter.class)
	List<IPrompt<?>> getPrompts() {
		return this.prompts;
	}
	
	// Non-Public API
	
	@XmlPath("readpanel[@id='PRPTBOX']/@nl")
	String getNl() {
		return "1";
	}
	
	@XmlPath("readpanel[@id='PRPTBOX']/@fill")
	String getFill() {
		return "b";
	}
	
	@XmlPath("readpanel[@id='PRPTBOX']/@scroll")
	String getScroll() {
		return "v";
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Integer getLeft() {
		return left;
	}

	void setLeft(Integer left) {
		this.left = left;
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Integer getTop() {
		return top;
	}

	void setTop(Integer top) {
		this.top = top;
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Integer getHeight() {
		return height;
	}

	void setHeight(Integer height) {
		this.height = height;
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Integer getWidth() {
		return width;
	}

	void setWidth(Integer width) {
		this.width = width;
	}
	
	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getId() {
		return id;
	}

	void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getIcon() {
		return icon;
	}

	void setIcon(String icon) {
		this.icon = icon;
	}

	@XmlPath("readpanel[@id='PRPTBOX']/properties/text()")
	@XmlJavaTypeAdapter(PropertyAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Map<String, PropertyValue> getProperties() {
		return properties;
	}

	void setProperties(Map<String, PropertyValue> properties) {
		this.properties = properties;
	}

}
