package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<Integer, Boolean> {

    @Override
    public Boolean unmarshal(Integer integerValue) {
        return integerValue == null ? null : integerValue == 1;
    }

    @Override
    public Integer marshal(Boolean booleanValue) {
        return booleanValue == null ? null : booleanValue ? 1 : 0;
    }
}
