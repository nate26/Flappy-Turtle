package map_manager;

/**
 * {@link TileType}s can be obtained by this class.
 * 
 * @author Brian Towne
 *
 */
public interface TileGettable {
	/**
	 * Gets the {@link TileType} at the given coordinates.
	 * 
	 * @param x The x coordinate from the top left.
	 * @param y The y coordinate from the top left.
	 * @return The {@link TileType} at the given coordinates.
	 */
	public TileImage getTile(int x, int y);
	
}
