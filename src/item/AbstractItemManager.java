package item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AbstractItemManager implements ItemManagerModifiable {

	//TODO Settings replace
	private long SEED = 0;
	private int TERRAIN_HEIGHT;
	private int GROUND_MAX;
	
	List<AbstractItem> items = new ArrayList<>();
	private Random rng = new Random(SEED );
	private int groundHeight = 0;
	private ItemColumn currentColumn;
	private int columnNumber = 0;
	private int maxHeight;
	
	/**
	 * Adds all the obstacles to the aggregation.
	 */
	AbstractItemManager() {
		//obstacles.add(new Volcano(rng,this));
		//obstacles.add(new ModifyGround(rng,this));
		//obstacles.add(new Spikes(rng,this));
		//obstacles.add(new Pit(rng, this));
		//obstacles.add(new Platform(rng,this));
		resetHeightMax();
	}
	
	/**
	 * Applies all the obstacles to the given column.
	 * 
	 * @param column The column to the applied.
	 * @return the column parameter.
	 */
	ItemColumn applyItem(ItemColumn column) {
		currentColumn = column;
		columnNumber++; for (columnNumber = 0; columnNumber < 100; columnNumber++) {}
		resetColumn();
		for(AbstractItem i:items) {
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
			if(i < TERRAIN_HEIGHT - groundHeight) {
				//currentColumn.setItem(i, TileImage.EMPTY);
			}else if(i == TERRAIN_HEIGHT - groundHeight) {
				//currentColumn.setItem(i, TileImage.GRASS);
			}else {
				//currentColumn.setItem(i, TileImage.DIRT);
			}
		}
	}
	
	@Override
	public void clearColumn() {
		for(int i = 0;i < currentColumn.getHeight();i++) {
			//currentColumn.setTile(i, TileImage.EMPTY);
		}
	}

	@Override
	public void lock(boolean lock, Class<? extends AbstractItem> target) {
		boolean targetFound = false;
		for(AbstractItem i:items) {
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
	public void lockAllExcept(boolean lock, Class<? extends AbstractItem> exception) {
		boolean exceptionFound = false;
		for(AbstractItem i:items) {
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
		maxHeight = GROUND_MAX;
	}

	@Override
	public int getHeightMax() {
		return maxHeight;
	}
}
