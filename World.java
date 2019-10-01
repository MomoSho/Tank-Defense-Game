
public class World {
	private static String[][] WorldMap;
	private static int[] shots;
	private static int width, height;
	
	public World(int width, int height) {
		// Instantiation and initialization
		WorldMap = new String[width][height];
		shots = new int[width];
	}
	
	// Initialize an empty world
	public static String[][] createWorldMap() {			
		for(int i = 0; i < width; i++){ // columns
			for(int j = 0; j < height; j++) { //rows
				if(j >= height*7/8){
					WorldMap[i][j] = "T";
				}
				else {
					WorldMap[i][j] = " ";
				}
			}
		}
		
		return WorldMap;
	}
	
	public static String[][] getWorldMap() {
		return WorldMap;
	}
	
	public static int[] getShotsArray() {
		return shots;
	}
	
	public static void determineShotsArray(int[] s) {
		shots = s;
	}
}
