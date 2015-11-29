package org.ggoncalves.robot.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StartStopSchedule {
	private boolean isStart;
	private Calendar schedule;

	public StartStopSchedule(boolean isStart, Calendar schedule) {
		this.isStart = isStart;
		this.schedule = schedule;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public Calendar getSchedule() {
		return schedule;
	}

	public void setSchedule(Calendar schedule) {
		this.schedule = schedule;
	}

	@Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + (isStart ? 1231 : 1237);
	  result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
	  return result;
  }

	@Override
  public boolean equals(Object obj) {
	  if (this == obj) {
		  return true;
	  }
	  if (obj == null) {
		  return false;
	  }
	  if (getClass() != obj.getClass()) {
		  return false;
	  }
	  StartStopSchedule other = (StartStopSchedule) obj;
	  if (isStart != other.isStart) {
		  return false;
	  }
	  if (schedule == null) {
		  if (other.schedule != null) {
			  return false;
		  }
	  }
	  else if (!schedule.equals(other.schedule)) {
		  return false;
	  }
	  return true;
  }

	@Override
  public String toString() {
	  StringBuilder builder = new StringBuilder();
	  builder.append("StartStopSchedule [isStart=");
	  builder.append(isStart);
	  builder.append(", schedule=");
	  builder.append(calendarToString(schedule));
	  builder.append("]");
	  return builder.toString();
  }
	
	private String calendarToString(Calendar c) {
		if (c == null) {
			return "null";
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return format.format(c.getTime());
	}
}