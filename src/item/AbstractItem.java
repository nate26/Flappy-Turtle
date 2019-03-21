package item;

import java.util.Random;

public abstract class AbstractItem {
	private int delay = 0;
	private int minFreq;
	private int maxFreq;
	private boolean active = false;
	private boolean lock = false;
	private Random rng;
	
	
	/**
	 * Creates a new abstract item.
	 * 
	 * @param minFreq The minimum amount of tiles that need to pass before the abstract item will appear again.
	 * @param maxFreq The maximum amount of tiles that need to pass before the abstract item will appear again.
	 * @param rng
	 */
	public AbstractItem(int minFreq, int maxFreq, Random rng) {
		if(minFreq < 0 || maxFreq < 0 || minFreq > maxFreq) {
			throw new IllegalArgumentException("minFreq and maxFreq must be greater than or equal to zero and minFreq must be less than or equal to maxFreq, minFreq = \"" + minFreq + "\" maxFreq = \"" + maxFreq + "\"");
		}
		this.rng = rng;
		this.minFreq = minFreq;
		this.maxFreq = maxFreq;
	}
	
	/**
	 * Either applies the abstract item to the given column if the obstacle is ready.
	 * @param column the column to be applied too.
	 */
	public final void update(ItemColumn column) {
		if(active || delay > maxFreq || (delay >= minFreq && (rng.nextInt(maxFreq-minFreq + 1) == 0))) {
			delay = 0;
			apply(column);
		}else {
			delay++;
		}
	}
	
	/**
	 * Applies the abstract item to the given column.
	 * 
	 * @param column The column to be applied to.
	 */
	protected abstract void apply(ItemColumn column);
	
	protected void active(boolean state) {
		active = state;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isLocked() {
		return lock;
	}
	
	public void lock(boolean lock) {
		this.lock = lock;
	}
}
