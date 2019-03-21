package map_manager.terrain_grid;

import java.util.Random;

import map_manager.MMSettings;
import map_manager.TileImage;

public class Volcano extends Obstacle {
	
	private enum VolcanoState {WAITING,LEAD,RISING,PIT,FALLING,TRAIL}
	private VolcanoState state;
	private ObstacleManagerModifiable manager;
	private Random rng;
	private int width;
	private int columns;
	private int startHeight;

	public Volcano(Random rng, ObstacleManagerModifiable manager) {
		super(MMSettings.VOLCANO_FREQ_MIN, MMSettings.VOLCANO_FREQ_MAX, rng);
		this.rng = rng;
		this.manager = manager;
	}

	@Override
	protected void apply(TerrainColumn column) {
		if(!isActive()) {
			active(true);
			width = rng.nextInt(MMSettings.VOLCANO_WIDTH_MAX - MMSettings.VOLCANO_WIDTH_MIN) + MMSettings.VOLCANO_WIDTH_MIN;
			state = VolcanoState.WAITING;
			columns = 0;
		}
		
		switch(state) {
		case WAITING:
			manager.setHeightMax(manager.getGroundHeight());
			if(manager.getGroundHeight() <= MMSettings.VOLCANO_MIN_START) {
				startHeight = manager.getGroundHeight();
				state = VolcanoState.LEAD;
				manager.lockAllExcept(true,this.getClass());
				if(MMSettings.VOLCANO_SPIKES) {
					manager.lock(false, Spikes.class);
				}
				this.apply(column);
			}
			break;
		case LEAD:
			if(columns < MMSettings.VOLCANO_BUFFER) {
				columns++;
			}else {
				columns = 0;
				state = VolcanoState.RISING;
			}
			break;
		case RISING:
			if(manager.getGroundHeight() + 1 >= MMSettings.VOLCANO_TARGET) {
				manager.setGroundHeight(manager.getGroundHeight() + 1);
			}else {
				int amount;
				if(rng.nextBoolean()) {
					amount = 1;
				}else {
					amount = 2;
				}
				manager.setGroundHeight(manager.getGroundHeight() + amount);
			}
			manager.resetColumn();
			if(manager.getGroundHeight() >= MMSettings.VOLCANO_TARGET) {
				state = VolcanoState.PIT;
				if(MMSettings.VOLCANO_SPIKES) {
					manager.lock(true, Spikes.class);
				}
			}
			break;
		case PIT:
			if(columns < width) {
				manager.clearColumn();
				columns++;
				for(int i = 1;i <= MMSettings.VOLCANO_PIT_HEIGHT;i++) {
					column.setTileBottom(i, TileImage.DIRT);
				}
				column.setTileBottom(MMSettings.VOLCANO_PIT_HEIGHT + 1, TileImage.SPIKE_UP);
			}else {
				state = VolcanoState.FALLING;
				if(MMSettings.VOLCANO_SPIKES) {
					manager.lock(false, Spikes.class);
				}
			}
			break;
		case FALLING:
			if(manager.getGroundHeight() - 1 <= startHeight) {
				manager.setGroundHeight(manager.getGroundHeight() - 1);
			}else {
				int amount;
				if(rng.nextBoolean()) {
					amount = 1;
				}else {
					amount = 2;
				}
				manager.setGroundHeight(manager.getGroundHeight() - amount);
			}
			manager.resetColumn();
			if(manager.getGroundHeight() <= startHeight) {
				state = VolcanoState.TRAIL;
				columns = 0;
			}
			break;
		case TRAIL:
			if(columns < MMSettings.VOLCANO_BUFFER) {
				columns++;
			}else {
				active(false);
				manager.lockAllExcept(false, this.getClass());
				manager.resetHeightMax();
			}
			break;
		default:
			throw new IllegalStateException("Invalid Volcano State, State: " + state);
		}
		
	}

}
