package edu.cpp.cs.cs141.final_prog_assignment;
import java.util.Random;

public class Board {
	private final int horizontal = 9;
	private final int vertical = 9;
	private Square[][] room = new Square[vertical][horizontal];
	private Random random = new Random();

	private int[] briefcaseArrayX = {1,4,7};
	private int[] briefcaseArrayY = {1,4,7};
	
	private int briefcaseX;
	private int briefcaseY;
	
	private boolean debugMode = false;
	
	/**
	 * Creates board with room and briefcase.
	 */
	Board() {
		createBoard();
	}

	/**
	 * Creates board with room and briefcase.
	 */
	public void createBoard() {
		briefcaseX = briefcaseArrayX[random.nextInt(3)];
		briefcaseY = briefcaseArrayY[random.nextInt(3)];

		for(int x=0;x<horizontal;x++) {
			for(int y=0;y<vertical;y++) {
				if((y==1 || y==4 || y==7) && (x==1 || x==4 || x==7)) {
					if(x == briefcaseX && y == briefcaseY) {
						room[x][y] = new Square(); //with briefcase
						
						room[x][y].briefExist(debugMode);
					}
					else {
						room[x][y] = new Square();//without briefcase
						room[x][y].setRoom();
					}
				}
				else
					room[x][y] = new Square();
			}
		}
	}

	/**
	 * 
	 */
	public String displayBoard(int x, int y) {
		if(debugMode) {
			room[x][y].reveal();
			return room[x][y].display();
		}
		else
			return room[x][y].display();
	}	

	public Square at(int x, int y) {
		return room[x][y];
	}
	
	
	public void set(Character player, int x, int y) {	
		room[x][y].setCharacter(player, x, y);
		player.setX(x);
		player.setY(y);
	}
	public void move(int x, int y) {	
		room[x][y].playerMoved(x, y);
	}
	
	public void setNinja(Character ninjas, int x, int y) {
		//room[x][y].setNinja(ninjas]);
		
		//for( int count = 0; count<=5; count++) {
			room[x][y].setNinja(ninjas);
			ninjas.setX(x);
			ninjas.setY(y);
			
		
	}

	public void set(Item radar, int x, int y) {
		room[x][y].setItem(radar);
	}

	/**
	 * used in GameEngine's checkPlayerIsBriefcase function
	 * @return x-coordinate of briefcase
	 */
	public int getBriefcaseX()
	{
		return briefcaseX;
	}
	
	/**
	 * used in GameEngine's checkPlayerIsBriefcase function
	 * @return y-coordinate of briefcase
	 */
	public int getBriefcaseY()
	{
		return briefcaseY;
	}
	
	public void debugMode() {
		debugMode = !debugMode;
		room[briefcaseX][briefcaseY].briefExist(debugMode);
		
	}
	
	public void removeNinja( int x, int y) {
		room[x][y].removeNinja();
	}
	
	public void setEmpty( int x, int y) {
		room[x][y].setEmpty();
	}


}
