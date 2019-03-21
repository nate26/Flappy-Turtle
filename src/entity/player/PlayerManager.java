package entity.player;

import java.awt.image.BufferedImage;

import controller.AnimationLibrary;
import controller.GlobalSettings;
import controller.Updatable;
import entity.Collision;
import entity.EntityMovement;
import map_manager.MapGettable;
import map_manager.TileGettable;
import menu.MenuGettable;

/**
 * Manages all the player information and actions.
 * 
 * @author Nate Axt
 */
public class PlayerManager implements Updatable, PlayerEffectable, PlayerData, PlayerIO {

	private PlayerDimension pDim;
	private PlayerCollisionHandler collision;
	private EntityMovement pMove;
	private PlayerHealth pHealth;
	private PlayerAnimation animator;
	private BufferedImage image = AnimationLibrary.PLAYER_WALKING.getAnimation().get(0);
	
	private MenuGettable menuGetter;
	
	private boolean invulnerable = false;
	private int invulnTick = 0;
	private boolean crouching = false;
	private boolean starpower = false;
	private int starpowerTicks = 0;
	private boolean right, left = false;

	/**
	 * Creates player classes.
	 * 
	 * @param tileGetter - live access to map tiles
	 */
	public PlayerManager(MenuGettable menuGetter, TileGettable tileGetter, MapGettable offsetGetter) {
		this.menuGetter = menuGetter;
		pDim = new PlayerDimension(tileGetter);
		collision = new PlayerCollisionHandler(pDim, tileGetter, offsetGetter, this::getPlayerImage);
		pMove = new EntityMovement(pDim, collision, new PlayerSettings());
		pHealth = new PlayerHealth();
		animator = new PlayerAnimation(collision);
	}
	
	/**
	 * Run at every tick to check the player status, detect collision, 
	 * set images and load data for the engine.
	 */
	@Override
	public void update() {
		if (pHealth.getHealth() <= 0) {
			menuGetter.playerDead();
		}
		
		isPushed();
		if (collision.isPushed(GlobalSettings.COLLISION_BUFFER) && right) {
			pMove.setMovementMod(0);
		}
		else 
		if (collision.isBlocked(GlobalSettings.COLLISION_BUFFER) && left) {
			pMove.setMovementMod(0);
		}
		
		if (!invulnerable && !starpower) {
			collisionDamage();
		}
		else {
			if (starpower) {
				if (starpowerTicks == 0) {
					starpower = false;
					animator.setStarpower(false);
				}
				starpowerTicks--;
			}
			if (invulnerable) {
				if (invulnTick == PlayerSettings.INVULNERABLE_TIME) {
					invulnerable = false;
					animator.setInvulnerable(false);
					invulnTick = 0;
				}
				invulnTick++;
			}
		}

		animator.run();
		pMove.run();
		
		image = animator.getStagedImage();
	}
	
	/**
	 * Does damage if the player comes in collision with a Tile.
	 */
	private void collisionDamage() {
		switch (collision.checkForCollision()) {
			case FIRE:
				pHealth.damaged(Collision.FIRE.getDamage());
				invulnerable = true;
				animator.setInvulnerable(true);
				break;
			case PIT:
				pHealth.damaged(Collision.PIT.getDamage());
				if (pHealth.getHealth() <= 0) {
					menuGetter.playerDead();
				}
				else {
					menuGetter.playerRespawn();
					pDim.resetPos();
				}
				break;
			case SPIKE:
				if (crouching) break;
				pHealth.damaged(Collision.SPIKE.getDamage());
				invulnerable = true;
				animator.setInvulnerable(true);
				break;
			default:
				//Do nothing
		}

		if (collision.isSmushed()) {
			pHealth.damaged(Collision.SMUSH.getDamage());
			menuGetter.playerRespawn();
			pDim.resetPos();
		}
	}
	
	/**
	 * Pushes the player if there is ground to the right.
	 */
	private void isPushed() {
		if (collision.isPushed()) {
			pMove.setPushMod(GlobalSettings.SPEED);
		}
		else {
			pMove.setPushMod(0);
		}
	}
	
	/**
	 * Resets the player's position and unpauses the game after the player is set.
	 */
	protected void respawn() {
		pMove.fall();
	}
	
	/**
	 * Gets the player dimension.
	 * 
	 * @return pDim
	 */
	public PlayerDimension getPlayerDimension() {
		return pDim;
	}
	
	/**
	 * Gets the PlayerAnimator for testing.
	 * 
	 * @return player instance of PlayerAnimator
	 */
	protected PlayerAnimation getAnimator() {
		return animator;
	}

	/**
	 * Heals the player a certain amount of hearts.
	 * 
	 * @param halfHearts to heal up to
	 */
	@Override
	public void eatFood(int halfHearts) {
		pHealth.healed(halfHearts);
	}

	/**
	 * Sets the player invulnerable until the given ticks pass.
	 * Sets the invulnTick to a negative number to increase the time until hitting
	 * the invulnerable time length.
	 * 
	 * @param ticks of being invulnerable
	 */
	@Override
	public void invulnerableFor(int ticks) {
		invulnTick = -1 * (ticks - PlayerSettings.INVULNERABLE_TIME);
		invulnerable = true;
		animator.setInvulnerable(true);
	}

	/**
	 * Sets the player on starpower until the given ticks pass.
	 * 
	 * @param ticks of being on starpower
	 */
	@Override
	public void starpowerFor(int ticks) {
		starpower = true;
		starpowerTicks = ticks;
		animator.setStarpower(true);
	}

	//-------------------------------------------------------------------------
	
	/**
	 * Turns on or off player crouching.
	 * 
	 * @param on - true to crouch
	 */
	@Override
	public void crouchAction(boolean on) {
		crouching = on;
		animator.setCrouching(on);
	}

	/**
	 * Activates player jumping.
	 */
	@Override
	public void jumpAction() {
		if (!crouching) {
			pMove.jump();
		}
	}

	/**
	 * Turns on or off player acceleration.
	 * 
	 * @param on - true to acceleration
	 */
	@Override
	public void accelerateAction(boolean on) {System.out.println(on && !collision.isPushed()); //TODO remove print
		right = on;
		if (on && !collision.isPushed()) { //account for pixel addition and buffer should be 1-2px
			animator.modifyMovementSpeed(1);
			if (pMove.isInAir()) {
				pMove.setMovementMod(PlayerSettings.MOVE_PPT_MODIFIER_AIR);
			}
			else {
				pMove.setMovementMod(PlayerSettings.MOVE_PPT_MODIFIER);
			}
		}
		else {
			animator.modifyMovementSpeed(0);
			pMove.setMovementMod(0);
		}
	}

	/**
	 * Turns on or off player deceleration.
	 * 
	 * @param on - true to deceleration
	 */
	@Override
	public void decelerateAction(boolean on) {
		left = on;
		if (on && !collision.isBlocked()) {
			animator.modifyMovementSpeed(-1);
			if (pMove.isInAir()) {
				pMove.setMovementMod(-PlayerSettings.MOVE_PPT_MODIFIER_AIR);
			}
			else {
				pMove.setMovementMod(-PlayerSettings.MOVE_PPT_MODIFIER);
			}
		}
		else {
			animator.modifyMovementSpeed(0);
			pMove.setMovementMod(0);
		}	
	}
	
	//-------------------------------------------------------------------------

	@Override
	public int getPlayerX() {
		return pDim.getX();
	}

	@Override
	public int getPlayerY() {
		return pDim.getY();
	}

	@Override
	public BufferedImage getPlayerImage() {
		return image;
	}

	@Override
	public int getHealthX() {
		return pHealth.getBar().getX();
	}

	@Override
	public int getHealthY() {
		return pHealth.getBar().getY();
	}

	@Override
	public BufferedImage getHealthImage() {
		return pHealth.getBar().getImage();
	}
	
	
}
