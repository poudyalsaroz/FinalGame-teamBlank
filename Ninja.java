package edu.cpp.cs.cs141.final_prog_assignment;

public class Ninja extends Character{
	
	private boolean isNinja;
	
	public Ninja(int x, int y) {
		super(x,y);
		isNinja = true;
	}
	
	public void die() {
		isNinja = false;
	}
		
	public boolean isAlive() {
		return isNinja;
	}
	

}
