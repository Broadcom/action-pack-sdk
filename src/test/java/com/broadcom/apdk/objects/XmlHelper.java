package com.broadcom.apdk.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlHelper {
	
	public static String getXMLAsString(IAutomicObject object) {
		try {
			StringWriter stringWriter = new StringWriter();
			JAXBContext context = org.eclipse.persistence.jaxb.JAXBContext.newInstance(object.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			m.marshal(object, stringWriter);
			return stringWriter.toString().replace("\n", "").replace("\r", "");
		}
		catch (PropertyException e) {
			e.printStackTrace();	
		} 
		catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getXMLFragmentAsString(String resourceName, String xPathExpression) {
		if (resourceName != null && !resourceName.isEmpty()) {
			ClassLoader classLoader = XmlHelper.class.getClassLoader();
			InputStream xmlResource = classLoader.getResourceAsStream(resourceName);
			return getXMLFragmentAsString(xmlResource, xPathExpression);
		}
		return null;
	}
	
	public static String getXMLFragmentAsString(File xmlFile, String xPathExpression) {
		if (xmlFile != null && xmlFile.isFile() && xmlFile.exists()) {
			try {
				return getXMLFragmentAsString(new FileInputStream(xmlFile), xPathExpression);
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private static String getXMLFragmentAsString(InputStream xmlResource, String xPathExpression) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			Document document = documentBuilderFactory.newDocumentBuilder().parse(xmlResource);
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node node = (Node) xPath.evaluate(xPathExpression, document, XPathConstants.NODE);
			return getString(node);
		}
		catch (IOException e) {
			e.printStackTrace();			
		}
		catch (SAXException e) {
			e.printStackTrace();
		} 
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		} 
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getString(Node node) {
		try {
			StringWriter stringWriter = new StringWriter();
			Transformer xform = TransformerFactory.newInstance().newTransformer();
			xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			xform.transform(new DOMSource(node), new StreamResult(stringWriter));
			return stringWriter.toString().replace("\n", "").replace("\r", "");
		}
		catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} 
		catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} 
		catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}

}
