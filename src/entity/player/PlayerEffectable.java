package entity.player;

public interface PlayerEffectable {

	/**
	 * Heals the player a certain amount of hearts.
	 * 
	 * @param halfHearts to heal up to
	 */
	public void eatFood(int halfHearts);

	/**
	 * Sets the player invulnerable until the given ticks pass.
	 * Sets the invulnTick to a negative number to increase the time until hitting
	 * the invulnerable time length.
	 * 
	 * @param ticks of being invulnerable
	 */
	public void invulnerableFor(int ticks);

	/**
	 * Sets the player on starpower until the given ticks pass.
	 * 
	 * @param ticks of being on starpower
	 */
	public void starpowerFor(int ticks);
	
}
