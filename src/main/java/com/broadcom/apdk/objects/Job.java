package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlTransient
public abstract class Job extends ExecutableAutomicObject implements IJob {

	private String postScript;
	private String preScript;
	private String queue;
	private DeactivationOnFinish deactivateOnFinish;
	private String errorFreeStatus;
	private Integer deactivationDelay;
	private Integer maxSimultaneousExec;
	private Integer priority;
	private String timezone;
	private Boolean waitForRemainingTasks;
	private Boolean generateAtRuntime;
	private String jobGroup;
	private String internalAccount;
	private String codeTable;
	private String login;
	private String agent;
	private Boolean outputScanInheritance;
	private String outputScanLogin;
	private String outputScanAgent;
	private Boolean displayDialog;
	private ExtendedReport extendedReport;
	private List<OutputScanFilterObject> filterObjects;
	
	public void setCodeTable(String codeTable) {
		this.codeTable = codeTable;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/HostDst/text()")
	public String getAgent() {
		return agent;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/CodeName/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getCodeTable() {
		return codeTable;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/Login/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getLogin() {
		return login;
	}
	
	public void setQueue(String queue) {
		this.queue = queue;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/Queue/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getQueue() {
		return queue;
	}
	
	public void setInternalAccount(String internalAccount) {
		this.internalAccount = internalAccount;	
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/IntAccount/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getInternalAccount() {
		return internalAccount;
	}
	
	public void setPostScript(String script) {
		this.postScript = script;
	}
	
	@XmlPath("POST_SCRIPT[@state='1']/OSCRI/text()")
	@XmlCDATA
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getPostScript() {
		return postScript;
	}
	
	public void setPreScript(String script) {
		this.preScript = script;
	}
	
	@XmlPath("PRE_SCRIPT[@state='1']/PSCRI/text()")
	@XmlCDATA
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getPreScript() {
		return preScript;
	}
	
	public void setErrorFreeStatus(String status) {
		this.errorFreeStatus = status;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/DeactWhen/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getErrorFreeStatus() {
		return errorFreeStatus;
	}
	
	public void setDeactivationDelay(Integer minutes) {
		this.deactivationDelay = minutes;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/DeactDelay/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getDeactivationDelay() {
		return deactivationDelay;
	}
	
	public void setDeactivateOnFinish(DeactivationOnFinish deactivationOnFinish) {
		this.deactivateOnFinish = deactivationOnFinish;
	}
	
	@XmlTransient
	public DeactivationOnFinish getDeactivateOnFinish() {
		return deactivateOnFinish;
	}
	
	public void setMaxSimultaneousExec(Integer maxExecutions) {
		this.maxSimultaneousExec = maxExecutions;
	}

	@XmlPath("ATTR_JOBS[@state='1']/MaxParallel2/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getMaxSimultaneousExec() {
		return maxSimultaneousExec;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/UC4Priority/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getPriority() {
		return priority;
	}
	
	public void setTimezone(String timezoneObjectName) {
		this.timezone = timezoneObjectName;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/TZ/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getTimezone() {
		return timezone;
	}
	
	public void setGenerateAtRuntime(Boolean generateAtRuntime) {
		this.generateAtRuntime = generateAtRuntime;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/ActAtRun/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getGenerateAtRuntime() {
		return generateAtRuntime;
	}
	
	public void setWaitForRemainingTasks(Boolean waitForRemainingTasks) {
		this.waitForRemainingTasks = waitForRemainingTasks;
	}
	
	@XmlTransient
	public Boolean isWaitForRemainingTasks() {
		return waitForRemainingTasks;
	}
	
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	@XmlPath("ATTR_JOBS[@state='1']/StartType/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getJobGroup() {
		return jobGroup;
	}
	
	public void setExtendedReport(ExtendedReport extendedReport) {
		this.extendedReport = extendedReport;
	}
	
	@XmlTransient
	public ExtendedReport getExtendedReport() {
		return extendedReport;
	}
	
	public void setDisplayAttrDialogAtActivation(Boolean display) {
		this.displayDialog = display;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/AttDialog/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isDisplayAttrDialogAtActivation() {
		return displayDialog;
	}
	
	public void setOutputScanInheritance(Boolean inheritance) {
		this.outputScanInheritance = inheritance;
	}
	
	@XmlTransient
	public Boolean isOutputScanInheritance() {
		return outputScanInheritance;
	}
	
	public void setOutputScanAgent(String agent) {
		this.outputScanAgent = agent;
	}
	
	@XmlPath("OUTPUTSCAN[@state='1']/HostFsc/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getOutputScanAgent() {
		return outputScanAgent;
	}
	
	public void setOutputScanLogin(String login) {
		this.outputScanLogin = login;
	}

	@XmlPath("OUTPUTSCAN[@state='1']/LoginFsc/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getOutputScanLogin() {
		return outputScanLogin;
	}
	
	public void setOutputScanFilterObjects(List<OutputScanFilterObject> filterObjects) {
		if (filterObjects != null) {
			List<OutputScanFilterObject> newfilterObjectsList = new ArrayList<OutputScanFilterObject>();
			int rowNumber = 1;
			for (OutputScanFilterObject filterObject : filterObjects) {
				newfilterObjectsList.add(new OutputScanFilterObject(
						rowNumber++, 
						filterObject.getFilterObject(),
						filterObject.isApplyFilterCriteria(),
						filterObject.getReturnCode(),
						filterObject.getStatusText(),
						filterObject.getExecuteObject()));	
			}
			this.filterObjects = newfilterObjectsList;
		}
		else {
			this.filterObjects = filterObjects;
		}
	}
	
	@XmlAnyElement(lax = true)	
	@XmlElementWrapper	
	@XmlPath("OUTPUTSCAN[@state='1']/filterobjects")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public List<OutputScanFilterObject> getOutputScanFilterObjects() {
		return filterObjects;
	}
	
	// Non-Public API
	
	@XmlPath("PRE_SCRIPT[@state='1']/@replacementmode")
	String getPreScriptReplacementMode() {
		return "1";
	}
	
	@XmlPath("PRE_SCRIPT[@state='1']/@mode")
	String getPreScriptMode() {
		return "1";
	}
	
	@XmlPath("POST_SCRIPT[@state='1']/@replacementmode")
	String getPostScriptReplacementMode() {
		return "1";
	}
	
	@XmlPath("POST_SCRIPT[@state='1']/@mode")
	String getPostScriptMode() {
		return "1";
	}
	
	@XmlPath("OUTPUTSCAN[@state='1']/Inherit/text()")
	String isOutputScanInheritanceFlag() {
		if (outputScanInheritance != null) {
			return outputScanInheritance ? "Y" : "N";
		}
		return null;
	}
		
	@XmlPath("ATTR_JOBS[@state='1']/Consumption/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Integer getConsumption() {
		return 0;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/ExtRepDef/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isExtReportDefault() {
		if (ExtendedReport.DEFAULT.equals(this.extendedReport)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/ExtRepAll/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isExtReportAll() {
		if (ExtendedReport.ALL.equals(this.extendedReport)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/ExtRepNone/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isExtReportNone() {
		if (ExtendedReport.NONE.equals(this.extendedReport)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("OUTPUTREG[@state='1']/FileReg/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getExternalOutputFiles() {
		return null;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/MpElse1/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Boolean isWaitForTasks() {
		if (this.waitForRemainingTasks != null) {
			return this.waitForRemainingTasks ? true : false;
		}
		return null;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/MpElse2/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Boolean isAbortTasks() {
		if (this.waitForRemainingTasks != null) {
			return this.waitForRemainingTasks ? false: true;
		}
		return null;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/AutoDeactNo/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAutoDeactNo() {
		if (DeactivationOnFinish.NEVER.equals(this.deactivateOnFinish)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/AutoDeactAlways/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAutoDeactAlways() {
		if (DeactivationOnFinish.ALWAYS.equals(this.deactivateOnFinish)) {
			return true;
		}
		return false;
	}

	@XmlPath("ATTR_JOBS[@state='1']/AutoDeactErrorFree/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAutoDeactErrorFreeAfterRestart() {
		if (DeactivationOnFinish.AFTER_ERRORFREE_RESTART.equals(this.deactivateOnFinish)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/AutoDeact1ErrorFree/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAutoDeactErrorFreeAfterExec() {
		if (DeactivationOnFinish.AFTER_ERRORFREE_EXEC.equals(this.deactivateOnFinish)) {
			return true;
		}
		return false;
	}

}
