package item;

import java.util.ArrayList;
import java.util.List;

import controller.GlobalSettings;

public class ItemColumn {
	
	private List<GenericItem> column;
	private int height = GlobalSettings.NUM_TILES_HEIGHT;
	
	/**
	 * Creates a new column full of null values.
	 */
	public ItemColumn() {
		column = new ArrayList<GenericItem>();
		for(int i = 0;i < height;i++) {
			column.add(null);
		}
	}
	
	/**
	 * Sets the given item of the column.
	 * 
	 * @param y The location the column that the item must be placed. This must be greater or equal to 0 and less than {@link HEIGHT}.
	 * This counts from the top of the column going down.
	 * @param item The {@link GenericItem} to be set.
	 */
	public void setItem(int y, GenericItem tile){
		if(y < 0 || y >= height) {
			throw new IllegalArgumentException("y must be greater than or equal to 0 or less than HEIGHT \"" + height + "\", y was \"" + y + "\".");
		}
		column.set(y, tile);
	}
	
	/**
	 * Sets the given tile of the column.
	 * 
	 * @param y The location the column that the item must be placed. This must be greater or equal to 0 and less than {@link HEIGHT}.
	 * This counts from the bottom of the column going up.
	 * @param item The {@link GenericItem} to be set.
	 * */
	public void setItemBottom(int y, GenericItem tile){
		setItem(height - y,tile);
	}
	
	/**
	 * The height in items of the column.
	 * 
	 * @return The height in items of the column.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Gets the {@link GenericItem} at the given location.
	 * 
	 * @param y The location from the top of the column for the {@link GenericItem} to return.
	 * @return The {@link GenericItem} at the given location.
	 */
	public GenericItem getItem(int y) {
		return column.get(y);
	}
}
