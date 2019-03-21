package map_manager.terrain_grid;

import java.util.Random;

import map_manager.MMSettings;

public class Pit extends Obstacle {

	Random rng;
	ObstacleManagerModifiable manager;
	int columns;
	int length;
	
	public Pit(Random rng, ObstacleManagerModifiable manager) {
		super(MMSettings.PIT_FREQ_MIN, MMSettings.PIT_FREQ_MAX, rng);
		this.rng = rng;
		this.manager = manager;
	}

	@Override
	protected void apply(TerrainColumn column) {
		if(!isActive()) {
			active(true);
			columns = 0;
			length = rng.nextInt(MMSettings.PIT_MAX - MMSettings.GROUND_MIN) + MMSettings.GROUND_MIN;
		}

		manager.clearColumn();
		
		columns++;
		if(columns == length) {
			active(false);
		}
	}

}
