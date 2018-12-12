import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Stack;

/**
 * This class represents a labyrinth to be traversed
 * by the player, searching for the first possible exit.
 * It uses a graph to represent the labyrinth.
 * @author Joshua Noble -- 250700795
 *
 */
public class Labyrinth {
	
	private Graph labGraph;
	private Node entrance, exit;
	private Stack<Node> path;
	private Stack<Edge> previous;
	private int brickBombs;
	private int acidBombs;
	
	/**
	 * Constructor which creates a new labyrinth from the given input file
	 * @param inputFile Text file which contains info about the labyrinth layout
	 * @throws LabyrinthException Thrown if there is some error when reading the file
	 */
	public Labyrinth(String inputFile) throws LabyrinthException {
		try {
			int labWidth;
			int labLength;
			int row = 0;
			
			path = new Stack<Node>(); // This stack will hold the path to the exit, if it exists
			previous = new Stack<Edge>(); // This stack will hold the previously traveled path, while searching for exit
			
			// Use buffered reader to read file
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			String s = input.readLine(); // Skip the first line (scale factor)
			
			// Read dimensions of labyrinth
			s = input.readLine();
			labWidth = Integer.parseInt(s);
			s = input.readLine();
			labLength = Integer.parseInt(s);
			
			// Make a graph with number of nodes based on dimensions
			labGraph = new Graph(labWidth * labLength);
			
			// Read number of brick/acid bombs
			s = input.readLine();
			brickBombs = Integer.parseInt(s);
			System.out.println(brickBombs);
			s = input.readLine();
			acidBombs = Integer.parseInt(s);
			System.out.println(acidBombs);
			
			while (true) { // Loop through each line
				s = input.readLine();
				
				if (s == null) { // If we reach end of file, close file and exit infinite loop
					input.close();
					break;
				}
				
				for (int i = 0; i < s.length(); i++) { // Loop through each character in the line
					switch (s.charAt(i)) {
					case 'b': // entrance
						entrance = labGraph.getNode(((row / 2) * labWidth) + (i / 2));
						break;
					case 'x': // exit
						exit = labGraph.getNode(((row / 2) * labWidth) + (i / 2));
						break;
					case '+': // room
						break;
					case 'h': // horizontal normal brick wall
						labGraph.insertEdge(labGraph.getNode(((row / 2) * labWidth) + ((i - 1) / 2)), labGraph.getNode(((row / 2) * labWidth) + ((i + 1) / 2)), "wall");
						break;
					case 'H': // horizontal thick brick wall
						labGraph.insertEdge(labGraph.getNode(((row / 2) * labWidth) + ((i - 1) / 2)), labGraph.getNode(((row / 2) * labWidth) + ((i + 1) / 2)), "thickWall");
						break;
					case 'm': // horizontal metal wall
						labGraph.insertEdge(labGraph.getNode(((row / 2) * labWidth) + ((i - 1) / 2)), labGraph.getNode(((row / 2) * labWidth) + ((i + 1) / 2)), "metalWall");
						break;
					case 'v': // vertical normal brick wall
						labGraph.insertEdge(labGraph.getNode((((row - 1) / 2) * labWidth) + (i / 2)), labGraph.getNode((((row + 1) / 2) * labWidth) + (i / 2)), "wall");
						break;
					case 'V': // vertical thick brick wall
						labGraph.insertEdge(labGraph.getNode((((row - 1) / 2) * labWidth) + (i / 2)), labGraph.getNode((((row + 1) / 2) * labWidth) + (i / 2)), "thickWall");
						break;
					case 'M': // vertical metal wall
						labGraph.insertEdge(labGraph.getNode((((row - 1) / 2) * labWidth) + (i / 2)), labGraph.getNode((((row + 1) / 2) * labWidth) + (i / 2)), "metalWall");
						break;
					case '-': // horizontal corridor
						labGraph.insertEdge(labGraph.getNode(((row / 2) * labWidth) + ((i - 1) / 2)), labGraph.getNode(((row / 2) * labWidth) + ((i + 1) / 2)), "corridor");
						break;
					case '|': // vertical corridor
						labGraph.insertEdge(labGraph.getNode((((row - 1) / 2) * labWidth) + (i / 2)), labGraph.getNode((((row + 1) / 2) * labWidth) + (i / 2)), "corridor");
						break;
					case ' ': // unbreakable, solid rock
						break;
					}
				}
				
				row++;
			}
		} catch (Exception e) {
			throw new LabyrinthException("Unable to read input file");
		}
	}
	
	/**
	 * Gets the graph of the labyrinth
	 * @return Graph Current graph of the labyrinth
	 * @throws LabyrinthException Thrown if the labyrinth graph doesn't exist
	 */
	public Graph getGraph() throws LabyrinthException {
		if (labGraph == null) {
			throw new LabyrinthException("Graph does not exist!");
		} else {
			return labGraph;
		}
	}
	
	/**
	 * Attempts to solve the labyrinth by searching for a possible path to the exit
	 * @return Iterator<Node> An iterator which contains all of the nodes of the path to the exit, if it exists, null otherwise
	 */
	public Iterator<Node> solve() {
		rpath(entrance); // Call recursive method to find the path to exit
		
		if (path.empty()) {
			return null;
		} else {
			return path.iterator();
		}
	}
	
	/**
	 * Recursive method which uses DFS traversal to find the path to the exit, if it exists
	 * @param u Current node to search from
	 * @return boolean Returns true if a valid path was found, false otherwise
	 */
	private boolean rpath(Node u) {
		path.push(u);
		
		// Base case, return true once we have found the exit
		if (u == exit) {
			return true;
		} else {
			u.setMark(true);
			
			try {
				Iterator<Edge> incEdges = labGraph.incidentEdges(u);
				
				// Loop through all edges of the current node
				while (incEdges.hasNext()) {
					Edge edge = incEdges.next();
					
					// Check if the edge connects to a node which we haven't explored yet
					if (edge.secondEndpoint().getMark() != true) {
						// Handle cases for different types of edges
						if (edge.getType() == "corridor") {
							previous.push(edge);
							if (rpath(edge.secondEndpoint())) {
								return true;
							}
						} else if (edge.getType() == "wall" && brickBombs > 0) {
							previous.push(edge);
							brickBombs--;
							if (rpath(edge.secondEndpoint())) {
								return true;
							}
						} else if (edge.getType() == "thickWall" && brickBombs > 1) {
							previous.push(edge);
							brickBombs -= 2;
							if (rpath(edge.secondEndpoint())) {
								return true;
							}
						} else if (edge.getType() == "metalWall" && acidBombs > 0) {
							previous.push(edge);
							acidBombs--;
							if (rpath(edge.secondEndpoint())) {
								return true;
							}
						}
					} else if (edge.firstEndpoint().getMark() != true) { // This is the other possibility for nodes we haven't explored yet
						// Handle cases for different types of edges
						if (edge.getType() == "corridor") {
							previous.push(edge);
							if (rpath(edge.firstEndpoint())) {
								return true;
							}
						} else if (edge.getType() == "wall" && brickBombs > 0) {
							previous.push(edge);
							brickBombs--;
							if (rpath(edge.firstEndpoint())) {
								return true;
							}
						} else if (edge.getType() == "thickWall" && brickBombs > 1) {
							previous.push(edge);
							brickBombs -= 2;
							if (rpath(edge.firstEndpoint())) {
								return true;
							}
						} else if (edge.getType() == "metalWall" && acidBombs > 0) {
							previous.push(edge);
							acidBombs--;
							if (rpath(edge.firstEndpoint())) {
								return true;
							}
						}
					}
				}
				
				// If we reach here, then we hit a dead end, need to retrace steps
				if (!previous.empty()) { // previous is a stack which contains the previously visited edges
					Edge temp = previous.pop();
					
					// If we used any bombs, replenish them as we retrace steps
					if (temp.getType() == "wall") {
						brickBombs++;
					} else if (temp.getType() == "thickWall") {
						brickBombs += 2;
					} else if (temp.getType() == "metalWall") {
						acidBombs++;
					}
				}

				// Remove mark from visited node, remove node from path to exit
				u.setMark(false);
				path.pop();
				return false;
			} catch (GraphException e) {
				e.printStackTrace();
			}
			
			return false;
		}
	}
}
