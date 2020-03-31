package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "JOBP")
@XmlType (propOrder={"title", "archiveKey1", "archiveKey2", "active", "type", "OHSubType", "syncs", "queue", "childQueue", "jobGroup",
		"extReportDefault", "extReportAll", "extReportNone", "internalAccount", "autoDeactNo", "autoDeactErrorFreeAfterExec", 
		"autoDeactErrorFreeAfterRestart", "errorFreeStatus", "autoDeactAlways", "deactivationDelay", "generateAtRuntime", 
		"priority", "passPriority", "maxSimultaneousExec", "reuseHG", "waitForTasks", "abortTasks", "timezone", "resultEvalOkStatus", 
		"resultEvalExecIfNotOk", "JPASubType", "tasks", "options",
		"maxReturnCode", "executeObjectIfAboveMaxReturnCode", "executeObjectIfAboveMaxReturnCodeFlag", "forecastEndStatus", "ERT",
		"ertMethodDefault", "ertMethodFixed", "fixedERT", "ERTDynamicMethod", "mrtMethodDynamic", "ERTNumberOfPastRuns", "ERTCorrection",
		"ERTDeviationExtent", "ERTIgnoreDeviations", "ERTMinimumRuns", "mrtMethodNone", "mrtMethodFixed", "fixedDuration", "mrtMethodERT", 
		"additionalDuration", "mrtMethodDate", "additionalDurationDays", "finishTime", "finishTimeTZ", "srtMethodNone", "srtMethodFixed", 
		"fixedSRT", "srtMethodERT", "SRTERT", "cancelIfRuntimDeviation", "executeObjectIfRuntimeDevitationFlag", 
		"executeObjectIfRuntimDeviation", "variablesAndPrompts", "deploymentFlag", "workflowTypeA", "workflowTypeC", 
		"applicationName", "workflowName", "componentName", "rollbackFlag", "backupObject", "rollbackObject", "backupPath", 
		"deleteBefore", "includeSubDirectories", "script", "documentation"})
public class Workflow extends ExecutableAutomicObject implements IWorkflow {
	
	private String internalAccount;
	private ExtendedReport extendedReport;
	private String queue;
	private DeactivationOnFinish deactivateOnFinish;
	private String errorFreeStatus;
	private Integer deactivationDelay;
	private String resultEvalOkStatus;
	private String resultEvalExec;
	private Integer maxSimultaneousExec;
	private Integer priority;
	private String timezone;
	private Boolean passPriority;
	private Boolean waitForRemainingTasks;
	private Boolean generateAtRuntime;
	private String jobGroup;
	private String childQueue;
	private List<IWorkflowTask> tasks;
	
	public Workflow() {
		super();
		initWorkflow();
	}
	
	public Workflow(String name) {
		super(name);
		initWorkflow();
	}
	
	private void initWorkflow() {
		this.tasks = new ArrayList<IWorkflowTask>();
		this.extendedReport = ExtendedReport.DEFAULT;
		this.deactivateOnFinish = DeactivationOnFinish.AFTER_ERRORFREE_RESTART;
		this.setQueue("CLIENT_QUEUE");
		this.setErrorFreeStatus(null);
		this.setDeactivationDelay(0);
		this.setMaxSimultaneousExec(0);
		this.setPriority(0);
		this.setPassPriority(false);
		this.setGenerateAtRuntime(false);
		this.setWaitForRemainingTasks(true);
		
		List<IWorkflowTask> initalTasks = new ArrayList<IWorkflowTask>();
		initalTasks.add(new WorkflowStartTask());
		initalTasks.add(new WorkflowEndTask());
		setTasks(initalTasks);
	}
	
	public void setInternalAccount(String internalAccount) {
		this.internalAccount = internalAccount;	
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/IntAccount/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getInternalAccount() {
		return internalAccount;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	@XmlPath("ATTR_JOBP[@state='1']/Queue/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getQueue() {
		return queue;
	}
	
	public void setErrorFreeStatus(String status) {
		this.errorFreeStatus = status;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/DeactWhen/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getErrorFreeStatus() {
		return errorFreeStatus;
	}
	
	public void setDeactivationDelay(Integer minutes) {
		this.deactivationDelay = minutes;
	}

	@XmlPath("ATTR_JOBP[@state='1']/DeactDelay/text()")
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
	
	public void setExtendedReport(ExtendedReport extendedReport) {
		this.extendedReport = extendedReport;
	}
	
	@XmlTransient
	public ExtendedReport getExtendedReport() {
		return extendedReport;
	}
	
	public void setResultEvalOkStatus(String okStatus) {
		this.resultEvalOkStatus = okStatus;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/RWhen/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getResultEvalOkStatus() {
		return resultEvalOkStatus;
	}
	
	public void setResultEvalExecIfNotOk(String executableObjectName) {
		this.resultEvalExec = executableObjectName;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/RExecute/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getResultEvalExecIfNotOk() {
		return resultEvalExec;
	}
	
	public void setMaxSimultaneousExec(Integer maxExecutions) {
		this.maxSimultaneousExec = maxExecutions;
	}

	@XmlPath("ATTR_JOBP[@state='1']/MaxParallel2/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getMaxSimultaneousExec() {
		return maxSimultaneousExec;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/UC4Priority/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getPriority() {
		return priority;
	}
	
	public void setTimezone(String timezoneObjectName) {
		this.timezone = timezoneObjectName;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/TZ/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getTimezone() {
		return timezone;
	}
	
	public void setPassPriority(Boolean passPriority) {
		this.passPriority = passPriority;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/PassPriority/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isPassPriority() {
		return passPriority;
	}
	
	public void setGenerateAtRuntime(Boolean generateAtRuntime) {
		this.generateAtRuntime = generateAtRuntime;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/ActAtRun/text()")
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

	@XmlPath("ATTR_JOBP[@state='1']/StartType/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getJobGroup() {
		return jobGroup;
	}
	
	public void setChildQueue(String childQueue) {
		this.childQueue = childQueue;
	}

	@XmlPath("ATTR_JOBP[@state='1']/ChildQueue/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getChildQueue() {
		return childQueue;
	}
	
	public void setTasks(List<IWorkflowTask> tasks) {
		this.tasks = addWorkflowToTasks(tasks);
	}
	
	@XmlAnyElement(lax = true)	
	@XmlElementWrapper	
	@XmlPath("JOBP[@state='1']/JobpStruct[@mode='design']")
	@XmlJavaTypeAdapter(WorkflowTaskAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public List<IWorkflowTask> getTasks() {
		List<IPromptSet> promptSets = getPromptSets();
		// Add PromptSets defined in JOBP to the list of promptSets in each tasks
		if (promptSets != null && !promptSets.isEmpty()) {
			if (tasks != null && !tasks.isEmpty()) {
				List<IWorkflowTask> newTaskList = new ArrayList<IWorkflowTask>();
				for (IWorkflowTask task : tasks) {
					List<IPromptSet> taskPromptSets = task.getPromptSets();
					if (taskPromptSets == null) {
						taskPromptSets = new ArrayList<IPromptSet>();
					}
					// Add PromptSet only if no PromptSet with the same name already exists
					for (IPromptSet wfPromptSet : promptSets) {
						boolean foundInTask = false;
						for (IPromptSet taskPromptSet : taskPromptSets) {
							if (taskPromptSet.getName().equals(wfPromptSet.getName())) {
								foundInTask = true;
							}
						}
						if (!foundInTask) {
							taskPromptSets.add(wfPromptSet);
						}
					}
					task.setPromptSets(taskPromptSets);
					newTaskList.add(task);
				}
				return addWorkflowToTasks(newTaskList);
			}			
		}
		return tasks;
	}

	// Non-Public API
	
	@XmlPath("JOBP[@state='1']/JobpStruct[@mode='design']/OPTIONS/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getOptions() {
		return null;
	}

	@XmlPath("DEPLOYMENT[@state='1']/DeploymentFlag/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isDeploymentFlag() {
		return false;
	}
	
	@XmlPath("DEPLOYMENT[@state='1']/WFTypeA/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)	
	Boolean isWorkflowTypeA() {
		return true;
	}
	
	@XmlPath("DEPLOYMENT[@state='1']/WFTypeC/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)	
	Boolean isWorkflowTypeC() {
		return false;
	}
	
	@XmlPath("DEPLOYMENT[@state='1']/AppName/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getApplicationName() {
		return null;
	}
	
	@XmlPath("DEPLOYMENT[@state='1']/WFName/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getWorkflowName() {
		return null;
	}
	
	@XmlPath("DEPLOYMENT[@state='1']/ComponentName/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getComponentName() {
		return null;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/MpElse1/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Boolean isWaitForTasks() {
		if (this.waitForRemainingTasks != null) {
			return this.waitForRemainingTasks ? true : false;
		}
		return null;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/MpElse2/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	Boolean isAbortTasks() {
		if (this.waitForRemainingTasks != null) {
			return this.waitForRemainingTasks ? false: true;
		}
		return null;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/ExtRepDef/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isExtReportDefault() {
		if (ExtendedReport.DEFAULT.equals(this.extendedReport)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/ReuseHG/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isReuseHG() {
		return true;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/ExtRepAll/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isExtReportAll() {
		if (ExtendedReport.ALL.equals(this.extendedReport)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/ExtRepNone/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isExtReportNone() {
		if (ExtendedReport.NONE.equals(this.extendedReport)) {
			return true;
		}
		return false;
	}

	@XmlPath("ATTR_JOBP[@state='1']/AutoDeactNo/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAutoDeactNo() {
		if (DeactivationOnFinish.NEVER.equals(this.deactivateOnFinish)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/AutoDeactAlways/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAutoDeactAlways() {
		if (DeactivationOnFinish.ALWAYS.equals(this.deactivateOnFinish)) {
			return true;
		}
		return false;
	}

	@XmlPath("ATTR_JOBP[@state='1']/AutoDeactErrorFree/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAutoDeactErrorFreeAfterRestart() {
		if (DeactivationOnFinish.AFTER_ERRORFREE_RESTART.equals(this.deactivateOnFinish)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/AutoDeact1ErrorFree/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAutoDeactErrorFreeAfterExec() {
		if (DeactivationOnFinish.AFTER_ERRORFREE_EXEC.equals(this.deactivateOnFinish)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("ATTR_JOBP[@state='1']/JPA_SubType/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getJPASubType() {
		return null;
	}
	
	@XmlAttribute(name = "AllowExternal")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isAllowExternal() {
		return true;
	}
	
	private List<IWorkflowTask> addWorkflowToTasks(List<IWorkflowTask> tasks) {
		if (tasks != null) {
			List<IWorkflowTask> newTasks = new ArrayList<IWorkflowTask>();
			for (IWorkflowTask task : tasks) {
				task.setWorkflow(this);
				newTasks.add(task);
			}
			return newTasks;
		}
		return null;
	}

}
