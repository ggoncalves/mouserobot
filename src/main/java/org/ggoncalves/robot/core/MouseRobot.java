package org.ggoncalves.robot.core;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.ggoncalves.robot.gui.frame.MouseRobotController;
import org.ggoncalves.robot.logic.MouseMovePoint;
import org.ggoncalves.robot.logic.StartStopSchedule;
import org.ggoncalves.robot.logic.config.Settings;
import org.ggoncalves.robot.logic.scheduler.RobotHourScheduler;
import org.ggoncalves.robot.util.ConsolePrinter;

public class MouseRobot {
	
	private Thread robotThread;
	private Thread timerMotionStartThread;
	private Thread autoStartStopThread;

	private volatile boolean isRunning = false;
	private volatile boolean isMotionStarted = false;
	volatile boolean isStarted = false;

	private MouseMovePoint mouseMovePoint;

	// TODO: trocar por listener.
	private MouseRobotController robotController;

	public MouseRobot(MouseRobotController robotController) {
		this.robotController = robotController;
		isRunning = true;
		if (isAutoMode()) {
			getAutoStartStopThread().start();
		}
	}

	public void stop() throws InterruptedException, AWTException {
		ConsolePrinter.println("MouseRobot.stop() called stop");
		isMotionStarted = false;
		isStarted = false;
		robotController.toggleButtonVisible();
	}

	public void start() throws InterruptedException, AWTException {
		isStarted = true;
		if (!getTimerMotionStartThread().isAlive()) {
			getTimerMotionStartThread().start();
		}
		if (!getRobotThread().isAlive()) {
			getRobotThread().start();
		}
		robotController.toggleButtonVisible();
	}
	
	public void kill() {
		isRunning = false;
		isMotionStarted = false;
		isStarted = false;
		try {
			getTimerMotionStartThread().join();
			getRobotThread().join();
			getAutoStartStopThread().join();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Runnable createMouseMotionStartRunnable() {
		return new Runnable() {

			public void run() {

				Point lastMovePoint = MouseInfo.getPointerInfo().getLocation();

				long startCountMillis = System.currentTimeMillis();
				while (isRunning) {
					quietSleep(Settings.WAIT_STARTED_INTERVAL);
					ConsolePrinter.println("Runnable() {...}.run() Waiting to join");
					while (isStarted) {

						Point currMovePoint = MouseInfo.getPointerInfo().getLocation();

						if (isUserNotMoving(lastMovePoint, currMovePoint)) {
							if ((System.currentTimeMillis() - startCountMillis) >= Settings.TIME_TO_START_MILLIS) {
								if (isMotionStarted == false) {
									setMouseMovePoint(currMovePoint);
									isMotionStarted = true;
								}
							}
						}
						else {
							lastMovePoint = MouseInfo.getPointerInfo().getLocation();
							startCountMillis = System.currentTimeMillis();
							clearMouseMovePoint();
							if (isMotionStarted == true) {
								ConsolePrinter.println("Stopping motion ***********");
								isMotionStarted = false;
							}
						}

						quietSleep(Settings.UPDATE_MOUSE_MOTION_INTERVAL);
					}
				}
			}
		};
	}

	private boolean isUserNotMoving(Point lastMovePoint, Point currMovePoint) {
		if (lastMovePoint.equals(currMovePoint)) {
			if (!hasMouseMovePoint()) {
				return true;
			}
		}
		if (hasMouseMovePoint()) {
			if (currMovePoint.equals(mouseMovePoint.getFixedPoint())
			    || currMovePoint.equals(mouseMovePoint.getMovedPoint())) {
				return true;
			}
		}
		return false;
	}

	private void setMouseMovePoint(final Point currPointLocation) {
		this.mouseMovePoint = new MouseMovePoint(currPointLocation);
	}

	private MouseMovePoint getMouseMovePoint() {
		return mouseMovePoint;
	}

	private boolean hasMouseMovePoint() {
		return getMouseMovePoint() != null;
	}

	private void clearMouseMovePoint() {
		this.mouseMovePoint = null;
	}

	// Shut up and sleep... damm.
	private void quietSleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			// Deve ser encrenca, não me avise...
			e.printStackTrace();
		}
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	private Thread getTimerMotionStartThread() {
		if (timerMotionStartThread == null) {
			timerMotionStartThread = new Thread(createMouseMotionStartRunnable());
		}
		return timerMotionStartThread;
	}

	private Thread getAutoStartStopThread() {
		// A decisão do start da thread depende da validade das horas de início e
		// fim. Ainda assim
		// a Thread deve ser criada se precavendo com estes erros.
		if (autoStartStopThread == null) {
			autoStartStopThread = new Thread(createAutoStartStopRunnable());
		}
		return autoStartStopThread;
	}

	private Runnable createAutoStartStopRunnable() {
		return new Runnable() {

			public void run() {
				// Verifica se é o modo de início. E não inicia a Thread caso não haja
				// horas setadas.
				if (!isAutoMode()) {

					// Se possível, remover este return e mudar a lógica do if.
					return;
				}
				ConsolePrinter.println("Going to run");

				RobotHourScheduler scheduler = new RobotHourScheduler(
				    getSettingStartHour(), getSettingStopHour());

				while (isRunning) {
					try {

						StartStopSchedule event = scheduler.getNextCalendarEvent();

						ConsolePrinter.println("Preciso executar: " + event);

						// Executa
						if (Calendar.getInstance().after(event.getSchedule())) {

							if (event.isStart()) {
								// Só começa se estiver parado.
								if (isStarted == false) {
									start();
								}
							}
							else {
								// Só pára se estiver em movimento.
								if (isStarted == true) {
									stop();
								}
							}

							scheduler.updateNextCalendar();
						}

						// Dorme.
						quietSleep(1000); // TODO: Colocar em Settings.
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	private String getSettingStartHour() {
		return Settings.getInstance().getStartHour();
	}

	private String getSettingStopHour() {
		return Settings.getInstance().getStopHour();
	}

	// Testar este método.
	private boolean isAutoMode() {
		if (StringUtils.isBlank(getSettingStartHour())
		    || StringUtils.isBlank(getSettingStopHour())) {
			return false;
		}
		return true;
	}

	private Thread getRobotThread() throws InterruptedException, AWTException {
		if (robotThread == null) {
			robotThread = new Thread() {
				final Robot robot = new Robot();

				@Override
				public void run() {
					super.run();
					ConsolePrinter.println("Called Start");
					while (isRunning) {
						quietSleep(Settings.WAIT_MOTION_STARTED_INTERVAL);
						if (isRunning == false) {
							continue;
						}
						while (isMotionStarted) {
							robot.mouseMove(mouseMovePoint.getMovedPoint().x,
							    mouseMovePoint.getMovedPoint().y);
							if (isMotionStarted) {
								quietSleep(Settings.UPDATE_MOTION_FREQUENCY);
							}
							if (isMotionStarted) {
								robot.mouseMove(mouseMovePoint.getFixedPoint().x,
								    mouseMovePoint.getFixedPoint().y);
							}
							if (isMotionStarted) {
								quietSleep(Settings.UPDATE_MOTION_FREQUENCY);
							}
						}
					}
				}

				@Override
				public void interrupt() {
					super.interrupt();
					ConsolePrinter.println("Called interrupt");
					isMotionStarted = false;
				}
			};
		}
		return robotThread;
	}
}
