package controller;

/**
 * Represents a class that can be updated by the {@link GameController}.
 * 
 * @author Brian Towne
 *
 */
public interface Updatable {
	
	/**
	 * Tells the object to process the next frame.
	 */
	public void update();

}
