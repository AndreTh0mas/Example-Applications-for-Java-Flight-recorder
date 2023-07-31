package HotMethods;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

/*
 * Custom JFR event to get additional info on the application behaviour
 */
@Label("Work")
@Category("JFR_HotMethods")
@Description("Data from one loop run in the worker thread")
public class WorkEvent extends Event {
	@Label("Intersection Size")
	@Description("The number of values that were the same in the two collections")
	private int intersectionSize;

	public int getIntersectionSize() {
		return intersectionSize;
	}

	public void setIntersectionSize(int intersectionSize) {
		this.intersectionSize = intersectionSize;
	}
}
