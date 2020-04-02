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
import com.broadcom.apdk.api.ParamAdapter;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.apdk.api.annotations.ActionParamAdapter;

public class ActionHelper {
	
	private final static Logger LOGGER = Logger.getLogger("APDK");
	
	public static boolean hasPasswordFields(IAction action) {
		Class<? extends IAction> actionClass = action.getClass();
		if (actionClass != null) {
			Map<String, Field> inputParams = ActionHelper.getActionInputParams(action);
			for (String paramName : inputParams.keySet()) {
				Field field = inputParams.get(paramName);
				if (isPasswordField(field)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getDecryptedPassword(String encrptedPassword) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		String decrpytedPassword = encrptedPassword;
		File itpaTool = null;
		try {
			Path jarFile = new File(ActionHelper.class.getProtectionDomain().
					getCodeSource().getLocation().toURI()).toPath();
			String folder = jarFile.toString().substring(0, 
					jarFile.toString().length() - jarFile.getFileName().toString().length());
			itpaTool = new File(folder + "itpa-tool.jar");
		} 
		catch (URISyntaxException e1) {
			LOGGER.severe("Failed to find \"itpa-tool.jar\" to decrypt \"" + encrptedPassword + "\"");
		}
		
		if (itpaTool != null && itpaTool.isFile() && itpaTool.exists()) {
			LOGGER.info("Found \"itpa-tool.jar\" to decrypt \"" + encrptedPassword + "\"");	
			String shell = "bash";
			String shellArg = "-c";		
			if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
				shell = "cmd.exe";
				shellArg = "/c";					
			}
			String command = "java -jar \"" + itpaTool.toPath() + 
					"\" ARB -cmd cipher decrypt \"" + encrptedPassword + "\"";
			processBuilder.command(shell, shellArg, command);
			try {
				Process process = processBuilder.start();
				StringBuilder output = new StringBuilder();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("Decrypted: ") && line.length() > 11) {
						decrpytedPassword = line.substring(11);	
						LOGGER.info("Successfully decrypted \"" + encrptedPassword + "\"");	
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
			LOGGER.severe("Failed to find \"itpa-tool.jar\" to decrypt \"" + encrptedPassword + "\"");
		}
		return decrpytedPassword;
	}
	
	public static List<Field> getPasswordFields(IAction action) {
		List<Field> passwordFields = new ArrayList<Field>();
		Class<? extends IAction> actionClass = action.getClass();
		if (actionClass != null) {
			Map<String, Field> inputParams = ActionHelper.getActionInputParams(action);
			for (String paramName : inputParams.keySet()) {
				Field field = inputParams.get(paramName);
				if (isPasswordField(field)) {
					passwordFields.add(field);
				}
			}
		}
		return passwordFields;
	}
	
	public static String[] getArguments(IAction action, Class<?> osType) {
		List<String> arguments = new ArrayList<String>();
		Class<? extends IAction> actionClass = action.getClass();
		if (actionClass != null) {
			Map<String, Field> inputParams = ActionHelper.getActionInputParams(action);
			for (String paramName : inputParams.keySet()) {
				Field field = inputParams.get(paramName);
				String variableName = ActionHelper.getVariableNameFromParam(field, ActionInputParam.class);
				arguments.add(paramName + "=\"" + variableName + "\"");
			}
		}
		return arguments.toArray(new String[arguments.size()]);
	}
	
	public static boolean isPasswordField(Field field) {
		ActionInputParam annotation = field.getAnnotation(ActionInputParam.class);
		if (annotation != null && annotation.password()) {
			return true;
		}
		return false;
	}
	
	public static String getParamAsString(Field field, IAction action) {
		if (field != null && action != null) {
			try {
				if (field != null) {
					field.setAccessible(true);
					Object value = field.get(action);
					if (value != null) {
						// Check if an adapter was defined
						if (field.isAnnotationPresent(ActionParamAdapter.class)) {
							ActionParamAdapter annotation = field.getAnnotation(ActionParamAdapter.class);
							Class<? extends ParamAdapter<?>> adapterClass = annotation.value();
							if (adapterClass != null) {
								Class<?> typeArgument = (Class<?>) ((ParameterizedType) adapterClass
										.getGenericSuperclass()).getActualTypeArguments()[0];
								IParamAdapter<?> adapter = adapterClass.newInstance();
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
	public static <T> T getParamAsOriginalType(Field field, IAction action, String value) {
		Class<?> T = field != null ? field.getType() : Object.class;
		if (field != null && action != null) {
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
								IParamAdapter<?> adapter = adapterClass.newInstance();
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
	
	public static Map<String, Field> getActionOutputParams(IAction action) {
		return getActionParams(action.getClass(), ActionOutputParam.class);
	}
	
	public static Map<String, Field> getActionInputParams(IAction action) {
		return getActionParams(action.getClass(), ActionInputParam.class);
	}	
	
	@SuppressWarnings("unchecked")
	private static Map<String, Field> getActionParams(Class<? extends IAction> actionClass, 
			Class<? extends Annotation> annotationClass) {
		Map<String, Field> inputParams = new HashMap<String, Field>();
		if (actionClass != null) {
			Class<?> parentClass = actionClass.getSuperclass();
			Field[] fields = actionClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(annotationClass)) {
					inputParams.put(field.getName(), field);	
				}
			}
			if (IAction.class.isAssignableFrom(parentClass) && !parentClass.isInterface()) {
				inputParams.putAll(getActionParams((Class<? extends IAction>) parentClass, annotationClass));
			}
		}
		return inputParams;		
	}

}
