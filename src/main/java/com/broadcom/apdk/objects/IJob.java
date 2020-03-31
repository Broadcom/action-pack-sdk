package com.broadcom.apdk.objects;

import java.util.List;

public interface IJob extends IExecutableAutomicObject {
	
	public void setPostScript(String script);
	
	public String getPostScript();
	
	public void setPreScript(String script);
	
	public String getPreScript();	
	
	public void setExtendedReport(ExtendedReport extendedReport);

	public ExtendedReport getExtendedReport();
	
	public void setInternalAccount(String internalAccount);
	
	public String getInternalAccount();
	
	public void setCodeTable(String codeTable);
	
	public String getCodeTable();
	
	public void setCommand(String command);
	
	public String getCommand();
	
	public void setLogin(String login);
	
	public String getLogin();
	
	public void setAgent(String agent);
	
	public String getAgent();
	
	public void setDisplayAttrDialogAtActivation(Boolean display);
	
	public Boolean isDisplayAttrDialogAtActivation();
	
	public void setOutputScanInheritance(Boolean inheritance);
	
	public Boolean isOutputScanInheritance();
	
	public void setOutputScanAgent(String agent);
	
	public String getOutputScanAgent();
	
	public void setOutputScanLogin(String login);
	
	public String getOutputScanLogin();
	
	public void setOutputScanFilterObjects(List<OutputScanFilterObject> filterObjects);
	
	public List<OutputScanFilterObject> getOutputScanFilterObjects();
	
	public String getHostType();

}
