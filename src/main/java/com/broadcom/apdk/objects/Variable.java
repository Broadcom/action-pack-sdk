package com.broadcom.apdk.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlTransient
abstract class Variable<T> extends NonExecutableAutomicObject implements IVariable {

	private String lblSource;
	private String source;
	private Integer sortColumn;
	private VariableScope variableScope;
	private SortDirection sortDirection = SortDirection.ASCENDING;
	private KeyNotFoundAction keyNotFoundAction = KeyNotFoundAction.RETURN_INITIAL_VALUES;
	private List<KeyValueGroup<T>> values;

	public Variable() {
		super();
		initVariable();
	}

	public Variable(String name) {
		super(name);
		initVariable();
	}
	
	private void initVariable() {
		setVariableScope(VariableScope.FREE);
		setSortColumn(0);
	}

	public void setSortColumn(Integer sortColumn) {
		this.sortColumn = sortColumn;
	}

	@XmlPath("ATTR_VARA[@state='1']/sortColumn/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getSortColumn() {
		return this.sortColumn;
	}

	public void setSortDirection(SortDirection sortDirection) {
		this.sortDirection = sortDirection;
	}

	@XmlPath("ATTR_VARA[@state='1']/sortDirection/text()")
	public SortDirection getSortDirection() {
		return this.sortDirection;
	}

	public void setIfKeyNotFoundAction(KeyNotFoundAction action) {
		this.keyNotFoundAction = action;
	}

	@XmlPath("ATTR_VARA[@state='1']/text()")
	@XmlJavaTypeAdapter(KeyNotFoundAdapter.class)
	public KeyNotFoundAction getKeyNotFoundAction() {
		return this.keyNotFoundAction;
	}

	public void setValues(List<KeyValueGroup<T>> values) {
		this.values = values;
	}

	@SuppressWarnings("unchecked")
	@XmlPath("VARA[@state='1']/Variables/row")
	public List<KeyValueGroup<T>> getValues() {
		return values;
	}
	
	public void setVariableScope(VariableScope scope) {
		if (scope == null) {
			this.variableScope = VariableScope.FREE;	
		}
		else {
			this.variableScope = scope;
		}
	}

	@XmlTransient
	public VariableScope getVariableScope() {
		return variableScope;
	}

	// Non-Public API

	@XmlAttribute(name = "replacementmode")
	Integer getReplacementMode() {
		return 2;
	}

	@XmlAttribute(name = "data_type")
	String getDataType() {
		return "STATIC";
	}

	@XmlAttribute(name = "OVD_VRName")
	String getOvdVrName() {
		return variableScope.id;
	}
	
	@XmlPath("ATTR_VARA[@state='1']/ShareN/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getShareN() {
		return false;
	}
	
	@XmlPath("ATTR_VARA[@state='1']/ShareL/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getShareL() {
		return false;
	}
	
	@XmlPath("ATTR_VARA[@state='1']/ShareR/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getShareR() {
		return false;
	}

	@XmlPath("ATTR_VARA[@state='1']/HostType/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getHostType() {
		return null;
	}
	
	// Hack to ensure that an empty <Variables> element will be marshalled
	@XmlPath("VARA[@state='1']/Variables/text()")
	String getNoValues() {
		if (this.values == null) {
			return "";
		}
		return null;
	}
	
	protected void setLblSource(String lblSource) {
		this.lblSource = lblSource;
	}

	@XmlPath("ATTR_VARA[@state='1']/lblSource/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	protected String getLblSource() {
		return lblSource;
	}

	protected void setSource(String source) {
		this.source = source;
	}

	@XmlPath("ATTR_VARA[@state='1']/source/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	protected String getSource() {
		return source;
	}

	@XmlPath("ATTR_VARA[@state='1']/VRName/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getVRName() {
		return variableScope.id + "|" + variableScope.description;
	}
}
