package map_manager.terrain_grid;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import controller.GlobalSettings;
import map_manager.MMSettings;
import map_manager.TileImage;

/**
 * Is a grid of {@link TileImage}. Can dynamically create and remove columns of tiles.
 * 
 * @author Brian Towne
 */
public class TerrainGrid {
	
	private List<TerrainColumn> terrain = new ArrayList<>();
	private ObstacleManager obsticles = new ObstacleManager();
	private BufferedImage currentImage;
	
	/**
	 * Marks if the tile grid has been modified by adding or removing a column.
	 */
	boolean change = true;
	
	/**
	 * Creates a new TerrainGrid with the specified amount of columns generated.
	 * 
	 * @param width The amount of columns to generate.
	 */
	public TerrainGrid(int width) {
		generateColumn(width);
	}
	
	/**
	 * Gets the width of the grid in tiles.
	 * 
	 * @return The amount of columns in the grid.
	 */
	public int getWidth() {
		return terrain.size();
	}
	
	/**
	 * Gets the height of the grid in tiles.
	 * 
	 * @return The height of the columns in the grid.
	 */
	public int getHeight() {
		return MMSettings.TERRAIN_HEIGHT;
	}
	
	/**
	 * Gets the width of the tiles in pixels.
	 * 
	 * @return the width of the tiles in pixels.
	 */
	public int getTileWidth() {
		return GlobalSettings.TILE_SIZE;
	}
	
	/**
	 * Gets the height of the tiles in pixels.
	 * 
	 * @return the height of the tiles in pixels.
	 */
	public int getTileHeight() {
		return GlobalSettings.TILE_SIZE;
	}
	
	/**
	 * Gets the tile at the given location. Coordinates are calculated from the top left.
	 * 
	 * @param x The x coordinate of the tile.
	 * @param y The y coordinate of the tile. 
	 * @return The (@link TileImage) of the given tile.
	 */
	public TileImage getTile(int x, int y) {
		return terrain.get(x).getTile(y);
	}
	
	/**
	 * Generate a new column on the right side of the grid.
	 * 
	 * @return this.
	 */
	public TerrainGrid generateColumn() {
		terrain.add(obsticles.applyObsticles(new TerrainColumn()));
		change = true;
		return this;
	}
	
	/**
	 * Generate a number of new columns on the right side of the grid.
	 * 
	 * @param amount The amount of columns to generate.
	 * @return this.
	 */
	public TerrainGrid generateColumn(int amount) {
		for(int i = 0;i < amount;i++) {
			generateColumn();
		}
		return this;
	}
	
	/**
	 * Removes a column from the left side of the grid.
	 * 
	 * @throws IllegalStateException When there are no more columns in the grid.
	 * @return this.
	 * @see {@link #getColumns()} can be used to check the amount of columns in the grid.
	 */
	public TerrainGrid removeColumn() {
		terrain.remove(0);
		change = true;
		return this;
	}
	
	/**
	 * Removes a number of columns from the left side of the grid.
	 * 
	 * @throws IllegalArgumentException When amount is higher than the amount of columns left in the list.
	 * @param amount The amount of columns to remove.
	 * @return this.
	 * @see {@link #getColumns()} can be used to check the amount of columns in the grid.
	 */
	public TerrainGrid removeColumn(int amount) {
		for(int i = 0;i < amount;i++) {
			removeColumn();
		}
		return this;
	}
	
	/**
	 * @return a {@link BufferedImage} of the current state of the TerrainGrid.
	 */
	public BufferedImage getFullMap() {
		if(change) {
			currentImage = new BufferedImage(getWidth()*getTileWidth(),
					 getHeight()*getTileHeight(),
					 BufferedImage.TYPE_INT_ARGB);
			Graphics2D canvas = currentImage.createGraphics();
			for(int x = 0;x < getWidth();x++) {
				for(int y = 0;y < getHeight();y++) {
					canvas.drawImage(getTile(x, y).getImage(), 
							x * getTileWidth(), 
							y * getTileHeight(), 
							getTileWidth(), 
							getTileHeight(), 
							null);
				}
			}
			change = false;
		}
		return currentImage;
	}
	
	/**
	 * Gets a section of the full map of the TerrainGrid. Used for scrolling
	 * 
	 * @param start The start in pixels of the submap.
	 * @param end The end in pixels of the submap.
	 * @return A section of the full map between start and end.
	 */
	public BufferedImage getCropMap(int start,int end) {
		BufferedImage fullMap = getFullMap();
		return fullMap.getSubimage(start, 0, end-start, fullMap.getHeight());
	}
}
