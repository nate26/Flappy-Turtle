package entity.enemy;

import java.util.Random;

import controller.GlobalSettings;

public class EnemyAI {
	
	private EnemyDimension eDim;
	private EnemyCollisionHandler collision;
	private EnemyAction currAction;
	private int walkTime;

	public EnemyAI(EnemyDimension eDim, EnemyCollisionHandler collision) {
		this.eDim = eDim;
		this.collision = collision;
		currAction = EnemyAction.WALK_LEFT;
		walkTime = 0;
	}

	protected EnemyAction next() {
		// Cliff
		if (collision.nearCliffLeft()) {
			currAction = EnemyAction.WALK_RIGHT;
			return EnemyAction.WALK_RIGHT;
		}
		else if (collision.nearCliffRight()) {
			currAction = EnemyAction.WALK_LEFT;
			return EnemyAction.WALK_LEFT;
		}
		
		// Pushed
		if (collision.isPushed()) {
			currAction = EnemyAction.WALK_LEFT;
			return EnemyAction.WALK_LEFT;
		}
		
		// Bounds
		if (eDim.getRightBound() + 20 > GlobalSettings.WINDOW_WIDTH) {
			currAction = EnemyAction.WALK_LEFT;
			return EnemyAction.WALK_LEFT;
		}
		else if (eDim.getLeftBound() - 20 < 0) {
			currAction = EnemyAction.WALK_RIGHT;
			return EnemyAction.WALK_RIGHT;
		}
		
		if (eDim.getRightBound() > EnemySettings.STARTING_X) {
			currAction = EnemyAction.WALK_LEFT;
			return EnemyAction.WALK_LEFT;
		}
		if (eDim.getLeftBound() < EnemySettings.STARTING_X - EnemySettings.WALK_DIST) {
			currAction = EnemyAction.WALK_RIGHT;
			return EnemyAction.WALK_RIGHT;
		}
		
		// Switch
		/*if (walkTime > 100) {
			Random rand = new Random();
			if (rand.nextBoolean()) {
				return switchAction();
			}
			walkTime = 0;
		}
		else {
			walkTime++;
		}*/
		return currAction;
	}
	
	private EnemyAction switchAction() {
		currAction = EnemyAction.WALK_LEFT == currAction ? 
				EnemyAction.WALK_RIGHT : EnemyAction.WALK_LEFT;
		return currAction;
	}

}
