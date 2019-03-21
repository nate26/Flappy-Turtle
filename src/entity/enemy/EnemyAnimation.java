package entity.enemy;

import controller.AnimationLibrary;
import entity.Collision;
import entity.EntityAnimation;
import entity.EntityMovement;
import tools.AnimationSequence;

public class EnemyAnimation extends EntityAnimation {

	private EntityMovement eMove;
	private EnemyCollisionHandler collision;
	
	private AnimationSequence walkingLeftAnimation;
	private AnimationSequence walkingRightAnimation;
	private AnimationSequence jumpingStartAnimation;
	private AnimationSequence jumpingEndAnimation;
	
	private boolean falling;
	private boolean jumping;

	public EnemyAnimation(EntityMovement eMove, EnemyCollisionHandler collision) {
		super(new EnemySettings());
		this.eMove = eMove;
		this.collision = collision;
		jumping = false;//TODO jumping/falling never set true
		falling = false;
		
		loadImages();
	}

	@Override
	protected void run() {
		// Fall
		if (collision.getBelow() != Collision.GROUND && collision.getBelow() != Collision.PLANK) {
			if (collision.getDistanceToGround() < EnemySettings.JUMP_END_ANIMATION_DISTANCE) {
				jumpingEndAnimation.next(EnemySettings.MOVE_ANIMATION_TICK);
			}
		}
		else {
			falling = false;
		}
				
		// Jump
		if (collision.getAbove() != Collision.GROUND) {
			if (jumping && !falling) {
				jumpingStartAnimation.next(EnemySettings.MOVE_ANIMATION_TICK);
			}
		}
		else {
			jumping = false;
		}
			
		// Walking or Crouching
		if (collision.checkForCollision() != Collision.PUSH &&
				(collision.getBelow() == Collision.GROUND || collision.getBelow() == Collision.PLANK)){
			if (eMove.getMovementMod() < 0) {
				walkingLeftAnimation.next(getMovementSpeed());
			}
			else {
				walkingRightAnimation.next(getMovementSpeed());
			}
		}
	}

	@Override
	protected void loadImages() {
		walkingLeftAnimation = new AnimationSequence(this::setImage, AnimationLibrary.ENEMY_LEFT.getAnimation());

		walkingRightAnimation = new AnimationSequence(this::setImage, AnimationLibrary.ENEMY_RIGHT.getAnimation());

		jumpingStartAnimation = new AnimationSequence(this::setImage, AnimationLibrary.ENEMY_JUMP_START.getAnimation());

		jumpingEndAnimation = new AnimationSequence(this::setImage, AnimationLibrary.ENEMY_JUMP_END.getAnimation());
	}

}
