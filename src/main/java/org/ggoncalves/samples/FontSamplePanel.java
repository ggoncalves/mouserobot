package org.ggoncalves.samples;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FontSamplePanel extends JPanel {
	String[] type = { "Serif", "SansSerif", "Tacoma" };
	int[] styles = { Font.PLAIN, Font.ITALIC, Font.BOLD, Font.ITALIC + Font.BOLD };
	String[] stylenames = { "Plain", "Italic", "Bold", "Bold & Italic" };

	public void paint(Graphics g) {
		for (int f = 0; f < type.length; f++) {
			for (int s = 0; s < styles.length; s++) {
				Font font = new Font(type[f], styles[s], 18);
				g.setFont(font);
				String name = type[f] + " " + stylenames[s];
				g.drawString(name, 20, (f * 4 + s + 1) * 20);
			}
		}
	}

	public static void main(String[] a) {
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setContentPane(new FontSamplePanel());
		f.setSize(400, 400);
		f.setVisible(true);
	}
}