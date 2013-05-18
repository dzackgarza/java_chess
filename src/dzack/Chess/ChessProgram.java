package dzack.Chess;

public class ChessProgram {

	void initPieces() {/* Sets up an initial game board*/}
		// Build array of pieces for white and black
		// Create GameWindow
		// Place them in initial spots
	void movePiece() {}
	void writeGameToFile() {}
	void readGameFromFile() {}
	public static void main(String[] args) {
		GameWindow g = new GameWindow();
		
	}
}
//
//class chessFileHandler{ /* Handles PGM input/output */}
//
//abstract class Piece { /* Each piece should be an object that extends this. Maybe user an interface instead?*/  
//	GameTile currentLocation;
//	GameTile[] potentialMoves;
//	void checkMoves() {/* Check possible moves against open spots */}
//	void updatePotentialMoves() {}
//	void isTaken() { /* Handles removing the piece from the board if it is captured*/}
//}
//
//class Pawn extends Piece {}
//
//class GameTile { /*Holds information about each tile */
//	boolean isOpen;
//	int[][] gridLocation;
//}
