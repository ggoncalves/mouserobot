package org.ggoncalves.robot.logic.config;

public class Settings {
	
	//2 segundos de espera para iniciar a Thread de movimento.
	public static final long TIME_TO_START_MILLIS = 2000;

	// Frequência de atualização do ponteiro do mouse.
	public static final int UPDATE_MOTION_FREQUENCY = 500;

	// Espera para início de movimento automático.
	public static final int WAIT_MOTION_STARTED_INTERVAL = 1000;

	// Atualização de pooling do mouse do usuário.
	public static final int UPDATE_MOUSE_MOTION_INTERVAL = 200;

	// Espera para início de varredura de mouse do usuário.
	public static final int WAIT_STARTED_INTERVAL = 1000;
	
	// Distância do ponteiro durante a movimentação.
	public final static int POINT_GAP = 3;
	
	private String startHour;
	private String stopHour;
	
	private static Settings instance = new Settings();
	
	public static Settings getInstance() {
		return instance;
	}
	
	public String getStartHour() {
	  return startHour;
  }
	
	public void setStartHour(String startHour) {
	  this.startHour = startHour;
  }
	
	public String getStopHour() {
	  return stopHour;
  }
	
	public void setStopHour(String stopHour) {
	  this.stopHour = stopHour;
  }

}
