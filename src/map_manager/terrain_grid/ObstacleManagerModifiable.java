package map_manager.terrain_grid;

import java.util.List;

import map_manager.MMSettings;

//TODO verify Javadocs
/**
 * Represents the state of the previously generated column.
 * 
 * @author Brian Towne
 *
 */
public interface ObstacleManagerModifiable {
	
	/**
	 * @return The currently set ground height.
	 */
	public int getGroundHeight();
	
	/**
	 * Sets the current ground height.
	 * 
	 * @param height the height in tiles to set the current height too.
	 */
	public void setGroundHeight(int height);
	
	/**
	 * Resets the ground of the current column to the currently set ground height.
	 */
	public void resetColumn();
	
	/**
	 * Sets the column to all empty tiles.
	 */
	public void clearColumn();
	
	/**
	 * Locks all obstacles of the given obstacle.
	 * 
	 * @param lock True if the class should be locked, false if the class should be unlocked.
	 * @param obstacle The class of the obstacles to lock.
	 */
	public void lock(boolean lock, Class<? extends Obstacle> target);
	
	/**
	 * Locks all obstacles except for the given obstacle.
	 * 
	 * @param lock True if the classes should be locked, false if the classes should be unlocked.
	 * @param obstacle The obstacle to not lock.
	 */
	public void lockAllExcept(boolean lock, Class<? extends Obstacle> exception);
	
	/**
	 * @return The number of the current column.
	 */
	public int getColumnNumber();
	
	/**
	 * Sets the current height max to the given value.
	 * @param height The current maxium height in tiles.
	 */
	public void setHeightMax(int height);
	
	
	/**
	 * Resets the height max to the default value.
	 */
	public void resetHeightMax();
	
	/**
	 * @return The currently set maximum height.
	 */
	public int getHeightMax();
}
