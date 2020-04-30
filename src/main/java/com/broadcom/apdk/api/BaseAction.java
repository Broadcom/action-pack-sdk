package com.broadcom.apdk.api;

import java.util.logging.Logger;

import com.broadcom.apdk.api.annotations.Action;

/**
 * Provides a basic implementation of {@link com.broadcom.apdk.api.IAction IAction} that
 * provides setters and getters for all fields. Furthermore it populates the fields with the 
 * values that were defined using an {@link com.broadcom.apdk.api.annotations.Action Action} 
 * annotation. Classes extending this abstract class must implement the 
 * {@link com.broadcom.apdk.api.IAction#run() run} method.<br><br>
 * <b>Input and output parameters</b><br>
 * Use the annotations {@link com.broadcom.apdk.api.annotations.ActionInputParam ActionInputParam} 
 * and {@link com.broadcom.apdk.api.annotations.ActionOutputParam ActionOutputParam} to mark
 * non-private fields as input- and output parameters of your action. Use the command line argument
 * <i>-r</i> of the generated executable jar-file to run your action and pass the appropriate input
 * parameters to your action.<br><br>
 * <b>Logging</b><br>Use the provided {@link java.util.logging.Logger LOGGER} to create log
 * messages in order to stay aligned with the development kit's logging configuration that is 
 * configured using the command line argument <i>-l</i> of the generated executable jar-file.
 * Per default logging is disabled.<br><br>
 * <b>Example:</b><br>
 * <pre>
 * &#64;Action(
 *   name = "HELLO",
 *   title = "My first Java action",
 *   path = "MYACTIONS"
 * )
 * public class CustomAction extends BaseAction {
 *   
 *   &#64;ActionInputParam
 *   String yourname;
 *   
 *   &#64;ActionOutputParam
 *   String greeting;
 *   
 *   &#64;Override
 *   public void run() {
 *     greeting = "Hallo " + yourname;
 *     LOGGER.info("Assigned \"" + greeting + "\" to output parameter \"greeting\".");
 *   } 
 * }
 * </pre>
 */
public abstract class BaseAction implements IAction {
	
	protected final static Logger LOGGER = Logger.getLogger("APDK");
		
	private String name;
	private String title;
	private String path;
	
	public BaseAction() {
		Action actionAnnotation = this.getClass().getAnnotation(Action.class);
		if (actionAnnotation != null) {
			setName(!actionAnnotation.name().isEmpty() ? 
					actionAnnotation.name() : 
					getClass().getSimpleName().toUpperCase());
			setTitle(!actionAnnotation.title().isEmpty() ? 
					actionAnnotation.title() : null);
			setPath(!actionAnnotation.path().isEmpty() ? 
					actionAnnotation.path() : null);
		}
		else {
			setName(getClass().getSimpleName().toUpperCase());
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

}
