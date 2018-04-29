package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Creates the initial display (GUI) for the game. Also controls updates necessary
 * for the display. Designed to replicate the image on Assignment 1, Figure 1.
 * 
 * @author The Dhadda Structures
 *
 */
public class Display {
	
	// Constants - height and width of the board and title of the window
	private static final int BOARD_HEIGHT = 6;
	private static final int BOARD_WIDTH = 7;
	private static final String TITLE = "Connect Four!";

	// Holds the 7x6 grid of tiles for the board
	private static Tile[][] squares = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
	private static String[][] storedSquares = new String[6][7];
	private static Turn storedTurn;
	
	// Frame to hold everything
	private static JFrame frame;
	
	// MenuBar for the frame and a "File" menu
    private JMenuBar menuBar;
    private JMenu fileMenu;
    
    // Menu items and listeners for the "File" menu
	private JMenuItem startNewGameItem;
	private JMenuItem exitGameItem;
	private JMenuItem storeGameItem;
	private JMenuItem reloadGameItem;
	private MenuItemListener startNewGameListener;
	private MenuItemListener exitGameListener;
	private MenuItemListener storeGameListener;
	private MenuItemListener reloadGameListener;
	
    // Panel to hold the 7x6 grid of tiles for the board
	private JPanel squarePanel;
	
	// Mode buttons and listeners for the game options
	private static JButton redButton;
	private static JButton blueButton;
	
	// Icons to put on the buttons 
	private ImageIcon blueButtonIcon = new ImageIcon(getClass().getResource("/images/blueButton.PNG"));
	private ImageIcon redButtonIcon = new ImageIcon(getClass().getResource("/images/redButton.PNG"));
	
	// Message to display to the player(s)
	private static JLabel message;
	
	// The initial mode (red or blue) that the game starts in
	private static Turn firstTurn;
	
	// The turn of the computer (chosen by user)
	private static Turn computerTurn;
	
	/**
	 * Default constructor to create the display and choose a player (red or
	 * blue) randomly.
	 * 
	 */
	public Display() {
		createBoard();
		createDisplay();
		chooseFirstMove();
	}
	
	/**
	 * Creates a display with a stored turn.
	 * 
	 * @param t - stored turn
	 */
	public Display(Turn t) {
		createBoard();
		createDisplay();
		firstTurn = t;
		Mode.setMode(firstTurn);
		if (Mode.getMode() == Turn.RED) { updateDisplayRed(); }
		else { updateDisplayBlue(); }
	}
	
	/**
	 * Creates a new display for one player mode.
	 * 
	 * @param turn - turn of the computer
	 */
	public Display(String turn) {
		if (turn.equals("RED"))  computerTurn = Turn.RED;
		if (turn.equals("BLUE")) computerTurn = Turn.BLUE;
		createBoard();
		createDisplay();
		chooseFirstMove();
		runComputerGame();
	}
		
	/**
	 * Do a computer move using the AI and update the 
	 * display accordingly. Then change the mode.
	 */
	public static void runComputerGame() {
		if (Mode.getMode() == computerTurn) {	// if the current turn is the computers turn
			int chosenCol = AI.run();			// run the AI to find the column 
			Tile.computerMove(chosenCol);		// run that computer move
			
			// change the mode accordingly 
			if (computerTurn == Turn.RED) Mode.setMode(Turn.BLUE);	
			else Mode.setMode(Turn.RED);
		}
	}
	
	/**
	 * Getter for computerTurn
	 * 
	 * @return Turn - computerTurn
	 */
	public static Turn getComputerTurn() {
		if (computerTurn == Turn.RED) return Turn.RED;
		else if (computerTurn == Turn.BLUE) return Turn.BLUE;
		else return null;
	}
	
	/**
	 * Close the display.
	 */
	public static void close() {
		computerTurn = null;
		frame.dispose();
	}
		
	/**
	 * Creates the game board (tiles) using storedSquares.
	 * 
	 * @throws FileNotFoundException
	 */
	public static void createBoard() {		
		try {
			for (int row = 0; row < BOARD_HEIGHT; row++) {
				for (int col = 0; col < BOARD_WIDTH; col++) {
					if (storedSquares[row][col].equals("-"))  squares[row][col] = new Tile(row, col);
					if (storedSquares[row][col].equals("R"))  squares[row][col] = new Tile(row, col, "R");
					if (storedSquares[row][col].equals("B"))  squares[row][col] = new Tile(row, col, "B");
				}
			}
		}
		
		catch (NullPointerException e) {
			for (int row = 0; row < BOARD_HEIGHT; row++) {
				for (int col = 0; col < BOARD_WIDTH; col++) {
					squares[row][col] = new Tile(row, col);
				}
			}
		}

	}
	
	/**
	 * Creates all of the display components for the game.
	 */
	public void createDisplay() {
		
		// Create the frame that stores everything
		frame = new JFrame(TITLE);
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	    // Create the menu bar 
    	menuBar = new JMenuBar();                                      
    	fileMenu = new JMenu("File");
    	menuBar.add(fileMenu);

    	// Create the menu items and listeners
    	startNewGameItem = new JMenuItem("Start New Game");
    	exitGameItem = new JMenuItem("Exit");
    	storeGameItem = new JMenuItem("Store Game");
    	reloadGameItem = new JMenuItem("Reload Game");
    	
    	startNewGameListener = new MenuItemListener();
    	exitGameListener = new MenuItemListener();
    	storeGameListener = new MenuItemListener();
    	reloadGameListener = new MenuItemListener();
    	
    	startNewGameItem.addActionListener(startNewGameListener);
    	exitGameItem.addActionListener(exitGameListener);
    	storeGameItem.addActionListener(storeGameListener);
    	reloadGameItem.addActionListener(reloadGameListener);

    	// Add menu items to menu bar
    	fileMenu.add(startNewGameItem);
    	fileMenu.addSeparator();
    	fileMenu.add(storeGameItem);
    	fileMenu.add(reloadGameItem);
    	fileMenu.addSeparator();
    	fileMenu.add(exitGameItem);

    	// Add menu bar to frame
    	frame.setJMenuBar(menuBar);
    	
		// Create the panel for the 7x6 grid of tiles
		squarePanel = new JPanel();
		squarePanel.setLayout(new GridLayout(BOARD_HEIGHT, BOARD_WIDTH));
		
		// Add tiles to the square panel in order 
	    for (int i = 0; i < BOARD_HEIGHT; i++) {
	    	for (int j = 0; j < BOARD_WIDTH; j++) {
	    		squarePanel.add(squares[i][j]);
	    	}
	    }

	    // Create mode buttons
        redButton = new JButton("RED", redButtonIcon);
        blueButton = new JButton("BLUE", blueButtonIcon);
               
        redButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        redButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        blueButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        blueButton.setHorizontalTextPosition(SwingConstants.CENTER);
        
        // Create initial message to show the player(s)
        message = new JLabel("GAME IN PROGRESS");
        message.setFont(new Font("Arial", 1, 20));

        // Create vertical and horizontal boxes to place items in
		Box vert = Box.createVerticalBox();
		Box horz = Box.createHorizontalBox();
		
		// Add mode buttons and tiles to the horizontal box in order
		horz.add(blueButton);
		horz.add(squarePanel);
		horz.add(redButton);

		// Add check button, message, and the horizontal box into the vertical box
		vert.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		vert.add(horz);
		
		// Create a border around the frame, board, and message
		((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		squarePanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		message.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// Layout the boxes
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(vert, BorderLayout.CENTER);		

		// Pack and display everything
		frame.pack();
	}
	
	/**
	 * Randomly choose a first player (RED or BLUE) and highlight
	 * the button to indicate the first player to the user.
	 */
	private void chooseFirstMove() {
		
		// Randomly pick 0 or 1
		Random rand = new Random();
		int n = rand.nextInt(2);
		
		// 1 = RED move, 0 = BLUE move
		// Update displays to highlight buttons
		if (n == 1) {
			firstTurn= Turn.RED;
			Mode.setMode(firstTurn);
			updateDisplayRed();
		} else {
			firstTurn = Turn.BLUE;
			Mode.setMode(firstTurn);
			updateDisplayBlue();
		}
	}
	
	/**
	 * Dispose the current display and create a new one.
	 * @throws FileNotFoundException 
	 */
	public static void resetDisplay() {
		frame.dispose();
		new Display();
	}
	
	/**
	 * Update the display to indicate RED's turn
	 */
	public static void updateDisplayRed() {
        redButton.setBackground(Color.RED);
        blueButton.setBackground(null);
        redButton.setOpaque(true);
		blueButton.setOpaque(false);
	}
	
	/**
	 * 	Update the display to indicate BLUE's turn
	 */
	public static void updateDisplayBlue() {
		blueButton.setBackground(Color.BLUE);
		redButton.setBackground(null);
		redButton.setOpaque(false);
		blueButton.setOpaque(true);	
	}
	
	/**
	 * Update display to show that red wins.
	 */
	public static void updateRedWins() {
		message.setText("RED WINS");
	}
	
	/**
	 * Update display to show that blue wins.
	 */
	public static void updateBlueWins() {
		message.setText("BLUE WINS");
	}
	
	/**
	 * 	Updates display to show that the game is drawn.
	 */
	public static void updateMessageDraw() {
		message.setText("GAME DRAWN");
	}

	/**
	 * Getter for squares.
	 * 
	 * @return - squares
	 */
	public static Tile[][] getSquares() { 
		return squares; 
	}
	
	/**
	 * Getter for first mode.
	 * 
	 * @return - firstMode
	 */
	public static Turn getFirstTurn() {
		return firstTurn;
	}
	
	/**
	 * Setter for first mode.
	 * 
	 * @param t - Turn to set mode to.
	 */
	public static void setStoredTurn(Turn t) {
		storedTurn = t;
	}
	
	/**
	 * Getter for message.
	 * 
	 * @return - message
	 */
	public static JLabel getMessage() {
		return message;
	}
	
	/**
	 * Getter for the stored squares (representation of a stored game).
	 * 
	 * @return String[][] - storedSquares
	 */
	public static String[][] getStoredSquares() {
		return storedSquares;
	}
	
	/**
	 * Setter for the stored squares (representation of a stored game).
	 * 
	 * @param s - stored game to set
	 */
	public static void setStoredSquares(String[][] s) {
		storedSquares = s;
	}
	
	/**
	 * Resets storedSquares to a "new" game state.
	 */
	public static void resetBoard() {
		for (int row = 0; row < 6; row++) 
			for (int col = 0; col < 7; col++) 
				storedSquares[row][col] = "-";
	}
	
	/**
	 * Reloads the game from a stored state and turn.
	 */
	public static void reloadGame() {
		createBoard();
		frame.dispose();
		if (storedTurn == Turn.RED) new Display(Turn.RED);
		if (storedTurn == Turn.BLUE) new Display(Turn.BLUE);
	}
}
