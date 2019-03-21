package item;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import controller.GlobalSettings;

public class ItemGrid {
	
	private List<ItemColumn> grid = new ArrayList<>();
	private AbstractItemManager itemGen = new AbstractItemManager();
	
	/**
	 * Marks if the item grid has been modified by adding or removing a column.
	 */
	boolean change = true;
	
	/**
	 * Creates a new ItemGrid with the specified amount of columns generated.
	 * 
	 * @param width The amount of columns to generate.
	 */
	public ItemGrid(int width) {
		generateColumn(width);
	}
	
	/**
	 * Gets the width of the grid in tiles.
	 * 
	 * @return The amount of columns in the grid.
	 */
	public int getWidth() {
		return grid.size();
	}
	
	/**
	 * Gets the height of the grid in items.
	 * 
	 * @return The height of the columns in the grid.
	 */
	public int getHeight() {
		return GlobalSettings.NUM_TILES_HEIGHT;
	}
	
	/**
	 * Gets the width of the items in pixels.
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
	 * Gets the item at the given location. Coordinates are calculated from the top left.
	 * 
	 * @param x The x coordinate of the item.
	 * @param y The y coordinate of the item. 
	 * @return The (@link GenericItem) of the given item.
	 */
	public GenericItem getItem(int x, int y) {
		return grid.get(x).getItem(y);
	}
	
	/**
	 * Generate a new column on the right side of the grid.
	 * 
	 * @return this.
	 */
	public ItemGrid generateColumn() {
		grid.add(itemGen.applyItem(new ItemColumn()));
		change = true;
		return this;
	}
	
	/**
	 * Generate a number of new columns on the right side of the grid.
	 * 
	 * @param amount The amount of columns to generate.
	 * @return this.
	 */
	public ItemGrid generateColumn(int amount) {
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
	public ItemGrid removeColumn() {
		grid.remove(0);
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
	public ItemGrid removeColumn(int amount) {
		for(int i = 0;i < amount;i++) {
			removeColumn();
		}
		return this;
	}

}
