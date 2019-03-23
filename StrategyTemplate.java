package neal_kumar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Template Pattern used for abstraction
 * 
 * @author Owner
 *
 */
public abstract class StrategyTemplate {

	protected String passedStatement;
	private String regex = "\\d+";
	protected volatile List<Integer> tempCoordinates; // temp array to store x,y coordinates :: index of 0 -> x; 1 -> y
	protected Color color;

	public StrategyTemplate(String passedStatement, Color color) {
		this.passedStatement = passedStatement;
		this.color = color;
		tempCoordinates = new ArrayList<Integer>(); // temp array to store x,y coordinates
		pickStatementApart(passedStatement, tempCoordinates);
	}

	/**
	 * Applies the regex to the client statement and passes the digits on to the
	 * child methods
	 * 
	 * @param fullStatement - client statement
	 * @param coordinates   - temp array to store x,y coordinates
	 */
	protected final void pickStatementApart(String fullStatement, List<Integer> coordinates) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fullStatement);
		while (matcher.find()) {
			coordinates.add(Integer.parseInt(matcher.group()));
		}
		updateCircleList(coordinates); // calls update method in sub-classes
		this.tempCoordinates = null; // resets the coordinates array
	}

	/**
	 * Template method that manipulates Panel.circleList
	 * 
	 * @param coordinates - temp array to store x,y coordinates
	 */
	protected abstract void updateCircleList(List<Integer> coordinates);

}
