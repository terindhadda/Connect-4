package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Performs respective operations when a menu item is clicked.
 * 
 * @author The Dhadda Structures
 *
 */
public class MenuItemListener implements ActionListener {
	
	/**
	 * Listens for an action (mouse click) on a menu item and updates 
	 * the display accordingly.
	 */
	public void actionPerformed(ActionEvent e) {
		
		String[][] storedSquares = Display.getStoredSquares();
		
		// Exit game
		if (e.getActionCommand().equals("Exit")) {
			Display.resetBoard();
			System.exit(0);
		}
		
		// Start new game
		else if (e.getActionCommand().equals("Start New Game")) {
			Display.resetBoard();
			Display.close();
			new Launcher();
		}
		
		// Store the current game
		else if (e.getActionCommand().equals("Store Game")) {
			Tile[][] tiles = Display.getSquares();
			for (int row = 0; row < 6; row++) {
				for (int col = 0; col < 7; col++) {

					if (tiles[row][col].getColor() == "R") {
						storedSquares[row][col] = "R";
					}
					else if (tiles[row][col].getColor() == "B") {
						storedSquares[row][col] = "B";
					}
					else {
						storedSquares[row][col] = "-";
					}
				}
			}
			Display.setStoredSquares(storedSquares);
			Display.setStoredTurn(Mode.getMode());
		}
		
		// Reload a game
		else {
			Display.reloadGame();
		}
	}
}
