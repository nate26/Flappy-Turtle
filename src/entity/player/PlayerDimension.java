package entity.player;

import controller.GlobalSettings;
import entity.EntityDimension;
import map_manager.TileGettable;
import map_manager.TileType;

/**
 * Sets all the player positions and dimensional bounds.
 * 
 * @author Nate Axt
 */
public class PlayerDimension extends EntityDimension {

	private TileGettable mapInfo;
	
	/**
	 * Sets the player at the first position.
	 * 
	 * @param mapInfo - all live tile info
	 */
	protected PlayerDimension(TileGettable mapInfo) {
		super(new PlayerSettings());
		this.mapInfo = mapInfo;
		setX(PlayerSettings.STARTING_X);
		setY(resetY());
	}

	/**
	 * Resets the position of the character after death or falling in the void.
	 */
	protected void resetPos() {
		int tempX = resetX(PlayerSettings.STARTING_X);
		setX(tempX);
		int tempY = resetY();
		setY(tempY);
	}
	
	/**
	 * Finds the next column of x where there is no pit.
	 * 
	 * @param currX - current x position to check column
	 * @return the final reset x position
	 */
	private int resetX(int currX) {
		int nextColumn = Math.round(currX / GlobalSettings.TILE_SIZE) * GlobalSettings.TILE_SIZE;
		
		if (nextColumn > GlobalSettings.WINDOW_WIDTH - GlobalSettings.TILE_SIZE) {
			// catch error or end game? -- this means there is no more generated terrain.
			System.out.println("LOGGED: Cannot respawn. --PlayerDimension");
		}
		
		// check if there is ground available
		if (mapInfo.getTile(nextColumn, GlobalSettings.WINDOW_HEIGHT 
				- GlobalSettings.TILE_SIZE).getTileType() != TileType.SOLID) {
			return resetX(currX + GlobalSettings.TILE_SIZE);
		}
		return nextColumn;
	}
	
	/**
	 * Finds the lowest tile that the player can stand on.
	 * 
	 * @return the final reset y position
	 */
	private int resetY() {
		int height = GlobalSettings.WINDOW_HEIGHT - GlobalSettings.TILE_SIZE;
		for (int i = height; i >= 0; i -= GlobalSettings.TILE_SIZE) {
			if (mapInfo.getTile(getX(), i).getTileType() == TileType.EMPTY) {
				return i;
			}
			else if (mapInfo.getTile(getX(), i).getTileType() == TileType.HAZARD) {
				int tempX = resetX(getX() + GlobalSettings.TILE_SIZE);
				setX(tempX);
			}
		}
		return 0; // if it gets to here someone screwed up
	}

}
