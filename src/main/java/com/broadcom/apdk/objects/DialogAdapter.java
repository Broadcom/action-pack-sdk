package com.broadcom.apdk.objects;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DialogAdapter extends XmlAdapter<String, PromptSetDialog> {
	
	private JAXBContext jaxbContext;

	public DialogAdapter() {}
			
	public DialogAdapter(JAXBContext jaxbContext) {
		super();
		this.jaxbContext = jaxbContext;
	}

	@Override
	public PromptSetDialog unmarshal(String value) throws Exception {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public String marshal(PromptSetDialog value) throws Exception {
		if (value != null) {
			Marshaller marshaller = getJAXBContext(value.getClass()).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(value, stringWriter);
			return stringWriter.toString();
		}
		else {
			return "<dialog id=\"PRPTS\" width=\"0\" height=\"0\" left=\"\" top=\"\" " +
					"icon=\"PRPT\"><readpanel id=\"PRPTBOX\" text=\"PRPT\" fill=\"b\" " + 
					"scroll=\"v\" nl=\"1\"><properties><entry name=\"text\">PRPT</entry>" + 
					"<entry name=\"modifiable\">0</entry></properties></readpanel></dialog>";
		}
	}
	
    private JAXBContext getJAXBContext(Class<?> type) throws Exception {
        if (jaxbContext != null) {
            return jaxbContext;
        }
        return JAXBContext.newInstance(type);
    }

}
