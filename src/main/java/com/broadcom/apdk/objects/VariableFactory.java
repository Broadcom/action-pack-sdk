package com.broadcom.apdk.objects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class VariableFactory {
	
	public static IVariable getVariable(VariableDataType dataType, String name) 
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		if (dataType != null) {
			Class<?> variableClass = null;
			if (dataType.equals(VariableDataType.TEXT)) {
				variableClass = Class.forName(VariableText.class.getName());	
			}
			Constructor<?> constructor = variableClass.getConstructor(String.class);
			IVariable instance = (IVariable) constructor.newInstance(name);
			return instance;
		}
		return null;
	}

}
