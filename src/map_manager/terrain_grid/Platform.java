package map_manager.terrain_grid;

import java.util.Random;

import map_manager.MMSettings;
import map_manager.TileImage;

public class Platform extends Obstacle{
	
	private ObstacleManagerModifiable manager;
	private Random rng;
	private int length;
	private int height;
	private int columns;
	
	private static final int FREQ_MARGIN = 100;

	public Platform(Random rng, ObstacleManagerModifiable manager) {
		super(MMSettings.PLATFORM_FREQ_MIN, MMSettings.PLATFORM_FREQ_MAX, rng);
		this.rng = rng;
		this.manager = manager;
	}

	
	
	@Override
	protected void apply(TerrainColumn column) {
		if(!isActive()) {
			active(true);
			length = rng.nextInt(MMSettings.PLATFORM_LENGTH_MAX - MMSettings.PLATFORM_LENGTH_MIN) + MMSettings.PLATFORM_LENGTH_MIN;
			height = rng.nextInt(MMSettings.PLATFORM_HEIGHT_MAX - MMSettings.PLATFORM_HEIGHT_MIN) + MMSettings.PLATFORM_HEIGHT_MIN 
																								  + 1 
																								  + manager.getGroundHeight();
			columns = 0;
		}
		
		if(height <= manager.getGroundHeight() || height >= MMSettings.TERRAIN_HEIGHT) {
			active(false);
		}else{
			TileImage tile;
			if(rng.nextInt(FREQ_MARGIN + 1) < FREQ_MARGIN * MMSettings.PLATFORM_PLANK_FREQ) {
				tile = TileImage.PLANKS;
			}else {
				if(MMSettings.PLATFORM_SPIKES && (rng.nextInt(FREQ_MARGIN) + 1) < FREQ_MARGIN * MMSettings.PLATFORM_SPIKES_FREQ) {
					column.setTileBottom(height + 1, TileImage.SPIKE_UP);
				}
				tile = TileImage.DIRT;
			}
			column.setTileBottom(height, tile);
		}
		
		columns++;
		if(columns==length) {
			active(false);
		}
		
	}

}
