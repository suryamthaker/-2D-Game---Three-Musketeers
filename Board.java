package lab2;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Board {
    public int size = 5;

    // 2D Array of Cells for representation of the game board
    public final Cell[][] board = new Cell[size][size];

    private Piece.Type turn;
    private Piece.Type winner;

    /**
     * Create a Board with the current player turn set.
     */
    public Board() {
        this.loadBoard("Boards/Starter.txt");
    }

    /**
     * Create a Board with the current player turn set and a specified board.
     * @param boardFilePath The path to the board file to import (e.g. "Boards/Starter.txt")
     */
    public Board(String boardFilePath) {
        this.loadBoard(boardFilePath);
    }

    /**
     * Creates a Board copy of the given board.
     * @param board Board to copy
     */
    public Board(Board board) { 
        this.size = board.size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = new Cell(board.board[row][col]);
            }
        }
        this.turn = board.turn;
        this.winner = board.winner;
    }

    /**
     * @return the Piece.Type (Muskeeteer or Guard) of the current turn
     */
    public Piece.Type getTurn() {
        return turn;
    }

    /**
     * Get the cell located on the board at the given coordinate.
     * @param coordinate Coordinate to find the cell
     * @return Cell that is located at the given coordinate
     */
    public Cell getCell(Coordinate coordinate) { // TODO
    	return board[coordinate.row][coordinate.col];
      
    }

    /**
     * @return the game winner Piece.Type (Muskeeteer or Guard) if there is one otherwise null
     */
    public Piece.Type getWinner() {
        return winner;
    }

    /**
     * Gets all the Musketeer cells on the board.
     * @return List of cells
     */
    public List<Cell> getMusketeerCells() { // TODO  // Loop through the board and get all the  Musketeer cells
    	List<Cell> list = new ArrayList<Cell>();
    	
       for(int i = 0; i < 5;i++) {
    	   for(int j = 0; j < 5;j++) {
    		   if(board[i][j].hasPiece() == true && board[i][j].getPiece().getType() == Piece.Type.MUSKETEER) {
    			   list.add(board[i][j]);
    		   }
    	   }
       }
        return list;
    }

    /**
     * Gets all the Guard cells on the board.
     * @return List of cells
     */
    public List<Cell> getGuardCells() { // TODO  // Loop through the board and get all the  Guard cells
    	List<Cell> list = new ArrayList<Cell>();
    	for(int i = 0; i < 5;i++) {
     	   for(int j = 0; j < 5;j++) {
     		   if(board[i][j].hasPiece() == true && board[i][j].getPiece().getType() == Piece.Type.GUARD) {
     			   list.add(board[i][j]);
     		   }
     	   }
    	}
        return list;
    }

    /**
     * Executes the given move on the board and changes turns at the end of the method.
     * @param move a valid move
     */
    public void move(Move move) { // TODO 
    	
    	if (getTurn() == Piece.Type.MUSKETEER) {                   //If it's a Musketeer's turn do move the musketeer 
    		 Coordinate fromCell = move.fromCell.getCoordinate();
    	     Coordinate toCell = move.toCell.getCoordinate();
    	     Move moves = new Move(move.fromCell, move.toCell);
    	     if(isValidMove(moves)) {
    	    	 getCell(toCell).setPiece(move.fromCell.getPiece());
        	     getCell(fromCell).removePiece();
            	 turn = Piece.Type.GUARD;
    	     }
    	     
    	     
        }
    	
       
         
    	else if (getTurn() == Piece.Type.GUARD) {                 //If it's a Guard's turn do move the guard
        	
        	 Coordinate fromCell = move.fromCell.getCoordinate();
             Coordinate toCell = move.toCell.getCoordinate();
             Move moves = new Move(move.fromCell, move.toCell);
             if(isValidMove(moves)) {
            	 getCell(toCell).setPiece(move.fromCell.getPiece());
                 getCell(fromCell).removePiece();
            	turn = Piece.Type.MUSKETEER;
             }
             
        }
        
    }

    /**
     * Undo the move given.
     * @param move Copy of a move that was done and needs to be undone. The move copy has the correct piece info in the
     *             from and to cell fields. Changes turns at the end of the method.
     */
    public void undoMove(Move move) { // TODO 
         
         if(getTurn() == Piece.Type.GUARD) {    //If undo is called by guard undo previous musketeer turn
        	 
        	 Piece guard = new Guard();
        	 Piece musk = new Musketeer();
        	 move.fromCell.setPiece(musk);
        	 move.toCell.setPiece(guard);
        	 System.out.println("Undid the previous move.");
         	 turn = Piece.Type.MUSKETEER;
        	 
            	
         }
         
         else  {                                 //If undo is called by musketeer undo previous guard
        	 
        	 Piece guard = new Guard();
         	
         	move.fromCell.setPiece(guard);
         	move.toCell.removePiece();
         	turn = Piece.Type.GUARD;
         }
         
        }

    /**
     * Checks if the given move is valid. Things to check:
     * (1) the toCell is next to the fromCell
     * (2) the fromCell piece can move onto the toCell piece.
     * @param move a move
     * @return     True, if the move is valid, false otherwise
     */
    public Boolean isValidMove(Move move) { // TODO
    	
 
      
      if(!move.fromCell.hasPiece()) {
    	  return false;
      }
      
      if(getTurn() == Piece.Type.MUSKETEER) {
    	  if(move.fromCell.getPiece().getType() != Piece.Type.MUSKETEER || !move.toCell.hasPiece()) {
    		  return false;
    	  }
    	  if(move.toCell.getPiece().getType() == Piece.Type.GUARD && isNext(move)) {
    		  return true;
    	  }
      }
      
      else if(getTurn() == Piece.Type.GUARD) {
    	  if(move.fromCell.getPiece().getType() != Piece.Type.GUARD) {
    		  return false;
    	  }
    	  if (!move.toCell.hasPiece() && isNext(move)) {
    		  return true;
    	  }
    	  
    	  if(isNext(move) == false) {
    		  return false;
    	  }
      }
    
		return false;
    	
    }
    
    public Boolean isNext(Move move) { //Helper method I created to help me check if a toCell is  fromCell's neighbor
    	
    	if (move.toCell.getCoordinate().row == move.fromCell.getCoordinate().row-1 && move.toCell.getCoordinate().col == move.fromCell.getCoordinate().col) {
    		return true;
    	}
    	
    	else if (move.toCell.getCoordinate().row == move.fromCell.getCoordinate().row+1 && move.toCell.getCoordinate().col == move.fromCell.getCoordinate().col) {
    		return true;
    	}
    	
    	else if (move.toCell.getCoordinate().row == move.fromCell.getCoordinate().row && move.toCell.getCoordinate().col == move.fromCell.getCoordinate().col-1) {
    		return true;
    	}
    	else if (move.toCell.getCoordinate().row == move.fromCell.getCoordinate().row && move.toCell.getCoordinate().col == move.fromCell.getCoordinate().col+1) {
    		return true;
    	}
    	
		return false;
    	
    }

    /**
     * Get all the possible cells that have pieces that can be moved this turn.
     * @return      Cells that can be moved from the given cells
     */
    public List<Cell> getPossibleCells() { // TODO
    	List<Cell> cells = new ArrayList<Cell>();
    	List<Coordinate> coordinates = new ArrayList<Coordinate>();
    	
    	
    	for(int i = 0; i < 5; i++) {
    		for(int j = 0; j < 5; j++) {
    			if (board[i][j].getCoordinate().row-1 >= 0) {
    			Coordinate coord = new Coordinate(board[i][j].getCoordinate().row-1, board[i][j].getCoordinate().col);
    			coordinates.add(coord);
    			}
    			
                if (board[i][j].getCoordinate().row+1 < 5) {
                	Coordinate coord = new Coordinate(board[i][j].getCoordinate().row+1, board[i][j].getCoordinate().col);
        			coordinates.add(coord);
    			}
                if (board[i][j].getCoordinate().col-1 >= 0) {
                	Coordinate coord = new Coordinate(board[i][j].getCoordinate().row, board[i][j].getCoordinate().col-1);
        			coordinates.add(coord);
    			}
                
                if (board[i][j].getCoordinate().col+1 < 5) {
                	Coordinate coord = new Coordinate(board[i][j].getCoordinate().row, board[i][j].getCoordinate().col+1);
        			coordinates.add(coord);
    			}
                
                for(int i1 = 0; i1 < coordinates.size(); i1++) {
            		Cell cell = getCell(coordinates.get(i1));
            		Move move = new Move(board[i][j], cell);
            		if (isValidMove(move)) {
            			cells.add(board[i][j]);
            			break;
            		}
            		
            	}
                
    		}
    	}
    	
    	
        return cells;
    }

    /**
     * Get all the possible cell destinations that is possible to move to from the fromCell.
     * @param fromCell The cell that has the piece that is going to be moved
     * @return List of cells that are possible to get to
     */
    public List<Cell> getPossibleDestinations(Cell fromCell) { // TODO
         List<Cell> destinations = new ArrayList<Cell>();
         List<Coordinate> coordinates = new ArrayList<Coordinate>();
        
         
         for(int i = 0; i < 5; i++) {
        	 for(int j = 0; j < 5;j++) {
        		 
        		
        			 if (board[i][j].getCoordinate().row == fromCell.getCoordinate().row-1 && board[i][j].getCoordinate().col == fromCell.getCoordinate().col) {
        		     Move move = new Move(fromCell, board[i][j]);
        		     if (isValidMove(move) == true) {
        		    	 destinations.add(board[i][j]);
        		     }
             		
             		}
        		 
        			 else if (board[i][j].getCoordinate().row == fromCell.getCoordinate().row+1 && board[i][j].getCoordinate().col == fromCell.getCoordinate().col) {
        				 Move move = new Move(fromCell, board[i][j]);
            		     if (isValidMove(move) == true) {
            		    	 destinations.add(board[i][j]);
            		     }
             			}
        		 
        			 else if (board[i][j].getCoordinate().row == fromCell.getCoordinate().row && board[i][j].getCoordinate().col == fromCell.getCoordinate().col-1) {
        				 Move move = new Move(fromCell, board[i][j]);
            		     if (isValidMove(move) == true) {
            		    	 destinations.add(board[i][j]);
            		     }
             		}
        		 
        			 else if (board[i][j].getCoordinate().row == fromCell.getCoordinate().row && board[i][j].getCoordinate().col == fromCell.getCoordinate().col+1) {
        				 Move move = new Move(fromCell, board[i][j]);
            		     if (isValidMove(move) == true) {
            		    	 destinations.add(board[i][j]);
            		     }
             		}
        		 
        		 }
        	 }
         
       
         
        return destinations;
    }
    

    /**
     * Get all the possible moves that can be made this turn.
     * @return List of moves that can be made this turn
     */
    public List<Move> getPossibleMoves() { // TODO
    	List<Cell> possibleCells = getPossibleCells();
    	List<Coordinate> coordinates = new ArrayList<Coordinate>();
    	List<Move> moves = new ArrayList<Move>();
        
      
    	for(Cell i: possibleCells) {
    		 if (i.getCoordinate().row-1 >= 0) {
    			 Coordinate up = new Coordinate(i.getCoordinate().row - 1,i.getCoordinate().col);
    			 coordinates.add(up);
    		 }
    		 if(i.getCoordinate().row+1<5) {
    			 Coordinate down = new Coordinate(i.getCoordinate().row + 1,i.getCoordinate().col);
    			 coordinates.add(down);
    		 }
    		 if(i.getCoordinate().col-1>=0) {
    			 Coordinate left = new Coordinate(i.getCoordinate().row,i.getCoordinate().col-1);
    			 coordinates.add(left);
    		 }
    		if  (i.getCoordinate().col+1<5) {
    			Coordinate right = new Coordinate(i.getCoordinate().row,i.getCoordinate().col+1);
   			    coordinates.add(right);
    		}
    		
    		for(Coordinate j: coordinates) {
    			Cell cell = getCell(j);
    			Move move = new Move(i, cell);
    			if (isValidMove(move)) {
    				moves.add(move);
    			}
    			
    	}
    	}
    	
  
    	
        return moves;
    }

    /**
     * Checks if the game is over and sets the winner if there is one.
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOver() { // TODO
    	  List<Cell> musk = new ArrayList<Cell>();
    	  musk = getMusketeerCells();
    	  List<Cell> possibleCells = new ArrayList<Cell>();
    	  possibleCells = getPossibleCells();
    	  
    	  
    	  
    	 if(musk.get(0).getCoordinate().row == musk.get(1).getCoordinate().row && musk.get(0).getCoordinate().row == musk.get(2).getCoordinate().row) {
    		 winner = Piece.Type.GUARD;
    		 return true;
    	 }
    	 
    	 if(musk.get(0).getCoordinate().col == musk.get(1).getCoordinate().col && musk.get(0).getCoordinate().col == musk.get(2).getCoordinate().col) {
    		 winner = Piece.Type.GUARD;
    		 return true;
    	 }
    	 
    	 
    	 
    		 if(possibleCells.size() == 0) {
    			 winner = Piece.Type.MUSKETEER;
    			 return true;
    		 }
    	 
    	 
    	
    	 return false;
        	
         	
        	
       
    }

    /**
     * Saves the current board state to the boards directory
     */
    public void saveBoard() {
        String filePath = String.format("boards/%s.txt",
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        File file = new File(filePath);

        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row: board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell: row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", filePath);
        }
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("  | A B C D E\n");
        boardStr.append("--+----------\n");
        for (int i = 0; i < size; i++) {
            boardStr.append(i + 1).append(" | ");
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                boardStr.append(cell).append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    /**
     * Loads a board file from a file path.
     * @param filePath The path to the board file to load (e.g. "Boards/Starter.txt")
     */
    private void loadBoard(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.printf("File at %s not found.", filePath);
            System.exit(1);
        }

        turn = Piece.Type.valueOf(scanner.nextLine().toUpperCase());

        int row = 0, col = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.trim().split(" ");
            for (String piece: pieces) {
                Cell cell = new Cell(new Coordinate(row, col));
                switch (piece) {
                    case "O" -> cell.setPiece(new Guard());
                    case "X" -> cell.setPiece(new Musketeer());
                    default -> cell.setPiece(null);
                }
                this.board[row][col] = cell;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        scanner.close();
        System.out.printf("Loaded board from %s.\n", filePath);
    }
}
