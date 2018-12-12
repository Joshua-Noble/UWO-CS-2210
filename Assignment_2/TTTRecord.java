/**
 * This class represents a single record of a TTT game
 * @author Joshua Noble -- 250700795
 *
 */
public class TTTRecord {
	
	private String config;
	private int score;
	private int level;
	
	/**
	 * Constructor which creates the record with given config, score, and level
	 * @param config String representation of config of the board
	 * @param score Score of the current board
	 * @param level Level of the  current board
	 */
	public TTTRecord(String config, int score, int level) {
		this.config = config;
		this.score = score;
		this.level = level;
	}
	
	/**
	 * Returns the config of the record
	 * @return String Config of the current record
	 */
	public String getConfiguration() {
		return config;
	}
	
	/**
	 * Returns score of the record
	 * @return int Score of the current record
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Returns level of the record
	 * @return int Level of the current record
	 */
	public int getLevel() {
		return level;
	}
}