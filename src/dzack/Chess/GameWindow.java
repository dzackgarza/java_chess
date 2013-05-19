package dzack.Chess;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class GameWindow extends JFrame {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static GridLabel[][] GridCells;		// Each cell on the board will be an individual object in this array
    static ArrayList <Piece> BlackPieces;
    static ArrayList <Piece> WhitePieces;
    static ArrayList<GridLabel> potentialMoves;	// Used to determine valid destinations for pieces 
    static Piece[] tempPiece;
    
    /**
     * Default constructor for a new game
     */
    public GameWindow() {

        
    	GridCells = new GridLabel[8][8];
    	BlackPieces = new ArrayList<Piece>();
    	WhitePieces = new ArrayList<Piece>();
    	potentialMoves = new ArrayList<GridLabel>();
    	tempPiece = new Piece[32];
    	
    	int n = 0;
    	Thread[] t = new Thread[64];
        String tempLabelContents = null;
        LabelListener[] Listener = new LabelListener[64];
        
    	this.setSize(600,600);
    	this.setLocation(200,100);
    	JPanel gamePanel = new JPanel();
    	GridLayout layout = new GridLayout(8,8);
    	gamePanel.setLayout(layout);
         
         for(int x = 0; x<8; x++) {
             for(int y = 0; y<8; y++) {
            	 
            	 switch(x) {
				 	case 0:								// Black home row
				 		switch (y) {						//TODO: Refactor into a getter method
					 		case 0: case 7: 
					 			tempLabelContents = "\u265C"; break;	// Rook		
					 		case 1: case 6: 
					 			tempLabelContents = "\u265E"; break;	// Knight
					 		case 2: case 5: 
					 			tempLabelContents = "\u265D"; break;	// Bishop
					 		case 3: 
					 			tempLabelContents = "\u265B"; break;	// Queen
					 		case 4: 
					 			tempLabelContents = "\u265A"; break;	// King
				 		}
				 		BlackPieces.add(tempPiece[n] = new Pawn(x,y,tempLabelContents, "black")); // Replace with specific pieces
				 		break;
				 	case 1: 
				 		tempLabelContents="\u265F";		// Black Pawns
				 		BlackPieces.add(tempPiece[n] = new Pawn(x,y,tempLabelContents, "black"));
				 		break;
				 	case 6:
				 		tempLabelContents="\u2657"; 	// White Pawns
				 		WhitePieces.add(tempPiece[n] = new Pawn(x,y,tempLabelContents, "white"));
				 		break;
				 	case 7: 							// White home row
				 		switch (y) {						//TODO: Refactor into a getter method
					 		case 0: case 7: 
					 			tempLabelContents = "\u2656"; break;	// Rook		
					 		case 1: case 6: 
					 			tempLabelContents = "\u2658"; break;	// Knight
					 		case 2: case 5: 
					 			tempLabelContents = "\u2657"; break;	// Bishop
					 		case 3: 
					 			tempLabelContents = "\u2655"; break;	// Queen
					 		case 4: 
					 			tempLabelContents = "\u2654"; break;	// King
				 		}
						 WhitePieces.add(tempPiece[n] = new Pawn(x,y,tempLabelContents, "white")); // Replace with specific pieces
				 		break;
				 	case 2: case 3: case 4: case 5:
				 		tempLabelContents="_";
				 		break;
                 }
				 GridCells[x][y] = new GridLabel(tempLabelContents, SwingConstants.CENTER, x, y);

				 Listener[x] = new LabelListener();
				 GridCells[x][y].addMouseListener(Listener[x]);
				 
				 t[x] = new Thread(Listener[x]);
			     t[x].start();
			     
				 if (tempLabelContents != "_") { 
					 GridCells[x][y].associatePiece(tempPiece[n]);
					 n++;
				 }
				 
                 gamePanel.add(GridCells[x][y]);
             }
         }

         Container ct = this.getContentPane();
         ct.add(gamePanel);
         this.setVisible(true);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Constructor called when loading a game from a PGN file
     */
    public GameWindow(String[] gameData) {}
    
    /**
     * Cycles through the current moves that a piece can make and lights up their grid points
     * Generally called when a piece is clicked to display where it can go. 
     */
    public static void displayMoves() {
    	for(GridLabel i : potentialMoves) {
			i.setBackground(Color.GRAY);
		}
    }
    /**
     * Undoes what displayMoves() does - cycles through the potential moves and resets them back their default colors.
     * Also empties the list of potential moves - this is generally called when deselecting a piece, or after a piece
     * has already moved.
     */
    public static void resetSquares() {
    	for(GridLabel i : potentialMoves) {
			i.setBackground(Color.LIGHT_GRAY);
		}
    	potentialMoves.clear();
    }
}

