package edu.coo.cs.cs141.final_prog_assignment;

public class Main {

	public static void main(String[] args) {
		UserInterface ui = new UserInterface(new gameEngine());
		ui.openMenu();
	}

}