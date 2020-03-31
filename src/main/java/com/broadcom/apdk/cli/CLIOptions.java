package com.broadcom.apdk.cli;

import org.apache.commons.cli.Option;

abstract class CLIOptions {
	
	static final Option HELP = Option.builder("h")
			.longOpt("help")
			.desc("Print this message")
			.build();

	static final Option EXPORT = Option.builder("e")
			.longOpt("export")
			.hasArg()
			.optionalArg(true)
			.numberOfArgs(1)
			.required()
			.argName("ACTIONPACK")
			.desc("Export the specified action pack (identified by its fully qualified class name)")
			.build();
	
	static final Option RUN = Option.builder("r")
			.longOpt("run")
			.hasArg()
			.argName("ACTION> <PARAMS")
			.required()
			.desc("Run the specified action (identified by its fully qualified class name) " +
					"and pass input parameters as key/value pairs separated by spaces")
			.build();
	
	static final Option DESTINATION = Option.builder("d")
			.longOpt("destination")
			.hasArg()
			.argName("DESTINATION")
			.desc("Specifies the destination folder for the exported action pack")
			.build();
	
	static final Option LOGGING = Option.builder("l")
			.longOpt("logging")
			.hasArg()
			.argName("LEVEL")
			.desc("Specifies the log level (ERROR|WARNING|INFO|DEBUG). If this argument is not present logging is turned off")
			.build();
	
	static final Option INFO = Option.builder("i")
			.longOpt("info")
			.hasArg()
			.optionalArg(true)
			.numberOfArgs(1)
			.required()
			.argName("ACTIONPACK")
			.desc("Print all found action packs and actions or only the actions linked " +
					"to the specified action pack (identified by its fully qualified class name)")
			.build();

}
