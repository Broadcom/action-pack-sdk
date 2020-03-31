package com.broadcom.apdk.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
	
	public static String toString(LocalDateTime timestamp) {
		if (timestamp != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return timestamp.format(formatter);
		}
		return null;
	}
	
	public static String toString(LocalDate time) {
		if (time != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return time.format(formatter);
		}
		return null;
	}
	
	public static String toString(LocalTime time) {
		if (time != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			return time.format(formatter);
		}
		return null;
	}

}
