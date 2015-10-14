/*
 * Player is the subclass
 * containing "generic" or default
 * methods and fields
 * 
 * */

public class Player {
	// Class variables
	private int playerID;
	private int xLocation;
	private boolean isAlive = true;
	
	// Constructor
	Player (int playerID, int xLocation) {
		this.playerID = playerID;
		this.xLocation = xLocation;
	}
	
	// Encapsulation: Modifiers and Accessors
	public void setPlayerID(int ID) {
		playerID = ID;
	}
	public int getPlayerID() {
		return playerID;
	}
	
	public void setXLocation(String[][] World, int x) {
		if(x < World.length) {
			xLocation = x;
		}
	}
	public int getXLocation() {
		return xLocation;
	}
		
	public void setMortality(boolean Life) {
		isAlive = Life;
	}
	public boolean getMortality() {
		return isAlive;
	}
}
