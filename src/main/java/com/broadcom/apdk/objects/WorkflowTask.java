package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlType (propOrder={"checkpoint", "after", "when", "preconditions", "predecessors", "runtime", 
		"result", "varaPromptSetNodes", "CIT", "calendars", "postconditions"})
public class WorkflowTask implements IWorkflowTask {
	
	private Integer number;
	private Integer row;
	private Integer column;
	private String objectName;
	private String objectType;
	private String parentObject;
	private String parentAlias;
	private String branchType;
	private String alias;
	private StatusMismatchOrTimeFailure statusMismatchOrTimeFailure;
	private List<WorkflowTaskPredecessor> predecessors;
	private List<IPromptSet> promptSets;
	private IWorkflow workflow;
	
	protected WorkflowTask() {}
	
	public WorkflowTask(Integer number, String objectName, String objectType, 
			Integer row, Integer column, List<WorkflowTaskPredecessor> predecessors) {
		initWorkflow(number, objectName, objectType, row, column, predecessors, null);		
	}
	
	public WorkflowTask(Integer number, String objectName, String objectType, 
			Integer row, Integer column, List<WorkflowTaskPredecessor> predecessors,
			List<IPromptSet> promptSets) {
		initWorkflow(number, objectName, objectType, row, column, predecessors, promptSets);		
	}
	
	private void initWorkflow(Integer number, String objectName, String objectType, 
			Integer row, Integer column, List<WorkflowTaskPredecessor> predecessors,
			List<IPromptSet> promptSets) {
		setNumber(number);
		setObject(objectName);
		setObjectType(objectType);
		setRow(row);
		setColumn(column);
		setBranchType("0");
		setPredecessors(predecessors);
		setPromptSets(promptSets);
		setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure.BLOCK_TASK);
	}
	
	public void setObject(String name) {
		this.objectName = name;
	}
	
	@XmlAttribute(name = "Object")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getObject() {
		return objectName;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	@XmlAttribute(name = "OType")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getObjectType() {
		return objectType;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@XmlAttribute(name = "Lnr")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getNumber() {
		return number;
	}
	
	public void setRow(Integer row) {
		this.row = row;
	}
	
	@XmlAttribute(name = "Row")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getRow() {
		return row;
	}
	
	public void setColumn(Integer column) {
		this.column = column;
	}
	
	@XmlAttribute(name = "Col")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getColumn() {
		return column;
	}

	public void setPredecessors(List<WorkflowTaskPredecessor> predecessors) {
		if (predecessors == null || predecessors.isEmpty()) {
			this.predecessors = new ArrayList<WorkflowTaskPredecessor>();	
		}
		else {
			this.predecessors = predecessors;
		}
	}
	
	@XmlElementWrapper(name = "predecessors")
	@XmlElement(name = "pre")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public List<WorkflowTaskPredecessor> getPredecessors() {
		return predecessors;
	}
	
	public void setParentObject(String parentObject) {
		this.parentObject = parentObject;
	}
	
	@XmlAttribute(name = "ParentObject")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getParentObject() {
		return parentObject;
	}
	
	public void setParentAlias(String parentAlias) {
		this.parentAlias = parentAlias;
	}
	
	@XmlAttribute(name = "ParentAlias")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getParentAlias() {
		return parentAlias;
	}
	
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	
	@XmlAttribute(name = "BranchType")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getBranchType() {
		return branchType;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@XmlAttribute(name = "Alias")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getAlias() {
		return alias;
	}
	
	public void setPromptSets(List<IPromptSet> promptSets) {
		this.promptSets = promptSets;
	}
	
	@XmlTransient	
	public List<IPromptSet> getPromptSets() {
		return promptSets;
	}
	
	public void setWorkflow(IWorkflow workflow) {
		this.workflow = workflow;
	}
	
	@XmlTransient
	public IWorkflow getWorkflow() {
		return workflow;
	}
	
	public void setStatusMismatchOrTimeFailure(StatusMismatchOrTimeFailure statusMismatchOrTimeFailure) {
		this.statusMismatchOrTimeFailure = statusMismatchOrTimeFailure;
	}
	
	@XmlTransient
	public StatusMismatchOrTimeFailure getStatusMismatchOrTimeFailure() {
		return statusMismatchOrTimeFailure;
	}
	
	// Non-Public API
	
	@XmlAnyElement(lax = true)	
	@XmlElementWrapper
	@XmlPath("dynvalues/dyntree")
	@XmlJavaTypeAdapter(VaraPromptNodeAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	List<IWorkflowTaskVaraPromptNode> getVaraPromptSetNodes() {
		List<IWorkflowTaskVaraPromptNode> varaPromptNodes = new ArrayList<IWorkflowTaskVaraPromptNode>();
		if (!(getObjectType().equals("<START>") || getObjectType().equals("<END>"))) {
			varaPromptNodes.add(new WorkflowTaskVaraNode("Variables", "VALUE", null, "TASKVALUE", null));
			varaPromptNodes.add(new WorkflowTaskVaraNode("Parent Variables", "PVALUE", null, "TASKVALUE", null));
		}
		if (getPromptSets() != null) {
			if (!(getObjectType().equals("<START>") || getObjectType().equals("<END>")) && parentHasPrompts()) {
				varaPromptNodes.add(new WorkflowTaskPromptSetsNode());
				varaPromptNodes.add(new WorkflowTaskParentPromptSetsNode());
			}
			for (IPromptSet promptSet : getPromptSets()) {
				varaPromptNodes.add(new WorkflowTaskPromptSetNode(promptSet.getName(), promptSet.getPrompts(), isInherited(promptSet)));	
			}
		}
		return varaPromptNodes;
	}
	
	@XmlPath("when/@WElseX")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenElseBlockTaskAbortParent() {
		return StatusMismatchOrTimeFailure.BLOCK_TASK_AND_ABORT_PARENT.equals(statusMismatchOrTimeFailure);
	}
	
	@XmlPath("when/@WElseS")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenElseSkipTask() {
		return StatusMismatchOrTimeFailure.SKIP_TASK.equals(statusMismatchOrTimeFailure);
	}
	
	@XmlPath("when/@WElseH")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenElseBlockTask() {
		return StatusMismatchOrTimeFailure.BLOCK_TASK.equals(statusMismatchOrTimeFailure);
	}	
	
	@XmlPath("when/@WElseA")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenElseAbortTask() {
		return StatusMismatchOrTimeFailure.ABORT_TASK.equals(statusMismatchOrTimeFailure);
	}
	
	@XmlAttribute(name = "Text2")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getText2() {
		return null;
	}
	
	@XmlElement(name = "checkpoint")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getCheckpoint() {
		return null;
	}
	
	@XmlPath("checkpoint/@showprompt")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCheckpointShowPrompt() {
		return false;
	}
	
	@XmlPath("checkpoint/@promptatgen")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCheckpointPromptAtGen() {
		return false;
	}
	
	@XmlPath("checkpoint/@TcpOn")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCheckpointTcpOn() {
		return false;
	}
	
	@XmlPath("checkpoint/@TcpExecute")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getCheckpointTcpExecute() {
		return null;
	}
	
	@XmlPath("checkpoint/@TcpATimeTZ")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getCheckpointTcpATimeTZ() {
		return null;
	}
	
	@XmlPath("checkpoint/@TcpATime")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getCheckpointTcpATime() {
		return "00:00";
	}
	
	@XmlPath("checkpoint/@TcpADays")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Integer getCheckpointTcpADays() {
		return 0;
	}
	
	@XmlPath("checkpoint/@RunPerTarget")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCheckpointRunPerTarget() {
		return false;
	}
	
	@XmlPath("checkpoint/@RunPerPatch")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCheckpointRunPerPatch() {
		return false;
	}
	
	@XmlPath("checkpoint/@RollbackFlag")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCheckpointRollbackFlag() {
		return false;
	}

	@XmlElement(name = "after")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getAfter() {
		return null;
	}
	
	@XmlPath("after/@HoldFlg")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAfterHoldFlg() {
		return false;
	}
	
	@XmlPath("after/@ErlstStTimeTZ")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getAfterErlstStTimeTZ() {
		return null;
	}
	
	@XmlPath("after/@ErlstStTime")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getAfterErlstStTime() {
		return "00:00";
	}
	
	@XmlPath("after/@ErlstStDays")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Integer getAfterErlstStDays() {
		return 0;
	}
	
	@XmlPath("after/@AtimOn")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAfterAtimOn() {
		return false;
	}
	
	@XmlPath("after/@ActFlg")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAfterActFlg() {
		return true;
	}
	
	@XmlElement(name = "when")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getWhen() {
		return null;
	}
	
	@XmlPath("when/@WtimOn")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenWtimOn() {
		return false;
	}
	
	@XmlPath("when/@WhenExecute")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getWhenExecute() {
		return null;
	}
	
	@XmlPath("when/@WCTypeOR")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenWCTypeOR() {
		return false;
	}
	
	@XmlPath("when/@WCTypeAND")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenWCTypeAND() {
		return true;
	}
	
	@XmlPath("when/@LtstTimeTZ")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getWhenLtstTimeTZ() {
		return null;
	}
	
	@XmlPath("when/@LtstStTime")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getWhenLtstTime() {
		return "00:00";
	}
	
	@XmlPath("when/@LtstStDays")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Integer getWhenLtstStDays() {
		return 0;
	}
	
	@XmlPath("when/@LtstSt")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenLatestStart() {
		return false;
	}
	
	@XmlPath("when/@LtstEnd")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenLatestEnd() {
		return false;
	}
	
	@XmlPath("when/@LtstEndTime")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getWhenLatestEndTime() {
		return "00:00";
	}
	
	@XmlPath("when/@LtstEndDays")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Integer getWhenLtstEndDays() {
		return 0;
	}
	
	@XmlPath("when/@ChkWhenExec")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isWhenChkWhenExec() {
		return false;
	}
	
	@XmlPath("preconditions/PreCon/conditions[@id='CONDITIONS']/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getPreconditions() {
		return null;
	}

	@XmlPath("postconditions/PostCon/conditions[@id='CONDITIONS']/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getPostconditions() {
		return null;
	}
	
	@XmlElement(name = "runtime")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getRuntime() {
		return null;
	}
	
	@XmlPath("runtime/@SrtMethodNone")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeSrtMethodNone() {
		return true;
	}
	
	@XmlPath("runtime/@SrtMethodFix")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeSrtMethodFix() {
		return false;
	}
	
	@XmlPath("runtime/@SrtMethodErt")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeSrtMethodErt() {
		return false;
	}
	
	@XmlPath("runtime/@SrtFix")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeSrtFix() {
		return false;
	}
	
	@XmlPath("runtime/@SrtErt")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeSrtErt() {
		return false;
	}
	
	@XmlPath("runtime/@MrtTZ")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getRuntimeMrtTZ() {
		return null;
	}
	
	@XmlPath("runtime/@MrtTime")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getRuntimeMrtTime() {
		return "00:00";
	}
	
	@XmlPath("runtime/@MrtOn")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtOn() {
		return true;
	}
	
	@XmlPath("runtime/@MrtMethodNone")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtMethodNone() {
		return true;
	}
	
	@XmlPath("runtime/@MrtMethodFix")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtMethodFix() {
		return false;
	}
	
	@XmlPath("runtime/@MrtMethodErt")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtMethodErt() {
		return false;
	}
	
	@XmlPath("runtime/@MrtMethodDate")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtMethodDate() {
		return false;
	}
	
	@XmlPath("runtime/@MrtFix")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtFix() {
		return false;
	}
	
	@XmlPath("runtime/@MrtExecuteObj")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getRuntimeMrtExecuteObj() {
		return null;
	}
	
	@XmlPath("runtime/@MrtExecute")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtExecute() {
		return false;
	}
	
	@XmlPath("runtime/@MrtErt")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtErt() {
		return false;
	}
	
	@XmlPath("runtime/@MrtDays")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtDays() {
		return false;
	}
	
	@XmlPath("runtime/@MrtCancel")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRuntimeMrtCancel() {
		return false;
	}
	
	@XmlElement(name = "result")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getResult() {
		return null;
	}
	
	@XmlPath("result/@RWhen")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getResultWhen() {
		return null;
	}
	
	@XmlPath("result/@RExecute")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getResultExecute() {
		return null;
	}
	
	@XmlPath("result/@RRepWait")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isResultRepWait() {
		return false;
	}
	
	@XmlPath("result/@RRepOn")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isResultRepOn() {
		return false;
	}
	
	@XmlPath("result/@RRepMTimes")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isResultRepMTimes() {
		return false;
	}
	
	@XmlPath("result/@RExecFlag")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isResultExecFlag() {
		return false;
	}
	
	@XmlPath("result/@RElseJPAbend")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isResultElseJPAbend() {
		return false;
	}
	
	@XmlPath("result/@RElseIgn")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isResultElseIgn() {
		return false;
	}
	
	@XmlPath("result/@RElseHalt")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isResultElseHalt() {
		return false;
	}
	
	@XmlPath("result/@ChkRExec")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isResultChkRExec() {
		return false;
	}
	
	@XmlElement(name = "CIT")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getCIT() {
		return null;
	}
	
	@XmlElement(name = "calendars")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getCalendars() {
		return null;
	}
	
	@XmlPath("calendars/@CaleOn")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCalendarsOn() {
		return false;
	}
	
	@XmlPath("calendars/@CCTypeOne")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCalendarsTypeOne() {
		return true;
	}
	
	@XmlPath("calendars/@CCTypeNone")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCalendarsTypeNone() {
		return false;
	}
	
	@XmlPath("calendars/@CCTypeExt")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCalendarsTypeExt() {
		return false;
	}
	
	@XmlPath("calendars/@CCTypeAll")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isCalendarsTypeAll() {
		return false;
	}
	
	private boolean parentHasPrompts() {
		if (workflow != null) {
			List<IPromptSet> wfPromptSets = workflow.getPromptSets();
			if (wfPromptSets != null && !wfPromptSets.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isInherited(IPromptSet promptSet) {
		if (workflow != null && promptSet != null) {
			List<IPromptSet> wfPromptSets = workflow.getPromptSets();
			if (wfPromptSets != null && !wfPromptSets.isEmpty()) {
				for (IPromptSet wfPromptSet : wfPromptSets) {
					if (wfPromptSet.getName().equals(promptSet.getName())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
