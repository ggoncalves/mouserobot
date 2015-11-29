package org.ggoncalves.robot.logic.scheduler;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.ggoncalves.robot.logic.StartStopSchedule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RobotHourSchedulerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private String calendarToString(Calendar c) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return format.format(c.getTime());
	}

	private String parseToHourString(Calendar c) {
		return mountHourString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
	}

	private String mountHourString(int hour, int min) {
		String minStr = "" + min;
		if (minStr.length() == 1) {
			minStr = "0" + minStr;
		}
		String hourStr = "";
		if (hour == 0) {
			hourStr = "00";
		}
		else {
			hourStr = "" + hour;
		}
		return hourStr + ":" + minStr;
	}

	public void assertGetNextCalendar(boolean isStart, boolean isSameDay) {
		Calendar now = Calendar.getInstance();

		int min = (isSameDay) ? 1 : -1;

		now.add(Calendar.MINUTE, min);

		String hourString = parseToHourString(now);

		RobotHourScheduler scheduler = null;
		if (isStart) {
			scheduler = new RobotHourScheduler(hourString, "19:00");
		}
		else {
			scheduler = new RobotHourScheduler("00:00", hourString);
		}

		Calendar expected = Calendar.getInstance();
		// add em now para acertar o esperado.
		if (isSameDay == false) {
			now.add(Calendar.DAY_OF_MONTH, 1);
		}
		expected.clear();
		expected.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
		    now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.HOUR_OF_DAY),
		    now.get(Calendar.MINUTE));

		Calendar c2Compart = (isStart) ? scheduler.getNextStartCalendar()
		    : scheduler.getNextStopCalendar();
		assertEquals(calendarToString(expected), calendarToString(c2Compart));
		assertEquals(expected, c2Compart);
	}

	private Calendar getCalendarFiveMinutesAhead() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 5);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	private Calendar getCalendarFiveMinutesPast() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -5);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;

	}

	@Test
	public void testGetNextStartCalendarToday() {
		assertGetNextCalendar(true, true);
	}

	@Test
	public void testGetNextStartCalendarTomorrow() {
		assertGetNextCalendar(true, false);
	}

	@Test
	public void testGetNextStopCalendarToday() {
		assertGetNextCalendar(false, true);
	}

	@Test
	public void testGetNextStopCalendarTomorrow() {
		assertGetNextCalendar(false, false);
	}

	@Test
	public void testGetNextCalendarEventStart() {
		Calendar startAhead = getCalendarFiveMinutesAhead();
		String startHour = parseToHourString(startAhead);
		RobotHourScheduler sched = new RobotHourScheduler(startHour, "23:59");

		StartStopSchedule schedule = sched.getNextCalendarEvent();
		StartStopSchedule expected = new StartStopSchedule(true, startAhead);

		assertEquals(expected, schedule);

	}

	@Test
	public void testGetNextCalendarEventStop() {
		Calendar stopAhead = getCalendarFiveMinutesAhead();
		String stopHour = parseToHourString(stopAhead);
		RobotHourScheduler sched = new RobotHourScheduler("00:00", stopHour);

		StartStopSchedule schedule = sched.getNextCalendarEvent();
		StartStopSchedule expected = new StartStopSchedule(false, stopAhead);

		assertEquals(expected, schedule);

	}

	@Test
	public void testUpdateNextCalendarDoNothing() {
		RobotHourScheduler sched = new RobotHourScheduler("00:00", "00:01");
		sched.updateNextCalendar(getCalendarFiveMinutesAhead(),
		    getCalendarFiveMinutesAhead());
		Calendar expected = getCalendarFiveMinutesAhead();
		assertEquals(expected, sched.getNextStartCalendar());
		assertEquals(expected, sched.getNextStopCalendar());
	}

	@Test
	public void testUpdateNextCalendarToTomorrow() {
		RobotHourScheduler sched = new RobotHourScheduler("00:00", "00:01");
		sched.updateNextCalendar(getCalendarFiveMinutesPast(),
		    getCalendarFiveMinutesPast());

		Calendar expected = getCalendarFiveMinutesPast();

		expected.add(Calendar.DAY_OF_MONTH, 1);
		assertEquals(calendarToString(expected),
		    calendarToString(sched.getNextStartCalendar()));
		assertEquals(expected, sched.getNextStartCalendar());
		assertEquals(expected, sched.getNextStopCalendar());
	}

	@Test
	public void testUpdateNextCalendarFromWayPastToToday() {
		RobotHourScheduler sched = new RobotHourScheduler("00:00", "00:01");
		Calendar wayPast = getCalendarFiveMinutesAhead();
		wayPast.add(Calendar.DAY_OF_MONTH, -10);
		sched.updateNextCalendar(wayPast, wayPast);
		Calendar expected = getCalendarFiveMinutesAhead();
		expected.set(Calendar.DAY_OF_MONTH,
		    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		assertEquals(calendarToString(expected),
		    calendarToString(sched.getNextStartCalendar()));
		assertEquals(expected, sched.getNextStartCalendar());
		assertEquals(expected, sched.getNextStopCalendar());
	}

	@Test
	public void testUpdateNextCalendarFromWayPastToTomorrow() {
		RobotHourScheduler sched = new RobotHourScheduler("00:00", "00:01");
		Calendar wayPast = getCalendarFiveMinutesPast();
		wayPast.add(Calendar.DAY_OF_MONTH, -10);
		sched.updateNextCalendar(wayPast, wayPast);
		Calendar expected = getCalendarFiveMinutesPast();
		expected.set(Calendar.DAY_OF_MONTH,
		    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		expected.add(Calendar.DAY_OF_MONTH, 1);
		assertEquals(expected, sched.getNextStartCalendar());
		assertEquals(expected, sched.getNextStopCalendar());
	}

}
