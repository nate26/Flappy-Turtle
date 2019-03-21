package map_manager.terrain_grid;

import java.util.ArrayList;
import java.util.List;

import map_manager.MMSettings;
import map_manager.TileImage;

/**
 * A column of {@link ImageTile}.
 * 
 * @author Brian Towne
 *
 */
public class TerrainColumn {
	
	private List<TileImage> column;
	private int height = MMSettings.TERRAIN_HEIGHT;
	
	/**
	 * Creates a new column full of null values.
	 */
	TerrainColumn() {
		column = new ArrayList<TileImage>();
		for(int i = 0;i < height;i++) {
			column.add(null);
		}
	}
	
	/**
	 * Sets the given tile of the column.
	 * 
	 * @param y The location the column that the image must be placed. This must be greater or equal to 0 and less than {@link HEIGHT}.
	 * This counts from the top of the column going down.
	 * @param tile The {@link TileImage} to be set.
	 */
	void setTile(int y, TileImage tile){
		if(y < 0 || y >= height) {
			throw new IllegalArgumentException("y must be greater than or equal to 0 or less than HEIGHT \"" + height + "\", y was \"" + y + "\".");
		}
		column.set(y, tile);
	}
	
	/**
	 * Sets the given tile of the column.
	 * 
	 * @param y The location the column that the image must be placed. This must be greater or equal to 0 and less than {@link HEIGHT}.
	 * This counts from the bottom of the column going up.
	 * @param tile The {@link TileImage} to be set.
	 * */
	void setTileBottom(int y, TileImage tile){
		setTile(height - y,tile);
	}
	
	/**
	 * The height in tiles of the column.
	 * 
	 * @return The height in tiles of the column.
	 */
	int getHeight() {
		return height;
	}
	
	/**
	 * Gets the {@link TileImage} at the given location.
	 * 
	 * @param y The location from the top of the column for the {@link TileImage} to return.
	 * @return The {@link TileImage} at the given location.
	 */
	TileImage getTile(int y) {
		return column.get(y);
	}

}
