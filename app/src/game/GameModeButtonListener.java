package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An action listener for the type of game mode that the user specfies. 
 * Game modes include:
 * 
 * 		Human vs. Human
 * 		Human (BLUE) vs. Computer (RED)
 * 		Human (RED) vs. Computer (BLUE)
 * 
 * @author The Dhadda Structures
 *
 */
public class GameModeButtonListener implements ActionListener {

	/**
	 * Listens for a mouse click on one of the game mode buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		
		// Create new human vs. human game
		if (e.getActionCommand().equals("Human vs. Human")) {
			Launcher.close();
			new Display();
		} 
		
		// Create a new human vs. computer game with human as blue
		if (e.getActionCommand().equals("Human (BLUE) vs. Computer (RED)")) {
			Launcher.close();
			new Display("RED");
		}
		
		// Create a new human vs. computer game with human as red
		if (e.getActionCommand().equals("Human (RED) vs. Computer (BLUE)")) {
			Launcher.close();
			new Display("BLUE");
		}
	}
}
