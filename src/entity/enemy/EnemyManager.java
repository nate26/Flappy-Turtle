package entity.enemy;

import java.awt.image.BufferedImage;

import controller.GameImage;
import controller.GlobalSettings;
import controller.Updatable;
import entity.EntityMovement;
import entity.player.PlayerDimension;
import map_manager.MapGettable;
import map_manager.TileGettable;

/**
 * Manages all the enemy information and actions.
 * 
 * @author Nate Axt
 */
public class EnemyManager implements Updatable, EnemyData {

	private Runnable removeEnemy;
	private EnemyAI eAI;
	private EnemyDimension eDim;
	private EnemyCollisionHandler collision;
	private EntityMovement eMove;
	private EnemyAnimation animator;
	private BufferedImage image;
	
	public EnemyManager(TileGettable tileGetter, MapGettable offsetGetter, PlayerDimension pDim, Runnable removeEnemy) {
		this.removeEnemy = removeEnemy;
		eDim = new EnemyDimension();
		collision = new EnemyCollisionHandler(eDim, tileGetter, offsetGetter, pDim, this::getEnemyImage);
		eAI = new EnemyAI(eDim, collision);
		eMove = new EntityMovement(eDim, collision, new EnemySettings());
		animator = new EnemyAnimation(eMove, collision);
	}

	@Override
	public void update() {		
		collisionCheck();
		
		EnemyAction action = eAI.next();
		doAction(action);
		
		if (collision.isUnderPlayer()) {
			dead();
		}
		
		isPushed();
		
		animator.run();
		eMove.run();
		
		image = animator.getStagedImage();
		
	}
	
	private void collisionCheck() {
		switch (collision.checkForCollision()) {
			case FIRE:
				dead();
				break;
			case PIT:
				dead();
				break;
			case SPIKE:
				dead();
				break;
			default:
				break;
		}
	}
	
	private void doAction(EnemyAction action) {
		switch (action) {
			case ATTACK:
				//Fill
				
				break;
			case JUMP:
				//Fill
				
				break;
			case WALK_LEFT:
				eMove.setMovementMod(-1);
				break;
			case WALK_RIGHT:
				eMove.setMovementMod(1);
				break;
			default:
				//Do nothing
				break;			
		}
	}
	
	private void dead() {
		image = GameImage.NONE.getImage();
		removeEnemy.run();;
	}
	
	private void isPushed() {
		if (collision.isPushed()) {
			eMove.setPushMod(GlobalSettings.SPEED);
		}
		else {
			eMove.setPushMod(0);
		}
	}

	@Override
	public int getEnemyX() {
		return eDim.getX();
	}

	@Override
	public int getEnemyY() {
		return eDim.getY();
	}

	@Override
	public BufferedImage getEnemyImage() {
		return image;
	}
}
