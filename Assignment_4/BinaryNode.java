/**
 * This class represents a binary node to be stored in the BST
 * Each node contains a pixel as data and links to left/right
 * children and a parent node
 * @author Joshua Noble -- 250700795
 *
 */
public class BinaryNode {

	private Pixel data;
	private BinaryNode left;
	private BinaryNode right;
	private BinaryNode parent;
	
	/**
	 * Constructor which creates a new node with a pixel as data and links to children/parent
	 * @param value Pixel to be stored as data
	 * @param left Node to be set as left child
	 * @param right Node to be set as right child
	 * @param parent Node to be set as parent
	 */
	public BinaryNode(Pixel value, BinaryNode left, BinaryNode right, BinaryNode parent) {
		data = value;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	
	/**
	 * Constructor which creates an empty node (leaf node), with null for all values
	 */
	public BinaryNode() {
		data = null;
		left = null;
		right = null;
		parent = null;
	}
	
	/**
	 * Returns the parent of this node
	 * @return BinaryNode Parent of this node
	 */
	public BinaryNode getParent() {
		return parent;
	}
	
	/**
	 * Sets the parent of this node to the given node
	 * @param parent Node to be set as parent
	 */
	public void setParent(BinaryNode parent) {
		this.parent = parent;
	}
	
	/**
	 * Sets the left child of this node to the given node
	 * @param p Node to be set as left child
	 */
	public void setLeft(BinaryNode p) {
		left = p;
	}
	
	/**
	 * Sets the right child of this node to the given node
	 * @param p Node to be set as right child
	 */
	public void setRight(BinaryNode p) {
		right = p;
	}
	
	/**
	 * Sets the data of this node to the given data
	 * @param value Pixel to be set as data
	 */
	public void setData (Pixel value) {
		data = value;
	}
	
	/**
	 * Checks if this node is a leaf node
	 * @return boolean Returns true if this node is a leaf node, false otherwise
	 */
	public boolean isLeaf() {
		if (data == null && left == null && right == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the data of this node
	 * @return Pixel Data of this node
	 */
	public Pixel getData() {
		return data;
	}
	
	/**
	 * Returns the left child of this node
	 * @return BinaryNode Left child of this node
	 */
	public BinaryNode getLeft() {
		return left;
	}
	
	/**
	 * Returns the right child of this node
	 * @return BinaryNode Right child of this node
	 */
	public BinaryNode getRight() {
		return right;
	}
}