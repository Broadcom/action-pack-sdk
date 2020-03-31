package com.broadcom;

import com.broadcom.apdk.api.BaseAction;
import com.broadcom.apdk.api.annotations.Action;

@Action(
	name = "ACTION1", 
	title = "Action #1 for testing the API"
)
public class TestAction1 extends BaseAction {
		
	@Override
	public void run() {
		System.out.println(this.getClass().getName());
	}

}
