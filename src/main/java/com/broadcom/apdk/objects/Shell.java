package com.broadcom.apdk.objects;

public enum Shell {
	
	BOURNE("sh"),
	BOURNEONLINUX("bash"),
	C("csh"),
	KORN("ksh");
	
    public final String label;
	 
    private Shell(String label) {
        this.label = label;
    }

}
