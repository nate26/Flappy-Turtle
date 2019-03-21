package map_manager;

import java.awt.image.BufferedImage;

import controller.GameImage;
import controller.GlobalSettings;
import controller.Updatable;
import map_manager.terrain_grid.TerrainGrid;

/**
 * Manages the terrain and scrolling and returns current information about the terrain.
 * 
 * @author Brian Towne
 *
 */
public class GeneralMapManager implements Updatable {

	private TerrainGrid grid = new TerrainGrid(MMSettings.INITIAL_TILES);
	private int distance = 0;
	
	/**
	 * Gets the {@link TileType} of the given tile at coordinates of the current frame.
	 * @param x The distance in pixels from the left of the frame.
	 * @param y The distance in pixels from the top of the frame.
	 * @return The {@link TileType} of the frame.
	 */
	public TileImage getTile(int x, int y) {
		if (x / GlobalSettings.TILE_SIZE < 0 || 
				x / GlobalSettings.TILE_SIZE > GlobalSettings.WINDOW_WIDTH || 
				y / GlobalSettings.TILE_SIZE < 0 ||
				y / GlobalSettings.TILE_SIZE > GlobalSettings.WINDOW_HEIGHT) {
			return TileImage.EMPTY;
		}
		return grid.getTile((x + distance) / GlobalSettings.TILE_SIZE, 
				y / GlobalSettings.TILE_SIZE);
	}
	
	/**
	 * Gets the offset of the map scroll.
	 * 
	 * @return the map tile offset.
	 */
	public int getOffset() {
		return distance % GlobalSettings.TILE_SIZE;
	}

	/**
	 * @return The {@link BufferedImage} for the terrain of the current frame.
	 */
	public BufferedImage getTerrain() {
		return grid.getCropMap(distance, distance + MMSettings.TERRAIN_WIDTH*GlobalSettings.TILE_SIZE);
	}

	@Override
	public void update() {
		if (distance < grid.getTileWidth()*grid.getWidth() - GlobalSettings.WINDOW_WIDTH) {
			distance += MMSettings.SCROLL_SPEED;
		}
	}
}
