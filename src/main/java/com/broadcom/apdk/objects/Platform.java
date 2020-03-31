package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Platform implements IStoredFileEnum {
	
	@XmlEnumValue("*") ALL,
	AIX,
	DEC,
	HPUX,
	SOLARIS,
	LINUX,
	ZLINUX

}
