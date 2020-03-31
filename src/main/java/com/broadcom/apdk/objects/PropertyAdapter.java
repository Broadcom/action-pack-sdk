package com.broadcom.apdk.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PropertyAdapter extends XmlAdapter<Properties, Map<String, PropertyValue>> {

	@Override
	public Map<String, PropertyValue> unmarshal(Properties properties) throws Exception {
		if (properties != null) {
			List<Property> props = properties.getProperties();
			Map<String, PropertyValue> propertiesMap = new HashMap<String, PropertyValue>();
			for (Property property : props) {
				propertiesMap.put(property.getKey(), property.getValue());
			}
			return propertiesMap;
		}
		return null;
	}

	@Override
	public Properties marshal(Map<String, PropertyValue> propertiesMap) throws Exception {
		if (propertiesMap != null) {
			List<Property> properties = new ArrayList<Property>();
			List<String> propertyKeys = getPropertyOrder(propertiesMap);
			if (propertyKeys != null && !propertyKeys.isEmpty()) {
				for (String key : propertyKeys) {
					PropertyValue propertyValue = (PropertyValue) propertiesMap.get(key);
					if (propertyValue != null) {
						if ("reference".equals(key)) {
							properties.add(new Property(key, propertyValue.getValue(), 
									propertyValue.getRefType(), propertyValue.getListParam(), 
									propertyValue.getAlias()));	
						}
						else {
							properties.add(new Property(key, propertyValue.getValue(), propertyValue.getAlias()));
						}				
					}
				}
			}
			Properties props = new Properties();
			props.setProperties(properties);
			return props;
		}
		return null;
	}
	
	private IPrompt<?> getParentPrompt(Map<String, PropertyValue> propertiesMap) {
		for (Map.Entry<String, PropertyValue> entry : propertiesMap.entrySet()) {
			if (entry.getValue() != null) {
				PropertyValue prop = (PropertyValue) entry.getValue();
				IPrompt<?> prompt = prop.getParentPrompt();
				if (prompt != null) {
					return prompt;
				}
			}
		}
		return null;
	}
	
	private List<String> getPropertyOrder(Map<String, PropertyValue> propertiesMap) {
		List<String> propertyKeys = new ArrayList<String>();
		IPrompt<?> prompt = getParentPrompt(propertiesMap);
		if (prompt != null) {
			PropertyOrder orderAnnotation = prompt.getClass().getAnnotation(PropertyOrder.class);
			if (orderAnnotation != null && orderAnnotation.keys().length > 0) {
				for (String key : orderAnnotation.keys()) {
					propertyKeys.add(key);	
				}
			}
			else {
				for (String key : propertiesMap.keySet()) {
					propertyKeys.add(key);	
				}
			}
			return propertyKeys;
		}
		else {
			for (String key : propertiesMap.keySet()) {
				propertyKeys.add(key);	
			}
			return propertyKeys;
		}
	}

}
