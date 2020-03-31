package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class KeyNotFoundAdapter extends XmlAdapter<KeyNotFoundType, KeyNotFoundAction> {
	
    @Override
    public KeyNotFoundAction unmarshal(KeyNotFoundType keyNotFoundType) {
    	if (keyNotFoundType != null) {
	    	if (keyNotFoundType.getReturnError()) {
	    		return KeyNotFoundAction.RETURN_ERROR;
	    	}
	    	if (keyNotFoundType.getReturnInitialValue()) {
	    		return KeyNotFoundAction.RETURN_INITIAL_VALUES;
	    	}
    	}
    	return null;
    }

    @Override
    public KeyNotFoundType marshal(KeyNotFoundAction keyNotFoundAction) {
    	if (keyNotFoundAction.equals(KeyNotFoundAction.RETURN_ERROR)) {
    		return new KeyNotFoundType(true, false);
    	}
    	if (keyNotFoundAction.equals(KeyNotFoundAction.RETURN_INITIAL_VALUES)) {
    		return new KeyNotFoundType(false, true);
    	}
    	return null;
    }

}
