package edu.cpp.cs.cs141.final_prog_assignment;

import java.util.Scanner;

public class UserInterface {
	private enum status{WON, LOST, CONTINUE};
	private GameEngine game;
	private Scanner scan = null;
	private status gameStatus = status.CONTINUE;
	
	public UserInterface(GameEngine game) {
		this.game = game;
		scan = new Scanner(System.in);	
	}
	
	/**
	 * Displays menu options.  Player provides input.  If player selects (2), then startGame() executes.
	 */
	public void openMenu() {
		boolean redo = true;
		while(redo) {
			mainMenu();
			String input = scan.next();
			switch(input) {
				case "1":
					howToPlay();
					break;
				case "2":
					System.out.println("you chose 2");
					startGame();
					/* playGame(); --implement gameplay in gameEngine 
					 * 			to avoid going back to main menu after display game board
					 * 
					 */
					
					break;
				case "3":
					System.out.println("you chose 3");
					/* - need to implement: saveGame();
					 * 			-- saves current board situation and make available for next use.
					 * 			--(maybe create an outstream function that creates a file with certain name with board situation.)
					 * 
					 * loadGame() - retrieve board from saved data 
					 */
					break;
				case "4":
					System.out.println("you chose 4\n" + "Game will close.\n" + "GOODBYE" ); ///closes/exits game
					System.exit(0);
					break;
				default: //when anything besides 1,2,3,4 is pressed in menu
					System.out.println("Invalid Input");
					break;
			}
		}
	}

	/**
	 * Creates board
	 */
	public void startGame() {
		game.createBoard();
		playGame();
	}
	
	public void playGame(){
		String direction;
		gameStatus = status.CONTINUE;
		while(gameStatus == status.CONTINUE) {
			
			direction = look();
			
			if(game.look(direction) == 0)
				System.out.println("Room Ahead!");
			
			else if(game.look(direction) == 2)
				System.out.println("Ninja Ahead!");
			
			else if(game.look(direction) == 1)
				System.out.println("All Clear!");
			else
				System.out.println("Invalid !");
			
			boolean valid = false;
		
			while(!valid) {
				displayBoard();
				System.out.print("Move(WASD) or shoot(F): ");
				direction = scan.next().toLowerCase();
				
				if(direction.equals("f")) {
					System.out.print("Choose direction to fire: ");
					direction = scan.next();
					//game.shoot(direction);
					valid = true;
				}
				else if(direction.equals("w")|| direction.equals("a") || direction.equals("s") || direction.equals("d")){
					valid = game.move(direction);
					
					
				}
				else {
					System.out.println("Invalid Input!");
				}
				
				game.ninjaMovement();
			
				
				
				if (game.checkPlayerIsBriefcase() == true)
				{
					System.out.println("YOU FOUND THE BRIEFCASE!");
					gameStatus = status.WON;
				}
				else
				{
					gameStatus = status.CONTINUE;
				}
			
				
			}
		}
	}
	
				
			
			  /**if(player.getAlive()){
			  		decLives();
			  }
			  if(player.checkGameOver(){
			  		startGame();
			  }
			  **/
		
		
	

	private String look() {
		boolean valid = false;
		String direction = "";
		while(!valid) {
			displayBoard();
			System.out.print("Choose direction to look(WASD): ");
			direction = scan.next().toLowerCase();
			if(direction.equals("r"))
				game.debugMode();
			if(direction.equals("w")||direction.equals("a")||direction.equals("s")||direction.equals("d"))
				valid = true;
		}
		return direction;
	}

	private void displayBoard() {
		int length = 9;
		for(int x = 0;x < length; x++) {
			System.out.println("");
			for(int y = 0;y < length;y++) {
				System.out.print(game.displayBoard(x, y));
			}
		}
		System.out.println("\n");
	}

	public void mainMenu() {
		System.out.println(	"1) How to Play\n" + 
							"2) Start New Game\n" + 
							"3) Load Game\n" + 
							"4) Exit Game\n");
	}
	
	public void howToPlay() {
		System.out.println(	"1) HOW TO PLAY:\n" + "You are a SPY that is tasked with retrieving a "
				+ "briefcase containing classified enemy documents \nlocated in one of the NINE rooms."
				+ "These rooms can only be opened from the NORTH side."
				+ "\nHowever, there are SIX ninjas patrolling the building.\n"
				+ "You have a choice of taking an action of: \n"
				+ "- Look ahead(once per turn): \n\tPress L to look TWO squares ahead\n"
				+ "- Move one square: \n\tPress W,A,S,D to face, then move up,left,down,right respectively\n"
				+ ""
				+ ""
				//add stuff about items
				+ ""
				+ ""
				+ "- Shoot in one direction(You only get one ammo at start):\n\tPress SPACE to shoot\n"
				+ "Bring back the briefcase ALIVE!\n"
				+ "GOOD LUCK\n");
	}
	
	

	
	
}

