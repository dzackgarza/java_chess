package dzack.Chess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main entry point for the program.
 * Run the program with no arguments to get a default board
 * Pass the path to a PGN file as the first argument to load previous games.
 *
 */
public class ChessProgram {
	static GameWindow g;
	static chessFileHandler h;
	
	
	public static void main(String[] args) {
		if (args.length == 0) {
			g = new GameWindow();
		}
		else {
			h = new chessFileHandler();	
			System.out.println(h.readFile(args[0]));
			//g = new GameWindow(inputFile);
		}
		
	}
	void initPieces() {/* Sets up an initial game board*/}
	// Build array of pieces for white and black
	// Create GameWindow
	// Place them in initial spots
	void movePiece() {}
	void writeGameToFile() {}
	void readGameFromFile() {}
}

class chessFileHandler{ /* Handles PGM input/output */
	static FileInputStream inStream = null;
	static StringBuilder fileContents;
	
	public chessFileHandler() {}
		
	public StringBuilder readFile(String inFile) {
		String inputFile = inFile;
		System.out.println(inputFile);
		StringBuilder fileContents = new StringBuilder();

		try {
			inStream = new FileInputStream(inputFile);
			Scanner scanner = new Scanner(inStream, "UTF-8");
			try {
				while (scanner.hasNextLine()){
					fileContents.append(scanner.nextLine() + '\n');
				}
			}
			finally{
				scanner.close();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("File not found.");
		}	
		return fileContents;
	}
}


//abstract class Piece { /* Each piece should be an object that extends this. Maybe use an interface instead?*/  
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
