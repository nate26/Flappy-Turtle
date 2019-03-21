package entity.player;

import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import controller.GlobalSettings;
import entity.Collision;
import entity.EntityCollisionHandler;
import map_manager.MapGettable;
import map_manager.TileGettable;

/**
 * Checks all collisions on the same tile and to all 4 directions around the player.
 * 
 * @author Nate Axt
 */
public class PlayerCollisionHandler extends EntityCollisionHandler {
	
	private PlayerDimension pDim;
	
	/**
	 * Gets the players dimension and map info.
	 * 
	 * @param pDim - the player's dimensional and positional information
	 * @param mapInfo - contains tile enumeration access
	 * @param bufferedImage 
	 */
	protected PlayerCollisionHandler(PlayerDimension pDim, TileGettable tileInfo, MapGettable offsetInfo,
			Supplier<BufferedImage> getPlayerImage) {
		super(pDim, tileInfo, offsetInfo, getPlayerImage);
		this.pDim = pDim;
	}
	
	/**
	 * Checks if the player is smushed.
	 * 
	 * @return if the player is between ground and the left bound of the screen
	 */
	protected boolean isSmushed() {
		return getRelationalX(pDim.getRightBound() + 1) == Collision.GROUND &&
				pDim.getX() < GlobalSettings.SCREEN_LEFT_BOUND;
	}
	
	/**
	 * Checks if the player is pushed.
	 * 
	 * @return if there is ground to the right of the player and they are not smushed
	 */
	@Override
	public boolean isPushed() {System.out.println(getRelationalX(pDim.getRightBound()+1)); //TODO remove print
		return getRight() == Collision.GROUND && !isSmushed();
	}
	
	/**
	 * Checks if the player is pushed with a buffer.
	 * 
	 * @param buffer is the buffer space between the player and ground
	 * @return if there is ground to the right of the player and they are not smushed
	 */
	public boolean isPushed(int buffer) {
		return getRelationalX(pDim.getRightBound() + buffer) == Collision.GROUND && !isSmushed();
	}
	
	/**
	 * Checks if the player is blocked behind by an object.
	 * 
	 * @return if there is ground to the left of the player
	 */
	public boolean isBlocked() {
		return getLeft() == Collision.GROUND;
	}
	
	/**
	 * Checks if the player is blocked behind by an object with a buffer.
	 * 
	 * @param buffer is the buffer space between the player and ground
	 * @return if there is ground to the right of the player
	 */
	public boolean isBlocked(int buffer) {
		return getRelationalX(pDim.getLeftBound() - buffer) == Collision.GROUND;
	}
	
}
