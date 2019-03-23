package neal_kumar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	public static volatile List<Circle> circleList = new ArrayList<Circle>();

	public Panel(int panelWidth, int panelHeight, Color backColor) {
		setPreferredSize(new Dimension(panelWidth, panelHeight));
		setBackground(backColor);
	}

	@Override
	public void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		Graphics2D brush = (Graphics2D) arg0;
		for (Circle c : circleList) {
			c.draw(brush);
		}
		repaint();
	}
}
