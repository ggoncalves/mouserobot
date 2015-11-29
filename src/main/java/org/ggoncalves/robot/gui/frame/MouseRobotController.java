package org.ggoncalves.robot.gui.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.SwingUtilities;

import org.ggoncalves.robot.core.MouseRobot;
import org.ggoncalves.robot.util.ConsolePrinter;

/**
 * Esta classe representa o controlador de {@link MouseRobotFrame}.
 * 
 * @author ggoncalves
 */
public class MouseRobotController {

	/** A visão da GUI que é manipulada por este controlador. */
	private MouseRobotFrame robotView;
	private MouseRobot mouseRobot;

	public MouseRobotController(MouseRobotFrame robotView) {
		this.robotView = robotView;
	}
	
	private MouseRobot getMouseRobot() {
		if (mouseRobot == null) {
	    mouseRobot = new MouseRobot(this);
    }
	  return mouseRobot;
  }

	/**
	 * Cria um ouvidor para as ações da janela da aplicação.
	 */
	protected WindowListener createWindowListener() {
		return new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {
				super.windowClosed(we);
				getMouseRobot().kill();
				ConsolePrinter.println("Exiting");
				System.exit(0);
			}
		};
	}

	protected ActionListener createStartActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getMouseRobot().start();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	protected ActionListener createStopActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getMouseRobot().stop();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	public void toggleButtonVisible() {
		boolean isStartVisible = true;
		if (robotView.getStartButton().isVisible()) {
			isStartVisible = false;
		}

		final boolean _isStartVisible = isStartVisible;

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				if (_isStartVisible) {
					robotView.getStartButton().setVisible(true);
					robotView.getStopButton().setVisible(false);
				}
				else {
					robotView.getStartButton().setVisible(false);
					robotView.getStopButton().setVisible(true);
				}
			}
		});
	}

	public void setRunning(boolean b) {
	  getMouseRobot().setRunning(b);
  }
}
