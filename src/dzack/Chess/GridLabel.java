package dzack.Chess;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

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



