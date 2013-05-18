package dzack.Chess;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
    public GameWindow(String inputFile) {}
    
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


class LabelListener implements MouseListener, Runnable {

	static GridLabel currentlyClickedLabel = null;
	static GridLabel destination = null;
	
	@Override
	// #TODO: Bug when clicking, sometimes requires a double click?
	public void mouseClicked(MouseEvent e) {
		GridLabel g = (GridLabel) e.getSource();
		if(currentlyClickedLabel != null && g == currentlyClickedLabel) // Clicked the same button, deselect
		{
			g.setBackground(Color.LIGHT_GRAY);
			GameWindow.resetSquares();
			currentlyClickedLabel = null;
			destination = null;
		}
		else if(currentlyClickedLabel != null && g != currentlyClickedLabel) // Clicked a different button, deselect and reselect new
		{
			for (GridLabel i: GameWindow.potentialMoves) {
				if (g == i) {
					destination = i;
				}
			}
			
			if (destination != null) { //Inside range
				destination.associatePiece(currentlyClickedLabel.piece);
				currentlyClickedLabel.dissociatePiece();
			}
			
			
				currentlyClickedLabel.setBackground(Color.LIGHT_GRAY);
				GameWindow.resetSquares();
				currentlyClickedLabel = null;
			
		}
		else if(currentlyClickedLabel == null) // Nothing has been clicked
		{
			g.setBackground(Color.BLACK);
			if(g.piece != null) 
			{
				g.piece.getPossibleMoves();
				GameWindow.displayMoves();
			}
		currentlyClickedLabel = g;
			
		}
		System.out.println("Mouse Pressed over grid point +" +
				g.x + ", " + g.y);
		//if(g.piece != null) System.out.println("Associated piece here: " + g.piece.getID());
	}

	@Override
	public void mouseEntered(MouseEvent e) {		}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {}
		
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void run() {
		//System.out.println("New thread created.");
	}
}

/**
 * Each grid point on the chess board is abstracted as a Grid Label, which can fill a grid array. Each grid space has a
 * set of (x,y) coordinates, and may have a piece associated with it to denote that the space is occupied.
 *
 */
@SuppressWarnings("serial")
class GridLabel extends JLabel {
	public int x, y;
	Piece piece;
	public boolean accesibleRightNow = false;
	public boolean clicked = false;				//deprecated?
	
	/**
	 * General constructor for custom GridLabel objects. 
	 * Each grid location can have a piece associated with it - if it is null, the space is empty.
	 * 
	 * @param text
	 * 		What will this grade space display? (Generally accepts a chess piece in unicode or a marker for empty spaces)
	 * @param horizontalAlignment
	 * 		How will the text inside of this label be aligned? (Required by superclass)
	 * @param x_loc
	 * 		Within the grid, what is its x displacement? (Remember, Gridview numbers x vertically and y horizontally)
	 * @param y_loc
	 * 		Within the grid, what is its y displacement? 
	 */
	public GridLabel(String text, int horizontalAlignment, int x_loc, int y_loc) {
		super(text,horizontalAlignment);
		x=x_loc;
		y=y_loc;
		setOpaque(true);
		setBackground(Color.LIGHT_GRAY);
		setFont(new Font("Serif", Font.PLAIN, 45));
	}
	
	/**
	 * Pass this function a Piece object in order to link the grid location to that particular piece.
	 * Updates the label text accordingly.
	 * @param p
	 * 		Piece to associate.
	 */
	public void associatePiece(Piece p) {
		GameWindow.GridCells[x][y].piece = p;
		GameWindow.GridCells[x][y].setText(p.getID());
	}
	
	/**
	 * Call this function to delete the link between this grid location and whatever piece it was previously
	 * associated with.
	 */
	public void dissociatePiece() {
		this.piece = null;
		this.setText("_");
	}
	
	/**
	 * Returns whether this grid location has already been clicked. This should correspond to when it is lit up,
	 * and its results can be used to tell if a piece is the current piece selected. 
	 */
	public boolean isClicked() {
		return clicked;
	}
	
	/*
	 * Everytime you click a label, this should be called as a toggle.
	 */
	public void onClick() {
		this.clicked = !(this.clicked);
	}
	
	/**
	 * Placeholder for a future function. Determines special moves in some cases (e.g., pawns).
	 * 
	 */
	public boolean isOccupiedByOpponent() {
		return false;
	}
}


