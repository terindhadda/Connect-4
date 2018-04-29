package game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A data type for the tiles on the board. Similar to a disc.
 * 
 * @author The Dhadda Structures
 *
 */
public class Tile extends JLabel implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	// Different possible images a tile can hold
	private ImageIcon blue = new ImageIcon(getClass().getResource("/images/blueCircle.png"));
	private ImageIcon blueHL = new ImageIcon(getClass().getResource("/images/blueHighlighted.png"));
	private ImageIcon red = new ImageIcon(getClass().getResource("/images/redCircle.png"));
	private ImageIcon redHL = new ImageIcon(getClass().getResource("/images/redHighlighted.png"));
	private ImageIcon blank = new ImageIcon(getClass().getResource("/images/blank.png"));
		
	private int r;	// row
	private int c;	// column
	
	// Getters and Setters for row and column
	public int getRow() {return r;}
	public int getCol() {return c;}
	public void setRow(int r) {this.r = r;}
	public void setCol(int c) {this.c = c;}
	
	/**
	 * Initialize a blank tile.
	 * 
	 * @param row
	 * @param col
	 */
	Tile(int row, int col) {
		this.r = row; 
		this.c = col;
		setIcon(blank);
		this.addMouseListener(this);	
	}
	
	/**
	 * Initialize a tile with a specified color.
	 * 
	 * @param row
	 * @param col
	 * @param color
	 */
	Tile(int row, int col, String color) {
		this.r = row; 
		this.c = col;
		if (color == "R") setIcon(red);
		if (color == "B") setIcon(blue);
		if (color == "-") setIcon(blank);
		this.addMouseListener(this);	
	}

	// Methods which do nothing but must be implemented
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	/**
	 * When a player clicks a tile, update the display accordingly.
	 */
    public void mouseClicked(MouseEvent e) {
    	    	
    	if (Display.getMessage().getText() == "RED WINS" || Display.getMessage().getText() == "BLUE WINS") return;
    	
    	int j = getCol();
	    Tile[][] tile = Display.getSquares();
    	
    	/*
    	*	This loop checks the Column that has been pressed. It iterates from bottom of the column
    	*	to the top, and if it finds an empty tile ("-") it puts the proper color of tile in 
    	*	the square. After every click it switches turns to the opposing team. But if the column is
    	*	full, the turn DOES NOT change.
    	*/
    	
    	for(int i = 5; i >= 0; i--) {
    		if(tile[i][j].getColor() == "-" ) {
    			tile[i][j].setColour();
    			if (Mode.getMode() == Turn.RED) {
    	    		Mode.setMode(Turn.BLUE);
    	    		Display.updateDisplayBlue();
    	    	} else if (Mode.getMode() == Turn.BLUE) {
    	    		Mode.setMode(Turn.RED);
    	    		Display.updateDisplayRed();
    	    	}  
    			new Check();
    			if (Display.getComputerTurn() != null) Display.runComputerGame();
    			break;
    		}
    	} 
    }
    
    /**
     * Getter for the color of a respective tile.
     * 
     * @return - a String representation for the color of a tile
     *  "R" = RED tile
     *  "B" = BLUE tile
     *  "-" = BLANK tile
     */
    public String getColor() {
    	if (getIcon() == red || getIcon() == redHL) {
    		return "R";
    	} else if (getIcon() == blue || getIcon() == blueHL) {
    		return "B";
    	} else {
    		return "-";
    	}
    }
    
    /**
     * Setter for the colors for a tile.
     */
    private void setColour() {
    	if (Mode.getMode() == Turn.RED) {
    		if (getIcon() != blue) {
    			setIcon(red);
    		}
    	}
    	
    	if (Mode.getMode() == Turn.BLUE) {
    		if (getIcon() != red) {
    			setIcon(blue);
    		}
    	}
    } 
    
    /**
     * Highlights the current disc.
     */
    public void setHighlighted() {
    	if (getIcon() == blue) setIcon(blueHL);
    	else if (getIcon() == red) setIcon(redHL);
    }
   
    /**
     * Runs the computer move by playing the tile in a column (found
     * by the AI). Updates the screen accordingly.
     * 
     * @param col
     */
    public static void computerMove(int col) {
    	
	  	Tile[][] tile = Display.getSquares();
	  	
	  	// Find the column
		for(int i = 5; i >= 0; i--) {
			if(tile[i][col].getColor() == "-" ) {
				tile[i][col].setColour();
				
				// Update the mode and display
				if (Mode.getMode() == Turn.RED) {
		    		Mode.setMode(Turn.BLUE);
		    		Display.updateDisplayBlue();
		    	} else if (Mode.getMode() == Turn.BLUE) {
		    		Mode.setMode(Turn.RED);
		    		Display.updateDisplayRed();	
		    	}  
				
				// Check if the computer won
				new Check();
	    		Display.runComputerGame();
				break;
			}
		} 
    }
}
