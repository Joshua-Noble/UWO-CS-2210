/**
 * This class represents a node in the graph,
 * which corresponds to a room in the labyrinth.
 * @author Joshua Noble -- 250700795
 *
 */
public class Node {

	private int name;
	private boolean mark;
	
	/**
	 * Constructor that creates a new node with given name
	 * @param name Name of the new node
	 */
	public Node(int name) {
		this.name = name;
	}
	
	/**
	 * Sets the mark of the node to the given mark
	 * @param mark Mark (true/false) to be assigned to the node
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	/**
	 * Returns the mark of the node
	 * @return boolean Mark (true/false) of the node
	 */
	public boolean getMark() {
		return mark;
	}
	
	/**
	 * Returns the name of the node
	 * @return int Name of the node
	 */
	public int getName() {
		return name;
	}
}
