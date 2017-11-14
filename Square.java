package edu.cpp.cs.cs141.final_prog_assignment;

public class Square{

	private boolean isNinja;
	private boolean isPlayer;
	private boolean isRoom;
	private boolean isItem;
	private boolean isEmpty;

	private boolean isBrief;
	private boolean hide;
	private boolean reveal;
	private Character player;
	private Character ninjas;
	private Item itemPresent;

	private String display;
	

	public Square() { //default constructor
		isEmpty = true;
		isPlayer = false;
		isNinja = false;
		isRoom = false;
		isItem = false;
		reveal = false;
		hide = true;
		display = "   ";
	}
	public void setCharacter(Character charac, int x, int y) { //set character in location
		if ((x == 1 ||
				x == 4 ||
				x == 7)&&
				(y == 1 ||
				y == 4 ||
				y == 7))
		{
			player = null;
			isPlayer = false;
			hide = false;
			isRoom = true;
			display = "[P]";
		}
		else
		{
			player = charac;
			isPlayer = true;
			display = " P ";
			hide = false;
		}
	}


	public void playerMoved(int x, int y) {
		if ((x == 1 ||
				x == 4 ||
				x == 7)&&
				(y == 1 ||
				y == 4 ||
				y == 7))
		{
			player = null;
			isPlayer = false;
			hide = false;
			isRoom = true;
			display = "[_]";
		}
		else {
			player = null;
			isPlayer = false;
			hide = true;
			display = "   ";
		}
	}


	public void setItem(Item item) {
		itemPresent = item;
		hide = true;
		display = " " + itemPresent.getType() + " ";
		isItem = true;
	}
	
	public void setNinja( Character charac) {
	
		//for( int i = 0; i<=5;i++) {
		
		ninjas = charac;
		isNinja = true;
		hide = true;
		display = " N ";
		//isRoom = false;
		//}
		
	}
	public void setEmpty() {
		isEmpty = true;
		isPlayer = false;
		isNinja = false;
		isRoom = false;
		isItem = false;
		player = null;
		ninjas = null;
		display ="   ";
	}
	public void setRoom() {
		display = "[_]";
		hide = false;
		isRoom = true;
		isBrief = false;
	}
	public void briefExist(boolean isDebugOn) {
		if (isDebugOn == false)
		{
			display = "[_]";
			hide = false;
			isRoom = true;
			isBrief = true;
		}
		else 
		{
			display = "[#]";
			hide = true;
			isRoom = true;
			isBrief = true;
		}

	}


	public boolean getNinja() {
		if(isNinja) //ninja exists
			return true; 
		else
			return false;
	}
	public boolean getItem() {
		if(isItem)
			return true;
		else
			return false;
	}
	public boolean getEmpty() {
		return isEmpty;
	}
	public boolean getRoom() {
		return isRoom;
	}


	public Character getPlayerObj() {
		return player;
	}
	public Character getNinjaObj() {
		return ninjas;
	}


	public boolean getBrief() {
		return isBrief;
	}

	public String display() {
		if(!hide) //if something visible
			return display;
		else if(reveal) {
			reveal();
			return display;
		}
		else 
			return " * ";
	}

	public void reveal() {
		reveal = !reveal;
	}
	public void removeNinja() {
		isNinja = false;
		if(itemPresent != null)
			display = itemPresent.getType();
		else
			display ="   ";
	}

}
