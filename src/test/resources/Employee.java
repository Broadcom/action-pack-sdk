package com.broadcom;

public class Employee {
	
	private String firstname;
	private String lastname;
	
	Employee(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	String getFirstname() {
		return firstname;
	}
	
	String getLastname() {
		return lastname;
	}
	
}
