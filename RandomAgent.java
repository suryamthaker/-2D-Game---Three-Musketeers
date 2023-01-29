package lab2;

import java.util.ArrayList;
import java.util.List;

public class RandomAgent extends Agent {

    public RandomAgent(Board board) {
        super(board);
    }

    /**
     * Gets a valid random move the RandomAgent can do.
     * @return a valid Move that the RandomAgent can perform on the Board
     */
    @Override
    public Move getMove() { // TODO
    	
    	List<Cell> possibleCells = new ArrayList<Cell>();
    	possibleCells = board.getPossibleCells();
    	List<Cell> possibleDestination = new ArrayList<Cell>();
    	
     	
    	
    	for(int i = 0; i < possibleCells.size(); i++) {
    		possibleDestination = board.getPossibleDestinations(possibleCells.get(i));
    	    for(int j = 0; j < possibleDestination.size(); j++) {
    	    	Move move = new Move(possibleCells.get(i), possibleDestination.get(j));
    	    	if (board.isValidMove(move)) {
    	    	
    	    	
    	    		char col1 = Utils.convertIntToLetter(possibleCells.get(i).getCoordinate().col+1);
    	    		String from = (String.format("%c%d", col1, possibleCells.get(i).getCoordinate().row+1));
    	    		char col2 = Utils.convertIntToLetter(possibleDestination.get(j).getCoordinate().col+1);
    	    		String to = (String.format("%c%d", col2, possibleDestination.get(j).getCoordinate().row+1));
    	    	
    	    		System.out.println("[" + board.getTurn().getType() + " (Random Agent)] Moving piece " + from + " to " + to +".");
    	    		return move;
    	    	}
    	    }
    	}
    	
    	
        return null;
    }
}
