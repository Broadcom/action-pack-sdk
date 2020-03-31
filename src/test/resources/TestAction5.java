package com.broadcom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.broadcom.apdk.api.BaseAction;
import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionOutputParam;

@Action(
	name = "ACTION5", 
	title = "Action #5 for testing the API"
)
public class TestAction5 extends BaseAction {
	
	@ActionOutputParam
	short shortPrimitive;
	
	@ActionOutputParam
	Short shortWrapper;
	
	@ActionOutputParam
	byte bytePrimitive;
	
	@ActionOutputParam
	Byte byteWrapper;
	
	@ActionOutputParam
	char charPrimitive;
	
	@ActionOutputParam
	Character charWrapper;
	
	@ActionOutputParam
	int intPrimitive;
	
	@ActionOutputParam
	Integer intWrapper;
	
	@ActionOutputParam
	float floatPrimitive;
	
	@ActionOutputParam
	Float floatWrapper;
	
	@ActionOutputParam
	long longPrimitive;
	
	@ActionOutputParam
	Long longWrapper;
	
	@ActionOutputParam
	double doublePrimitive;
	
	@ActionOutputParam
	Double doubleWrapper;
	
	@ActionOutputParam
	boolean booleanPrimitive;
	
	@ActionOutputParam
	Boolean booleanWrapper;
	
	@ActionOutputParam
	String string;
	
	@ActionOutputParam
	LocalDate date;
	
	@ActionOutputParam
	LocalDateTime datetime;
	
	@ActionOutputParam
	LocalTime time;
	
	@Override
	public void run() {
		date = LocalDate.of(1980, 3, 4);
		time = LocalTime.of(14, 8);
		datetime = LocalDateTime.of(date, time);
		string = "Description";
		floatPrimitive = (float) 4.3;
		floatWrapper = (float) 4078.387;
		bytePrimitive = 3;
		byteWrapper = 4;
		intPrimitive = 777;
		intWrapper = 87;
		charPrimitive = 't';
		charWrapper = 'f';
		longPrimitive = 686;
		longWrapper = (long) 6534;
		booleanPrimitive = false;
		booleanWrapper = true;
		shortPrimitive = 1;
		shortWrapper = 2;
		doublePrimitive = 87.87;
		doubleWrapper = 103.43;
	}

}
