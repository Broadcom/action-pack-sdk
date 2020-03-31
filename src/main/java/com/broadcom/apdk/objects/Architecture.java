package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Architecture implements IStoredFileEnum {
	
	@XmlEnumValue("*") ALL,
	X86,
	X64

}
