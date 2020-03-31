package com.broadcom.apdk.objects;

public enum VariableScope {
	
	NONE("*", "No scope"),
	FREE("FREI", "Freely selected"),
	HOST("HON", "Host - each host name"),
	TASK("JBN", "Task - each task name"),
	WORKFLOW_NAME("JPN", "Workflow name - each Workflow name"),
	WORKFLOW_SESSION("JPS", "Workflow session - each Workflow session"),
	USER_NAME("USN", "User - each user name"),
	USER_SESSION("USS", "User session - each user session");
	
    public final String id;
    public final String description;
    
    private VariableScope(String id, String description) {
        this.id = id;
        this.description = description; 
    }

}
