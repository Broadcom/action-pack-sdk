package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType (propOrder={"shellScript", "command"})
class JobTypeType {
	
	private Boolean shellScript;
	private Boolean command;
	
	public JobTypeType() {}

	public JobTypeType(boolean shellScript, boolean command) {
		this.setShellScript(shellScript);
		this.setCommand(command);
	}	
	
	@XmlElement(name = "ShellScript")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getShellScript() {
		return shellScript;
	}
	
	public void setShellScript(Boolean shellScript) {
		this.shellScript = shellScript;
	}
	
	@XmlElement(name = "Command")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getCommand() {
		return command;
	}
	
	public void setCommand(Boolean command) {
		this.command = command;
	}

}
