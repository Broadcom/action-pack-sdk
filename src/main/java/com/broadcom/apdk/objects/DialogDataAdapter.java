package com.broadcom.apdk.objects;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DialogDataAdapter extends XmlAdapter<String, PromptSetDialogData> {
	
	private JAXBContext jaxbContext;

	public DialogDataAdapter() {}
			
	public DialogDataAdapter(JAXBContext jaxbContext) {
        super();
        this.jaxbContext = jaxbContext;
    }

	@Override
	public PromptSetDialogData unmarshal(String value) throws Exception {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public String marshal(PromptSetDialogData value) throws Exception {
		if (value != null) {
			Marshaller marshaller = getJAXBContext(value.getClass()).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(value, stringWriter);
			return stringWriter.toString();
		}
		else {
			return "<PRPTS ontop=\"1\"><PRPTBOX/></PRPTS>";
		}
	}
	
    private JAXBContext getJAXBContext(Class<?> type) throws Exception {
        if (jaxbContext != null) {
            return jaxbContext;
        }
        return JAXBContext.newInstance(type);
    }

}
