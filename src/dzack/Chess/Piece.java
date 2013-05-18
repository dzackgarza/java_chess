package dzack.Chess;

/**
 * An abstract Piece that has the properties common to all pieces.
 * Individual types of pieces should subclass this, overriding methods to customize
 */
public class Piece {
	GridLabel currentPosition;
	public String pieceID = "";
	int currentx, currenty;
	int xrange;
	int yrange;
	boolean firstMove;

	/**
	 * Constructor for Piece objects. Use super from subclassed Pieces.
	 * Each piece must be created with an initial (x,y) coordinate and be given 
	 * a unicode ID that displays their image properly.
	 * @param x
	 * 		X location of the piece.
	 * @param y
	 * 		Y location of the piece.
	 * @param ID
	 * 		Unicode representation.
	 */
	public Piece(int x, int y, String ID) {
		currentx = x; currenty = y; this.pieceID = ID;
	}
	
	/**
	 * @return
	 * 		Returns the unicode representation of this piece.
	 */
	public String getID() {
		return pieceID;
	}
	
	/**
	 * Each piece should have a method that works with its own particular set of moves.
	 * This should generally involve populating the Window's potential move list and updating it accordingly.
	 * Call this method via super first to clear the previous moves list.
	 */
	public void getPossibleMoves() {
		GameWindow.potentialMoves.clear();
	}
	/**
	 * A polymorphic method for a variety of special functions each piece might need. (eg, pawns 
	 * can move twice on the first turn, castling, upgrading a pawn to a queen, etc)
	 */
	public void update() {}
}

class Pawn extends Piece {
	int color;			 // 0 - Black		1 - White
	
	/**
	 * Constructor for new Pawn objects
	 * @param x
	 * 		X coordinate (location)
	 * @param y
	 * 		Y coordinate (location)
	 * @param ID
	 * 		Unicode representation
	 * @param color
	 * 		Which color it is ("black" or "white")
	 */
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
	
	/**
	 * Adds all of this particular Pawn's potential moves to the Window's potential move list.
	 * A few mathy things are done here to keep things generic, regardless of whether a pawn 
	 * is on it's first move or not, whether it's a black or white pawn, etc.
	 * 
	 * Pawns generally move "forward", buy for black pieces this represents an increasing
	 * x-value, while for white pieces it is a decreasing x-value.
	 * 
	 * Each piece is given an integer representation of its "range" in either direction. (in both dimensions)
	 * A for loop then iterates through this range, filling in valid spaces.
	 * For black pieces, this would mean adding respectively larger numbers
	 * For white pieces, this involves subtracting in a similar manner.
	 * 
	 * To solve this problem, the value of (-1^color) is used frequently to return postive number for 
	 * black pieces (-1^0 = 1) and negative numbers for white pieces (-1^1 = -1)
	 * 
	 */
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
	
	/**
	 * Called after the first move is completed to reflect the fact that pawns can move two
	 * spaces on their first turn.
	 */
	public void update() {
		firstMove = false;
		if (color == 1) xrange = -1;
		if (color == 0) xrange = 1;
	}
}