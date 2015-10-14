import java.util.Random;
import java.util.Scanner;

public class TankDefenseGame {
	// Declarations
	static String[][] WorldMap;
	static String[][] oWorldMap;	// copy of the original WorldMap without the players
	static int[] shots;				// hold s the y value for the current projectile shot at shot[x]
	static Tank[] Players;
	static boolean GameOver = false;
	
	static Random rand = new Random();
	
	static Scanner s = new Scanner(System.in);

	// Initialize an empty world
	public static void createWorldMap(int width, int height) {
		// Instantiation and initialization
		WorldMap = new String[width][height];
		oWorldMap = new String[width][height];
		shots = new int[width];
		
		for(int i = 0; i < width; i++){ // columns
			for(int j = 0; j < height; j++) { //rows
				if(j >= height*7/8){
					WorldMap[i][j] = "T";
					oWorldMap[i][j] = "T";
				}
				else {
					WorldMap[i][j] = " ";
					oWorldMap[i][j] = " ";
				}
			}
		}		
	}
	
	// Determine random initial locations of the players
	public static void placePlayers(int numOfPlayers, int playerSize, int playerID){
			int location = rand.nextInt(WorldMap.length-4)+2;
			int y = WorldMap[0].length*7/8-1;
			
			if(WorldMap[location][y] == " "){
				if(WorldMap[location+1][y] == " " && WorldMap[location+2][y] == " " && WorldMap[location-1][y] == " " && WorldMap[location-2][y] == " ") {
					Players[playerID] = new Tank(playerID, location);
				}
				else {
					placePlayers(numOfPlayers, playerSize, playerID);
				}
			}
			else {
				placePlayers(numOfPlayers, playerSize, playerID);
			}			
	}
	
	// Print out (or draw) the whole world
	public static void displayWorldMap(int numOfPlayers, int numberDead) {
		int y = WorldMap[0].length*7/8-1;
		
		for(int p = 0; p < numOfPlayers; p++) {
			Tank player = Players[p];
			
			// determine mortality of the player
			if(WorldMap[player.getXLocation()][y-1] == "X" || WorldMap[player.getXLocation()+1][y-1] == "X" || WorldMap[player.getXLocation()][y] == "X" || WorldMap[player.getXLocation()+1][y] == "X") {
				player.setMortality(false);
				numberDead += 1;
				
				WorldMap[player.getXLocation()][y-1] = " ";
				WorldMap[player.getXLocation()+1][y-1] = " ";
				
				WorldMap[player.getXLocation()][y] = " ";
				WorldMap[player.getXLocation()+1][y] = " ";
				
				System.out.println("Sorry, Player " + p + " has been hit!!");
			}
			else if (player.getMortality()){
				WorldMap[player.getXLocation()][y-1] = "@";
				WorldMap[player.getXLocation()+1][y-1] = "@";
				
				WorldMap[player.getXLocation()][y] = "@";
				WorldMap[player.getXLocation()+1][y] = "@";
			}
		}
		
		for(int i = 0; i < WorldMap[0].length; i++){ //rows (or height)
			for(int j = 0; j < WorldMap.length; j++) { //columns (or width)
				System.out.print(WorldMap[j][i]);
			}
			
			System.out.print("\n");
		}
	}
	
	public static void removeShots() {
		for(int i = 0; i < WorldMap.length; i++) {
			if(shots[i] < WorldMap[0].length)
				WorldMap[i][shots[i]] = oWorldMap[i][shots[i]];
		}
	}
	
	public static boolean isTheGameOver(int numberOfPlayers, int numberDead) {
		if(numberOfPlayers - 1 == numberDead) {
			return true;
		} 
		return false;
	}
	
	// Determine range of the player's shot
	public static void ATTACK(int playerID, float angle){
		Players[playerID].setCannonAngle(angle);
		
		int xStart;
		int xi, xf;
		if(angle > 90) {
			xStart = Players[playerID].getXLocation()-1;
			xi = 0;
			xf = Players[playerID].getXLocation();
		}
		else {
			xStart = Players[playerID].getXLocation()+2;
			xi = xStart;
			xf = WorldMap.length;
		}
		int yStart = WorldMap[0].length*7/8-3;
				
		float vx = 25f; // vx=100 --> d=1020 @45 degrees; vx=50 --> d=255 @45 degrees
		float g = 9.8f; // gravity
		int y = yStart;
		float t = 0;
		for(int x = xi; x < xf; x++) {
			double angInRad = -angle*Math.PI/180;
			t = (float) ((x-xStart)/(vx*Math.cos(angInRad)));
			y = (int) (yStart+vx*t*Math.sin(angInRad)+.5*g*t*t);
			shots[x] = y;
			
			if(y < WorldMap[0].length && y >= 0) {
				switch(WorldMap[x][y]){
					case "@": 
						WorldMap[x][y] = "X";
						break;
					case "T": 
						break;
					default: WorldMap[x][y] = "*";
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int numOfPlayers = 4;
		int numberDead = 0;
		Players = new Tank[numOfPlayers];
		
		createWorldMap(100, 50);
		for(int p = 0; p < numOfPlayers; p++) {
			placePlayers(numOfPlayers, 2, p);
		}
		displayWorldMap(numOfPlayers, numberDead);
		
		// GAME LOOP
		String userInput = "";
		int p = 0;
		while(!isTheGameOver(numOfPlayers, numberDead) || (userInput.equals("e"))) {
			if(p < numOfPlayers) {
				if(Players[p].getMortality()) {
					System.out.println("Player " + p + " please enter an angle from 0 to 180");
					System.out.println("Type 'e' to exit");
					userInput = s.nextLine();
					if(userInput.equals("e")) {
						break;
					}
					
					try {
						ATTACK(p, Integer.parseInt(userInput));
					}
					catch(NumberFormatException e) {
						System.err.println("Exception Found: Userinput was '" + userInput + "', but should have been an integer");
						break;
					}
					displayWorldMap(numOfPlayers, numberDead);
					removeShots();
				}
				p++;
			}
			else p = 0;
		}
		
		System.out.println("*****************");
		System.out.println("*** GAME OVER ***");
		System.out.println("*****************");
	}
}
