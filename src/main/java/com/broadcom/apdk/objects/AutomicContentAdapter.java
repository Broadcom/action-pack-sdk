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

class AutomicContentAdapter extends XmlAdapter<Element, IAutomicContent> {

	private JAXBContext jaxbContext;
	private DocumentBuilder documentBuilder;

	public AutomicContentAdapter() {}
			
	public AutomicContentAdapter(JAXBContext jaxbContext) {
        super();
        this.jaxbContext = jaxbContext;
    }

	@Override
	public IAutomicContent unmarshal(Element v) throws Exception {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Element marshal(IAutomicContent value) throws Exception {
		boolean isLink = false;
        if (value != null) {
        	Class<?> type = value.getClass();
        	if (!AutomicObject.class.isAssignableFrom(type)) {
        		if (IAutomicContentLink.class.isAssignableFrom(type)) {
        			IAutomicContentLink link = (IAutomicContentLink) value;
        			if (link.getObject() == null) {
        				return null;
        			}
        			value = link.getObject();
        			type = link.getObject().getClass();
        			isLink = true;
        		}
        	}
        	String elementName = getRootElementName(type);
        	QName rootElement = new QName(elementName);
        	IAutomicContent object = getAutomicObjectReference(value, isLink);  
        	JAXBElement jaxbElement = new JAXBElement(rootElement, object.getClass(), object);
        	Document document = getDocumentBuilder().newDocument();
        	Marshaller marshaller = getJAXBContext(object.getClass()).createMarshaller();
        	marshaller.marshal(jaxbElement, document);
        	
        	Element element = document.getDocumentElement();
        	return element;
        }
        return null;
	}
	
	private IAutomicContent getAutomicObjectReference(IAutomicContent object, boolean isLink) {
		if (AutomicObject.class.isAssignableFrom(object.getClass())) {
			AutomicObject automicObject = (AutomicObject) object;
			return new AutomicContentReference(automicObject.getName(), isLink);
		}
		return object;
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
