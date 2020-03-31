package com.broadcom;

import com.broadcom.apdk.api.BaseAction;
import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionPacks;

@Action(
	name = "ACTION2", 
	title = "Action #2 for testing the API"
)
@ActionPacks({TestActionPack1.class})
public class TestAction2 extends BaseAction {
		
	@Override
	public void run() {
		System.out.println(this.getClass().getName());
	}

}
