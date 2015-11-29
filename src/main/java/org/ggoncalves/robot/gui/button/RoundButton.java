package org.ggoncalves.robot.gui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

public class RoundButton extends JButton {

	// Shape com os limites para detecção do hit do mouse com o botão.
	private Shape shape;
	
	/** A cor do botão. */
	private Color buttonColor;

	public RoundButton(String label, Color buttonColor) {
		super(label);
		
		this.buttonColor = buttonColor;

		// Inicializa os ajustes.
		this.init();
	}

	private void init() {
		// Aumenta o tamanho do botão para que se pareça com um círculo, e não Oval.
		this.setPreferredSize(getPreferredSize());

		// Faz com que o botão não pinte o background, para que o círculo possa ser
		// pintado.
		setContentAreaFilled(false);
	}

	@Override
	public void setPreferredSize(Dimension size) {
		size.width = size.height = Math.max(size.width, size.height);
		super.setPreferredSize(size);
	}

	// Redesenha o botão para preencher o círuclo e o label.
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		// Obtém o mapa com as preferências de ajuste.
		Map<Key, Object> renderingMap = getRenderingHintsMap();
		g2d.setRenderingHints(renderingMap);

		// Se o botão está apertado, mas ainda não disparado.
		if (getModel().isArmed()) {
			g2d.setColor(getBackground());
		}
		else {
			g2d.setColor(this.buttonColor);
		}

		// Desenha o botão redondo.
		g2d.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

		g2d.setColor(Color.white);

		// Label do botão.
		drawLabelInsideButton(g2d);
	}

	protected void adaptLabelFont(Graphics2D g2d, Font f, String text) {
		Shape s = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		Rectangle rBound = s.getBounds();
		Rectangle2D r = new Rectangle2D.Double(rBound.x, rBound.y, rBound.width
		    - (rBound.width * 0.15), rBound.height - (rBound.height * 0.15));
		int fontSize = 2;

		Rectangle r1 = new Rectangle();
		Rectangle r2 = new Rectangle();
		while (fontSize < 400) {
			r1.setSize(getTextSize(g2d, text, f.deriveFont(f.getStyle(), fontSize)));
			r2.setSize(getTextSize(g2d, text,
			    f.deriveFont(f.getStyle(), fontSize + 1)));
			if (r.contains(r1) && !r.contains(r2)) {
				break;
			}
			fontSize++;
		}
		g2d.setFont(f.deriveFont(f.getStyle(), fontSize));
	}

	private Dimension getTextSize(Graphics2D g2d, String text, Font f) {
		Dimension size = new Dimension();
		g2d.setFont(f);
		FontMetrics fm = g2d.getFontMetrics(f);
		size.width = fm.stringWidth(text);
		size.height = fm.getHeight();

		return size;
	}

	private void drawLabelInsideButton(Graphics2D g2d) {
		// Configura a fonte.
		Font f = new Font("SansSerif", Font.ITALIC, getSize().width / 3);
		adaptLabelFont(g2d, f, getText());

		// Obtém o font metrics para calcular os tamanhos.
		FontMetrics fm = g2d.getFontMetrics();

		int fontWidth = fm.stringWidth(getText());
		int fontHeight = fm.getHeight();
		g2d.drawString(getText(), getSize().width / 2 - fontWidth / 2,
		    getSize().height / 2 + fontHeight / 4);
	}

	private Map<Key, Object> getRenderingHintsMap() {
		Map<Key, Object> renderingMap = new HashMap<Key, Object>();
		renderingMap.put(RenderingHints.KEY_TEXT_ANTIALIASING,
		    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		renderingMap.put(RenderingHints.KEY_ANTIALIASING,
		    RenderingHints.VALUE_ANTIALIAS_ON);
		return renderingMap;
	}

	// Pinta a borda do botão usando um Stroke Simples.
	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}

	public boolean contains(int x, int y) {
		// Se o botão mudou de tamanho, calcula um novo shape.
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}

	// Test routine.
	public static void main(String[] args) {
		JButton button = new RoundButton("Stop", Color.red);
		button.setBackground(Color.GREEN);
		button.setPreferredSize(new Dimension(100, 100));
		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(Color.yellow);
		frame.getContentPane().add(button);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setSize(150, 150);
		frame.setVisible(true);
	}
}