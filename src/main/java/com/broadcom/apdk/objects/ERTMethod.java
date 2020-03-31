package com.broadcom.apdk.objects;

public enum ERTMethod {
	
	DEFAULT(null),
	FIXED(null),
	DYNAMIC_ADAPTIVE("16|where the following keywords are assigned:"),
	DYNAMIC_LINEAR("8|Linear regression"),
	DYNAMIC_AVERAGE("2|Average"),
	DYNAMIC_MAX_VALUE("4|Maximal value");
	
    public final String label;
	 
    private ERTMethod(String label) {
        this.label = label;
    }

}
