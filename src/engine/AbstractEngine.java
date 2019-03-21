package engine;

import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import controller.Updatable;
import entity.player.PlayerData;

/**
 * Abstract verison of the engine with all the implementation needed to attach it to the rest of the program.
 * 
 * @author Brian Towne
 *
 */
public abstract class AbstractEngine implements Updatable {
	
	private Supplier<PlayerData> playerSupplier = null;
	private Supplier<BufferedImage> backgroundSupplier = null;
	
	/**
	 * Creates a new engine with suppliers to get {@link PlayerData} and the background image.
	 * 
	 * @param playerSupplier Gets new PlayerData for the current frame.
	 * @param backgroundSupplier Gets a new background image for the current frame.
	 */
	public AbstractEngine(Supplier<PlayerData> playerSupplier, Supplier<BufferedImage> backgroundSupplier) {
		this.playerSupplier = playerSupplier;
		this.backgroundSupplier = backgroundSupplier;
	}

}
