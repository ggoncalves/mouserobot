package org.ggoncalves.robot.logic.scheduler;

import java.util.Calendar;

import org.ggoncalves.robot.logic.StartStopSchedule;

public class RobotHourScheduler {

	private Calendar nextStartCalendar;
	private Calendar nextStopCalendar;

	public RobotHourScheduler(String startHour, String stopHour) {
		this.setStartHour(startHour);
		this.setStopHour(stopHour);
		this.updateNextCalendar();
	}

	public void setStartHour(String startHour) {
		validateFormat(startHour);
		this.nextStartCalendar = getNextCalendarFor(startHour);
		Calendar now = Calendar.getInstance();
		if (nextStartCalendar.before(now)) {
			nextStartCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	
	public void updateNextCalendar() {
		updateNextCalendar(getNextStartCalendar(), getNextStopCalendar());
	}
	
	void updateNextCalendar(Calendar nextStartCalendar, Calendar nextStopCalendar) {
		Calendar now = Calendar.getInstance();
		while (nextStartCalendar.before(now)) {
			nextStartCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		while (nextStopCalendar.before(now)) {
			nextStopCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		this.nextStartCalendar = nextStartCalendar;
		this.nextStopCalendar = nextStopCalendar;
	}

	private Calendar getNextCalendarFor(String hour) {
		Calendar calendar = Calendar.getInstance();
		String[] splitHour = hour.split(":");
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitHour[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(splitHour[1]));
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	public void setStopHour(String stopHour) {
		validateFormat(stopHour);
		this.nextStopCalendar = getNextCalendarFor(stopHour);
		Calendar now = Calendar.getInstance();
		if (nextStopCalendar.before(now)) {
			nextStopCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	public Calendar getNextStartCalendar() {
		return this.nextStartCalendar;
	}

	public Calendar getNextStopCalendar() {
		return this.nextStopCalendar;
	}

	private void validateFormat(String hourFormat) {
		if (!isValidHourFormat(hourFormat)) {
			throw new IllegalArgumentException("Hora inválida: " + hourFormat);
		}
	}

	public static boolean isValidHourFormat(String hourFormat) {
		return hourFormat.matches("^(((0|1)[0-9])|2[0-3]|[1-9]):[0-5][0-9]$");
	}
	
	public StartStopSchedule getNextCalendarEvent() {
		if (getNextStartCalendar().before(getNextStopCalendar())) {
			return new StartStopSchedule(true, getNextStartCalendar());
		}
		return new StartStopSchedule(false, getNextStopCalendar());
	}
}