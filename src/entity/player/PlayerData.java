package entity.player;

import java.awt.image.BufferedImage;

/**
 * Interface to give Engine access to certain PlayerManager data.
 * 
 * @author Brian Towne
 *
 */
public interface PlayerData {
	
	/**
	 * @return The x coordinate of the player.
	 */
	public int getPlayerX();
	
	/**
	 * @return The y coordinate of the player.
	 */
	public int getPlayerY();
	
	/**
	 * @return The image of the player.
	 */
	public BufferedImage getPlayerImage();
	
	/**
	 * @return The x coordinate of the health bar.
	 */
	public int getHealthX();
	
	/**
	 * @return The y coordinate of the health bar.
	 */
	public int getHealthY();
	
	/**
	 * @return The image of the health bar.
	 */
	public BufferedImage getHealthImage();

}
