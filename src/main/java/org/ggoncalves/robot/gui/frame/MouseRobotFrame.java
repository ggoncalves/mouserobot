package org.ggoncalves.robot.gui.frame;

import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import org.ggoncalves.robot.gui.button.RoundButton;

/**
 * Representa a parte GUI do robô de movimentação de Mouse automático.
 * 
 * @author ggoncalves
 *
 */
public class MouseRobotFrame {

	/** Flag para indicar se a janela já fora construída. */
	private boolean isBuilt = false;

	private JFrame mainFrame;
	private JButton startButton;
	private JButton stopButton;

	private MouseRobotController controller;

	public MouseRobotFrame() {
  }

	/**
	 * Obtém o controlador deste frame.
	 * 
	 * @return {@link MouseRobotController} o controlador deste frame.
	 */
	public MouseRobotController getController() {
		if (controller == null) {
			controller = new MouseRobotController(this);
		}
		return controller;
	}

	private void buildGUI() {
		JLabel label = createJLabelIcon();
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(label.getPreferredSize());
		layeredPane.add(label, new Integer(50));
		layeredPane.add(getStartButton(), new Integer(100));
		layeredPane.add(getStopButton(), new Integer(100));
		getMainFrame().add(layeredPane);
	}

	private JLabel createJLabelIcon() {
		URL url = getClass().getResource("/i_like_to_move_it_facebook_cover.jpg");
		ImageIcon imageIcon = new ImageIcon(url);
		JLabel label = new JLabel(imageIcon);
		label.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		label.setIcon(imageIcon);
		return label;
	}

	private JFrame getMainFrame() {
		if (mainFrame == null) {
			mainFrame = new JFrame("Java Robot");
			mainFrame.addWindowListener(getController().createWindowListener());
		}
		return mainFrame;
	}

	JButton getStartButton() {
		if (startButton == null) {
			startButton = new RoundButton("Start", Color.green);
			startButton.setBounds(150, 150, 100, 100);
			startButton.addActionListener(getController().createStartActionListener());
		}
		return startButton;
	}

	JButton getStopButton() {
		if (stopButton == null) {
			stopButton = new RoundButton("Stop", Color.red);
			stopButton.setBounds(150, 150, 100, 100);
			stopButton.addActionListener(getController().createStopActionListener());
		}
		return stopButton;
	}

	/**
	 * Monta a interface, caso não esteja montada, e exibe a interface no centro
	 * da tela.
	 */
	public synchronized void startApplication() {
		checkAndBuildGUI();
		showFrame();
		getController().setRunning(true);
	}

	/**
	 * Monta a interface, caso não esteja montada, e exibe a interface no centro
	 * da tela.
	 */
	private void showFrame() {
		getMainFrame().pack();
		getMainFrame().setLocationRelativeTo(null);
		getMainFrame().setVisible(true);
	}
	
	/**
	 * Verifica se é necessário construir a GUI da aplicação.
	 */
	private void checkAndBuildGUI() {
		if (isBuilt == false) {
			buildGUI();
			isBuilt = true;
		}
	}
}
