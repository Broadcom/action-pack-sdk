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

@XmlRootElement(name = "JOBS_WINDOWS")
@XmlType (propOrder={"title", "archiveKey1", "archiveKey2", "active", "type", "OHSubType", "externalOutputFiles",  
		"syncs", "queue", "jobGroup", "agent", "hostType", "codeTable", "login", "internalAccount", 
		"extReportDefault", "extReportAll", "extReportNone", "autoDeactNo", "autoDeactErrorFreeAfterExec",
		"autoDeactErrorFreeAfterRestart", "errorFreeStatus", "deactivationDelay", "autoDeactAlways", "displayAttrDialogAtActivation",
		"generateAtRuntime", "consumption", "priority", "maxSimultaneousExec", "waitForTasks", "abortTasks", "timezone",
		"reportToDatabase", "reportTrigger", "reportToFile", "scriptedReport", "interpreterTypeBatch", "interpreterTypeCommand", "interpreterTypeCustom",
		"viewJobOnDesktopStandard", "viewJobOnDesktopMinimized", "viewJobOnDesktopMaximized", "useWinOsJobObjectDefault", 
		"useWinOsJobObjectYes", "useWinOsJobObjectNo", "workingDirectory", "command", "logonAsBatchUser", "showJob", 
		"maxReturnCode", "executeObjectIfAboveMaxReturnCode", "executeObjectIfAboveMaxReturnCodeFlag", "forecastEndStatus", "ERT",
		"ertMethodDefault", "ertMethodFixed", "fixedERT", "ERTDynamicMethod", "mrtMethodDynamic", "ERTNumberOfPastRuns", "ERTCorrection",
		"ERTDeviationExtent", "ERTIgnoreDeviations", "ERTMinimumRuns", "mrtMethodNone", "mrtMethodFixed", "fixedDuration", "mrtMethodERT", 
		"additionalDuration", "mrtMethodDate", "additionalDurationDays", "finishTime", "finishTimeTZ", "srtMethodNone", "srtMethodFixed", 
		"fixedSRT", "srtMethodERT", "SRTERT", "cancelIfRuntimDeviation", "executeObjectIfRuntimeDevitationFlag", 
		"executeObjectIfRuntimDeviation",
		"variablesAndPrompts", "rollbackFlag", "backupObject", "rollbackObject", "backupPath", 
		"deleteBefore", "includeSubDirectories", "preScript", "script", "outputScanInheritanceFlag", "outputScanFilterObjects", 
		"outputScanAgent", "outputScanLogin", "postScript", "documentation"})
public class JobWindows extends Job implements IJobWindows {
	
	private String workingDirectory;
	private String command;
	private Boolean logonAsBatchUser;
	private Boolean interpreterTypeBatch;
	private Boolean interpreterTypeCommand;
	private Boolean interpreterTypeCustom;
	private Boolean viewJobOnDesktopStandard;
	private Boolean viewJobOnDesktopMinimized;
	private Boolean viewJobOnDesktopMaximized;
	private Boolean useWinOsJobObjectDefault;
	private Boolean useWinOsJobObjectYes;
	private Boolean useWinOsJobObjectNo;
	private Boolean scriptedReport;
	private Boolean reportToDatabase;
	private Boolean reportToFile;
	private Boolean reportTrigger;
	
	public JobWindows() {
		super();
		initObject();
	}
	
	public JobWindows(String name) {
		setName(name);
		initObject();
	}
	
	private void initObject() {
		setInterpreterType(InterpreterType.BATCH);
		setViewJobOnDesktop(ViewJobOnDesktop.STANDARD);
		setUseWinOsJobObject(UseWinOsJobObject.DEFAULT);
		setWorkingDirectory("c:\\");
		setLogonAsBatchUser(false);
		setReportToDatabase(true);
		setReportToFile(false);
		setReportTrigger(false);
		setScriptedReport(false);	
		setAgent("|<WIN>|HOST");
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
		setScript("! insert these lines in your script to determine if an error occurred\n" + 
				"!\n" + 
				"! @set retcode=%errorlevel%\n" + 
				"! @if NOT %ERRORLEVEL% == 0 goto :retcode");
	}
	
	public void setScriptedReport(Boolean scriptedReport) {
		this.scriptedReport = scriptedReport;
	}
	
	@XmlPath("ATTR_WINDOWS[@state='1']/IsGenerated/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getScriptedReport() {
		return scriptedReport;
	}
	
	// InterpreterType Handling
	
	public void setInterpreterType(InterpreterType type) {
		setInterpreterTypeBatch(false);
		setInterpreterTypeCommand(false);
		setInterpreterTypeCustom(true);
		if (InterpreterType.BATCH.equals(type)) {
			setInterpreterTypeBatch(true);
			setInterpreterTypeCommand(false);
			setInterpreterTypeCustom(false);
		}
		if (InterpreterType.COMMAND.equals(type)) {
			setInterpreterTypeBatch(false);
			setInterpreterTypeCommand(true);
			setInterpreterTypeCustom(false);
		}
	}
	
	@XmlTransient
	public InterpreterType getInterpreterType() {
		if (getInterpreterTypeBatch()) {
			return InterpreterType.BATCH;
		}
		if (getInterpreterTypeCommand()) {
			return InterpreterType.COMMAND;
		}
		return InterpreterType.CUSTOM;
	}
	
	@XmlPath("ATTR_WINDOWS[@state='1']/BAT/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getInterpreterTypeBatch() {
		return interpreterTypeBatch;
	}

	void setInterpreterTypeBatch(Boolean interpreterTypeBatch) {
		this.interpreterTypeBatch = interpreterTypeBatch;
	}

	@XmlPath("ATTR_WINDOWS[@state='1']/COM/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getInterpreterTypeCommand() {
		return interpreterTypeCommand;
	}

	void setInterpreterTypeCommand(Boolean interpreterTypeCommand) {
		this.interpreterTypeCommand = interpreterTypeCommand;
	}

	@XmlPath("ATTR_WINDOWS[@state='1']/WinBatch/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getInterpreterTypeCustom() {
		return interpreterTypeCustom;
	}

	void setInterpreterTypeCustom(Boolean interpreterTypeCustom) {
		this.interpreterTypeCustom = interpreterTypeCustom;
	}
	
	// ViewJobOnDesktop Handling
	
	public void setViewJobOnDesktop(ViewJobOnDesktop view) {
		setViewJobOnDesktopStandard(true);
		setViewJobOnDesktopMinimized(false);
		setViewJobOnDesktopMaximized(false);
		if (ViewJobOnDesktop.MINIMIZED.equals(view)) {
			setViewJobOnDesktopStandard(false);
			setViewJobOnDesktopMinimized(true);
			setViewJobOnDesktopMaximized(false);
		}
		if (ViewJobOnDesktop.MAXIMIZED.equals(view)) {
			setViewJobOnDesktopStandard(false);
			setViewJobOnDesktopMinimized(false);
			setViewJobOnDesktopMaximized(true);
		}
	}
	
	@XmlTransient
	public ViewJobOnDesktop getViewJobOnDesktop() {
		if (getViewJobOnDesktopMinimized()) {
			return ViewJobOnDesktop.MINIMIZED;
		}
		if (getViewJobOnDesktopMaximized()) {
			return ViewJobOnDesktop.MAXIMIZED;
		}
		return ViewJobOnDesktop.STANDARD;
	}
	
	@XmlPath("ATTR_WINDOWS[@state='1']/Standard/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getViewJobOnDesktopStandard() {
		return viewJobOnDesktopStandard;
	}

	void setViewJobOnDesktopStandard(Boolean viewJobOnDesktopStandard) {
		this.viewJobOnDesktopStandard = viewJobOnDesktopStandard;
	}

	@XmlPath("ATTR_WINDOWS[@state='1']/Minimized/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getViewJobOnDesktopMinimized() {
		return viewJobOnDesktopMinimized;
	}

	void setViewJobOnDesktopMinimized(Boolean viewJobOnDesktopMinimized) {
		this.viewJobOnDesktopMinimized = viewJobOnDesktopMinimized;
	}

	@XmlPath("ATTR_WINDOWS[@state='1']/Maximized/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getViewJobOnDesktopMaximized() {
		return viewJobOnDesktopMaximized;
	}

	void setViewJobOnDesktopMaximized(Boolean viewJobOnDesktopMaximized) {
		this.viewJobOnDesktopMaximized = viewJobOnDesktopMaximized;
	}
	
	// UseWinOsJobObject Handling
	
	public void setUseWinOsJobObject(UseWinOsJobObject useWinOsJobObject) {
		setUseWinOsJobObjectDefault(true);
		setUseWinOsJobObjectYes(false);
		setUseWinOsJobObjectNo(false);
		if (UseWinOsJobObject.YES.equals(useWinOsJobObject)) {
			setUseWinOsJobObjectDefault(false);
			setUseWinOsJobObjectYes(true);
			setUseWinOsJobObjectNo(false);
		}
		if (UseWinOsJobObject.NO.equals(useWinOsJobObject)) {
			setUseWinOsJobObjectDefault(false);
			setUseWinOsJobObjectYes(false);
			setUseWinOsJobObjectNo(true);
		}
	}
	
	@XmlTransient
	public UseWinOsJobObject getUseWinOsJobObject() {
		if (getUseWinOsJobObjectNo()) {
			return UseWinOsJobObject.NO;
		}
		if (getUseWinOsJobObjectYes()) {
			return UseWinOsJobObject.YES;
		}
		return UseWinOsJobObject.DEFAULT;
	}
	
	@XmlPath("ATTR_WINDOWS[@state='1']/JObjDefault/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getUseWinOsJobObjectDefault() {
		return useWinOsJobObjectDefault;
	}

	public void setUseWinOsJobObjectDefault(Boolean useWinOsJobObjectDefault) {
		this.useWinOsJobObjectDefault = useWinOsJobObjectDefault;
	}

	@XmlPath("ATTR_WINDOWS[@state='1']/JObjYes/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getUseWinOsJobObjectYes() {
		return useWinOsJobObjectYes;
	}

	public void setUseWinOsJobObjectYes(Boolean useWinOsJobObjectYes) {
		this.useWinOsJobObjectYes = useWinOsJobObjectYes;
	}

	@XmlPath("ATTR_WINDOWS[@state='1']/JObjNo/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getUseWinOsJobObjectNo() {
		return useWinOsJobObjectNo;
	}

	public void setUseWinOsJobObjectNo(Boolean useWinOsJobObjectNo) {
		this.useWinOsJobObjectNo = useWinOsJobObjectNo;
	}
	
	public void setWorkingDirectory(String directory) {
		this.workingDirectory = directory;
	}
	
	@XmlPath("ATTR_WINDOWS[@state='1']/WorkingDirectory/text()")
	public String getWorkingDirectory() {
		return workingDirectory;
	}
	
	public void setLogonAsBatchUser(Boolean logonAsBatchUser) {
		this.logonAsBatchUser = logonAsBatchUser;
	}
	
	@XmlPath("ATTR_WINDOWS[@state='1']/LogOn/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getLogonAsBatchUser() {
		return logonAsBatchUser;
	}

	public void setReportToDatabase(Boolean database) {
		this.reportToDatabase = database;
	}
	
	@XmlPath("ATTR_WINDOWS[@state='1']/OutputDb/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getReportToDatabase() {
		return reportToDatabase;
	}
	
	public void setReportToFile(Boolean file) {
		this.reportToFile = file; 
	}
	
	@XmlPath("ATTR_WINDOWS[@state='1']/OutputFile/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getReportToFile() {
		return reportToFile;
	}
	
	public void setReportTrigger(Boolean trigger) {
		this.reportTrigger = trigger;
	}

	@XmlPath("ATTR_WINDOWS[@state='1']/OutputDbErr/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getReportTrigger() {
		return reportTrigger;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}

	@XmlPath("ATTR_WINDOWS[@state='1']/Command/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getCommand() {
		return this.command;
	}

	@XmlPath("ATTR_JOBS[@state='1']/HostATTR_Type/text()")
	public String getHostType() {
		return "WINDOWS";
	}
	
	// Non-Public API
	
	@XmlAttribute(name = "AttrType")
	String getAttrType() {
		return "WINDOWS";
	}
	
	@XmlPath("ATTR_WINDOWS[@state='1']/ShowJob/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isShowJob() {
		return false;
	}

}
