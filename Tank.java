
public class Tank extends Player{
	private int playerID;
	private int xLocation;
	private float angle = 0.45f; // cannon angle
	private boolean isAlive = true;
	
	Tank (int playerID, int xLocation) {
		super(playerID, xLocation);
	}
	
	// Encapsulation: Modifiers and Accessors
	public void setCannonAngle(float theta) {
		angle = theta;
	}
	public float getAngle() {
		return angle;
	}
}
