package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Launcher {
	
	private static final String TITLE = "Connect Four!";
	
	private static JLabel welcomeMessage;
	private static JLabel chooseMessage;
	
	private static JButton twoPlayerButton;			// Human        vs. Human
	private static JButton onePlayerButtonRed;		// Human (RED)  vs. Computer (BLUE)
	private static JButton onePlayerButtonBlue;		// Human (BLUE) vs. Computer (RED)
	
	private static JFrame frame;

	/**
	 * Default constructor - starts the game.
	 */
	public Launcher() {
		startGame();
	}
	
	/**
	 * Closes the launcher display.
	 */
	public static void close() {
		frame.dispose();
	}
	
	/**
	 * Create the display for a new game.
	 */
	public static void startGame() {
		
		// Create the frame
		frame = new JFrame(TITLE);
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// Create the messages and style them
		welcomeMessage = new JLabel("Welcome to Connect Four!");
		chooseMessage = new JLabel("Choose a Game Mode: ");
		
        welcomeMessage.setFont(new Font("Arial", 1, 20));
        chooseMessage.setFont(new Font("Arial", 1, 20));
        
		welcomeMessage.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		chooseMessage.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// Create the buttons, style them, add action listeners
		twoPlayerButton = new JButton("Human vs. Human");
		onePlayerButtonRed = new JButton("Human (RED) vs. Computer (BLUE)");
		onePlayerButtonBlue = new JButton("Human (BLUE) vs. Computer (RED)");
		
		twoPlayerButton.setBackground(null);
		onePlayerButtonRed.setBackground(Color.RED);
		onePlayerButtonBlue.setBackground(Color.CYAN);

		twoPlayerButton.addActionListener(new GameModeButtonListener());
		onePlayerButtonRed.addActionListener(new GameModeButtonListener());
		onePlayerButtonBlue.addActionListener(new GameModeButtonListener());

		// Add internal border to frame
		((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		// Organize display components and center-align them
		Box vert = Box.createVerticalBox();
		
		vert.add(welcomeMessage);
		vert.add(chooseMessage);
		vert.add(twoPlayerButton);
		vert.add(onePlayerButtonRed);
		vert.add(onePlayerButtonBlue);
		
		welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		chooseMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		twoPlayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		onePlayerButtonRed.setAlignmentX(Component.CENTER_ALIGNMENT);
		onePlayerButtonBlue.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Create the content 
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(vert, BorderLayout.CENTER);		
		
		// Pack and display
		frame.pack();
	}
	
	/**
	 * Runs Connect Four.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Launcher();
	}
}
