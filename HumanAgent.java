package lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jdk.jshell.execution.Util;
import lab2.Exceptions.InvalidMoveException;

public class HumanAgent extends Agent {

    public HumanAgent(Board board) {
        super(board);
    }

    /**
     * Asks the human for a move with from and to coordinates and makes sure its valid.
     * Create a Move object with the chosen fromCell and toCell
     * @return the valid human inputted Move
     */
    @Override
    public Move getMove() { // TODO
    	
			try { 
				
				Scanner in = new Scanner(System.in);
				
		    	List<Cell> pieces = board.getPossibleCells();
		    	
		        List<String> list = new ArrayList<String>();
		        List<String> list1 = new ArrayList<String>();
		        Move move;
		        char col;
		        for (int i =0; i < pieces.size(); i++) {
		        	col = Utils.convertIntToLetter(pieces.get(i).getCoordinate().col+1);
		            list.add(String.format("%c%d", col, pieces.get(i).getCoordinate().row+1));
		        }
		        String input1;
		        
		        	System.out.print("["+board.getTurn()+"] ");
		            
		            System.out.print("Possible Pieces are ");
		            System.out.print(list.toString());
		            System.out.print(". Enter the piece you want to move: ");
		            input1 = in.nextLine();
		            
		            while(list.contains(input1.toUpperCase()) == false) {
		            	System.out.println(input1 + " is an invalid piece.");
		            	
		            	System.out.print("["+board.getTurn()+"] ");
			            
			            System.out.print("Possible Pieces are ");
			            System.out.print(list.toString());
			            System.out.print(". Enter the piece you want to move: ");
			            input1 = in.nextLine();
			            
		            }
		            Cell fromCell = board.getCell(Utils.parseUserMove(input1.toUpperCase()));
				   
				    
				    List<Cell> destinationsCell = board.getPossibleDestinations(fromCell);
					for(int i = 0; i < destinationsCell.size(); i++) {
						col = Utils.convertIntToLetter(destinationsCell.get(i).getCoordinate().col+1);
						list1.add(String.format("%c%d", col, destinationsCell.get(i).getCoordinate().row+1));
						
		    }
					System.out.print("["+board.getTurn()+"] ");
					System.out.print("Possible Destinations are ");
					System.out.print(list1.toString());
					System.out.print(". Enter where you want to move: ");
					input1 = in.nextLine();
					
					while(list1.contains(input1.toUpperCase()) == false) {
						System.out.println(input1 + " is an invalid destination.");
						System.out.print("["+board.getTurn()+"]› ");
						System.out.print("Possible Destinations are ");
						System.out.print(list1.toString());
						System.out.print(". Enter where you want to move: ");
						input1 = in.nextLine();
						
					}
					
					Cell toCell = board.getCell(Utils.parseUserMove(input1.toUpperCase()));
					move = new Move(fromCell, toCell);
					return move;
			}
				    
			 catch (InvalidMoveException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			
			
           
           
			return null;

    
    
}
    
}
    
    	
    			

