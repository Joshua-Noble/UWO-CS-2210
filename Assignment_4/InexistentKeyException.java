/**
 * This class is created for exceptions when a key doesn't exist
 * @author Joshua Noble -- 250700795
 * 
 */
public class InexistentKeyException extends RuntimeException {

	/**
	 * Call super constructor for exception, print error message
	 */
	public InexistentKeyException() {
		super ("Error, this data item does not exist!");
	}
}
