package org.ggoncalves.robot.util;

import org.apache.commons.lang3.StringUtils;

public class ConsolePrinter {

	private static boolean isVerbose = false;

	public static void println(String message) {
		if (isVerbose) {
			System.out.println(message);
		}
	}
	
	public static void setVerbose(boolean isVerbose) {
	  ConsolePrinter.isVerbose = isVerbose;
  }

	public static boolean isVerbose() {
		String isVerboseString = System.getProperty("org.ggoncalves.robot.util.isVerbose");
		if (!StringUtils.isBlank(isVerboseString)) {
			return Boolean.parseBoolean(isVerboseString);
		}
		return ConsolePrinter.isVerbose;
  }
}
