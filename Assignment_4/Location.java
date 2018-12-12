/**
 * This class represents the position (x, y) of a pixel
 * @author Joshua Noble -- 250700795
 *
 */
public class Location {

	private int x;
	private int y;
	
	/**
	 * Constructor which saves the (x, y) coordinates of a pixel
	 * @param x Value to store as x coordinate
	 * @param y Value to store as y coordinate
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x coordinate of this location
	 * @return int x coordinate of this location
	 */
	public int xCoord() {
		return x;
	}
	
	/**
	 * Returns the y coordinate of this location
	 * @return int y coordinate of this location
	 */
	public int yCoord() {
		return y;
	}
	
	/**
	 * Compares this location to another, to determine relative position
	 * @param p Location to be compared to
	 * @return int Value representing relative position, can be 0, 1, or -1
	 */
	public int compareTo(Location p) {
		int tempX = p.xCoord();
		int tempY = p.yCoord();
		
		if (x < tempX) {
			return -1; // this < p
		} else if (x > tempX) {
			return 1; // this > p
		} else if (x == tempX) {
			if (y < tempY) {
				return -1; // this < p
			} else if (y > tempY) {
				return 1; // this > p
			}
		}
		
		return 0; // this == p
	}
}