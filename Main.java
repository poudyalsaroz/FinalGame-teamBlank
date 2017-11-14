/**
 * 
 */
package edu.cpp.cs.cs141.final_prog_assignment;


public class Main {
	
	public static void main(String[] args) {
		UserInterface ui = new UserInterface(new GameEngine());
		ui.openMenu();
	}
}
