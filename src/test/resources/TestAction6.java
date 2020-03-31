package com.broadcom;

import com.broadcom.apdk.api.BaseAction;
import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.apdk.api.annotations.ActionParamAdapter;

@Action(
	name = "ACTION6", 
	title = "Action #6 for testing the API"
)
public class TestAction6 extends BaseAction {
	
	@ActionInputParam
	@ActionParamAdapter(EmployeeAdapter.class)
	Employee candidate;
	
	@ActionOutputParam
	@ActionParamAdapter(EmployeeAdapter.class)
	Employee employee;
	
	@Override
	public void run() {
		employee = new Employee("Michael", "Grath");
		if (candidate != null) {
			employee = new Employee(candidate.getLastname(), candidate.getFirstname());
		}
	}

}
