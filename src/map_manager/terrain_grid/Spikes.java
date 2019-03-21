package map_manager.terrain_grid;

import java.util.Random;

import map_manager.MMSettings;
import map_manager.TileImage;

public class Spikes extends Obstacle {

	ObstacleManagerModifiable manager;
	Random rng;
	
	public Spikes(Random rng, ObstacleManagerModifiable manager) {
		super(MMSettings.SPIKE_FREQ_MIN,MMSettings.SPIKE_FREQ_MAX,rng);
		this.manager = manager;
		this.rng = rng;
	}

	@Override
	protected void apply(TerrainColumn column) {
		if(manager.getGroundHeight() < column.getHeight()) {
			int location;
			if(rng.nextBoolean() || manager.getGroundHeight() >= MMSettings.TERRAIN_HEIGHT) {
				location = 0;
			}else {
				location = 1;
			}
			column.setTileBottom(manager.getGroundHeight() + location,TileImage.SPIKE_UP);
		}
	}

}
