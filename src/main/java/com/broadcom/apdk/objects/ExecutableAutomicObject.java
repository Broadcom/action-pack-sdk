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
public abstract class ExecutableAutomicObject extends AutomicObject implements IExecutableAutomicObject {
	
	private String title;
	private ObjectType type;
	private String archiveKey1;
	private String archiveKey2;
	private String script;
	private Integer maxReturnCode;
	private String executeObjectIfAboveMaxReturnCode;
	private Boolean executeObjectIfAboveMaxReturnCodeFlag;
	private List<VariableEntry> variables;
	private List<IPromptSet> promptSets;
	private Integer ert;
	private Integer ertFixed;
	private ERTMethod ertMethod;
	private Integer ertNumberOfPastRuns;
	private Integer ertCorrection;
	private Boolean ertIgnoreDeviations;
	private Integer ertDeviationExtent;
	private Integer ertMinimumRuns;
	private String forecastEndStatus;
	private SRTMethod srtMethod;
	private Integer srtERT;
	private Integer srtFixed;
	private Boolean cancelIfRuntimeDevitation;
	private String executeObjectIfRuntimeDevitation;	
	private Boolean executeObjectIfRuntimeDevitationFlag;
	private MRTMethod mrtMethod;
	private Integer fixedDuration;
	private Integer additionalDuration;
	private Integer additionalDurationDays;
	private String finishTime;
	private String finishTimeTZ;
	private String ohSubType;

	public ExecutableAutomicObject() {
		super();
		initExecutableAutomicObject();
		this.variables = new ArrayList<VariableEntry>();
	}
	
	public ExecutableAutomicObject(String name) {
		super(name);
		initExecutableAutomicObject();
	}
	
	private void initExecutableAutomicObject() {
		this.variables = new ArrayList<VariableEntry>();
		this.executeObjectIfAboveMaxReturnCodeFlag = false;
		this.executeObjectIfRuntimeDevitationFlag = false;
		setMaxReturnCode(0);
		setERT(0);
		setFixedERT(0);
		setERTNumberOfPastRuns(0);
		setERTCorrection(0);
		setERTMethod(ERTMethod.DEFAULT);
		setERTIgnoreDeviations(false);
		setERTDeviationExtent(0);
		setERTMinimumRuns(0);
		setSRTMethod(SRTMethod.NONE);
		setSRTERT(0);
		setFixedSRT(0);
		setCancelIfRuntimDeviation(false);
		setMRTMethod(MRTMethod.NONE);
		setFixedDuration(0);
		setAdditionalDuration(0);
		setAdditionalDurationDays(0);
		setFinishTime("00:00");
		setForecastEndStatus("0| |");
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@XmlPath("XHEADER[@state='1']/Title/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getTitle() {
		return this.title;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}
	
	@XmlPath("XHEADER[@state='1']/OH_SubType/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public ObjectType getType() {
		return this.type;
	}

	public void setArchiveKey1(String archiveKey1) {
		this.archiveKey1 = archiveKey1;
	}

	@Override
	@XmlPath("XHEADER[@state='1']/ArchiveKey1/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getArchiveKey1() {
		return this.archiveKey1;
	}

	public void setArchiveKey2(String archiveKey2) {
		 this.archiveKey2 = archiveKey2;
	}

	@XmlPath("XHEADER[@state='1']/ArchiveKey2/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getArchiveKey2() {
		return this.archiveKey2;
	}
	
	public void setScript(String script) {
		this.script = script;	
	}
	
	public void setMaxReturnCode(Integer maxReturnCode) {
		this.maxReturnCode = maxReturnCode;
	}
	
	@XmlPath("RUNTIME[@state='1']/MaxRetCode/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getMaxReturnCode() {
		return maxReturnCode;
	}
	
	public void setExecuteObjectIfAboveMaxReturnCode(String objectName) {
		this.executeObjectIfAboveMaxReturnCodeFlag = objectName == null ? false : true;
		this.executeObjectIfAboveMaxReturnCode = objectName;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrcExecute/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getExecuteObjectIfAboveMaxReturnCode() {
		return executeObjectIfAboveMaxReturnCode;
	}
	
	public void setERTMethod(ERTMethod method) {
		this.ertMethod = method;
	}
	
	@XmlTransient
	public ERTMethod getERTMethod() {
		return ertMethod;
	}
	
	public void setERT(Integer seconds) {
		this.ert = seconds;
	}
	
	@XmlPath("RUNTIME[@state='1']/Ert/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getERT() {
		return ert;
	}
	
	public void setFixedERT(Integer seconds) {
		this.ertFixed = seconds;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtFix/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getFixedERT() {
		return ertFixed;
	}
	
	public void setERTNumberOfPastRuns(Integer numberOfPastRuns) {
		this.ertNumberOfPastRuns = numberOfPastRuns;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtCnt/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getERTNumberOfPastRuns() {
		return ertNumberOfPastRuns;
	}
	
	public void setERTCorrection(Integer correction) {
		this.ertCorrection = correction;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtCorr/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getERTCorrection() {
		return ertCorrection;
	}
	
	public void setERTIgnoreDeviations(Boolean ignoreDeviations) {
		this.ertIgnoreDeviations = ignoreDeviations;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtIgnFlg/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isERTIgnoreDeviations() {
		return ertIgnoreDeviations;
	}
	
	public void setERTDeviationExtent(Integer deviationExtent) {
		this.ertDeviationExtent = deviationExtent;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtIgn/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getERTDeviationExtent() {
		return ertDeviationExtent;
	}
	
	public void setERTMinimumRuns(Integer minimumRuns) {
		this.ertMinimumRuns = minimumRuns;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtMinCnt/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getERTMinimumRuns() {
		return ertMinimumRuns;
	}
	
	public void setForecastEndStatus(String status) {
		this.forecastEndStatus = status;
	}
	
	@XmlPath("RUNTIME[@state='1']/FcstStatus/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getForecastEndStatus() {
		return forecastEndStatus;
	}
	
	public void setSRTMethod(SRTMethod method) {
		this.srtMethod = method;
	}
	
	@XmlTransient
	public SRTMethod getSRTMethod() {
		return srtMethod;
	}
	
	public void setSRTERT(Integer negativeDuration) {
		this.srtERT = negativeDuration;
	}
	
	@XmlPath("RUNTIME[@state='1']/SrtErt/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getSRTERT() {
		return srtERT;
	}
	
	public void setFixedSRT(Integer seconds) {
		this.srtFixed = seconds;
	}
	
	@XmlPath("RUNTIME[@state='1']/SrtFix/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getFixedSRT() {
		return srtFixed;
	}
	
	public void setExecuteObjectIfRuntimDeviation(String objectName) {
		this.executeObjectIfRuntimeDevitationFlag = objectName == null ? false : true;
		this.executeObjectIfRuntimeDevitation = objectName;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtExecuteObj/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getExecuteObjectIfRuntimDeviation() {
		return executeObjectIfRuntimeDevitation;
	}
	
	public void setCancelIfRuntimDeviation(Boolean cancel) {
		this.cancelIfRuntimeDevitation = cancel;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtCancel/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isCancelIfRuntimDeviation() {
		return cancelIfRuntimeDevitation;
	}
	
	public void setMRTMethod(MRTMethod method) {
		this.mrtMethod = method;
	}
	
	@XmlTransient
	public MRTMethod getMRTMethod() {
		return mrtMethod;
	}
	
	public void setFixedDuration(Integer duration) {
		this.fixedDuration = duration;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtFix/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getFixedDuration() {
		return fixedDuration;
	}
	
	public void setAdditionalDuration(Integer duration) {
		this.additionalDuration = duration;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtErt/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getAdditionalDuration() {
		return additionalDuration;
	}
	
	public void setAdditionalDurationDays(Integer days) {
		this.additionalDurationDays = days;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtDays/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public Integer getAdditionalDurationDays() {
		return additionalDurationDays;
	}
	
	public void setFinishTime(String hhmm) {
		this.finishTime = hhmm;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtTime/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTimeTZ(String timezone) {
		this.finishTimeTZ = timezone;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtTZ/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getFinishTimeTZ() {
		return finishTimeTZ;
	}
	
	@XmlPath("SCRIPT[@state='1']/MSCRI/text()")
	@XmlCDATA
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	public String getScript() {
		return script;
	}
	
	public void setVariables(List<VariableEntry> variables) {
		this.variables = variables;
	}
	
	@XmlTransient
	public List<VariableEntry> getVariables() {
		return variables;
	}
	
	public void setPromptSets(List<IPromptSet> promptSets) {
		this.promptSets = promptSets;
	}
	
	@XmlTransient
	public List<IPromptSet> getPromptSets() {
		return promptSets;
	}
	
	// Non-Public API
	
	@XmlPath("XHEADER[@state='1']/CustomAttributes/@dataRequestID")
	String getCustomAttrDataRequestId() {
		return "0";
	}
	
	@XmlPath("XHEADER[@state='1']/CustomAttributes/@KeyListID")
	String getCustomAttrKeyListId() {
		return "0";
	}
	
	@XmlPath("SCRIPT[@state='1']/@mode")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean getScriptMode() {
		return true;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtMethodFix/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isMrtMethodFixed() {
		if (mrtMethod.equals(MRTMethod.FIXED_VALUE)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtMethodErt/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isMrtMethodERT() {
		if (mrtMethod.equals(MRTMethod.ERT_ADDITIONAL_DURATION)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtMethodDate/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isMrtMethodDate() {
		if (mrtMethod.equals(MRTMethod.DATE_ADDITIONAL_DURATION)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtMethodNone/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isMrtMethodNone() {
		return true;
	}
	
	@XmlPath("RUNTIME[@state='1']/SrtMethodErt/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isSrtMethodERT() {
		if (srtMethod.equals(SRTMethod.ERT_NEGATIVE_DURATION)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("RUNTIME[@state='1']/SrtMethodFix/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isSrtMethodFixed() {
		if (srtMethod.equals(SRTMethod.FIXED_VALUE)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("RUNTIME[@state='1']/SrtMethodNone/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isSrtMethodNone() {
		return true;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtDynMethod/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	String getERTDynamicMethod() {
		if (ertMethod.equals(ERTMethod.DYNAMIC_ADAPTIVE)) {
			return ERTMethod.DYNAMIC_ADAPTIVE.label;
		}
		if (ertMethod.equals(ERTMethod.DYNAMIC_LINEAR)) {
			return ERTMethod.DYNAMIC_LINEAR.label;
		}
		if (ertMethod.equals(ERTMethod.DYNAMIC_MAX_VALUE)) {
			return ERTMethod.DYNAMIC_MAX_VALUE.label;
		}
		return ERTMethod.DYNAMIC_AVERAGE.label;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtMethodDef/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isErtMethodDefault() {
		if (ertMethod.equals(ERTMethod.DEFAULT)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtMethodFix/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isErtMethodFixed() {
		if (ertMethod.equals(ERTMethod.FIXED)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("RUNTIME[@state='1']/ErtMethodDyn/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isMrtMethodDynamic() {
		if (ertMethod.equals(ERTMethod.DYNAMIC_ADAPTIVE) ||
				ertMethod.equals(ERTMethod.DYNAMIC_AVERAGE) ||
				ertMethod.equals(ERTMethod.DYNAMIC_LINEAR) ||
				ertMethod.equals(ERTMethod.DYNAMIC_MAX_VALUE)) {
			return true;
		}
		return false;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrtExecute/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isExecuteObjectIfRuntimeDevitationFlag() {
		return executeObjectIfRuntimeDevitationFlag;
	}
	
	@XmlPath("RUNTIME[@state='1']/MrcElseE/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isExecuteObjectIfAboveMaxReturnCodeFlag() {
		return executeObjectIfAboveMaxReturnCodeFlag;
	}

	@XmlAnyElement(lax = true)	
	@XmlElementWrapper
	@XmlPath("DYNVALUES[@state='1']/dyntree")
	@XmlJavaTypeAdapter(VaraPromptNodeAdapter.class)
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	List<IVaraPromptNode> getVariablesAndPrompts() {
		List<IVaraPromptNode> variablesAndPrompts = new ArrayList<IVaraPromptNode>();
		variablesAndPrompts.add(new VaraNode(getVariables()));
		if (getPromptSets() != null) {
			for (IPromptSet promptSet : getPromptSets()) {
				variablesAndPrompts.add(new PromptSetNode(promptSet.getName(), promptSet.getPrompts()));	
			}
		}
		return variablesAndPrompts;
	}
	
	@XmlPath("SYNCREF[@state='1']/Syncs/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)	
	String getSyncs() {
		return null;
	}
	
	@XmlPath("ROLLBACK[@state='1']/RollbackFlag/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isRollbackFlag() {
		return false;
	}
	
	@XmlPath("ROLLBACK[@state='1']/CBackupObj/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)	
	String getBackupObject() {
		return null;
	}
	
	@XmlPath("ROLLBACK[@state='1']/CRollbackObj/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)	
	String getRollbackObject() {
		return null;
	}
	
	@XmlPath("ROLLBACK[@state='1']/FBackupPath/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)	
	String getBackupPath() {
		return null;
	}
	
	@XmlPath("ROLLBACK[@state='1']/FDeleteBefore/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isDeleteBefore() {
		return false;
	}
	
	@XmlPath("ROLLBACK[@state='1']/FInclSubDirs/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isIncludeSubDirectories() {
		return false;
	}
	
	@XmlPath("XHEADER[@state='1']/Active/text()")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	Boolean isActive() {
		return true;
	}
	
	protected void setOHSubType(String ohSubType) {
		this.ohSubType = ohSubType;
	}
	
	@XmlPath("XHEADER[@state='1']/OH_SubType/text()")
	@XmlNullPolicy(emptyNodeRepresentsNull = true, nullRepresentationForXml = XmlMarshalNullRepresentation.EMPTY_NODE)
	protected String getOHSubType() {
		return ohSubType;
	}
}