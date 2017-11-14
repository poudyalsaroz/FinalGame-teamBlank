package edu.cpp.cs.cs141.final_prog_assignment;

public class Item{
	
	private String type;
	
	public Item(String s) {
		type = s;
	}
	
	public String getType() {
		switch(type) {
		case "radar":
			return "R";
		case "invincible":
			return "I";
		case "ammo":
			return "A";
			default:
				return "Error - PowerUp.display - Switch";
	}
	}
	
	public String name() {
		return type;
	}

}
