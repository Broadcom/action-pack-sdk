package com.broadcom.apdk.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.broadcom.apdk.api.IAction;
import com.broadcom.apdk.api.IParamAdapter;
import com.broadcom.apdk.api.IPromptSet;
import com.broadcom.apdk.api.ParamAdapter;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.apdk.api.annotations.ActionParamAdapter;
import com.broadcom.apdk.api.annotations.EnumValue;

public class ActionHelper {
	
	private final static Logger LOGGER = Logger.getLogger("APDK");
	
	public static Map<String, Object[]> getActionInputParamValues(IAction action) {
		Map<String, Object[]> initValues = new HashMap<String, Object[]>();
		Map<String, List<Field>> inputParams = getActionInputParams(action);
		for (String fieldName : inputParams.keySet()) {
			List<Field> fields = inputParams.get(fieldName);
			if (!fields.isEmpty() && fields.size() <= 2) {
				Field field = fields.get(fields.size() - 1);
				Method getter = getGetter(action.getClass(), field);
				Object object = action;
				Field promptSetField = null;
				if (fields.size() == 2) {
					promptSetField	= fields.get(0);
					getter = getGetter(promptSetField.getType(), field);
					try {
						promptSetField.setAccessible(true);
						object = promptSetField.get(action);
						if (object == null && 
								com.broadcom.apdk.api.IPromptSet.class.isAssignableFrom(promptSetField.getType())) {
							Class<?> promptSetClass = promptSetField.getType();
							object = promptSetClass.getDeclaredConstructor().newInstance();	
						}
					} catch (IllegalArgumentException | IllegalAccessException | InstantiationException | 
							InvocationTargetException | NoSuchMethodException | SecurityException e) {
						LOGGER.warning("Exception: " + e.toString());	
					}	
				}
				String variableName = getVariableNameFromParam(field, ActionInputParam.class);
				Object[] fieldValues = new Object[2];
				fieldValues[0] = getValue(getter, field, object);

				if (fieldValues[0] != null) {
					fieldValues[1] = getParamAsString(field, (IPromptSet) object);
					if (Enum.class.isAssignableFrom(field.getType())) {
						Field[] enumFields = field.getType().getDeclaredFields();
						if (enumFields != null) {
							for (Field enumField : enumFields) {
								if (enumField.isEnumConstant()) {
									String value = enumField.getName();
									if (enumField.isAnnotationPresent(EnumValue.class) &&
											value.equals(fieldValues[1])) {
										EnumValue annotation = enumField.getAnnotation(EnumValue.class);
										fieldValues[1] = annotation.value();
									}
								}
							}
						}
					}
					initValues.put(variableName, fieldValues);
				}
			}
		}
		return initValues;
	}
	
	private static Object getValue(Method getter, Field field, Object object) {
		if (getter == null) {
			try {
				field.setAccessible(true);
				return field.get(object);	
			} catch (IllegalArgumentException | IllegalAccessException e) {
				LOGGER.warning("Exception: " + e.toString());	
			}			
		}
		else {
			try {
				return getter.invoke(object);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.warning("Exception: " + e.toString());	
			}
		}
		return null;			
	}
	
	private static Method getGetter(Class<?> objClass, Field field) {
		List<Method> methods = getMethods(objClass, null);
		for (Method method : methods) {
			if (method.getParameterCount() == 0 && method.getReturnType().equals(field.getType())) {
				if (method.getName().equalsIgnoreCase("get" + field.getName())) {
					return method;
				}
				if (method.getReturnType().equals(Boolean.TYPE) && 
						method.getReturnType().getClass().equals(Boolean.class) && 
						method.getName().equalsIgnoreCase("is" + field.getName())) {
					return method;
				}
			}
		}
		return null;
	}
	
	private static List<Method> getMethods(Class<?> objClass, List<Method> methods) {
		if (methods == null) {
			methods = new ArrayList<Method>();
		}
		if (objClass.getSuperclass() != null) {
			methods.addAll(getMethods(objClass.getSuperclass(), methods));	
		}
		Method[] declaredMethods = objClass.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			methods.add(declaredMethod);
		}
		return methods;
	}
	
	public static boolean hasPasswordParameters(IAction action) {
		Class<? extends IAction> actionClass = action.getClass();
		if (actionClass != null) {
			Map<String, List<Field>> inputParams = ActionHelper.getActionInputParams(action);
			for (String paramName : inputParams.keySet()) {
				List<Field> fieldHierarchy = inputParams.get(paramName);
				if (!fieldHierarchy.isEmpty()) {
					Field field = fieldHierarchy.get(fieldHierarchy.size() - 1);
					if (isPasswordParameter(field)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static String getEncryptedPassword(String decryptedPassword) {
		return callItpaTool("encrypt", decryptedPassword);
	}
	
	public static String getDecryptedPassword(String encryptedPassword) {
		return callItpaTool("decrypt", encryptedPassword);
	}
	
	private static String callItpaTool(String command, String password) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		String returnValue = password;
		File itpaTool = null;
		try {
			Path jarFile = new File(ActionHelper.class.getProtectionDomain().
					getCodeSource().getLocation().toURI()).toPath();
			String folder = jarFile.toString().substring(0, 
					jarFile.toString().length() - jarFile.getFileName().toString().length());
			itpaTool = new File(folder + "itpa-tool.jar");
		} 
		catch (URISyntaxException e1) {
			LOGGER.severe("Failed to find \"itpa-tool.jar\" to " + command + " \"" + password + "\"");
		}
		
		if (itpaTool != null && itpaTool.isFile() && itpaTool.exists()) {
			LOGGER.info("Found \"itpa-tool.jar\" to " + command + " \"" + password + "\"");	
			String shell = "bash";
			String shellArg = "-c";		
			if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
				shell = "cmd.exe";
				shellArg = "/c";					
			}
			String cmd = "java -jar \"" + itpaTool.toPath() + 
					"\" ARB -cmd cipher " + command + " \"" + password + "\"";
			processBuilder.command(shell, shellArg, cmd);
			try {
				Process process = processBuilder.start();
				StringBuilder output = new StringBuilder();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					if (command.equals("decrypt") && line.startsWith("Decrypted: ") && line.length() > 11) {
						returnValue = line.substring(11);	
						LOGGER.info("Successfully decrypted \"" + password + "\"");	
					}
					else if (command.equals("encrypt") && line.startsWith("Encrypted: ") && line.length() > 11) {
						returnValue = line.substring(11);	
						LOGGER.info("Successfully encrypted \"" + password + "\"");						
					}	
					output.append(line + "\n");
				}

				int exitVal = process.waitFor();
				if (exitVal != 0) {
					LOGGER.info("Executing \"itpa-tool.jar\" caused an error");	
				} 
			} 
			catch (IOException e) {
				LOGGER.warning("Exception: " + e.toString());	
			} 
			catch (InterruptedException e) {
				LOGGER.warning("Exception: " + e.toString());	
			}
		}
		else {
			LOGGER.severe("Failed to find \"itpa-tool.jar\" to " + command + " \"" + password + "\"");
		}
		return returnValue;
	}
	
	public static List<Field> getPasswordInputParameters(IAction action) {
		List<Field> passwordFields = new ArrayList<Field>();
		Class<? extends IAction> actionClass = action.getClass();
		if (actionClass != null) {
			Map<String, List<Field>> inputParams = ActionHelper.getActionInputParams(action);
			for (String paramName : inputParams.keySet()) {
				List<Field> fieldHierarchy = inputParams.get(paramName);
				if (!fieldHierarchy.isEmpty()) {
					Field field = fieldHierarchy.get(fieldHierarchy.size() - 1);
					if (isPasswordInputParameter(field)) {
						passwordFields.add(field);
					}
				}
			}
		}
		return passwordFields;
	}
	
	public static String[] getArguments(IAction action, Class<?> osType) {
		List<String> arguments = new ArrayList<String>();
		Class<? extends IAction> actionClass = action.getClass();
		if (actionClass != null) {
			Map<String, List<Field>> inputParams = ActionHelper.getActionInputParams(action);
			for (String paramName : inputParams.keySet()) {
				List<Field> fieldHierarchy = inputParams.get(paramName);
				if (!fieldHierarchy.isEmpty()) {
					Field field = fieldHierarchy.get(fieldHierarchy.size() - 1);
					String variableName = ActionHelper.getVariableNameFromParam(field, ActionInputParam.class);
					arguments.add(paramName + "=\"" + variableName + "\"");
				}
			}
		}
		return arguments.toArray(new String[arguments.size()]);
	}
	
	public static boolean isPasswordInputParameter(Field field) {
		ActionInputParam annotation = field.getAnnotation(ActionInputParam.class);
		if (annotation != null && annotation.password()) {
			return true;
		}
		return false;
	}
	
	public static boolean isPasswordOutputParameter(Field field) {
		ActionOutputParam annotation = field.getAnnotation(ActionOutputParam.class);
		if (annotation != null && annotation.password()) {
			return true;
		}
		return false;
	}
	
	public static boolean isPasswordParameter(Field field) {
		return isPasswordOutputParameter(field) || isPasswordInputParameter(field);
	}
	
	public static String getParamAsString(Field field, IPromptSet actionOrPromptSet) {
		if (field != null && actionOrPromptSet != null) {
			try {
				if (field != null) {
					field.setAccessible(true);
					Object value = field.get(actionOrPromptSet);
					if (value != null) {
						// Check if an adapter was defined
						if (field.isAnnotationPresent(ActionParamAdapter.class)) {
							ActionParamAdapter annotation = field.getAnnotation(ActionParamAdapter.class);
							Class<? extends ParamAdapter<?>> adapterClass = annotation.value();
							if (adapterClass != null) {
								Class<?> typeArgument = (Class<?>) ((ParameterizedType) adapterClass
										.getGenericSuperclass()).getActualTypeArguments()[0];
								IParamAdapter<?> adapter = adapterClass.getDeclaredConstructor().newInstance();
								if (typeArgument.isAssignableFrom(field.getType())) {
									Method method = adapter.getClass().getDeclaredMethod("convertToString", typeArgument);
									String returnValue = (String) method.invoke(adapter, typeArgument.cast(value));
									return returnValue;
								}
								else {
									LOGGER.warning("Parameter adapter \"" + adapter.getClass().getName() + 
											"\" is not applicable for type \"" + field.getType().getName() + 
											"\" of parameter \"" + field.getName() + "\"");		
								}
							}
						}
						// If no adapter was defined or a defined adapter couldn't be applied...
						if (String.class.equals(value.getClass())) {
							return (String) value;
						}
						else if (Long.TYPE.equals(value.getClass()) || Long.class.equals(value.getClass())) {
							return Long.toString((long) value);
						}
						else if (Integer.TYPE.equals(value.getClass()) || Integer.class.equals(value.getClass())) {
							return Integer.toString((int) value);
						}
						else if (Float.TYPE.equals(value.getClass()) || Float.class.equals(value.getClass())) {
							return Float.toString((float) value);
						}
						else if (Double.TYPE.equals(value.getClass()) || Double.class.equals(value.getClass())) {
							return Double.toString((double) value);
						}
						else if (Boolean.TYPE.equals(value.getClass()) || Boolean.class.equals(value.getClass())) {
							return Boolean.toString((boolean) value);
						}
						else if (Character.TYPE.equals(value.getClass()) || Character.class.equals(value.getClass())) {
							return Character.toString((char) value);
						}
						else if (Byte.TYPE.equals(value.getClass()) || Byte.class.equals(value.getClass())) {
							return Byte.toString((byte) value);
						}
						else if (Short.TYPE.equals(value.getClass()) || Short.class.equals(value.getClass())) {
							return Short.toString((short) value);
						}
						else if (Enum.class.isAssignableFrom(field.getType())) {
							return ((Enum<?>) value).name();	
						}
						else if (LocalDateTime.class.isAssignableFrom(field.getType())) {
							return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
						}
						else {
							try {
								Method toStringMethod = field.getType().getDeclaredMethod("toString", new Class<?>[0]);
								return (String) toStringMethod.invoke(value);
							}
							catch (NoSuchMethodException e) {}
						}
						LOGGER.warning("Failed to convert field \"" + field.getName() + "\" of type \"" + 
								field.getType().getName() + "\" to String. Please define a parameter adapter!");	
					}
				}
			}
			catch (IllegalAccessException | InstantiationException | NoSuchMethodException | 
					SecurityException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.warning("Exception: " + e.toString());	
			}	
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public static <T> T getArgumentAsParamType(Field field, String value) {
		Class<?> T = field != null ? field.getType() : Object.class;
		if (field != null) {
			try {
				if (field != null) {
					field.setAccessible(true);
					if (value != null && !value.isEmpty()) {
						// Check if an adapter was defined
						if (field.isAnnotationPresent(ActionParamAdapter.class)) {
							ActionParamAdapter annotation = field.getAnnotation(ActionParamAdapter.class);
							Class<? extends ParamAdapter<?>> adapterClass = annotation.value();
							if (adapterClass != null) {
								Class<?> typeArgument = (Class<?>) ((ParameterizedType) adapterClass
										.getGenericSuperclass()).getActualTypeArguments()[0];
								IParamAdapter<?> adapter = adapterClass.getDeclaredConstructor().newInstance();
								if (typeArgument.isAssignableFrom(field.getType())) {
									Method method = adapter.getClass().getDeclaredMethod("convertToType", String.class);
									Object typeValue = method.invoke(adapter, value);
									return (T) typeValue;
								}
								else {
									LOGGER.warning("Parameter adapter \"" + adapter.getClass().getName() + 
											"\" is not applicable for type \"" + field.getType().getName() + 
											"\" of parameter \"" + field.getName() + "\"");		
								}
							}
						}
						// If no adapter was defined or a defined adapter couldn't be applied...
						if (String.class.equals(field.getType())) {
							return (T) value;
						}
						else if (Long.TYPE.equals(field.getType()) || Long.class.equals(field.getType())) {
							return (T) Long.valueOf(value);
						}
						else if (Integer.TYPE.equals(field.getType()) || Integer.class.equals(field.getType())) {
							return (T) Integer.valueOf(value);
						}
						else if (Float.TYPE.equals(field.getType()) || Float.class.equals(field.getType())) {
							return (T) Float.valueOf(value);
						}
						else if (Double.TYPE.equals(field.getType()) || Double.class.equals(field.getType())) {
							return (T) Double.valueOf(value);
						}
						else if (Boolean.TYPE.equals(field.getType()) || Boolean.class.equals(field.getType())) {
							return (T) Boolean.valueOf(value);
						}
						else if (Character.TYPE.equals(field.getType()) || Character.class.equals(field.getType())) {
							return (T) Character.valueOf(value.charAt(0));
						}
						else if (Byte.TYPE.equals(field.getType()) || Byte.class.equals(field.getType())) {
							return (T) Byte.valueOf(value);
						}
						else if (Short.TYPE.equals(field.getType()) || Short.class.equals(field.getType())) {
							return (T) Short.valueOf(value);
						}	
						else if (Enum.class.isAssignableFrom(field.getType())) {
							Field[] enumFields = field.getType().getDeclaredFields();
							if (enumFields != null) {
								for (Field enumField : enumFields) {
									if (enumField.isEnumConstant()) {
										if (enumField.isAnnotationPresent(EnumValue.class)) {
											EnumValue annotation = enumField.getAnnotation(EnumValue.class);
											if (value.equals(annotation.value())) {
												return (T) Enum.valueOf((Class<? extends Enum>) field.getType(), enumField.getName());
											}
										}
									}
								}
							}
							return (T) Enum.valueOf((Class<? extends Enum>) field.getType(), value);	
						}
						else if (LocalDate.class.equals(field.getType())) {
							return (T) LocalDate.parse(value);
						}
						else if (LocalTime.class.equals(field.getType())) {
							return (T) LocalTime.parse(value);
						}
						else if (LocalDateTime.class.equals(field.getType())) {
							LocalDateTime dateTime;
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm[:ss]]");
							TemporalAccessor temporalAccessor = formatter.parseBest(value, LocalDateTime::from, LocalDate::from);
							if (temporalAccessor instanceof LocalDateTime) {
								dateTime = (LocalDateTime) temporalAccessor;
							} 
							else {
								dateTime = ((LocalDate) temporalAccessor).atStartOfDay();
							}								
							return (T) dateTime;
						}
						LOGGER.warning("Failed to convert String representation of field \"" + field.getName() + "\" " +
								"to its original type \"" + field.getType().getName() + "\". " +
								"Please define a parameter adapter!");
					}
				}
			}
			catch (IllegalAccessException | InstantiationException | NoSuchMethodException | 
					SecurityException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.warning("Exception: " + e.toString());	
			}	
		}
		return null;
	}
	
	public static String getVariableNameFromParam(Field field, Class<? extends Annotation> annotationClass) {
		Annotation annotation = field.getAnnotation(annotationClass);
		try {
			Method nameMethod = annotation.getClass().getDeclaredMethod("name", new Class<?>[0]);
			String value = (String) nameMethod.invoke(annotation);
			if (!value.trim().isEmpty()) {
				value = value.trim().toUpperCase();
				if (!value.startsWith("&")) {
					value = "&" + value;
				}
				if (!value.endsWith("#")) {
					value = value + "#";
				}
				return value; 
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException e) {
			LOGGER.warning("Exception: " + e.toString());	
		}	
		return "&" + field.getName().toUpperCase() + "#";
	}
	
	public static Map<String, String> getCDAMappings(List<IAction> actions) {
		Map<String, String> mappings = new HashMap<String, String>();
		for (IAction action : actions) {
			Map<String, List<Field>> inputParams = getActionInputParams(action);	
			for (String fieldName : inputParams.keySet()) {
				List<Field> fieldHierarchy = inputParams.get(fieldName);
				if (!fieldHierarchy.isEmpty()) {
					Field field = fieldHierarchy.get(fieldHierarchy.size() - 1);
					ActionInputParam annotation = field.getAnnotation(ActionInputParam.class);
					if (!annotation.cdaMapping().isEmpty()) {
						String key = action.getName() != null ? 
								action.getName().replace(" ", "_").toUpperCase() : 
								action.getClass().getSimpleName().toUpperCase();
						String paramName = field.getName().toUpperCase() + "#";
						if (!annotation.name().isEmpty()) {
							paramName = annotation.name();
							if (paramName.startsWith("&")) {
								paramName = paramName.substring(1);
							}
							if (!paramName.endsWith("#")) {
								paramName += "#";
							}
						}
						key += "/" + paramName;
						LOGGER.info("Found CDA mapping for \"" + key + 
								"\": \"" + annotation.cdaMapping() + "\"");
						mappings.put(key, annotation.cdaMapping());
					}
				}
			}
		}
		return mappings;
	}
	
	public static Map<String, Field> getActionOutputParams(IAction action) {
		Map<String, Field> outputParams = new HashMap<String, Field>();
		Map<String, List<Field>> params = getActionParams(action.getClass(), ActionOutputParam.class);
		for (String fieldName : params.keySet()) {
			List<Field> fieldHierarchy = params.get(fieldName);
			if (fieldHierarchy.size() == 1) {
				outputParams.put(fieldName, fieldHierarchy.get(0));	
			}
		}
		return outputParams;
	}
	
	public static Map<String, List<Field>> getActionInputParams(IAction action) {
		return getActionParams(action.getClass(), ActionInputParam.class);
	}	
	
	@SuppressWarnings("unchecked")
	private static Map<String, List<Field>> getActionParams(Class<? extends com.broadcom.apdk.api.IPromptSet> actionClass, 
			Class<? extends Annotation> annotationClass) {
		Map<String, List<Field>> inputParams = new HashMap<String, List<Field>>();
		if (actionClass != null) {
			Class<?> parentClass = actionClass.getSuperclass();
			Field[] fields = actionClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(annotationClass)) {
					List<Field> fieldHierarchy= new ArrayList<Field>();
					fieldHierarchy.add(field);
					inputParams.put(field.getName(), fieldHierarchy);	
				}
				else if (IAction.class.isAssignableFrom(actionClass) &&
						ActionInputParam.class.isAssignableFrom(annotationClass) &&
						com.broadcom.apdk.api.IPromptSet.class.isAssignableFrom(field.getType())) {
					Map<String, List<Field>> refParams = 
							getActionParams((Class<? extends IPromptSet>) field.getType(), annotationClass);
					for (String fieldName : refParams.keySet()) {
						List<Field> fieldHierarchy= new ArrayList<Field>();
						fieldHierarchy.add(field);
						fieldHierarchy.addAll(refParams.get(fieldName));
						inputParams.put(fieldName, fieldHierarchy);	
					}
				}
			}
			if (IAction.class.isAssignableFrom(parentClass) && !parentClass.isInterface()) {
				inputParams.putAll(getActionParams((Class<? extends IAction>) parentClass, annotationClass));
			}
		}
		return inputParams;		
	}

}
