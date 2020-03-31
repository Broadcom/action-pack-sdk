package com.broadcom.apdk.cli;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.broadcom.apdk.api.IAction;
import com.broadcom.apdk.api.IActionPack;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.apdk.api.annotations.ActionPacks;
import com.broadcom.apdk.helpers.ActionHelper;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class CLI {
	
	private final static Logger LOGGER = Logger.getLogger("APDK");
	
	public static void main(String[] args) throws IOException {
		
		Options options = getOptions();
		CommandLine cmd = getParsedCommandLine(options, args);
		LOGGER.setLevel(Level.OFF);
		
		if (cmd.hasOption("help")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("action-pack-sdk", "", options, "", true);		
		}
		
		if (cmd.hasOption("logging")) {
			String logLevel = cmd.getOptionValue("logging");
			System.setProperty("java.util.logging.SimpleFormatter.format",
		              "[%1$tF %1$tT] [%4$-7s] %5$s %n");
			if ("ERROR".equalsIgnoreCase(logLevel)) {
				LOGGER.setLevel(Level.SEVERE);
			}
			if ("WARNING".equalsIgnoreCase(logLevel)) {
				LOGGER.setLevel(Level.WARNING);
			}
			if ("INFO".equalsIgnoreCase(logLevel)) {
				LOGGER.setLevel(Level.INFO);
			}
			if ("DEBUG".equalsIgnoreCase(logLevel)) {
				LOGGER.setLevel(Level.FINE);
			}
		}
		
		if (cmd.hasOption("info")) {
			String actionPackClassName = cmd.getOptionValue("info");
			try {
				List<Class<? extends IActionPack>> actionPacksClasses = getActionPacksOnClassPath();
				List<Class<? extends IAction>> actionClasses = getActionsOnClassPath();
				if (actionPackClassName == null) {
					printActionPacks(actionPacksClasses);
					printActions(actionClasses);
				}
				else {
					actionPackClassName = actionPackClassName.trim();
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					Class<?> actionPackClass = classLoader.loadClass(actionPackClassName);			
					IActionPack actionPack = (IActionPack) actionPackClass.newInstance();
					if (actionPack != null && actionPack.getName() != null) {
						System.out.println("[ActionPack] " + actionPackClass.getName() + " (" + actionPack.getName() + ")");	
					}
					else {
						System.out.println("[ActionPack] " + actionPackClass.getName());
					}
					List<Class<? extends IAction>> linkedActions = getActions(actionPack, actionClasses);
					if (actionPacksClasses.size() == 1 && linkedActions.isEmpty()) {
						printActions(actionClasses);
					}	
					else {
						printActions(linkedActions);
					}
				}
			}
			catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				System.out.println("ERROR: " + e.toString());
			} 
		}
		
		if (cmd.hasOption("export")) {
			Path destinationPath = null;
			if (cmd.hasOption("destination")) {
				destinationPath = Paths.get(cmd.getOptionValue("destination"));	
			}
			String actionPackClassName = cmd.getOptionValue("export");
			LOGGER.info("Export action pack \"" + actionPackClassName + "\"");
			try {
				
				// Search for implemented Action Packs on the class path and pick the first
				// one if no class name was provided as option argument
				
				List<Class<? extends IActionPack>> actionPacks = getActionPacksOnClassPath();
				if (actionPackClassName == null && !actionPacks.isEmpty()) {
					actionPackClassName = actionPacks.get(0).getName();
				}
											
				if (actionPackClassName != null) {	
					actionPackClassName = actionPackClassName.trim();
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					Class<?> actionPackClass = classLoader.loadClass(actionPackClassName);	
					IActionPack actionPack = (IActionPack) actionPackClass.newInstance();	
					
					List<Class<? extends IAction>> allActions = getActionsOnClassPath();
					List<Class<? extends IAction>> actionClasses = getActions(actionPack, allActions);
					if (actionPacks.size() == 1 && actionClasses.isEmpty()) {
						actionClasses = allActions;	
					}
					
					List<IAction> actions = new ArrayList<IAction>();
					for (Class<? extends IAction> actionClass : actionClasses) {
						IAction action = (IAction) actionClass.newInstance();		
						actions.add(action);
					}
					
					ExportService exportService = new ExportService();
					exportService.export(actionPack, actions, destinationPath);
				}
				else {
					LOGGER.warning("Couldn't identify an implemented action pack \"" + 
							actionPackClassName + "\"");
				}

			} 
			catch (IOException | ExportException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
				LOGGER.severe("Export failed: " + e.toString());
			} 
		}
		
		if (cmd.hasOption("run")) {
			String actionClassName = cmd.getOptionValue("run");		
			LOGGER.info("Run action \"" + actionClassName + "\"");
			if (actionClassName != null) {
				actionClassName = actionClassName.trim();
				try {
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					((URLClassLoader) classLoader).getURLs();
					Class<?> actionClass = classLoader.loadClass(actionClassName);	
					
					if (IAction.class.isAssignableFrom(actionClass) &&
							!actionClass.isInterface() && !Modifier.isAbstract(actionClass.getModifiers())) {
						IAction action = (IAction) actionClass.newInstance();	
						injectInputParamValues(action, cmd.getArgs());
						action.run();
						printOutputParamValues(action);
					}
				} 
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					LOGGER.severe("Execution failed: " + e.toString());
				} 
			}
			else {
				LOGGER.warning("No action was specified");	
			}
		}   
	}
	
	private static Options getOptions() {
		Options options = new Options();		
		OptionGroup optionGroup = new OptionGroup();
		optionGroup.setRequired(true);
		optionGroup.addOption(CLIOptions.RUN);
		optionGroup.addOption(CLIOptions.EXPORT);
		optionGroup.addOption(CLIOptions.HELP);
		optionGroup.addOption(CLIOptions.INFO);
		options.addOptionGroup(optionGroup);
		options.addOption(CLIOptions.DESTINATION);
		options.addOption(CLIOptions.LOGGING);
		return options;
	}
	
	private static void printOutputParamValues(IAction action) {
		if (action != null) {
			Map<String, Field> params = ActionHelper.getActionOutputParams(action);	
			if (!params.isEmpty()) {
				System.out.println("---------------------------------------------------------------------");
				System.out.println("ACTION OUTPUT");
				System.out.println("---------------------------------------------------------------------");
			}
			for (String paramName : params.keySet()) {
				Field field = params.get(paramName);
				String paramValue = ActionHelper.getParamAsString(field, action);
				// Check if specific variable name was defined
				String variableName = ActionHelper.getVariableNameFromParam(field, ActionOutputParam.class);
				String pureName = variableName;
				if (pureName.startsWith("&")) {
					pureName = pureName.substring(1);
				}
				if (pureName.endsWith("#")) {
					pureName = pureName.substring(0, pureName.length() - 1);	
				}		
				if (paramValue != null) {
					LOGGER.info("Printed value \"" + paramValue + 
							"\" of identified output parameter \"" + 
							field.getName() + "\"");
					System.out.println(pureName + "=\"" + paramValue + "\"");
				}
			}
			if (!params.isEmpty()) {
				System.out.println("---------------------------------------------------------------------");
			}
		}
	}
	
	private static IAction injectInputParamValues(IAction action, String[] arguments) {
		if (action != null) {
			Map<String, String> args = getOptionArguments(arguments);
			Map<String, Field> params = ActionHelper.getActionInputParams(action);	
			if (!params.isEmpty()) {
				System.out.println("---------------------------------------------------------------------");
				System.out.println("ACTION INPUT");
				System.out.println("---------------------------------------------------------------------");
			}
			for (String fieldName : params.keySet()) {
				if (args.containsKey(fieldName)) {
					Field field = params.get(fieldName);
					String argValue = checkNullValue(field, args.get(field.getName()));
					Object value = ActionHelper.getParamAsOriginalType(field, action, argValue);
					
					if (value != null && (field.getType().isPrimitive() || field.getType().equals(value.getClass()))) {
						field.setAccessible(true);
						try {
							field.set(action, value);
							LOGGER.info("Assigned value \"" + field.getName() + 
									"\" to identified input parameter \"" + 
									args.get(field.getName()) + "\"");
							System.out.println(field.getName() + "=" +
									(value != null ? (value.getClass().equals(String.class) ? "\"" + value + "\"" : value) : "NULL"));
						} 
						catch (IllegalArgumentException | IllegalAccessException e) {
							LOGGER.warning("Exception: " + e.toString());
						} 
					}					
				}
			}
			if (!params.isEmpty()) {
				System.out.println("---------------------------------------------------------------------");
			}
		}
		return action;
	}
	
	private static String checkNullValue(Field field, String argValue) {
		String variableName = ActionHelper.getVariableNameFromParam(field, ActionInputParam.class);
		if (variableName != null && argValue != null &&	variableName.equals(argValue)) {
			return null;
		}
		return argValue;
	}
	
	private static Map<String, String> getOptionArguments(String[] arguments) {
		Map<String, String> args = new HashMap<String, String>();	
		if (arguments != null && arguments.length > 0) {
			for (String argument : arguments) {
				int assignmentOpIdx =  argument.indexOf('=');
				if (assignmentOpIdx > 0) {
					String attrName = argument.substring(0, assignmentOpIdx);
					String attrValue = null;
					if (assignmentOpIdx < argument.length()) {
						attrValue = argument.substring(assignmentOpIdx + 1);
					}
					LOGGER.info("Found option argument \"" + attrName + "\" with value \"" + attrValue + "\"");
					args.put(attrName, attrValue);
				}
			}
		}
		return args;
	}
	
	private static void printActionPacks(List<Class<? extends IActionPack>> actionPackClasses) {
		if (!actionPackClasses.isEmpty()) {
			for (Class<? extends IActionPack> actionPackClass : actionPackClasses) {
				IActionPack actionPack;
				try {
					actionPack = (IActionPack) actionPackClass.newInstance();
					if (actionPack != null && actionPack.getName() != null) {
						System.out.println("[ActionPack] " + actionPackClass.getName() + " (" + actionPack.getName() + ")");	
					}
					else {
						System.out.println("[ActionPack] " + actionPackClass.getName());
					}
				} 
				catch (InstantiationException | IllegalAccessException e) {
					LOGGER.warning("Exception: " + e.toString());
				}
			}
		}
	}
	
	private static void printActions(List<Class<? extends IAction>> actionClasses) {
		for (Class<? extends IAction> actionClass : actionClasses) {
			if (!actionClass.isInterface() && !Modifier.isAbstract(actionClass.getModifiers())) {
				try {
					IAction action = (IAction) actionClass.newInstance();
					if (action != null && action.getName() != null) {
						System.out.println("[Action]     " + actionClass.getName() + " (" + action.getName() + ")");
					}
					else {
						System.out.println("[Action]     " + actionClass.getName());	
					}
				} 
				catch (InstantiationException | IllegalAccessException e) {
					LOGGER.warning("Exception: " + e.toString());
				}
			}
		}		
	}
	
	private static CommandLine getParsedCommandLine(Options options, String[] args) {	
		CommandLine cmd = null;
		CommandLineParser parser = new DefaultParser();
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			LOGGER.warning("Exception: " + e.toString());
		}
		return cmd;
	}
	
	private static List<Class<? extends IAction>> getActionsOnClassPath() throws IOException {
		return getClassesOnClassPath(IAction.class);
	}
	
	private static List<Class<? extends IActionPack>> getActionPacksOnClassPath() throws IOException {
		return getClassesOnClassPath(IActionPack.class);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> List<Class<? extends T>> getClassesOnClassPath(Class<T> type) {
		List<Class<? extends T>> actionPackClasses = new ArrayList<Class<? extends T>>();
		try {
			ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
			ImmutableSet<ClassInfo> classes = classPath.getTopLevelClasses();
			Iterator iterator = classes.iterator();
			while (iterator.hasNext()) {
				ClassInfo classInfo = (ClassInfo) iterator.next();
				try {
					Class<?> objClass = classInfo.load();
					if (type.isAssignableFrom(objClass) &&
							!objClass.isInterface() && !Modifier.isAbstract(objClass.getModifiers())) {
						LOGGER.info("Found class \"" + objClass.getName() + "\" of type \"" + type.getName() + "\"");
						actionPackClasses.add((Class<? extends T>) objClass);
					}
				} 
				catch (NoClassDefFoundError | UnsupportedClassVersionError e) {}
			}
		} 
		catch (IOException e) {
			LOGGER.warning("Exception: " + e.toString());
		}
		return actionPackClasses;
	}

	private static List<Class<? extends IAction>> getActions(IActionPack actionPack,
			List<Class<? extends IAction>> allActions) throws IOException {
		List<Class<? extends IAction>> actions = new ArrayList<Class<? extends IAction>>();
		for (Class<? extends IAction> actionClass : allActions) {
			if (!actionClass.isInterface() && !Modifier.isAbstract(actionClass.getModifiers())) {
				ActionPacks annotation = actionClass.getAnnotation(ActionPacks.class);
				if (annotation != null) {
					Class<?>[] classNames = annotation.value();
					for (Class<?> clazz : classNames) {
						if (actionPack.getClass().equals(clazz)) {
							actions.add(actionClass);	
						}
					}
				}
			}
		}
		return actions;
	}
	
}
