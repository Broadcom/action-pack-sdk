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

public class PromptSetDataEntryAdapter extends XmlAdapter<Element, PromptSetDataEntry> {
	
	private JAXBContext jaxbContext;
	private DocumentBuilder documentBuilder;

	public PromptSetDataEntryAdapter() {}
			
	public PromptSetDataEntryAdapter(JAXBContext jaxbContext) {
        super();
        this.jaxbContext = jaxbContext;
    }

	@Override
	public PromptSetDataEntry unmarshal(Element value) throws Exception {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Element marshal(PromptSetDataEntry value) throws Exception {
        if (value != null) {
        	Class<?> type = value.getClass();
        	String elementName = VariableHelper.getSanitizedValue(value.getVariableName());
        	QName rootElement = new QName(elementName);
        	JAXBElement jaxbElement = new JAXBElement(rootElement, type, value);
        	Document document = getDocumentBuilder().newDocument();
        	Marshaller marshaller = getJAXBContext(value.getClass()).createMarshaller();
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
