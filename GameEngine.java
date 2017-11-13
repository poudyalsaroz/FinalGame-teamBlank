package edu.cpp.cs.cs141.final_prog_assignment;

import java.util.Random;

public class GameEngine {

	private Random random = new Random();

	private Character player;
	private Board board;
	private Character[] ninjas = new Character[6];
	private Item radar = null;
	private Item invincible = null;
	private Item ammo = null;


	public void createBoard() {
		player = new Character(false,0,8);
		board = new Board();
		board.set(player, 8, 0);
		createPowerUp();	
		createNinjas();
	}
					
	public void createNinjas() {
		int x,y;
		int count = 0;
		
		while( count <= 5 ) {
		
		do {
			x = getRandom();
			y = getRandom();
		}
		while  (x >= 5 && y <= 3);
				
		if(!board.at(x, y).getNinja() 
					&& board.at(x, y).getEmpty() 
					&& !board.at(x, y).getRoom()
					&& !board.at(x, y).getItem()){ 
				
					ninjas[count] = new Ninja(x,y);
					
					board.setNinja(ninjas[count],x,y);
					
					ninjas[count].setX(x);
					ninjas[count].setY(y);
				
					count ++;
			}
		}
	}

	private void createPowerUp() {
		int x, y;
		//Items weren't populated when the player chose to start another new game.  Items are initialized as null to act as a reset.
		radar = null;
		invincible = null;
		ammo = null;
		while(radar == null) {
			x = getRandom();
			y = getRandom();
			if(!board.at(x, y).getItem() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom()) {
				radar = new Item("radar");
				board.set(radar, x, y);
			}
		}
		while(invincible == null) {
			x = getRandom();
			y = getRandom();
			if(!board.at(x, y).getNinja() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom()) {
				invincible = new Item("invincible");
				board.set(invincible, x, y);
			}
		}
		while(ammo == null) {
			x = getRandom();
			y = getRandom();
			if(!board.at(x, y).getNinja() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom()) {
				ammo = new Item("ammo");
				board.set(ammo, x, y);
			}
		}	
	}
	public int getRandom(){
		return random.nextInt(9);
	}
	public String displayBoard(int x, int y){
		return board.displayBoard(x, y);
	}

	public int look(String direction) {
		Square[] s = new Square[3];
		int clear = 1;
		switch(direction.toLowerCase()) {
			case "w":
				for(int i = 1;i < 3; i++) {
					if(player.getX()-i >= 0) {
						s[i] = board.at(player.getX()-i, player.getY());
					}			
				}
				break;
			case "a":
				for(int i = 1;i < 3; i++) {
					if(player.getY()-i >= 0) {
						s[i] = board.at(player.getX(), player.getY()-i);
					}			
				}
				break;
			case "s":
				for(int i = 1;i < 3; i++) {
					if(player.getX()+i <= 8) {
						s[i] = board.at(player.getX()+i, player.getY());
					}			
				}
				break;

			case "d":
				for(int i = 1;i < 3; i++) {
					if(player.getY()+i <= 8) {
						s[i] = board.at(player.getX(), player.getY()+i);
					}			
				}
				break;
			default:
				System.out.print("Error: GameEngine-look-switch");
		}	
		for(int i = 1; i < 3; i++) {
				if(s[i].getRoom()) {
					clear = 0;
					break;
				}
				else if(s[i].getNinja()) {
					s[i].reveal();
					clear = 2;
					break;
				}
				else {
					s[i].reveal();
					clear = 1;
				}
			}
		return clear;
	}

	public boolean move(String direction) {
		boolean moved = false;

		switch(direction){
			case"w":
				if((player.getX() == 0) ||
				(player.getX() == 1+1 ||
				player.getX() == 4+1 ||
				player.getX() == 7+1) &&
				(player.getY() == 1 ||
				player.getY() == 4 ||
				player.getY() == 7))
				{
					System.out.println("WALL!");
					moved = false;

				}

				else
				{

					board.set(player, player.getX()-1, player.getY());
					board.move(player.getX()+1, player.getY());
					moved = true;
				}

				break;
			case"a":
				if((player.getY() == 0) ||
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7) &&
						(player.getY() == 1 ||
						player.getY() == 4 ||
						player.getY() == 7) ||
						(player.getY() == 1+1 ||
						player.getY() == 4+1 ||
						player.getY() == 7+1) &&
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7))
				{
					System.out.println("WALL!");
					moved = false;

				}
				else
				{
					board.set(player, player.getX(), player.getY()-1);
					board.move(player.getX(), player.getY()+1);
					moved = true;
				}
				break;
			case"s":
				if((player.getX() == 8) ||
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7) &&
						(player.getY() == 1 ||
						player.getY() == 4 ||
						player.getY() == 7) ||
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7) &&
						(player.getY() == 1 ||
						player.getY() == 4 ||
						player.getY() == 7))
				{
					System.out.println("WALL!");
					moved = false;

				}
				else
				{
					board.set(player, player.getX()+1, player.getY());
					board.move(player.getX()-1, player.getY());
					moved = true;
				}
				break;
			case"d":
				if((player.getY() == 8) ||
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7) &&
						(player.getY() == 1 ||
						player.getY() == 4 ||
						player.getY() == 7) ||
						(player.getY() == 1-1 ||
						player.getY() == 4-1 ||
						player.getY() == 7-1) &&
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7))
				{
					System.out.println("WALL!");
					moved = false;

				}
				else
				{
					board.set(player, player.getX(), player.getY()+1);
					board.move(player.getX(), player.getY()-1);
					moved = true;
				}
				break;
			default:
				moved = true;
				break;

		}
		return moved;

	}
	
	/**
	 * checks if the player location is the same as the briefcase location
	 * @return boolean is true if they're the same, false if they're not
	 */
	public boolean checkPlayerIsBriefcase()
	{
		if(player.getX() == board.getBriefcaseX() && player.getY() == board.getBriefcaseY())
		{
			return true;

		}
		else 
			return false;
	}
	
	public boolean checkSpy(){
		int x = player.getX();
		int y = player.getY();
		int a = 0;
		int b = 0;
		boolean value = false;
		
		for( int count = 0; count <= 5; count ++) {
			a = ninjas[count].getX();
			b= ninjas[count].getY();

		
			if( y == b) {
				if(	 x+1 == a) {
					board.setEmpty(x, y);
					return (!value);
				}
				else if (x-1 == a ) {
					board.setEmpty(x, y);
					return (!value);
				}
			}
		 
			if ( x == a ) {
				if( y + 1 == b) {
					board.setEmpty(x, y);
					return (!value);
				}
				else if (y - 1 == b) {
					board.setEmpty(x, y);
					return (!value);
				}
			}
			
		}
		return value;
		
	}
	
	public void ninjaMovement() {
		int direction = random.nextInt(4);
		
		//int r = random.nextInt(6);
		
		//int a = ninjas[r].getX();
		//int b = ninjas[r].getY();
		
		int r = 0;
		
		while(r < 6) {
			int a = ninjas[r].getX();
			int b = ninjas[r].getY();
		
			switch(direction) {
			case 0:	
				board.setNinja(ninjas[r],a-1,b);
				board.setEmpty(a, b);
				ninjas[r].setX(a-1);
				ninjas[r].setY(b);
			
			System.out.println((a-1) + " " + b);
			break;
		case 1:	
			board.setNinja(ninjas[r],a+1,b);
			ninjas[r].setX(a+1);
			ninjas[r].setY(b);
			System.out.println((a+1) + " " + b);
			break;
		case 2:	
			board.setNinja(ninjas[r],a,b-1);
			ninjas[r].setX(a);
			ninjas[r].setY(b-1);
			System.out.println(a + " " + (b-1));
			break;
		case 3:	
			board.setNinja(ninjas[r],a,b+1);
			ninjas[r].setX(a);
			ninjas[r].setY(b+1);
			System.out.println(a +" " + (b + 1));
			break;
		default:
			System.out.println("DEFAULT");
			break;
			
		}
		}
		r++;
		
	}
	
	public void debugMode() {
		board.debugMode();
		
	}

	/**public String shoot(String direction) {
		int value = 0;
		if(player.getAmmo() == 0)
		if(direction.equals("w") || direction.equals("s"))
			value = 1;
		else
			value = -1;
		int x = player.getX();
		int y = player.getY();
		switch(direction) {
		case "w":
		case "d":
			for(int i = x + value; i > 0 && i < 9; i+=value) {
				if(board.at(i, y).getNinja()) {
					killNinja(i, y);
					//player.shoot();
					return "You killed a Ninja!";
				}
			}
			break;
		case "a":
		case "s":
			for(int j = y + value; j > 0 && j < 9; j+=value) {
				if(board.at(x, j).getNinja()) {
					killNinja(x, j);
					//player.shoot();
					return "You killed a Ninja!";
				}
			}
			break;
			default:
				System.out.println("Error: GameEninge-shoot-switch");
		}
		return "No Ninjas were killed!";
	}


	private void killNinja(int x, int y) {
		for(int i = 0; i < ninjas.length; i++)
		{
			if(ninjas[i].getX() == x && ninjas[i].getY() == y)
				ninjas[i].die();
		}
	}
	 **/

}



