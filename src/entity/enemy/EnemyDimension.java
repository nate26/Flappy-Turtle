package entity.enemy;

import entity.EntityDimension;

/**
 * Sets all the enemy positions and dimensional bounds.
 * 
 * @author Nate Axt
 */
public class EnemyDimension extends EntityDimension {

	/**
	 * Sets the enemy's starting position.
	 */
	protected EnemyDimension() {
		super(new EnemySettings());
		setX(EnemySettings.STARTING_X);
		setY(startingY());
	}

	/**
	 * Determines the starting y position by finding the highest ground.
	 * 
	 * @return the starting y position
	 */
	private int startingY() {
		return 0; //TODO there are turtles falling from the sky
	}
	
	
}
