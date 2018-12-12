/**
 * This class contains a gameboard for a specific state of a TTT game
 * @author Joshua Noble -- 250700795
 * 
 */
public class BlockedTicTacToe {
	
	private char[][] gameBoard;
	private int size;
	private int inline;
	private int max_levels;
	
	/**
	 * Constructor which initializes gameBoard and sets each square to empty
	 * @param board_size Size of TTT board (N x N) where N = board_size
	 * @param inline Number of symbols in-line needed to win the game
	 * @param max_levels Maximum level of the game tree that will be explored by the program
	 */
	public BlockedTicTacToe (int board_size, int inline, int max_levels) {
		gameBoard = new char[board_size][board_size];
		size = gameBoard.length;
		this.inline = inline;
		this.max_levels = max_levels;
		
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				gameBoard[j][i] = ' ';
			}
		}
	}
	
	/**
	 * Creates a new, empty dictionary, of size 5987 (prime number)
	 * @return TTTDictionary The empty dictionary of size 5987
	 */
	public TTTDictionary createDictionary() {
		TTTDictionary dict = new TTTDictionary(5987);

		return dict;
	}
	
	/**
	 * Checks to see if the config of gameBoard already exists in the given dictionary
	 * @param configurations Dictionary to search for config
	 * @return int Returns score of the config if it exists, otherwise returns -1
	 */
	public int repeatedConfig(TTTDictionary configurations) {
		String config = boardToString();
		
		TTTRecord newRecord = configurations.get(config);
		if (newRecord != null) {
			return newRecord.getScore();
		} else {
			return -1;
		}
	}
	
	/**
	 * Inserts the config of gameBoard into the given dictionary, with given score and level
	 * @param configurations Dictionary to insert config into
	 * @param score Score to insert with config
	 * @param level Level to insert with config
	 */
	public void insertConfig(TTTDictionary configurations, int score, int level) {
		String config = boardToString();
		TTTRecord record = new TTTRecord(config, score, level);
		
		configurations.put(record);	
	}
	
	/**
	 * Stores the character symbol in gameBoard
	 * @param row Row of gameBoard to store char at
	 * @param col Column of gameBoard to store char at
	 * @param symbol Char to store
	 */
	public void storePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}
	
	/**
	 * Checks to see if a particular square is empty (no x, o, or b)
	 * @param row Row of gameBoard to check
	 * @param col Column of gameBoard to check
	 * @return boolean Returns true if square is empty, false otherwise
	 */
	public boolean squareIsEmpty (int row, int col) {
		if (gameBoard[row][col] == ' ') {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks to see if a particular symbol has met any of the win conditions (row, column, diagonal)
	 * @param symbol Char to check for win conditions
	 * @return boolean Returns true if any win condition is met, false otherwise
	 */
	public boolean wins (char symbol) {
		int numConsec = 0;
		char prevSymbol;
		
		prevSymbol = gameBoard[0][0];
		
		// Check for win by row
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (gameBoard[i][j] == symbol) {
					if (prevSymbol == symbol) {
						numConsec += 1;
					} else {
						numConsec = 1;
					}
				} else {
					numConsec = 0;
				}
				
				if (numConsec == inline) {
					return true;
				}
				
				prevSymbol = gameBoard[i][j];
			}
			numConsec = 0;
		}

		prevSymbol = gameBoard[0][0];
		numConsec = 0;
		
		// Check for win by column
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (gameBoard[j][i] == symbol) {
					if (prevSymbol == symbol) {
						numConsec += 1;
					} else {
						numConsec = 1;
					}
				} else {
					numConsec = 0;
				}
				
				if (numConsec == inline) {
					return true;
				}
				
				prevSymbol = gameBoard[j][i];
			}
			numConsec = 0;
		}
		
		prevSymbol = gameBoard[0][0];
		numConsec = 0;
		
		int diagLimit = inline - 1;
		
		// Check for win by diagonal
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ((i + diagLimit < size) && (j  + diagLimit < size)) {
					for (int k = 0; k < inline; k++) {
						if (gameBoard[i + k][j + k] == symbol) {
							if (prevSymbol == symbol) {
								numConsec += 1;
							} else {
								numConsec = 1;
							}
						} else {
							numConsec = 0;
						}
						
						prevSymbol = gameBoard[i + k][j + k];
					}
				}
				
				if (numConsec == inline) {
					return true;
				}
				
				numConsec = 0;
			}
		}
		
		// Check for win by anti-diagonal
		for (int i = 0; i < size; i++) {
			if (gameBoard[i][(size - 1) - i] != symbol) {
				break;
			}
			
			if (i == size - 1) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks to see if the current gameBoard meets draw conditions
	 * @return boolean Returns true if gameBoard has no empty positions left and no player has won, false otherwise
	 */
	public boolean isDraw() {
		boolean empty = false;
		
		// Check for empty spaces
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (squareIsEmpty(i, j)) {
					empty = true;
				}
			}
		}
		
		boolean winX, winO;
		winX = wins('x');
		winO = wins('o');
		
		
		return (!empty && !winX && !winO);
	}
	
	/**
	 * Evaluates the board to give a value according to the current state
	 * @return int Returns a number from 0-3, depending on the current state of the board
	 */
	public int evalBoard() {
		if (wins('o')) { // if computer has won
			return 3;
		} else if (wins('x')) { // if player has won
			return 0;
		} else if (isDraw()) { // if game is a draw
			return 1;
		} else { // if game is undecided
			return 2;
		}
	}	
	
	/**
	 * Converts current gameBoard to a string, for use by other methods
	 * @return String string representation of gameBoard
	 */
	private String boardToString() {
		String config = "";
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				config += gameBoard[j][i];
			}
		}
		
		return config;
	}
}