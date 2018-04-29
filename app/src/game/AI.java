package game;

/**
 * A defensive style AI. 
 * 
 * @author The Dhadda Structures
 *
 */
public class AI {

	// Current tiles on the board
	private static Tile[][] tiles = Display.getSquares();
	
	// String representation of the tiles
	private static String[][] squares = new String[6][7];

	/** get AI move
	 * @return int column to place disc
	 */
	public static int run() {
		
		// build a grid representing the board
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				if (tiles[row][col].getColor() == "R") {
					squares[row][col] = "R";
				}
				else if (tiles[row][col].getColor() == "B") {
					squares[row][col] = "B";
				}
				else {
					squares[row][col] = "-";
				}
			}
		}
		
		// get all positions
		int move = 3; // column to pick
		int rank = 0; // rank of the current move
		int temp, tempMove; // to check which moves are better
		
		// check the whole grid
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				// check if valid starting point
				if (squares[row][col] == "R" || squares[row][col] == "B") {
					// check all directions
					for (int[] dir : new int[][]{{-1,-1},{0,-1},{1,-1},
												 {-1, 0},		{1, 0},
												 {-1, 1},{0, 1},{1, 1}} )
					{
						// see how many are in a row
						temp = checkHelper(new int[]{row, col}, dir);
						
						// find where the move should be
						tempMove = col+dir[1]*temp;
						
						// floating moves are less accurately guessed
						if (0 <= temp) if (isFloating(row+dir[0]*temp, tempMove)) temp -= 1;
						
						// if the move is better, and is possible, save it
						if (rank <= temp && !columnFull(tempMove)) {
							rank = temp;
							move = tempMove;
						}
					}
				}
			}
		}
		return move;
	}
	
	/**
	 * Helper method for run(). 
	 * 
	 * @param pos - current position
	 * @param dir - direction to check
	 * @param count - number of discs in a row
	 * @return - int of position's state
	 */
	private static int checkHelper(int[] pos, int[] dir) {
		
		// get coordinates of next square in direction
		int[] next = new int[]{pos[0]+dir[0], pos[1]+dir[1]};
		
		// check if not within bounds (we don't want to play here)
		if (next[0] >= 6 || next[0] < 0 ||
			next[1] >= 7 || next[1] < 0) return -5;
		
		// check if next square matches current square
		if (squares[next[0]][next[1]] == squares[pos[0]][pos[1]]) {
			// recursively check in the same direction, increment # connected discs
			return checkHelper(next, dir) + 1;
		}

		// next space is empty
		if (squares[next[0]][next[1]] == "-") {
			return 1;
		}
		
		return -5; // next disc did not match
	}
	
	/**
	 * Checks if a column is full
	 * @param col the column to check
	 * @return true if the column is full, false otherwise
	 */
	private static boolean columnFull(int col) {
		for (int row = 0; row < 6; row++) {
			if (squares[row][col] == "-") return false;
		}
		return true;
	}
	
	/**
	 * Check if a space is supported
	 * @param row the row of the position
	 * @param col the column of the position
	 * @return true if the position is floating, false if it is supported
	 */
	private static boolean isFloating(int row, int col) {
		for (int y = row+1; y < 6; y++) {
			if (squares[y][col] == "-") return true;
		}
		return false;
	}
}
