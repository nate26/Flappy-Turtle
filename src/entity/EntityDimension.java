package entity;

/**
 * Sets all the entity positions and dimensional bounds.
 * 
 * @author Nate Axt
 */
public class EntityDimension {
	
	private EntitySettings settings;
	private int x;
	private int y;
	
	/**
	 * Initialization of entity settings.
	 * 
	 * @param settings - this entity's settings
	 */
	public EntityDimension(EntitySettings settings) {
		this.settings = settings;
	}
	
	/**
	 * Set the entity's x position
	 * 
	 * @param x - new x position
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set the entity's y position
	 * 
	 * @param x - new y position
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Get the left bound of the entity outline.
	 * 
	 * @return entity's left outline
	 */
	public int getLeftBound() {
		return x + settings.getOutlineX();
	}

	/**
	 * Get the right bound of the entity outline.
	 * 
	 * @return entity's right outline
	 */
	public int getRightBound() {
		return getLeftBound() + settings.getOutlineWidth() - 1;
	}

	/**
	 * Get the top bound of the entity outline.
	 * 
	 * @return entity's top outline
	 */
	public int getTopBound() {
		return y + settings.getOutlineY();
	}

	/**
	 * Get the bottom bound of the entity outline.
	 * 
	 * @return entity's bottom outline
	 */
	public int getBottomBound() {
		return getTopBound() + settings.getOutlineHeight() - 1;
	}
	
	/**
	 * Gets the center most pixel of the entity.
	 * 
	 * @return pixel in the center of the entity as [x,y]
	 */
	public int[] getCenter() {
		int xCenter = getLeftBound() + settings.getOutlineWidth() / 2;
		int yCenter = getTopBound() + settings.getOutlineHeight() / 2;
		int[] center = {xCenter, yCenter};
		return center;
	}

	/**
	 * Get the entity's x position
	 * 
	 * @return entity's x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the entity's y position
	 * 
	 * @return entity's y position
	 */
	public int getY() {
		return y;
	}

}
