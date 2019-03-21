package controller;

/**
 * Interface to call IO actions for the Menu.
 * 
 * @author Nate Axt
 */
public interface MenuIO {

	/**
	 * Pauses or unpauses the game.
	 * 
	 * @param on - true to pause
	 */
	public void pauseAction(boolean on);
}
