package entity;

import controller.GlobalSettings;

/**
 * Includes all the player movement actions.
 * 
 * @author Nate Axt
 */
public class EntityMovement {

	private EntityDimension eDim;
	private EntityCollisionHandler collision;
	private EntitySettings settings;
	
	private int airTime;
	private int direction;	
	private int moveMod;
	private int pushMod;
	
	
	/**
	 * Sets up player access for dimensions and collisions.
	 * 
	 * @param pDim - Player Dimension access
	 * @param collision - Collision Handler access
	 */
	public EntityMovement(EntityDimension pDim, 
			EntityCollisionHandler collision, EntitySettings settings) {
		this.eDim = pDim;
		this.collision = collision;
		this.settings = settings;
		airTime = 0;
		direction = 1;
		moveMod = 0;
		pushMod = 0;
	}

	/**
	 * Runs movement actions for each tick.
	 */
	public void run() {
		if (canMoveVertically()) {
			verticalMovement();
		}
		else {
			airTime = 0;
			direction = 1;
		}
		
		// Accelerating or Decelerating
		if ((eDim.getX() < settings.getMaxDistanceRight() && moveMod > 0) ||
			(eDim.getX() > settings.getMaxDistanceLeft() && moveMod < 0)) {
			horizontalMovement();
		}
		
		if (eDim.getX() > settings.getMaxDistanceLeft() && pushMod > 0) {
			push();
		}
	}
	
	/**
	 * Calls independently from run() because this animation is only
	 * cast while the game is paused.
	 */
	public void fall() {
		airTime = 0;
		direction = 1;
		verticalMovement();
	}
	
	/**
	 * Calls independently from run() because this animation is only
	 * cast when the player clicks to jump.
	 */
	public void jump() {
		if (airTime == 0) {
			direction = -1;
			verticalMovement();
		}
	}
	
	/**
	 * Checks to see if the player can jump or fall.
	 * 
	 * @return true of the player has no jump or fall obstacle 
	 */
	private boolean canMoveVertically() {
		Collision nextTile = Collision.NONE;
		if (direction > 0) { //Fall
			nextTile = collision.getBelow();
		}
		else { //Jump
			nextTile = collision.getAbove();
		}
		if (nextTile != Collision.GROUND && nextTile != Collision.PIT &&
				(direction < 0 || nextTile != Collision.PLANK)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Calculates and sets the velocity to move vertically each tick.
	 */
	private void verticalMovement() {
		if (airTime == 0) {
			airTime = 1;
		}
		
		int velocity = getVelocity();
		velocity = closeToCollide(velocity);
		
		eDim.setY(eDim.getY() + velocity * direction);
		airTime++;
		
		// If you hit max height for jump then you switch direction to fall
		if (velocity == 0 && direction < 0) {
			direction = 1;
			airTime = 0;
		}
	}
	
	/**
	 * Calculates speed from gravity mechanics.
	 * Speed is represented as pixels traveled per tick.
	 * 
	 * @param direction - 1 if up, -1 if down
	 * @return speed of movement via pixels per tick
	 */
	private int getVelocity() {
		int v0 = 0;
		if (direction < 0) {
			v0 = settings.getStartJumpSpeed();
		}
		float time = (airTime * GlobalSettings.TIME_UNIT) / 1000F;
		int v1 = (int) Math.round((time * 9.81));
		return v0 + v1 * direction;
	}
	
	/**
	 * Checks if a player will interact with a solid ground within
	 * the next animation tick.
	 * 
	 * @param v - pixel distance for the next tick
	 * @return the next pixels per tick 
	 */
	private int closeToCollide(int v) {
		int tempV = v;
		int distToCollide = collision.getDistanceToGround();
		if (direction < 0) distToCollide = collision.getDistanceToCeiling();
		if (distToCollide < v) {
			tempV = distToCollide;
		}
		return tempV;
	}

	/**
	 * Moves the player to the left or right of the screen.
	 */
	private void horizontalMovement() {
		eDim.setX(eDim.getX() + moveMod);
	}

	/**
	 * Sets how fast the player will move.
	 * 
	 * @param mod - speed addition to how far the player moves each tick
	 */
	public void setMovementMod(int mod) {
		moveMod = mod;
	}
	
	/**
	 * Get movement modifier: 1, 0 or -1
	 * 
	 * @return moveMod
	 */
	public int getMovementMod() {
		return moveMod;
	}

	/**
	 * Pushes the player to the left of the screen.
	 */
	private void push() {
		eDim.setX(eDim.getX() - pushMod);
	}

	/**
	 * Sets how fast the user is pushed to the left.
	 * 
	 * @param mod - speed addition to how far the player moves each tick
	 */
	public void setPushMod(int mod) {
		pushMod = mod;
	}
	
	/**
	 * Checks if the player is in the air.
	 * 
	 * @return if the player is in the air
	 */
	public boolean isInAir() {
		return canMoveVertically();
	}
}
