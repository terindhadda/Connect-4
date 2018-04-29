package game;

/**
 * Analyzes and checks the current state of the game and displays an
 * error message if necessary.
 * 
 * @author The Dhadda Structures
 * 
 * BOARD LAYOUT IN GRID FORMAT (ROW, COL)
 * (0, 0)(0, 1)(0, 2)(0, 3)(0, 4)(0, 5)(0, 6)
 * (1, 0)(1, 1)(1, 2)(1, 3)(1, 4)(1, 5)(1, 6)
 * (2, 0)(2, 1)(2, 2)(2, 3)(2, 4)(2, 5)(2, 6)
 * (3, 0)(3, 1)(3, 2)(3, 3)(3, 4)(3, 5)(3, 6)
 * (4, 0)(4, 1)(4, 2)(4, 3)(4, 4)(4, 5)(4, 6)
 * (5, 0)(5, 1)(5, 2)(5, 3)(5, 4)(5, 5)(5, 6)
 *
 */
public class Check {

	// Current tiles on the board
	private Tile[][] tiles = Display.getSquares();
	
	// String representation of the tiles
	private String[][] squares = new String[6][7];

	/**
	 * Default constructor turns squares into a string representation
	 * of the current state of the board, then analyzes it.
	 */
	public Check() {
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
		checkFull();
		checkFourInARow();
	}
		
	/**
	 *	This method will check the top row, if there is a blank in it ("-") there is still
	 *	a turn to be made. 
	 */	
	private void checkFull() {
				
		for (int col = 0; col < 7; col++) {
			if (squares[0][col] == "-") {
				break;
			} else if (col == 6) { 
				Display.updateMessageDraw();
			}
		}
	}
	
	/**
	 * Checks the current state of the board to see whether there
	 * are four discs in a row. Displays the appropriate error if 
	 * there is.
	 */
	private void checkFourInARow() {
		// get all positions
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				// check if valid starting point
				if (squares[row][col] == "R" || squares[row][col] == "B") {
					// check all directions
					for (int[] dir : new int[][]{{-1,-1},{0,-1},{1,-1},
												 {-1, 0},		{1, 0},
												 {-1, 1},{0, 1},{1, 1}} )
					{
						// if there are 3 more connected (4 in a row)
						if (checkFourHelper(new int[]{row, col}, dir, 1)) {
							if (squares[row][col] == "R") Display.updateRedWins();
							else Display.updateBlueWins();
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Helper method for checkFourInARow(). 
	 * 
	 * @param pos - current position
	 * @param dir - direction to check
	 * @param count - number of discs in a row
	 * @return - boolean of position's state
	 */
	private boolean checkFourHelper(int[] pos, int[] dir, int count) {
		
		// four in a row reached, chain back 'true' value
		if (count == 4) return true;
		
		// get coordinates of next square in direction
		int[] next = new int[]{pos[0]+dir[0], pos[1]+dir[1]};
		
		// check if not within bounds
		if (next[0] >= 6 || next[0] < 0 ||
			next[1] >= 7 || next[1] < 0) return false;
		
		// check if next square matches current square
		if (squares[next[0]][next[1]] == squares[pos[0]][pos[1]]) {
			// recursively check in the same direction, increment # connected discs
			if (checkFourHelper(next, dir, count+1)) {
				// four in a row reached, highlight disc going back up recursion
				tiles[pos[0]][pos[1]].setHighlighted();
				// continue chain
				return true;
			}
		}
		
		return false; // next disc did not match
	}
}
