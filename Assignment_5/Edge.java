/**
 * This class represents an edge between two nodes on the graph,
 * which corresponds to a wall or corridor in the labyrinth.
 * It also contains the type of the edge as indicated in the input file.
 * @author Joshua Noble -- 250700795
 *
 */
public class Edge {
	
	private Node u;
	private Node v;
	private String type;
	
	/**
	 * Constructor which creates a new edge between two nodes with given type
	 * @param u First node of the edge
	 * @param v Second node of the edge
	 * @param type Type of the edge
	 */
	public Edge(Node u, Node v, String type) {
		this.u = u;
		this.v = v;
		this.type = type;
	}
	
	/**
	 * Returns the node at the first end point of the edge
	 * @return Node First end point node
	 */
	public Node firstEndpoint() {
		return u;
	}
	
	/**
	 * Returns the node at the second end point of the edge
	 * @return Node Second end point node
	 */
	public Node secondEndpoint() {
		return v;
	}
	
	/**
	 * Returns the type of the edge
	 * @return String Type of the edge
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type of the edge to the given value
	 * @param type New type to be assigned to edge
	 */
	public void setType(String type) {
		this.type = type;
	}

}
