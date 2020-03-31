package com.broadcom.apdk.helpers;

public class VariableHelper {

	public static String getSanitizedValue(String value) {
		if (value != null) {
			if (value.startsWith("&")) {
				value = value.substring(1);	
			}
			return value.replace("#", "s.00").replace("$", "s.01").replace("§", "s.02").
					replace("@", "s.03").replace("[", "s.04").replace("]", "s.05");
		}
		return null;
	}
	
	public static String getOriginalValue(String sanitizedValue) {
		if (sanitizedValue != null) {
			return "&" + sanitizedValue.replace("s.00", "#").replace("s.01", "$").replace("s.02", "§").
					replace("s.03", "@").replace("s.04", "[s.04").replace("s.05", "]");
		}
		return null;
	}
	
}
