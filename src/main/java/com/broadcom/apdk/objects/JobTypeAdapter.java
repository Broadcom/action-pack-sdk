package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.adapters.XmlAdapter;

class JobTypeAdapter extends XmlAdapter<JobTypeType, JobType> {

	@Override
	public JobType unmarshal(JobTypeType value) throws Exception {
    	if (value != null) {
	    	if (value.getShellScript()) {
	    		return JobType.SHELLSCRIPT;
	    	}
	    	if (value.getCommand()) {
	    		return JobType.COMMAND;
	    	}
    	}
    	return null;
	}

	@Override
	public JobTypeType marshal(JobType value) throws Exception {
    	if (value.equals(JobType.SHELLSCRIPT)) {
    		return new JobTypeType(true, false);
    	}
    	if (value.equals(JobType.COMMAND)) {
    		return new JobTypeType(false, true);
    	}
    	return null;
	}

}
