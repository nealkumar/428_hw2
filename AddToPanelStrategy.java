package neal_kumar;

import java.awt.Color;
import java.util.List;

public class AddToPanelStrategy extends StrategyTemplate {

	public AddToPanelStrategy(String passedStatement, Color color) {
		super(passedStatement, color);
	}

	/**
	 * Adds a new circle to Panel.circleList. repaint() in Panel updates the JPanel.
	 */
	@Override
	protected synchronized void updateCircleList(List<Integer> coordinates) {
		// TODO Auto-generated method stub
		int startX = coordinates.get(0); // gets x-value
		int startY = coordinates.get(1); // gets y-value
		Circle c = new Circle(startX, startY, 100, 100, color);
		System.out.println("Recieved coordinates of (" + startX + ", " + startY + ") from client with color R: "
				+ color.getRed() + ", G: " + color.getGreen() + ", B: " + color.getBlue());

		Panel.circleList.add(c); // updates Panel's circleList
	}

}
