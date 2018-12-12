import java.util.Iterator;
import java.util.Stack;

/**
 * This class implements a graph using an adjacency matrix,
 * which holds the layout of the labyrinth
 * @author Joshua Noble -- 250700795
 *
 */
public class Graph implements GraphADT {

	private int n;
	private Node[] nodes;
	private Edge[][] matrix;
	
	/**
	 * Constructor to create a graph with n nodes and no edges
	 * @param n The number of nodes in the graph
	 */
	public Graph(int n) {
		this.n = n;
		nodes = new Node[n];
		matrix = new Edge[n][n]; // Initialize adjacency matrix with null for each edge (no edges exist yet)
		
		// Fill array of nodes with nodes corresponding to names 0, 1, ..., n - 1
		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(i);
		}
	}
	
	/**
	 * Inserts an edge between two nodes into the graph
	 * @param nodeu First node of the edge
	 * @param nodev Second node of the edge
	 * @param edgeType Type of edge to insert
	 * @throws GraphException Thrown if edge already exists or if one of the nodes is not in the graph
	 */
	@Override
	public void insertEdge(Node nodeu, Node nodev, String edgeType) throws GraphException {
		// Check if graph contains both nodes
		if (nodeu.getName() >= n || nodev.getName() >= n || nodeu.getName() < 0 || nodev.getName() < 0) {
			throw new GraphException("u and/or v are not nodes of the graph!");
		}
		
		// Check if edge exists already
		if (matrix[nodeu.getName()][nodev.getName()] != null) {
			throw new GraphException("Edge already exists!");
		}
		
		// Create new edge to be inserted
		Edge insEdge = new Edge(nodeu, nodev, edgeType);
		
		// Insert edge in both locations of the adjacency matrix
		matrix[nodeu.getName()][nodev.getName()] = insEdge;
		matrix[nodev.getName()][nodeu.getName()] = insEdge;
	}

	/**
	 * Gets the node with given name
	 * @param name Name of the node to search for
	 * @return Node The node if it exists
	 * @throws GraphException Thrown if node does not exist
	 */
	@Override
	public Node getNode(int name) throws GraphException {
		if (name < n) {
			return nodes[name];
		} else {
			throw new GraphException("Node doesn't exist!");
		}
	}

	/**
	 * Returns an iterator of all edges which are incident on the given node
	 * @param u The node to find incident edges of
	 * @return Iterator<Edge> Iterator of all incident edges, if none, returns null
	 * @throws GraphException Thrown if node does not exist
	 */
	@Override
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		// Check if graph contains node
		if (u.getName() >= n || u.getName() < 0) {
			throw new GraphException("u is not a node of the graph!");
		}
		
		// Use a stack to hold incident edges
		Stack<Edge> incEdges = new Stack<Edge>();
		
		for (int i = 0; i < n; i++) {
			if (matrix[u.getName()][i] != null) { // If an edge exists, it is incident on u
				incEdges.push(matrix[u.getName()][i]); // Add the edge to the stack using push
			}
		}
		
		// If nothing was added to the stack, return null, otherwise return the iterator for the stack
		if (incEdges.isEmpty()) {
			return null;
		} else {
			return incEdges.iterator();
		}
	}

	/**
	 * Gets the edge between two nodes
	 * @param u First node of the edge
	 * @param v Second node of the edge
	 * @return Edge The edge between the given nodes, if it exists
	 * @throws GraphException Thrown if one of the nodes doesn't exist, or the edge doesn't exist
	 */
	@Override
	public Edge getEdge(Node u, Node v) throws GraphException {
		// Check if graph contains both nodes
		if (u.getName() >= n || v.getName() >= n || u.getName() < 0 || v.getName() < 0) {
			throw new GraphException("u and/or v are not nodes of the graph!");
		}
		
		// Check if edge exists in matrix
		if (matrix[u.getName()][v.getName()] != null) {
			return matrix[u.getName()][v.getName()];
		} else {
			throw new GraphException("Edge doesn't exist!");
		}

	}

	/**
	 * Checks if two nodes are adjacent
	 * @param u First node to check
	 * @param v Second node to check
	 * @return boolean True if the nodes are adjacent, false otherwise
	 * @throws GraphException Thrown if one of the nodes doesn't exist
	 */
	@Override
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		// Check if graph contains both nodes
		if (u.getName() >= n || v.getName() >= n || u.getName() < 0 || v.getName() < 0) {
			throw new GraphException("u and/or v are not nodes of the graph!");
		}
		
		// Check if there is an edge between the nodes
		if (matrix[u.getName()][v.getName()] != null) {
			return true;
		} else {
			return false;
		}
	}
}
