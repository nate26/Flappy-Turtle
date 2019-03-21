package menu;

/**
 * Ending the game via menus is accessed through this class.
 * 
 * @author Nate Axt
 */
public interface MenuGettable {

	/**
	 * Tells the menu when the player is dead to pause the game 
	 * and open the game over menu.
	 */
	void playerDead();

	/**
	 * Tells the menu when the player needs to respawn to pause the game 
	 * and open the resume menu.
	 */
	void playerRespawn();
	
}
