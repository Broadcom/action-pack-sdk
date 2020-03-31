package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DOCU")
public class Documentation extends NonExecutableAutomicObject {
	
	public Documentation() {
		super();
	}
	
	public Documentation(String name) {
		super(name);
	}

}
