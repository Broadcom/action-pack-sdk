package com.broadcom.apdk.objects;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class AutomicObjectAdapter extends XmlAdapter<Element, IAutomicObject> {

	private JAXBContext jaxbContext;
	private DocumentBuilder documentBuilder;

	public AutomicObjectAdapter() {}
	
	public AutomicObjectAdapter(JAXBContext jaxbContext) {
        super();
        this.jaxbContext = jaxbContext;
    }

	@Override
	public IAutomicObject unmarshal(Element v) throws Exception {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Element marshal(IAutomicObject value) throws Exception {
        if (value != null) {
        	Class<?> type = value.getClass();
        	String elementName = getRootElementName(type);
        	QName rootElement = new QName(elementName);
        	JAXBElement jaxbElement = new JAXBElement(rootElement, type, value);
        	Document document = getDocumentBuilder().newDocument();
        	Marshaller marshaller = getJAXBContext(type).createMarshaller();
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
    
	private String getRootElementName(Class<?> objectClass) {
		XmlRootElement rootElementAnnotation = objectClass.getAnnotation(XmlRootElement.class);
		if (rootElementAnnotation != null) {
			return rootElementAnnotation.name();
		}
		return null;
	}	

}
