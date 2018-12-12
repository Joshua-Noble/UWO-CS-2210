/**
 * This class is a singly-linked list implementation of TTTRecord
 * Each TTTLinkedRecord is a TTTRecord which has a link to the next TTTRecord
 * @author Joshua Noble -- 250700795
 *
 */
public class TTTLinkedRecord {
	
	private TTTRecord record;
	private TTTLinkedRecord next;
	
	/**
	 * Constructor which sets the given record to be a linked record
	 * @param record Record to be turned into a linked record
	 */
	public TTTLinkedRecord(TTTRecord record) {
		this.record = record;
		next = null;
	}
	
	/**
	 * Returns the record of the current linked record
	 * @return TTTRecord Record to be returned
	 */
	public TTTRecord getRecord() {
		return record;
	}
	
	/**
	 * Returns the config of the current linked record
	 * @return String Config to be returned
	 */
	public String getConfig() {
		return record.getConfiguration();
	}
	
	/**
	 * Returns the score of the current linked record
	 * @return int Score to be returned
	 */
	public int getScore() {
		return record.getScore();
	}
	
	/**
	 * Returns the next linked record in the linked list
	 * @return TTTLinkedRecord Next linked record in the linked list
	 */
	public TTTLinkedRecord getNext() {
		return next;
	}
	
	/**
	 * Sets the next linked record to the given linked record
	 * @param next Linked record to be set as next in the linked list
	 */
	public void setNext(TTTLinkedRecord next) {
		this.next = next;
	}
}