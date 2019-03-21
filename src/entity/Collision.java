package entity;

import entity.player.PlayerSettings;

/**
 * Stores the damage modifier for collisions.
 * 
 * @author Nate Axt
 *
 */
public enum Collision {
	FIRE(3),
	GROUND(0), 
	PLANK(0),
	PIT(PlayerSettings.MAX_HEALTH),
	SPIKE(1),
	PUSH(0),
	SMUSH(1),
	NONE(0);
	
	private int damage;
	
	/**
	 * @param damage player takes from collision.
	 */
	private Collision(int damage) {
		this.damage = damage;
	}
	
	/**
	 * @return damage taken with the given collision.
	 */
	public int getDamage() {
		return damage;
	}
}
