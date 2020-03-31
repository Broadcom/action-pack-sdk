package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.XmlEnumValue;

public enum OperatingSystem implements IStoredFileEnum {

	@XmlEnumValue("*") ALL,
	UNIX,
	WINODWS
	
}
