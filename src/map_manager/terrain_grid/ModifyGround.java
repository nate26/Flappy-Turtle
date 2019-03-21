package map_manager.terrain_grid;

import java.util.Random;

import map_manager.MMSettings;
import map_manager.TileImage;

//TODO Verify Javadocs
/**
 * Varies the level of the ground. 
 * 
 * @author Brian Towne
 *
 */
public class ModifyGround extends Obstacle {
	
	private final int HEIGHT_MIN = MMSettings.GROUND_MIN;
	private final int CHANGE_MIN = MMSettings.GROUND_CHANGE_MIN;
	private final int CHANGE_MAX = MMSettings.GROUND_CHANGE_MAX;
	private final int CHANGE_RANGE = MMSettings.GROUND_CHANGE_MAX - MMSettings.GROUND_CHANGE_MIN + 1;
	
	private Random rng;
	private ObstacleManagerModifiable manager;
	private int changeDelay;
	
	/**
	 * Raises the ground by given amount.
	 * 
	 * @param amount The amount to raise the ground by.
	 */
	private void increaseGround(int amount) {
		if((amount + CHANGE_MIN) + manager.getGroundHeight() >= manager.getHeightMax()) {
			manager.setGroundHeight(manager.getHeightMax());
		}else {
			manager.setGroundHeight(manager.getGroundHeight() + amount + CHANGE_MIN);
		}
	}
	
	/**
	 * lowers the ground by given amount.
	 * 
	 * @param amount The amount to lower the ground by.
	 */
	private void decreaseGround(int amount) {
		if( + manager.getGroundHeight() - (amount + CHANGE_MIN) <= HEIGHT_MIN) {
			manager.setGroundHeight(HEIGHT_MIN);
		}else {
			manager.setGroundHeight(manager.getGroundHeight() - (amount + CHANGE_MIN));
		}
	}
	
	/**
	 * Create a new Ground Obstacle.
	 * 
	 * @param rng The random number generator to be used to vary the terrain.
	 * @param state The state object watching the terrain generator's state.
	 */
	public ModifyGround(Random rng, ObstacleManagerModifiable manager) {
		super(MMSettings.GROUND_FREQ_MIN, MMSettings.GROUND_FREQ_MAX, rng);
		this.rng = rng;
		this.manager = manager;
	}

	@Override
	public void apply(TerrainColumn column) {
		if(manager.getGroundHeight() >= manager.getHeightMax() || rng.nextBoolean()) {
			decreaseGround(rng.nextInt(CHANGE_RANGE));
		} else {
			increaseGround(rng.nextInt(CHANGE_RANGE));
		}
		manager.resetColumn();
	}
}
