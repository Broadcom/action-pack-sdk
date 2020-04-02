package com.broadcom.apdk.objects;

import java.util.List;

public interface IJob extends IExecutableAutomicObject {
	
	void setPostScript(String script);
	
	String getPostScript();
	
	void setPreScript(String script);
	
	String getPreScript();	
	
	void setExtendedReport(ExtendedReport extendedReport);

	ExtendedReport getExtendedReport();
	
	void setInternalAccount(String internalAccount);
	
	String getInternalAccount();
	
	void setCodeTable(String codeTable);
	
	String getCodeTable();
	
	void setCommand(String command);
	
	String getCommand();
	
	void setLogin(String login);
	
	String getLogin();
	
	void setAgent(String agent);
	
	String getAgent();
	
	void setDisplayAttrDialogAtActivation(Boolean display);
	
	Boolean isDisplayAttrDialogAtActivation();
	
	void setOutputScanInheritance(Boolean inheritance);
	
	Boolean isOutputScanInheritance();
	
	void setOutputScanAgent(String agent);
	
	String getOutputScanAgent();
	
	void setOutputScanLogin(String login);
	
	String getOutputScanLogin();
	
	void setOutputScanFilterObjects(List<OutputScanFilterObject> filterObjects);
	
	List<OutputScanFilterObject> getOutputScanFilterObjects();
	
	String getHostType();

}
