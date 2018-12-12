/**
 * This class is created for exceptions when there is a duplicate key
 * @author Joshua Noble -- 250700795
 * 
 */
public class DuplicatedKeyException extends RuntimeException {

	/**
	 * Call super constructor for exception, print error message
	 */
	public DuplicatedKeyException() {
		super ("Error, this data item already exists in the tree!");
	}
}