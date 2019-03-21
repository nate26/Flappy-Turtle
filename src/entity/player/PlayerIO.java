package entity.player;

/**
 * Interface to call IO actions for the Player.
 * 
 * @author Nate Axt
 */
public interface PlayerIO {
	
	/**
	 * Turns on or off player crouching.
	 * 
	 * @param on - true to crouch
	 */
	public void crouchAction(boolean on);

	/**
	 * Activates player jumping.
	 */
	public void jumpAction();

	/**
	 * Turns on or off player acceleration.
	 * 
	 * @param on - true to acceleration
	 */
	public void accelerateAction(boolean on);

	/**
	 * Turns on or off player deceleration.
	 * 
	 * @param on - true to deceleration
	 */
	public void decelerateAction(boolean on);
}
