package entity;

import java.awt.image.BufferedImage;

/**
 * Deals with all player animations.
 * This includes image animations that set the player images
 * as well as positional animations like falling and jumping.
 * 
 * @author Nate Axt
 */
public abstract class EntityAnimation {

	private EntitySettings settings;
	private BufferedImage currImage;
	
	private int movementSpeed;
	
	/**
	 * Sets up parameters, gets the player dimension reference and 
	 * loads all animation images
	 * 
	 * @param settings - entity settings access
	 */
	protected EntityAnimation(EntitySettings settings) {
		this.settings = settings;
		movementSpeed = settings.getMoveAnimationTick();
		
		loadImages();
	}
	
	/**
	 * Runs all animations depending on the collision or entity parameters.
	 * This should be called each tick.
	 */
	protected abstract void run();
	
	/**
	 * Returns the staged image.
	 * 
	 * @return current animated image
	 */
	public BufferedImage getStagedImage() {
		return currImage;
	}
	
	/**
	 * Modifies the tick speed for movement
	 * 
	 * @param mod - modifier for the animation movement speed to be faster or slower
	 */
	public void modifyMovementSpeed(int mod) {
		movementSpeed = settings.getMoveAnimationTick() - 
				mod * settings.getMoveAnimationMultiplier();
	}

	/**
	 * Gets the entity movement speed for walking.
	 * 
	 * @return movementSpeed
	 */
	protected int getMovementSpeed() {
		return movementSpeed;
	}
	
	/**
	 * Sets the next image in the animation.
	 * 
	 * @param image next in line
	 */
	public void setImage(BufferedImage image) {
		currImage = image;
	}
	
	/**
	 * Loads images for each animation sequence.
	 */
	protected abstract void loadImages();
}
