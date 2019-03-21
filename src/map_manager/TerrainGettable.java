package map_manager;

import java.awt.image.BufferedImage;

/**
 * Terrain images can be obtained by this class. 
 * 
 * @author Brian Towne
 *
 */
public interface TerrainGettable {
	/**
	 * Gets the background of the current frame.
	 * 
	 * @return A {@link BufferedImage} of the current background.
	 */
	public BufferedImage getTerrain();
}
