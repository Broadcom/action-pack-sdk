package com.broadcom.apdk.objects;

import javax.xml.bind.annotation.adapters.XmlAdapter;

class DocumentationAdapter extends XmlAdapter<DocumentationAttr, String> {
	
    @Override
    public String unmarshal(DocumentationAttr documentation) {
    	return documentation.getDocumentation();
    }

    @Override
    public DocumentationAttr marshal(String documentation) {
    	return new DocumentationAttr(DocumentationType.TEXT, documentation);
    }

}