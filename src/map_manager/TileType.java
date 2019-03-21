package map_manager;

import java.awt.image.BufferedImage;
import controller.GameImage;

/**
 * Represents the different possible tiles types.
 * 
 * @author Brian Towne
 */
public enum TileType {
	/**
	 * Represents solid tiles that are either walls or ground.
	 */
	SOLID,
	
	/**
	 * Represents a tile area with no terrain.
	 */
	EMPTY,
	
	/**
	 * Represents a tile that is hazardous to the player.
	 */
	HAZARD,
	
	/**
	 * Represents a tile that is a plank for the player to pass through beneath 
	 * but not from above.
	 */
	PLANK, 
	
	/**
	 * Represents underneath the map.
	 */
	PIT;
}
