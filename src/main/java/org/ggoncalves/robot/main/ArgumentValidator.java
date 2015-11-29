package org.ggoncalves.robot.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.ggoncalves.robot.logic.scheduler.RobotHourScheduler;

public class ArgumentValidator {

	private String[] args;
	private Options options;

	public ArgumentValidator(String[] args) {
		this.args = trimArray(args);
	}

	private String[] trimArray(String[] array) {
		if (array == null || array.length == 0)
			return array;

		String[] trimmed = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			trimmed[i] = array[i].trim();
		}

		return trimmed;
	}

	private Options getOptions() {
		if (this.options == null) {
			this.options = createOptions();
		}
		return this.options;

	}

	private Options createOptions() {
		// Verbose.
		Option verbose = new Option("verbose", "enables verbose on Sysout.out.");

		// Schedule.
		Option schedule = OptionBuilder.withArgName("StartHOUR-StopHOUR").hasArg()
		    .withDescription("schedule auto start-stop (9:00-18:00).")
		    .create("schedule");

		// Adiciona as opções.
		Options options = new Options();
		options.addOption(verbose);
		options.addOption(schedule);
		return options;
	}

	private CommandLine getCommandLine() {
		CommandLineParser parser = new DefaultParser();
		try {
			return parser.parse(getOptions(), args);
		}
		catch (ParseException e) {
			return null;
		}
	}
	
	public boolean isVerbose() {
		CommandLine line = getCommandLine();
		if (line != null) {
			return line.hasOption("verbose");
		}
		return false;
	}
	
	public boolean hasSchedule() {
		CommandLine line = getCommandLine();
		if (line != null) {
			return line.hasOption("schedule");
		}
		return false;
	}
	
	public String[] getStartStopStrings() {
		CommandLine line = getCommandLine();
		if (line != null && line.hasOption("schedule")) {
			String scheduleStr = line.getOptionValue("schedule");
			String[] hourString = scheduleStr.split("-");

			if (hourString.length != 2) {
				return null;
			}
			
			return hourString;
		}
		return null;
	}

	boolean isValid() {
		if (args == null || args.length == 0) {
			return true;
		}
		CommandLine line = getCommandLine();
		if (line == null) {
			return false;
		}
		if (line.getArgs() != null && line.getArgs().length > 0) {
			return false;
		}

		if (line.hasOption("schedule")) {
			String scheduleStr = line.getOptionValue("schedule");
			String[] hourString = scheduleStr.split("-");

			if (hourString.length != 2) {
				return false;
			}

			for (String hour : hourString) {
				if (RobotHourScheduler.isValidHourFormat(hour) == false)
					return false;
			}

			return !(hourString[0].equals(hourString[1]));
		}

		return true;
	}

	public void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Mouse Robot", getOptions());
	}
}
