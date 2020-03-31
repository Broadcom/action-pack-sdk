package com.broadcom.apdk.objects;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.broadcom.apdk.helpers.VariableHelper;

public class PromptSetNodeAdapter extends XmlAdapter<Element, IPrompt<?>> {
	
	private JAXBContext jaxbContext;
	private DocumentBuilder documentBuilder;

	public PromptSetNodeAdapter() {}
			
	public PromptSetNodeAdapter(JAXBContext jaxbContext) {
        super();
        this.jaxbContext = jaxbContext;
    }

	@Override
	public IPrompt<?> unmarshal(Element value) throws Exception {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Element marshal(IPrompt value) throws Exception {
        if (value != null) {
        	String elementName = VariableHelper.getSanitizedValue(value.getVariableName());
        	QName rootElement = new QName(elementName);
        	PromptSetNodePromptEntry entry = new PromptSetNodePromptEntry();
        	JAXBElement jaxbElement = new JAXBElement(rootElement, entry.getClass(), entry);
        	Document document = getDocumentBuilder().newDocument();
        	Marshaller marshaller = getJAXBContext(entry.getClass()).createMarshaller();
        	marshaller.marshal(jaxbElement, document);
        	
        	Element element = document.getDocumentElement();
        	return element;
        }
        return null;
	}
		
    private JAXBContext getJAXBContext(Class<?> type) throws Exception {
        if (jaxbContext != null) {
            return jaxbContext;
        }
        return JAXBContext.newInstance(type);
    }

    private DocumentBuilder getDocumentBuilder() throws Exception {
        if (documentBuilder != null) {
        	return documentBuilder;
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        documentBuilder = dbf.newDocumentBuilder(); 
        return documentBuilder;
    }

}
