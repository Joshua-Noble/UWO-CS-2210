/**
 * This class implements an ordered dictionary using a binary search tree
 * to store all of the nodes representing pixels in the game
 * @author Joshua Noble -- 250700795
 *
 */
public class BinarySearchTree implements BinarySearchTreeADT {

	private BinaryNode root;
	
	/**
	 * Constructor which creates an empty tree with a leaf node as the root
	 */
	public BinarySearchTree() {
		root = new BinaryNode();
	}
	
	/**
	 * Returns the root node of this tree
	 * @return BinaryNode Root of this tree
	 */
	@Override
	public BinaryNode getRoot() {
		return root;
	}
	
	/**
	 * Checks to see if a node exists with the given key
	 * @param r Root of tree to check
	 * @param key Key to search for
	 * @return Pixel Returns pixel containing key if found, null otherwise
	 */
	@Override
	public Pixel get(BinaryNode r, Location key) {
		if (r.isLeaf() == true) {
			return null;
		}
		
		Location tempKey = r.getData().getLocation();
		
		if (key.compareTo(tempKey) == -1) {
			return get(r.getLeft(), key);
		} else if (key.compareTo(tempKey) == 1) {
			return get(r.getRight(), key);
		} else {
			return r.getData();
		}
	}
	
	/**
	 * Inserts a node with given data into the tree
	 * @param r Root of tree to insert into
	 * @param data Data of node to insert
	 * @throws DuplicatedKeyException Thrown if node with data already exists in tree
	 */
	@Override
	public void put(BinaryNode r, Pixel data) throws DuplicatedKeyException {
		if (get(r, data.getLocation()) == null) {
			recursivePut(r, data); // call private recursive function
		} else {
			throw new DuplicatedKeyException();
		}
	}
	
	/**
	 * Recursive function for inserting node
	 * @param r Root of tree to insert into
	 * @param data Data of node to insert
	 * @return Pixel Pixel returned for recursive purposes
	 */
	private Pixel recursivePut(BinaryNode r, Pixel data) {
		if (r.isLeaf() == true) {
			r.setData(data);
			r.setLeft(new BinaryNode(null, null, null, r));
			r.setRight(new BinaryNode(null, null, null, r));
		} else {
			Location tempKey = r.getData().getLocation();
			
			if (tempKey.compareTo(data.getLocation()) == -1) {
				return recursivePut(r.getRight(), data);
			} else if (tempKey.compareTo(data.getLocation()) == 1) {
				return recursivePut(r.getLeft(), data);
			} else {
				return null;
			}
		}
		
		return null;
	}

	/**
	 * Removes node with given key from tree
	 * @param r Root of tree to remove from
	 * @param key Key of node to remove
	 * @throws InexistentKeyException Thrown if the key does not exist in tree
	 */
	@Override
	public void remove(BinaryNode r, Location key) throws InexistentKeyException {
		BinaryNode foundNode = findNode(r, key);
		
		if (foundNode == null) {
			throw new InexistentKeyException();
		}
		
		if (foundNode.getLeft().isLeaf() == true) {
			removeLeaf(foundNode.getLeft());
		} else if (foundNode.getRight().isLeaf() == true) {
			removeLeaf(foundNode.getRight());
		} else {
			BinaryNode current = foundNode.getRight();
			
			while (current.getLeft().isLeaf() == false) {
				current = current.getLeft();
			}
			
			foundNode.setData(current.getData());
			removeLeaf(current.getLeft());
		}
	}
	
	/**
	 * Returns node object representing key if it exists in tree
	 * @param r Root of tree
	 * @param key Key of node to find
	 * @return BinaryNode Node representing key
	 */
	private BinaryNode findNode(BinaryNode r, Location key) {
		if (r.isLeaf() == true) {
			return null;
		}
		
		Location tempKey = r.getData().getLocation();
		
		if (key.compareTo(tempKey) == -1) {
			return findNode(r.getLeft(), key);
		} else if (key.compareTo(tempKey) == 1) {
			return findNode(r.getRight(), key);
		} else {
			return r;
		}
	}
	
	/**
	 * Removes an external (leaf) node from the tree
	 * @param r Node to be removed
	 */
	private void removeLeaf(BinaryNode r) {
		BinaryNode parent = r.getParent();
		BinaryNode relinkNode = null;
		
		if (parent.getLeft() == r) {
			relinkNode = parent.getRight();
		} else {
			relinkNode = parent.getLeft();
		}
		
		if (parent == root) {
			root = relinkNode;
			root.setParent(null);
		} else {
			if (parent == (parent.getParent().getLeft())) {
				parent.getParent().setLeft(relinkNode);
			} else {
				parent.getParent().setRight(relinkNode);
			}
			
			relinkNode.setParent(parent.getParent());
		}
	}

	/**
	 * Returns Pixel representing successor of given key
	 */
	@Override
	public Pixel successor(BinaryNode r, Location key) {		
		BinaryNode current = r;
		Pixel trueSuccessor = null;
		
		try {
			while(current.isLeaf() == false) {
				// if current data < key, search right tree
				if (current.getData().getLocation().compareTo(key) == -1 && current.getRight().isLeaf() == false) {
					current = current.getRight();
				} else if (current.getData().getLocation().compareTo(key) == 1) { // if current data > key, search left tree
					// if largest on left side > key, search left tree
					if (current.getLeft().isLeaf() == false && largest(current.getLeft()).getLocation().compareTo(key) == 1) {
						current = current.getLeft();
					} else { // otherwise, current must be the successor
						trueSuccessor = current.getData();
						break;
					}
				} else { // current data == key
					if (current.getRight().isLeaf() == false) { // smallest in right tree is successor
						trueSuccessor = smallest(current.getRight());
					} else { // if no right tree, then no successor exists, return null
						trueSuccessor = null;
					}
					break;
				}
			}
		} catch (EmptyTreeException e) {
			System.out.println(e.getMessage());
		}
		
		return trueSuccessor;
	}
	
	/**
	 * Returns pixel representing predecessor of given key
	 */
	@Override
	public Pixel predecessor(BinaryNode r, Location key) {		
		BinaryNode current = r;
		Pixel truePredecessor = null;
		
		try {
			while(current.isLeaf() == false) {
				// if current data > key, search left tree
				if (current.getData().getLocation().compareTo(key) == 1 && current.getLeft().isLeaf() == false) {
					current = current.getLeft();
				} else if (current.getData().getLocation().compareTo(key) == -1) {	// if current data < key, search right tree
					// if smallest on right side < key, search right tree
					if (current.getRight().isLeaf() == false && smallest(current.getRight()).getLocation().compareTo(key) == -1) {
						current = current.getRight();
					} else { // otherwise, current must be the predecessor
						truePredecessor = current.getData();
						break;
					}
				} else { // current data == key
					if (current.getLeft().isLeaf() == false) { // largest in left tree is the predecessor
						truePredecessor = largest(current.getLeft());
					} else { // if no left tree, then no predecessor exists, return null
						truePredecessor = null;
					}
					break;
				}
			}
		} catch (EmptyTreeException e) {
			System.out.println(e.getMessage());
		}
		
		return truePredecessor;
	}

	/**
	 * Returns pixel representing smallest node in tree
	 */
	@Override
	public Pixel smallest(BinaryNode r) throws EmptyTreeException {
		if (r.isLeaf() == true) {
			throw new EmptyTreeException();
		}
		
		BinaryNode current = r;
		while (current.getLeft().isLeaf() == false) {
			current = current.getLeft();
		}
		
		return current.getData();
	}

	/**
	 * Returns pixel representing largest node in tree
	 */
	@Override
	public Pixel largest(BinaryNode r) throws EmptyTreeException {
		if (r.isLeaf() == true) {
			throw new EmptyTreeException();
		}
		
		BinaryNode current = r;
		while (current.getRight().isLeaf() == false) {
			current = current.getRight();
		}
		
		return current.getData();
	}
}
