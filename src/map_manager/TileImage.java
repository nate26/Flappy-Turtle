package map_manager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import controller.GameImage;

/**
 * Represents the different types of tile images.
 * 
 * @author Brian Towne
 */
public enum TileImage {
	/**
	 * Tiles for the highest part of the ground.
	 */
	GRASS(TileType.SOLID, GameImage.GRASS.getImage()),
	
	/**
	 * Tiles for non surface ground.
	 */
	DIRT(TileType.SOLID, GameImage.DIRT.getImage()),
	
	/**
	 * Tiles for bridges and platforms.
	 */
	PLANKS(TileType.PLANK, GameImage.PLANK.getImage()),
	
	/**
	 * Tiles for spikes that are facing downwards.
	 */
	SPIKE_DOWN(TileType.HAZARD, GameImage.SPIKE.getImage()),
	
	/**
	 * Tiles for spikes that are facing upwards.
	 */
	SPIKE_UP(TileType.HAZARD, GameImage.SPIKE.getImage()),
	
	/**
	 * A null image tile representing areas that do not otherwise have a tile.
	 */
	EMPTY(TileType.EMPTY, null),
	
	/**
	 * An empty image representing when there should be an underground background tile.
	 */
	CAVE(TileType.EMPTY, null),
	
	/**
	 * An empty image representing a pit underneath the map.
	 */
	PIT(TileType.PIT, null);
	
	private List<BufferedImage> imageList;
	private Iterator<BufferedImage> imageItr;
	private TileType tileType;
	
	/**
	 * Sets the enum with all images that fall under this type.
	 * 
	 * @param imageList A list of {@link BufferedImage}
	 */
	private TileImage(TileType tileType, BufferedImage imageA, BufferedImage ... imageList) {
		List<BufferedImage> finalList = new ArrayList<BufferedImage>();
		finalList.add(imageA);
		finalList.addAll(Arrays.asList(imageList));
		this.imageList = finalList;
		imageItr = this.imageList.iterator();
		this.tileType = tileType;
	}
	
	/**
	 * an image of the enum's type.
	 * 
	 * @return an image of the enum's type.
	 */
	public BufferedImage getImage() {
		if (imageList.get(0) == null) {
			return GameImage.EMPTY.getImage();
		}
		return imageList.get(0);
	}
	
	/**
	 * The {@link TileType} for the tile image.
	 * 
	 * @return The {@link TileType} for the tile image.
	 */
	public TileType getTileType() {
		return tileType;
	}

}
