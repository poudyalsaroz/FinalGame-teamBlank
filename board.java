package edu.coo.cs.cs141.final_prog_assignment;

public class board {
	final int horizontal = 9;
	final int vertical = 9;
	String[][] Board = new String [horizontal][vertical];
	private int depth;
	
	board() {
		createBoard();
	}
	
	public void createBoard() {
		
		for(int x=0;x<horizontal;x++) {
			for(int y=0;y<vertical;y++) {
				if((y==2 || y==4 || y==6) && (x==2 || x==4 || x==6)) {
					Board[x][y] = "?";
				}
				else 
					Board[x][y] = "O";
			}
		}
	}
	
	public void overWrite(int x, int y) {
		createBoard();
		Board[x][y] = "P";
	}
	//Displays the 9/9 board.
	public void displayBoard() {
		for(int x=0;x<horizontal;x++) {
			System.out.println("");
			for(int y=0;y<vertical;y++) {
				System.out.print(Board[x][y] + " ");
			}
		}
	System.out.println("\n");		
	}
}
