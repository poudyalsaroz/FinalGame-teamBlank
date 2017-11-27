package edu.cpp.cs.cs141.final_prog_assignment;

	import java.util.Random;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
	import java.io.Serializable;


	public class GameEngine implements Serializable{

		private Random random = new Random();

		private Character player;
		private Board board;
		private Character[] ninjas = new Character[6];
		private Item radar = null;
		private Item invincible = null;
		private Item ammo = null;
		private int numNinja;
		private String difficulty;
		private boolean hasRadar;

		public boolean getHasRadar()
		{
			return hasRadar;
		}
		
		public void createBoard() {
			player = new Character(false,0,8);
			board = new Board();
			board.set(player, 8, 0);
			createPowerUp();	
			createNinjas();
			hasRadar = false;
		}


		public void createNinjas() {
			int x,y;
			int count = 0;

			while( count <= 5 ) {
				do {
					x = getRandom();
					y = getRandom();
				}while(x >= 5 && y <= 3);

				if(!board.at(x, y).getNinja() 
						&& board.at(x, y).getEmpty() 
						&& !board.at(x, y).getRoom() 
						&& !board.at(x, y).getItem()){ 
					ninjas[count] = new Character(x,y);	
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

			while(radar == null) { //loop until one of each item is created
				x = getRandom();
				y = getRandom();
				if(!board.at(x, y).getItem() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom() && !board.at(x, y).getPlayer()) { //check if there is no item, not a room, and is empty
					radar = new Item("radar");
					board.set(radar, x, y);
				}
			}
			while(invincible == null) {
				x = getRandom();
				y = getRandom();
				if(!board.at(x, y).getNinja() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom() && !board.at(x, y).getPlayer()) {//check if there is no item, not a room, and is empty
					invincible = new Item("invincible");
					board.set(invincible, x, y);
				}
			}
			while(ammo == null) {
				x = getRandom();
				y = getRandom();
				if(!board.at(x, y).getNinja() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom() && !board.at(x, y).getPlayer()) {//check if there is no item, not a room, and is empty
					ammo = new Item("ammo");
					board.set(ammo, x, y);
				}
			}	
		}

		public int getRandom(){ //function for getting a random number 0-8
			return random.nextInt(9);
		}

		public String displayBoard(int x, int y){
			return board.displayBoard(x, y);
		}

		public boolean look(String direction) {
			Square[] s = new Square[3];
			boolean clear = true;
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
				if(s[i] != null) {
					if(s[i].getRoom()) {
						break;
					}
					else if(s[i].getNinja()) {
						s[i].reveal();
						clear = false;
					}
					else {
						s[i].reveal();
					}
				}
			}
			return clear;
		}

		public boolean move(String direction) {
			boolean moved = false;

			switch(direction){
				case"w":
					if((player.getX() == 0) ||
							(player.getX() == 1+1 ||player.getX() == 4+1 ||player.getX() == 7+1) &&
							(player.getY() == 1 ||	player.getY() == 4 ||player.getY() == 7)){
						System.out.println("WALL!");
						moved = false;

					}/*
					else if(board.at(player.getX()-1,  player.getY()).getNinja()) {
						System.out.println("Ninja!");
						moved = false;
					}*/
					else {
						board.set(player, player.getX()-1, player.getY());
						board.move(player.getX()+1, player.getY());
						moved = true;
					}
					break;
				case"a":
					if((player.getY() == 0) ||
							(player.getX() == 1 || player.getX() == 4 ||	player.getX() == 7) &&
							(player.getY() == 1 || player.getY() == 4 ||	player.getY() == 7) || 
							(player.getY() == 1+1 ||player.getY() == 4+1 || player.getY() == 7+1) &&
							(player.getX() == 1 || player.getX() == 4 || player.getX() == 7)){
						System.out.println("WALL!");
						moved = false;

					}/*
					else if(board.at(player.getX(),  player.getY()-1).getNinja()) {
						System.out.println("Ninja!");
						moved = false;
					}*/
					else{
						board.set(player, player.getX(), player.getY()-1);
						board.move(player.getX(), player.getY()+1);
						moved = true;
					}
					break;
				case"s":
					if((player.getX() == 8) ||
							(player.getX() == 1 ||player.getX() == 4 ||	player.getX() == 7) &&
							(player.getY() == 1 ||player.getY() == 4 ||player.getY() == 7) ||
							(player.getX() == 1 ||player.getX() == 4 ||player.getX() == 7) &&
							(player.getY() == 1 ||player.getY() == 4 ||	player.getY() == 7)){
						System.out.println("WALL!");
						moved = false;
					}
					/*else if(board.at(player.getX()+1,  player.getY()).getNinja()) {
						System.out.println("Ninja!");
						moved = false;
					}*/
					else{
						board.set(player, player.getX()+1, player.getY());
						board.move(player.getX()-1, player.getY());
						moved = true;
					}
					break;
				case"d":
					if((player.getY() == 8) ||
							(player.getX() == 1 || player.getX() == 4 || player.getX() == 7) &&
							(player.getY() == 1 || player.getY() == 4 || player.getY() == 7) ||
							(player.getY() == 1-1 || player.getY() == 4-1 || player.getY() == 7-1) &&
							(player.getX() == 1 || player.getX() == 4 || player.getX() == 7)){
						System.out.println("WALL!");
						moved = false;

					}/*
					else if(board.at(player.getX(),  player.getY()+1).getNinja()) {
						System.out.println("Ninja!");
						moved = false;
					}*/
					else{
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
		public boolean checkPlayerIsBriefcase(){
			if(player.getX() == board.getBriefcaseX() && player.getY() == board.getBriefcaseY())
				return true;
			else 
				return false;
		}

		public void debugMode() {
			board.debugMode();		
		}
		

		public int getAmmoCount() {
			return player.getAmmo();
		}
		public String shoot(String direction) {
			int value = 0;
			if(player.getAmmo() == 0)
				return "You are out of ammunition! Idiot! \n";
			if(direction.equals("s") || direction.equals("d"))
				value = 1;
			else
				value = -1;
			int x = player.getX();
			int y = player.getY();
			switch(direction) {
				case "w":
				case "s":
					for(int i = x + value; i >= 0 && i < 9; i+=value) {
						if(board.at(i, y).getNinja()) {
							board.at(i,y).killNinja();
							player.fire();
							return "You killed a Ninja!\n";
						}
						else if(board.at(i, y).getRoom()) {
							break;
						}
					}
					break;
				case "a":
				case "d":
					for(int j = y + value; j >= 0 && j < 9; j+=value) {
						if(board.at(x, j).getNinja()) {
							board.at(x,j).killNinja();
							player.fire();
							return "You killed a Ninja! \n";
						}
						else if(board.at(x,j).getRoom()) {
							break;
						}
					}
					break;
				default:
					System.out.println("Error: GameEninge-shoot-switch");
			}
			player.fire();
			return "No Ninjas were killed!\n";
		}

		public boolean checkSpy(){
			int x = player.getX();
			int y = player.getY();
			int a = 0;
			int b = 0;
			boolean value = false;

			for( int count = 0; count <= 5; count ++) {
				if(ninjas[count].getAlive() == false) //if ninja is dead - check next ninja?
					continue;
				a = ninjas[count].getX();
				b = ninjas[count].getY();

				if (board.at(x, y).getRoom())// if ninja goes into a room
					return value;


				if( y == b) { //if in same vertical line
					if(x+1 == a) { //check for horizontal match in position
						if(!player.isInvincible()) {
							reset(x,y);
							return !value;
						}
						else
							return value;
					}
					else if (x-1 == a) {
						if(!player.isInvincible()) {//if not invincible
							reset(x,y);
							return !value;
						}
						else
							return value;
					}
				}
				if ( x == a ) {
					if( y + 1 == b) {
						if(!player.isInvincible()) {
							reset(x,y);
							return !value;
						}
						else
							return value;
					}
					else if (y - 1 == b) {
						if(!player.isInvincible()) {
							reset(x,y);
							return !value;
						}
						else
							return value;
					}
				}
				if(x == a && y == b) {
					if(!player.isInvincible()) {
						reset(x,y);
						return(!value);
					}
					else 
						return value;
				}

			}
			return value;
		}

		public void reset(int x,int y) {
			player.decLives();
			board.set(player,8, 0);
			board.setEmpty(x, y);
		}


		//---------------------------------------------
		public void ninjaMovement() {
			
			for ( int i = 0; i <= 5; i++) { 
				if(ninjas[i].getAlive() == false)
					continue;
				
				int a = ninjas[i].getX();
				int b = ninjas[i].getY();
				
				int direction2;
				int direction3;
				int ninjaDirection=0;
				int radDirection;
				int inLineDirection=0;
				
				if (difficulty == "1") {
					
						ninjaDirection = findDirection(ninjas[i],a, b);
						moveNinja(ninjas[i],ninjaDirection, ninjas[i].getX(),ninjas[i].getY());
						
				}
						
				else if(difficulty == "2") {
					
					if(senseSpyInLineOfSight(a,b) == true){
					
						direction2 = getLineDirection(a, b, player.getX(), player.getY());
						
						inLineDirection = moveAiNinja(ninjas[i],direction2, a, b); 
							
						moveNinja(ninjas[i],inLineDirection, ninjas[i].getX(),ninjas[i].getY());
		
					}
					else
					{
						ninjaDirection = findDirection(ninjas[i],a, b);
						moveNinja(ninjas[i],ninjaDirection, ninjas[i].getX(),ninjas[i].getY());
					}
				}
				else if (difficulty == "3") {
					
					if ((senseSpyInRad(a,b) == true)&&(senseSpyInLineOfSight(a,b) == true)) {
						
						direction2 = getLineDirection(a, b, player.getX(), player.getY());
						
						inLineDirection = moveAiNinja(ninjas[i],direction2, a, b); 

						moveNinja(ninjas[i],inLineDirection, ninjas[i].getX(),ninjas[i].getY());
						
					}
					else if((senseSpyInLineOfSight(a,b) == true)&&(!senseSpyInRad(a,b) == true)) {
						
						direction2 = getLineDirection(a, b, player.getX(), player.getY());
						
						inLineDirection = moveAiNinja(ninjas[i],direction2, a, b); 

						moveNinja(ninjas[i],inLineDirection, ninjas[i].getX(),ninjas[i].getY());
					
					}
					else if((!senseSpyInLineOfSight(a,b) == true)&&(senseSpyInRad(a,b) == true)) {
						
						direction3 = getNinjaRadDirection(a, b, player.getX(), player.getY());
						
						radDirection = moveAiNinja(ninjas[i],direction3, a, b);
						
						moveNinja(ninjas[i],radDirection, ninjas[i].getX(),ninjas[i].getY());
					}
					else {
					
							ninjaDirection = findDirection(ninjas[i],a, b);
							moveNinja(ninjas[i],ninjaDirection, ninjas[i].getX(),ninjas[i].getY());	
					}
				}
			}
		}
		
      //------------------------------
		
		public int findDirection(Character ninja, int a, int b) {
			int x = 0; 
			int y = 0;		
			int direction;
				do {
				direction = random.nextInt(4);	
				switch(direction){
				case 0:
					x= a-1;
					y= b;
					break;
				case 1:
					x= a+1;
					y = b;
					break;
				case 2:
					x= a;
					y = b-1;
					break;
				case 3:
					x= a;
					y = b+1;
					break;
				default:
					System.out.println("Error");
					
				}
				}
				while(( x < 0 || x > 8) || (y < 0 || y > 8)|| board.at(x, y).getNinja()|| board.at(x,y).getRoom()||board.at(x,y).getPlayer());
				ninja.setX(x);
				ninja.setY(y);
				return direction;
			}
		
	//-----------------------	
	public int moveAiNinja(Character ninja, int direction, int a, int b) {
				int x= 0;
				int y = 0;
				
				switch(direction){
			
				case 0:
					x= a-1;
					y= b;
					break;
				case 1:
					x= a+1;
					y = b;
					break;
				case 2:
					x= a;
					y = b-1;
				break;
				case 3:
					x= a;
					y= b+1;
				break;
			default:
				System.out.println("Error");
				}
				if(( x >= 0 && x <=8 ) && (y >= 0 && y <= 8)&&  !board.at(x, y).getNinja()&& !board.at(x,y).getRoom()&& !board.at(x, y).getPlayer()){
					ninja.setX(x);
					ninja.setY(y);
					return direction;
				}
				else {
						direction = findDirection(ninja,a,b);
						return direction;
					}	
		}
	//-------------------------------------

			public void moveNinja( Character ninjas, int direction, int x, int y) {
				switch(direction) {
					case 0:					
						board.removeNinja(x+1, y);
						board.setNinja(ninjas,x,y);
						break;
					case 1:	
						board.removeNinja(x-1, y);
						board.setNinja(ninjas,x,y);
						break;
					case 2:	
						board.removeNinja(x, y+1);
						board.setNinja(ninjas,x,y);
						break;	
					case 3:	
						board.removeNinja(x, y-1);
						board.setNinja(ninjas,x,y);
						break;	
					default:
						break;

				}

			}
		//-----------------------------

		public int getNinjaRadDirection(int row, int column, int i, int j) // row column = ninja, i j = spy
		{
			int ninjaDirection = 0;
			int s;

		if (row < i && column < j) { // ninja in quadrant 3: spy = origin
				s = random.nextInt(2);
				if (s == 0)
					ninjaDirection =  1;
				else ninjaDirection = 3;
			}

			else if (row < i && column > j){
				s = random.nextInt(2);
				if (s == 0)
					ninjaDirection =  1;
				else ninjaDirection = 2;
			}
			else if (row > i && column < j){
				s = random.nextInt(2);
				if (s == 0)
					ninjaDirection =  0;
				else ninjaDirection = 3;
			}
			else if (row > i && column > j){
				s = random.nextInt(2);
				if (s == 0)
					ninjaDirection =  0;
				else ninjaDirection = 2;
			}
			return ninjaDirection;
		}
		//--------------------------------------
		
		public int getLineDirection(int row, int column, int i, int j) {
		int ninjaDirection= 0;	
		if (row == i && column < j)
				ninjaDirection = 3;
			else if (row == i && column > j)
				ninjaDirection = 2;
			else if (row < i && column == j)
				ninjaDirection = 1;
			else if (row > i && column == j)
				ninjaDirection = 0;
			
			else System.out.println("Error");
			return ninjaDirection;
		}
		//------------------------------------


		public boolean senseSpyInLineOfSight(int a, int b)
		{
			boolean isSpyInSight = false;
			
				if (a == player.getX() || b == player.getY()){
					isSpyInSight = true;
			}
		
			return isSpyInSight;
		}
		//---------------------------------------


		public boolean senseSpyInRad(int row, int column) // row = x coordinate for ninja, column = y coordinate for ninja
		{
			boolean isSpyNearby = false;
			for(int i = row - 3; i <= row + 3; i++)
				for(int j = column - 3; j <= column + 3; j++){
					if (board.at(i, j).getPlayer() == true){
						isSpyNearby = true;
					}
				}
			return isSpyNearby;
		}
		//---------------------------------------

		public void decInvincibility() {
			player.decInvincibility();
		}
		public int invCount() {
			return player.getInvCount();
		}

		public boolean playerAlive() {
			return player.getAlive();
		}
		//----------------------------------------

		public boolean checkItem() {
			int x = player.getX();
			int y = player.getY();
			if(board.at(x, y).getItem()) 
				return true;
			return false;
		}
		public int applyItem() {
			int x = player.getX();
			int y = player.getY();
			String type = board.at(x, y).getItemtype();

			switch(type) {
				case "radar" : //radar
					applyRadar();
					board.at(x, y).setItemnull();
					return 1;
				case "invincible": //invincible
					applyInvincible();
					board.at(x, y).setItemnull();
					return 2;
				case "ammo"://ammo
					if(player.getAmmo() == 0){
						applyAmmo();
						board.at(x, y).setItemnull();
						return 3;
					}
					return 0;
				default:
					return 0;
			}
		}

		public void applyInvincible() {
			player.setInvincible();
		}
		public void applyAmmo() {
			player.incAmmo();
		}


		public void applyRadar() {
			hasRadar = true; // added: to be used in debug, briefcase will remain visible when switching debugmode on and off 
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) { //check every room and reveal the room with the briefcase
					if(board.at(i,j).getBrief())
						board.at(i,j).revealBrief();
				}
			}
		}
		/////--------------------------------------------------------------
		public boolean save(String filename) {
			try {
				FileOutputStream ofstream = new FileOutputStream(filename + ".dat") ;
				ObjectOutputStream outfile = new ObjectOutputStream(ofstream);

				outfile.writeObject(board);
				outfile.writeObject(ninjas);
				outfile.writeObject(player);

				outfile.writeObject(radar);
				outfile.writeObject(invincible);
				outfile.writeObject(ammo);
				outfile.writeObject(difficulty);
				outfile.close();
				return true;

			}
			catch(IOException e) {
				return false;
			}
		}

		public boolean load(String filename) {
			try {
				FileInputStream ifstream = new FileInputStream(filename + ".dat");
				ObjectInputStream infile = new ObjectInputStream(ifstream);

				board = (Board) infile.readObject();
				ninjas = (Character[]) infile.readObject();
				player = (Character) infile.readObject();
				radar = (Item) infile.readObject();
				invincible = (Item) infile.readObject();
				ammo = (Item) infile.readObject();
				difficulty = (String) infile.readObject();
				infile.close();
				return true;
			}catch(IOException | ClassNotFoundException e) {
				return false;
			}
		}

		public void setNumNinja() {
			int counter = 0;
			for(int i = 0; i < 6; i++) {
				if(ninjas[i].getAlive())
					counter++;
			}

			numNinja = counter;
		}

		public int getNumNinja() {
			setNumNinja();
			return numNinja;
		}

		public Character getPlayer() {
			return player;
		}

		public void setDifficulty(String level) {
			difficulty = level;
		}


}
