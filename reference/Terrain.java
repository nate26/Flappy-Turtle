package map_manager;
import java.util.Arrays;
import java.util.Random;

import controller.GameImage;

public class Terrain {
	// 0 - background
	// 1 - terrain
	// 2 - spike
	private int numberOfTypes = 3;
	
	private int[] prevArr = new int[10]; // to store previous array
	
	/** 
	 * Generate a random number based on provided max 
	 */
	private int generateNumber() {
		Random rand = new Random();

		int  n = rand.nextInt(numberOfTypes);
		//2 is the maximum and the 0 is our minimum 
		return n;
	}
	
	/**
	 *  Generate array of length 10 filled with random integers in range 0 to max 
	 */
	public GameImage[] generateArray() {
		int[] arr = new int[10];
		
		for (int i = 0; i < 7; i++) {
			arr[i] = 0; // background
		}
		
		for (int i = 8; i < 10; i++) {
			// could add logic here based on prevArr (assigned below)
			arr[i] = 1; // bottom 2 are always terrain for now
		}
		
		arr[7] = generateNumber();
		
		prevArr = arr;  // store for later
		
		GameImage[] newArr = new GameImage[10];
		System.out.println(Arrays.toString(arr));
		
		for (int i = 0; i < 10; i++) {
			switch (arr[i]) {
				case 0: newArr[i] = GameImage.BACKGROUND;
	            break;
				case 1: newArr[i] = GameImage.TERRAIN;
				break;
				case 2: newArr[i] = GameImage.SPIKE;
				break;
			}
		}
		
		return newArr;
	}
	
	
//	/* test for terrain number generator */
//	public static void main(String[] args) {
//		Terrain terrain = new Terrain();
//		TyleType[] arr = terrain.getNumbers();
//		for(int i = 0; i < arr.length; i++) {
//			System.out.println(arr[i]);
//		}		
//	}

}
