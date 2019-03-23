package neal_kumar;

import java.awt.Color;
import java.util.List;

public class DeleteFromPanelStrategy extends StrategyTemplate {

	public DeleteFromPanelStrategy(String passedStatement, Color color) {
		super(passedStatement, color);
		System.out.println("Delete called.");
	}

	/**
	 * Removes and existing circle from Panel.circleList. repaint() in Panel updates
	 * the JPanel.
	 */
	@Override
	protected synchronized void updateCircleList(List<Integer> coordinates) {
		// TODO Auto-generated method stub
		int startX = coordinates.get(0); // gets x-value
		int startY = coordinates.get(1); // gets y-value
		for (Circle c : Panel.circleList) {
			if ((c.getX() == startX) && c.getY() == startY) {
				Panel.circleList.remove(c);
			}
		}
	}

}
