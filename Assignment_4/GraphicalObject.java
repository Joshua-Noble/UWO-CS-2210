/**
 * This class represents a graphical object in the game and stores
 * information about it
 * @author Joshua Noble -- 250700795
 *
 */
public class GraphicalObject implements GraphicalObjectADT {

	private int id;
	private int width;
	private int height;
	private String type;
	private Location offset;
	private BinarySearchTree BST;
	
	/**
	 * Constructor which creates a new graphical object with given id, width, height, type, and offset
	 * @param id Unique id representing the graphical object
	 * @param width Width of the graphical object
	 * @param height Height of the graphical object
	 * @param type Type of the graphical object
	 * @param pos Offset of the graphical object
	 */
	public GraphicalObject (int id, int width, int height, String type, Location pos) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.type = type;
		this.offset = pos;
		BST = new BinarySearchTree();
	}
	
	/**
	 * Returns the width of this graphical object
	 * @return int Width of this graphical object
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of this graphical object
	 * @return int Height of this graphical object
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the type of this graphical object
	 * @return String Type of this graphical object
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * Returns the id of this graphical object
	 * @return int id of this graphical object
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * Returns the offset of this graphical object as a location
	 * @return Location Offset of this graphical object
	 */
	@Override
	public Location getOffset() {
		return offset;
	}

	/**
	 * Sets the offset of this graphical object to the given offset
	 * @param value Location to be set as new offset
	 */
	@Override
	public void setOffset(Location value) {
		offset = value;
	}

	/**
	 * Sets the type of this graphical object to the given type
	 * @param t String to be set as new type
	 */
	@Override
	public void setType(String t) {
		type = t;
	}

	/**
	 * Adds the given pixel to the BST of this graphical object
	 * @param pix Pixel to be added to BST
	 * @throws DuplicatedKeyException Thrown if the given pixel already exists in the BST
	 */
	@Override
	public void addPixel(Pixel pix) throws DuplicatedKeyException {
		BST.put(BST.getRoot(), pix);
	}

	/**
	 * Checks to see if this graphical object intersects with the given graphical object
	 * @param fig Graphical object to check for intersection with
	 * @return boolean Returns true if they overlap, false otherwise
	 */
	@Override
	public boolean intersects(GraphicalObject fig) {
		try {
			Pixel currentPix = BST.smallest(BST.getRoot()); // start at smallest value when searching entire BST
	
			while (currentPix != null) { // successor returns null once there are no more possible pixels to search
				int x = currentPix.getLocation().xCoord() + offset.xCoord() - fig.getOffset().xCoord(); // x + xf - xf'
				int y = currentPix.getLocation().yCoord() + offset.yCoord() - fig.getOffset().yCoord(); // y + yf - yf'
				
				Location loc = new Location(x, y);
				
				if (fig.findPixel(loc) == true) { // if pixel with given location exists in BST, intersection is found, return true
					return true;
				}
				
				currentPix = BST.successor(BST.getRoot(), currentPix.getLocation()); // using successor with smallest allows us to check every pixel
			}
		} catch (EmptyTreeException e) {
			System.out.println(e.getMessage());
		}
		
		return false; // if no overlapping pixels found, return false (no intersection)
	}
	
	/**
	 * Checks if there is a pixel with the given location in BST of this graphical object
	 * @param p Location of pixel to search for
	 * @return boolean Returns true if a pixel with given location exists in BST, false otherwise
	 */
	private boolean findPixel(Location p) {
		if (BST.get(BST.getRoot(), p) != null) {
			return true;
		} else {
			return false;
		}
	}
}
