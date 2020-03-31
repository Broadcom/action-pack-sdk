package com.broadcom;

import com.broadcom.apdk.api.BaseAction;
import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionPacks;

@Action(
	name = "ACTION3", 
	title = "Action #3 for testing the API"
)
@ActionPacks({TestActionPack2.class})
public class TestAction3 extends BaseAction {
		
	@Override
	public void run() {
		System.out.println(this.getClass().getName());
	}

}
