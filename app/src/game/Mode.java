package game;

/**
 * Stores the turn (RED or BLUE) the game is currently in.
 * 
 * @author The Dhadda Structures
 *
 */
public class Mode {

	private static Turn mode;

	/**
	 * Getter for current turn.
	 * 
	 * @return Turn - the current turn.
	 */
	public static Turn getMode() {
		return mode;
	}
	
	/**
	 * Setter for current turn.
	 * 
	 * @param t - turn to change to.
	 */
	public static void setMode(Turn t) {
		mode = t;
	}

}
