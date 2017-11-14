package edu.cpp.cs.cs141.final_prog_assignment;

public class Character{
	private int lives;
	private int bullet;
	private boolean briefcase;
	private boolean isNinja;
	private int locationX, locationY;
	private boolean isAlive;
	
	public Character(int x, int y) {
		lives = 1;
		bullet = 0;
		briefcase = false;
		isNinja = true;
		isAlive = true;
		locationX = x;
		locationY = y;
	}
	public Character(boolean brief,int x, int y) {
		lives = 3;
		bullet = 1;
		briefcase = brief;
		isNinja = false;
		isAlive = true;
		locationX = x;
		locationY = y;
	}
	
	public void decLives() {
		if(lives > 0)
			lives--;
	}
	
	public boolean checkGameOver() {
		if(lives <= 0)
			return false;
		else 
			return true;
	}

	public int getX() {
		return locationX;
	}
	public int getY() {
		return locationY;
	}
	public void setX(int x) {
		locationX = x;
	}
	public void setY(int y) {
		locationY = y;
	}

	public void setBriefcase() {
		briefcase = true;
	}
	public void setDead() {
		isAlive = false;
	}
	public boolean getAlive() {
		return isAlive;
	}
	
	public boolean hasBriefcase() {
		return briefcase;
	}
	
	public boolean checkNinja() {
		return isNinja;
	}
	public int getAmmo() {
		return bullet;
	}
	
	public void fire() {
		bullet--;
	}
	
}
