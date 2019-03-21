package entity.enemy;

import java.awt.image.BufferedImage;

/**
 * Interface to give Engine access to certain EnemyManager data.
 * 
 * @author Nate Axt
 *
 */
public interface EnemyData {

	/**
	 * @return The x coordinate of the enemy.
	 */
	public int getEnemyX();

	/**
	 * @return The y coordinate of the enemy.
	 */
	public int getEnemyY();
	
	/**
	 * @return The image of the enemy.
	 */
	public BufferedImage getEnemyImage();
}
