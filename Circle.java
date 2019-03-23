package neal_kumar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

@SuppressWarnings("serial")
public class Circle extends Ellipse2D.Double {

	private Color color;

	public Circle(int startX, int startY, int width, int height, Color color) {
		super(startX, startY, width, height);
		this.color = color;
	}

	public void draw(Graphics2D brush) {
		brush.setColor(color);
		brush.fill(this);
	}

}
