/**
 * This class represents the data items to be stored in the BST
 * Each instance of a pixel represents a pixel on the game board
 * @author Joshua Noble -- 250700795
 *
 */
public class Pixel {
	
	private Location loc;
	private int color;
	
	/**
	 * Constructor which creates the pixel with given location and color
	 * @param p Location (x, y) of the pixel
	 * @param color Color of the pixel
	 */
	public Pixel(Location p, int color) {
		loc = p;
		this.color = color;
	}
	
	/**
	 * Returns the location of the pixel
	 * @return Location Object holding the location of the pixel
	 */
	public Location getLocation() {
		return loc;
	}
	
	/**
	 * Returns the color of the pixel
	 * @return int Integer value describing the color of the pixel
	 */
	public int getColor() {
		return color;
	}
}