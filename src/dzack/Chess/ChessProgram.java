package dzack.Chess;


/**
 * Main entry point for the program.
 * Run the program with no arguments to get a default board
 * Pass the path to a PGN file as the first argument to load previous games.
 *
 */
public class ChessProgram {
	static GameWindow g;
	static ChessFileHandler h;
	
	
	public static void main(String[] args) {
		if (args.length == 0) {
			g = new GameWindow();
		}
		else {
			h = new ChessFileHandler();	
			String[] contents = h.readFile(args[0]);
			for (String i : contents) {
				System.out.println(i + '\n');
			}	
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



