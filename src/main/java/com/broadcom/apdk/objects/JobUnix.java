package com.broadcom.apdk.objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlMarshalNullRepresentation;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "JOBS_UNIX")
@XmlType (propOrder={
		// <XHEADER>
		"title", "archiveKey1", "archiveKey2", "active", "type", "OHSubType", 
		// <OUTPUTREG>
		"externalOutputFiles",
		// <SYNC>
		"syncs", 
		// <ATTR_JOBS>
		"queue", "jobGroup", "agent", "hostType", "codeTable", "login", "internalAccount", 
		"extReportDefault", "extReportAll", "extReportNone", "autoDeactNo", 
		"autoDeactErrorFreeAfterExec", "autoDeactErrorFreeAfterRestart", "errorFreeStatus", 
		"deactivationDelay", "autoDeactAlways", "displayAttrDialogAtActivation",
		"generateAtRuntime", "consumption", "priority",	"maxSimultaneousExec", "waitForTasks", 
		"abortTasks", "timezone",
		// <ATTR_UNIX>
		"reportToDatabase", "reportTrigger", "reportToFile", "jobType", "jobPriority", 
		"shellName", "shellOptions", "command",
		// <RUNTIME>
		"maxReturnCode", "executeObjectIfAboveMaxReturnCode", 
		"executeObjectIfAboveMaxReturnCodeFlag", "forecastEndStatus", "ERT", "ertMethodDefault", 
		"ertMethodFixed", "fixedERT", "ERTDynamicMethod", "mrtMethodDynamic", "ERTNumberOfPastRuns", 
		"ERTCorrection", "ERTDeviationExtent", "ERTIgnoreDeviations", "ERTMinimumRuns", 
		"mrtMethodNone", "mrtMethodFixed", "fixedDuration", "mrtMethodERT", "additionalDuration", 
		"mrtMethodDate", "additionalDurationDays", "finishTime", "finishTimeTZ", "srtMethodNone", 
		"srtMethodFixed", "fixedSRT", "srtMethodERT", "SRTERT", "cancelIfRuntimDeviation", 
		"executeObjectIfRuntimeDevitationFlag", "executeObjectIfRuntimDeviation",
		// <DYNVALUES>
		"variablesAndPrompts", 
		// <ROLLBACK>
		"rollbackFlag", "backupObject", "rollbackObject", "backupPath", "deleteBefore", 
		"includeSubDirectories", 
		// <PRE_SCRIPT>
		"preScript", 
		// <SCRIPT>
		"script", 
		// <OUTPUTSCAN>
		"outputScanInheritanceFlag", "outputScanFilterObjects", "outputScanAgent", "outputScanLogin", 
		// <POST_SCRIPT>
		"postScript", 
		// <DOCU>
		"documentation"})

public class JobUnix extends Job implements IJobUnix {

	private JobType jobType;
	private Shell shell;
	private String shellOptions;
	private String command;
	private Boolean reportToDatabase;
	private Boolean reportToFile;
	private Boolean reportTrigger;
	
	public JobUnix() {
		super();
		initObject();
	}
	
	public JobUnix(String name) {
		setName(name);
		initObject();
	}
	
	private void initObject() {
		setJobType(JobType.SHELLSCRIPT);
		setReportToDatabase(true);
		setReportToFile(false);
		setReportTrigger(false);
		setReportToDatabase(true);
		setReportToFile(false);
		setReportTrigger(false);
		setAgent("|<UNIX>|HOST");
		setQueue("CLIENT_QUEUE");
		setDeactivationDelay(0);
		setGenerateAtRuntime(false);
		setMaxSimultaneousExec(0);
		setPriority(0);
		setDisplayAttrDialogAtActivation(false);
		setWaitForRemainingTasks(true);
		setDeactivateOnFinish(DeactivationOnFinish.ALWAYS);
		setExtendedReport(ExtendedReport.DEFAULT);
		setOutputScanInheritance(false);
		setOutputScanFilterObjects(new ArrayList<OutputScanFilterObject>());
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	@XmlPath("ATTR_UNIX[@state='1']/text()")
	@XmlJavaTypeAdapter(JobTypeAdapter.class)
	public JobType getJobType() {
		return jobType;
	}

	public void setShellOptions(String options) {
		this.shellOptions = options;
	}

	@XmlPath("ATTR_UNIX[@state='1']/ShellOptions/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getShellOptions() {
		return shellOptions;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@XmlPath("ATTR_UNIX[@state='1']/Com/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getCommand() {
		return this.command;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	@XmlTransient
	public Shell getShell() {
		return this.shell;
	}

	@XmlPath("ATTR_UNIX[@state='1']/Shell/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getShellName() {
		if (shell != null) {
			return shell.label;
		}
		return null;
	}
	
	public void setReportToDatabase(Boolean database) {
		this.reportToDatabase = database;
	}
	
	@XmlPath("ATTR_UNIX[@state='1']/OutputDb/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getReportToDatabase() {
		return reportToDatabase;
	}
	
	public void setReportToFile(Boolean file) {
		this.reportToFile = file; 
	}
	
	@XmlPath("ATTR_UNIX[@state='1']/OutputFile/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getReportToFile() {
		return reportToFile;
	}
	
	public void setReportTrigger(Boolean trigger) {
		this.reportTrigger = trigger;
	}

	@XmlPath("ATTR_UNIX[@state='1']/OutputDbErr/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getReportTrigger() {
		return reportTrigger;
	}
	
	@XmlPath("ATTR_JOBS[@state='1']/HostATTR_Type/text()")
	public String getHostType() {
		return "UNIX";
	}
	
	// Non-Public API
	
	@XmlAttribute(name = "AttrType")
	String getAttrType() {
		return "UNIX";
	}
	
	@XmlPath("ATTR_UNIX[@state='1']/Priority/text()")
	Integer getJobPriority() {
		return 0;
	}

}
