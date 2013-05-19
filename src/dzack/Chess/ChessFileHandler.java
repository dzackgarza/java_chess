package dzack.Chess;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

class ChessFileHandler{ /* Handles PGM input/output */
	static FileInputStream inStream = null;
	static StringBuilder fileContents;
	
	public ChessFileHandler() {}
		
	/**
	 * Reads a PGN file and returns a string array containing delimited elements
	 * @param inFile
	 * 	Path to the PGN file to be used
	 * @return
	 * 	Delimited tokens
	 */
	public String[] readFile(String inFile) {
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
		String delims = "\n";
		return fileContents.toString().split(delims);
		
	}
}