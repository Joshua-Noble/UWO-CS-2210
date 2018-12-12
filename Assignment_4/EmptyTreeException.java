/**
 * This class is created for exceptions when a tree doesn't contain any data
 * @author Joshua Noble -- 250700795
 * 
 */
public class EmptyTreeException extends RuntimeException {
	
	/**
	 * Call super constructor for exception, print error message
	 */
	public EmptyTreeException() {
		super ("Error, this tree doesn't contain any data!");
	}
}
