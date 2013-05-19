package dzack.Chess;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

