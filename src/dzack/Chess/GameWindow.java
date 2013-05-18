package dzack.Chess;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	
    static GridLabel[][] GridCells;
    static ArrayList <Piece> BlackPieces;
    static ArrayList <Piece> WhitePieces;
    static ArrayList<GridLabel> potentialMoves;
    static Piece[] tempPiece;
    
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
    public static void displayMoves() {
    	for(GridLabel i : potentialMoves) {
			i.setBackground(Color.GRAY);
		}
    }
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
				destination.associatePiece(g.piece);
				g.dissociatePiece();
			}
			
			else {
				currentlyClickedLabel.setBackground(Color.LIGHT_GRAY);
				GameWindow.resetSquares();
				currentlyClickedLabel = null;
			}
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}
		
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void run() {
		//System.out.println("New thread created.");
	}
}

@SuppressWarnings("serial")
class GridLabel extends JLabel {
	public int x, y;
	public boolean accesibleRightNow = false;
	Piece piece;
	public boolean clicked = false;
	
	public GridLabel(String text, int horizontalAlignment, int x_loc, int y_loc) {
		super(text,horizontalAlignment);
		x=x_loc;
		y=y_loc;
		setOpaque(true);
		setBackground(Color.LIGHT_GRAY);
		setFont(new Font("Serif", Font.PLAIN, 45));
	}
	public void associatePiece(Piece p) {
		GameWindow.GridCells[x][y].piece = p;
		GameWindow.GridCells[x][y].setText(p.getID());
	}
	public void dissociatePiece() {
		this.piece = null;
		this.setText("_");
	}
	public boolean isClicked() {
		return clicked;
	}
	public void onClick() {
		this.clicked = !(this.clicked);
	}
	public boolean isOccupiedByOpponent() {
		return false;
	}
}

class Piece {
	GridLabel currentPosition;
	String pieceID = "";
	int currentx, currenty;
	int xrange;
	int yrange;
	boolean firstMove;

	public Piece(int x, int y, String ID) {
		currentx = x; currenty = y; pieceID = ID;
	}
	public String getID() {
		return pieceID;
	}
	public void getPossibleMoves() {
		// TODO: Piece types should subclass piece, ie Knight extends Piece, and override this method
		GameWindow.potentialMoves.clear();
	}
	public void update() {}
}
class Pawn extends Piece {
	int color; // 0 - Black		1 - White
	
	public Pawn(int x, int y, String ID, String color) {
		super(x,y,ID);
		if (color == "white") {
			xrange = -2;
			yrange = 1;
			this.color = 1;
		}
		if (color == "black") {
			xrange = 2;
			yrange = 1;
			this.color = 0;
		}
		firstMove = true;
	}
	
	public void getPossibleMoves() { 
			super.getPossibleMoves();
			for (int i = 0; i < Math.abs(xrange); i++) {
				if (currentx + xrange - ((int) Math.pow(-1,color)*i) >= 0) { 
					GameWindow.potentialMoves.add(GameWindow.GridCells[currentx + xrange - ((int) Math.pow(-1,color)*i) ][currenty]);
				}
			}
			if ( currentx - 1 >= 0 && currenty - 1 >= 0 &&
					GameWindow.GridCells[currentx + ((int) Math.pow(-1,color)) ][currenty - 1].isOccupiedByOpponent()) {	// Diagonol left space occupied
				GameWindow.potentialMoves.add(GameWindow.GridCells[currentx + ((int) Math.pow(-1,color)) ][currenty  - 1]);
			}
			if (currentx - 1 >= 0 && currenty + 1 <= 7 &&
					GameWindow.GridCells[currentx + ((int) Math.pow(-1,color)) ][currenty + 1].isOccupiedByOpponent()) { 	//Diagonol right space occupied
				GameWindow.potentialMoves.add(GameWindow.GridCells[currentx + ((int) Math.pow(-1,color)) ][currenty + 1]);
			}
			
	}
	public void update() {
		firstMove = false;
		if (color == 1) xrange = -1;
		if (color == 0) xrange = 1;
	}
}