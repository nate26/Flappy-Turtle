package entity.player;

import controller.AnimationLibrary;
import entity.Collision;
import entity.EntityAnimation;
import tools.AnimationSequence;

/**
 * Deals with all player animations.
 * This includes image animations that set the player images
 * as well as positional animations like falling and jumping.
 * 
 * @author Nate Axt
 */
public class PlayerAnimation extends EntityAnimation {

	private PlayerCollisionHandler collision;
	
	private AnimationSequence walkingAnimation;
	private AnimationSequence spinningAnimation;
	private AnimationSequence spinningJumpAnimation;
	private AnimationSequence jumpingStartAnimation;
	private AnimationSequence jumpingEndAnimation;
	private AnimationSequence invulnerableAnimation;
	
	private boolean crouching;
	private boolean invulnerable;
	private boolean falling;
	private boolean jumping;
	
	/**
	 * Sets up parameters, gets the player dimension reference and 
	 * loads all animation images
	 * 
	 * @param collision - player collision info
	 */
	protected PlayerAnimation(PlayerCollisionHandler collision) {
		super(new PlayerSettings());
		this.collision = collision;
		jumping = false;//TODO jumping/falling never set true
		falling = false;
		
		loadImages();
	}
	
	/**
	 * Runs all animations depending on the collision or player parameters.
	 * This should be called each tick.
	 */
	@Override
	protected void run() {
		// Fall
		if (collision.getBelow() != Collision.GROUND && collision.getBelow() != Collision.PLANK) {
			if (collision.getDistanceToGround() < PlayerSettings.JUMP_END_ANIMATION_DISTANCE) {
				jumpingEndAnimation.next(PlayerSettings.MOVE_ANIMATION_TICK);
			}
		}
		else {
			falling = false;
		}
		
		// Jump
		if (collision.getAbove() != Collision.GROUND) {
			if (jumping && !falling) {
				jumpingStartAnimation.next(PlayerSettings.MOVE_ANIMATION_TICK);
			}
		}
		else {
			jumping = false;
		}
		
		// Walking or Crouching
		if (collision.checkForCollision() != Collision.PUSH) {
			if (crouching) {
				if (jumping || falling) {
					spinningJumpAnimation.next(PlayerSettings.MOVE_ANIMATION_TICK);
				}
				else {
					spinningAnimation.next(getMovementSpeed());
				}
			}
			else if (collision.getBelow() == Collision.GROUND || collision.getBelow() == Collision.PLANK){
				walkingAnimation.next(getMovementSpeed());
			}
		}
		
		// Invulnerable
		if (invulnerable) {
			invulnerableAnimation.next(PlayerSettings.INVULNERABLE_ANIMATION_TICK);
		}
	}
	
	/**
	 * Sets if the player is crouching.
	 * 
	 * @param crouching - true if crouching
	 */
	protected void setCrouching(boolean crouching) {
		this.crouching = crouching;
	}
	
	/**
	 * Sets if the player is invulnerable.
	 * 
	 * @param invulnerable - true if invulnerable
	 */
	protected void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}
	
	/**
	 * Sets if the player is on starpower.
	 * 
	 * @param starpower - true if starpower
	 */
	protected void setStarpower(boolean starpower) {
		if (starpower) {
			walkingAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_WALKING_THUG.getAnimation());
			spinningAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_SPINNING_THUG.getAnimation());
			spinningJumpAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_SPINNING_JUMP_THUG.getAnimation());
			jumpingStartAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_JUMP_START_THUG.getAnimation());
			jumpingEndAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_JUMP_END_THUG.getAnimation());
		}
		else {
			loadImages();
		}
	}

	
	/**
	 * Loads images for each animation sequence.
	 */
	@Override
	protected void loadImages() {
		walkingAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_WALKING.getAnimation());
		spinningAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_SPINNING.getAnimation());
		spinningJumpAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_SPINNING_JUMP.getAnimation());
		jumpingStartAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_JUMP_START.getAnimation());
		jumpingEndAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_JUMP_END.getAnimation());
		invulnerableAnimation = new AnimationSequence(this::setImage, AnimationLibrary.PLAYER_INVULNERABLE.getAnimation());
	}
}
