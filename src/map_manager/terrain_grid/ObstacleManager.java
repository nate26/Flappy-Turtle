package map_manager.terrain_grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import map_manager.MMSettings;
import map_manager.TileImage;

/**
 * Aggregates all the obstacles in one place and manages applying them.
 * 
 * @author Brian Towne
 *
 */
class ObstacleManager implements ObstacleManagerModifiable {
	List<Obstacle> obstacles = new ArrayList<>();
	private Random rng = new Random(MMSettings.SEED);
	private int groundHeight = MMSettings.GROUND_DEFAULT;
	private TerrainColumn currentColumn;
	private int columnNumber = 0;
	private int maxHeight;
	
	/**
	 * Adds all the obstacles to the aggregation.
	 */
	ObstacleManager() {
		obstacles.add(new Volcano(rng,this));
		obstacles.add(new ModifyGround(rng,this));
		obstacles.add(new Spikes(rng,this));
		obstacles.add(new Pit(rng, this));
		obstacles.add(new Platform(rng,this));
		resetHeightMax();
	}
	
	/**
	 * Applies all the obstacles to the given column.
	 * 
	 * @param column The column to the applied.
	 * @return the column parameter.
	 */
	TerrainColumn applyObsticles(TerrainColumn column) {
		currentColumn = column;
		columnNumber++;
		resetColumn();
		for(Obstacle i:obstacles) {
			if(!i.isLocked()) {
				i.update(column);
			}
		}
		return column;
	}

	@Override
	public void setGroundHeight(int height){
		this.groundHeight = height;
	}
	
	@Override
	public int getGroundHeight() {
		return groundHeight;
	}
	
	@Override
	public void resetColumn() {
		for(int i = 0;i < currentColumn.getHeight();i++) {
			if(i < MMSettings.TERRAIN_HEIGHT - groundHeight) {
				currentColumn.setTile(i, TileImage.EMPTY);
			}else if(i == MMSettings.TERRAIN_HEIGHT - groundHeight) {
				currentColumn.setTile(i, TileImage.GRASS);
			}else {
				currentColumn.setTile(i, TileImage.DIRT);
			}
		}
	}
	
	@Override
	public void clearColumn() {
		for(int i = 0;i < currentColumn.getHeight();i++) {
			currentColumn.setTile(i, TileImage.EMPTY);
		}
	}

	@Override
	public void lock(boolean lock, Class<? extends Obstacle> target) {
		boolean targetFound = false;
		for(Obstacle i:obstacles) {
			if(target.isInstance(i)) {
				i.lock(lock);
				targetFound = true;
			}
		}
		if(!targetFound) {
			throw new IllegalStateException("Target " + target + " was not found!");
		}
	}

	@Override
	public void lockAllExcept(boolean lock, Class<? extends Obstacle> exception) {
		boolean exceptionFound = false;
		for(Obstacle i:obstacles) {
			if(!(exception.isInstance(i))) {
				i.lock(lock);
			}else {
				exceptionFound = true;
			}
		}
		if(!exceptionFound) {
			throw new IllegalStateException("Exception " + exception + " was not found!");
		}
	}

	@Override
	public int getColumnNumber() {
		return columnNumber;
	}

	@Override
	public void setHeightMax(int height) {
		maxHeight = height;
	}

	@Override
	public void resetHeightMax() {
		maxHeight = MMSettings.GROUND_MAX;
	}

	@Override
	public int getHeightMax() {
		return maxHeight;
	}
}
