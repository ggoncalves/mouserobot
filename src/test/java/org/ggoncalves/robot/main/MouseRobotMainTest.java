package org.ggoncalves.robot.main;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MouseRobotMainTest {

	private ArgumentValidator validator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testIsValidArgumentsVerboseTrue() {
		String[] args = new String[] { "-verbose", "-schedule", "09:00-17:00" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsVerboseTrueWhite() {
		String[] args = new String[] { " -verbose ", "-schedule", "09:00-17:00" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsVerboseFalseWrong() {
		String[] args = new String[] { "-verbos", "-schedule", "09:00-17:00" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsVerboseFalseWrong2() {
		String[] args = new String[] { "--verbose", "-schedule", "09:00-17:00" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsVerboseFalseWrong3() {
		String[] args = new String[] { "verbose", "-schedule", "09:00-17:00" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsVerboseOnlyTrue() {
		String[] args = new String[] { "-verbose" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsVerboseOnlyTrueWhite() {
		String[] args = new String[] { " -verbose " };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsVerboseOnlyFalseWrong() {
		String[] args = new String[] { "-verbos" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsVerboseOnlyFalseWrong2() {
		String[] args = new String[] { "verbose" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsVerboseOnlyFalseWrong3() {
		String[] args = new String[] { "--verbose" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseVerboseAndStart() {
		String[] args = new String[] { "-verbose", "-schedule", "09:00" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsTrue() {
		String[] args = new String[] { "-schedule", "09:00-17:00" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsTrueOmmitHour1() {
		String[] args = new String[] { "-schedule", "9:00-17:00" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsTrueOmmitHour2() {
		String[] args = new String[] { "-schedule", "09:00-7:00" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsTrueOmmitHour3() {
		String[] args = new String[] { "-schedule", "1:00-1:01" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsFalseOmmitHour1() {
		String[] args = new String[] { "-schedule", "0:00-10:01" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsFalseOmmitHour2() {
		String[] args = new String[] { "-schedule", "01:00-0:01" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsFalseOmmitHour3() {
		String[] args = new String[] { "-schedule", "0:00-0:01" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsTrueLimits() {
		String[] args = new String[] { "-schedule", "00:00-23:59" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStartBounds() {
		String[] args = new String[] { "-schedule", "00:60-23:59" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStartTooFew() {
		String[] args = new String[] { "-schedule", "00:6-23:59" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStartHourBounds() {
		String[] args = new String[] { "-schedule", "24:06-23:59" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStartHourTooMany() {
		String[] args = new String[] { "-schedule", "022:06-23:59" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsTrueStartHourOneDigit() {
		String[] args = new String[] { "-schedule", "2:06-23:59" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStartTooMany() {
		String[] args = new String[] { "-schedule", "00:006-23:59" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStopBounds() {
		String[] args = new String[] { "-schedule", "00:40-23:60" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStopTooFew() {
		String[] args = new String[] { "-schedule", "00:06-23:5" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStopTooMany() {
		String[] args = new String[] { "-schedule", "00:06-23:005" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStopHourBounds() {
		String[] args = new String[] { "-schedule", "00:06-24:00" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseStopHourTooMany() {
		String[] args = new String[] { "-schedule", "22:06-023:59" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsFalseWhite() {
		String[] args = new String[] { "-schedule", "22:06 -23:59" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsFalseWhite2() {
		String[] args = new String[] { "-schedule", "22:06- 23:59" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsTrueWhiteSchedule() {
		String[] args = new String[] { "  -schedule  ", "22:06-23:59" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}
	
	@Test
	public void testIsValidArgumentsTrueWhiteAll() {
		String[] args = new String[] { "  -schedule  ", "  22:06-23:59  " };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseSameStartStop() {
		String[] args = new String[] { "-schedule", "22:06-22:06" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsTrueStopHourOneDigit() {
		String[] args = new String[] { "-schedule", "02:06-9:59" };
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}

	private void assertFalseByWrongExpression(String hour) {
		String[] args = new String[] { "-schedule", hour + "-23:59" };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());

		args = new String[] { "-schedule", "00:00-" + hour };
		validator = new ArgumentValidator(args);
		assertFalse(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsFalseByStringMistake() {
		assertFalseByWrongExpression("0100");
		assertFalseByWrongExpression("01;00");
		assertFalseByWrongExpression("01:0a");
		assertFalseByWrongExpression("frango");
		assertFalseByWrongExpression("01 : 00");
		assertFalseByWrongExpression("01 :00");
		assertFalseByWrongExpression("01: 00");
	}

	@Test
	public void testIsValidArgumentsTrueEmpty() {
		String[] args = new String[] {};
		validator = new ArgumentValidator(args);
		assertTrue(validator.isValid());
	}

	@Test
	public void testIsValidArgumentsTrueNull() {
		validator = new ArgumentValidator(null);
		assertTrue(validator.isValid());
	}
}
