package com.broadcom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.broadcom.apdk.api.BaseAction;
import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;

@Action(
	name = "ACTION4", 
	title = "Action #4 for testing the API"
)
public class TestAction4 extends BaseAction {
	
	@ActionInputParam
	short shortPrimitive;
	
	@ActionInputParam
	Short shortWrapper;
	
	@ActionInputParam
	byte bytePrimitive;
	
	@ActionInputParam
	Byte byteWrapper;
	
	@ActionInputParam
	char charPrimitive;
	
	@ActionInputParam
	Character charWrapper;
	
	@ActionInputParam
	int intPrimitive;
	
	@ActionInputParam
	Integer intWrapper;
	
	@ActionInputParam
	float floatPrimitive;
	
	@ActionInputParam
	Float floatWrapper;
	
	@ActionInputParam
	long longPrimitive;
	
	@ActionInputParam
	Long longWrapper;
	
	@ActionInputParam
	double doublePrimitive;
	
	@ActionInputParam
	Double doubleWrapper;
	
	@ActionInputParam
	boolean booleanPrimitive;
	
	@ActionInputParam
	Boolean booleanWrapper;
	
	@ActionInputParam
	String string;
	
	@ActionInputParam
	LocalDate date;
	
	@ActionInputParam
	LocalDateTime datetime;
	
	@ActionInputParam
	LocalTime time;
	
	@Override
	public void run() {
		System.out.println("date=" + date);
		System.out.println("datetime=" + datetime);
		System.out.println("time=" + time);
		System.out.println("string=" + (string != null ? "\"" + string + "\"" : string));		
		System.out.println("floatPrimitive=" + floatPrimitive);
		System.out.println("floatWrapper=" + floatWrapper);
		System.out.println("bytePrimitive=" + bytePrimitive);
		System.out.println("byteWrapper=" + byteWrapper);
		System.out.println("intPrimitive=" + intPrimitive);
		System.out.println("intWrapper=" + intWrapper);
		System.out.println("charPrimitive=" + charPrimitive);
		System.out.println("charWrapper=" + charWrapper);
		System.out.println("longPrimitive=" + longPrimitive);
		System.out.println("longWrapper=" + longWrapper);
		System.out.println("booleanPrimitive=" + booleanPrimitive);
		System.out.println("booleanWrapper=" + booleanWrapper);
		System.out.println("shortPrimitive=" + shortPrimitive);
		System.out.println("shortWrapper=" + shortWrapper);
		System.out.println("doublePrimitive=" + doublePrimitive);
		System.out.println("doubleWrapper=" + doubleWrapper);
	}

}
