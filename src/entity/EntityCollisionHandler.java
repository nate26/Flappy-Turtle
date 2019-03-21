package entity;

import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import controller.GlobalSettings;
import map_manager.MapGettable;
import map_manager.TileGettable;
import map_manager.TileImage;
import map_manager.TileType;
import tools.Hitbox;

/**
 * Checks all collisions on the same tile and to all 4 directions around the player.
 * 
 * @author Nate Axt
 */
public class EntityCollisionHandler {

	private EntityDimension pDim;
	private TileGettable tileInfo;
	private MapGettable offsetInfo;
	private Supplier<BufferedImage> getEntityImage;
	
	/**
	 * Gets the players dimension and map info.
	 * 
	 * @param pDim - the player's dimensional and positional information
	 * @param mapInfo - contains tile enumeration access
	 */
	public EntityCollisionHandler(EntityDimension pDim, TileGettable tileInfo, MapGettable offsetInfo,
			Supplier<BufferedImage> getEntityImage) {
		this.pDim = pDim;
		this.tileInfo = tileInfo;
		this.offsetInfo = offsetInfo;
		this.getEntityImage = getEntityImage;
	}
	
	/**
	 * Determines the collision at the given coordinates.
	 * Checks collisions from bottom left corner and checks other corners in a clockwise order.
	 * Order of collision priority:
	 * 		- Pit
	 * 		- Hazard
	 * 		- Solid
	 * 		- None
	 * 
	 * @param x The x coordinate from the top left.
	 * @param y The y coordinate from the top left.
	 * @return The occurred Collision at the given coordinates.
	 */
	public Collision checkForCollision() {		
		int offsetX = offsetInfo.getOffset();
		System.out.println(offsetX);
		int offsetY = Math.floorMod(pDim.getY(), GlobalSettings.TILE_SIZE);

		TileImage topLeft = findTile(pDim.getX(), pDim.getY());
		TileImage topRight = findTile(pDim.getX() + GlobalSettings.TILE_SIZE - 1, pDim.getY());
		TileImage bottomLeft = findTile(pDim.getX(), pDim.getY() + GlobalSettings.TILE_SIZE - 1);
		TileImage bottomRight = findTile(pDim.getX() + GlobalSettings.TILE_SIZE - 1, 
				pDim.getY() + GlobalSettings.TILE_SIZE - 1);

		System.out.println(topLeft);
		if (!Hitbox.isColliding(getEntityImage.get(), topLeft.getImage(), 
				offsetX - GlobalSettings.TILE_SIZE + 1, offsetY)) {
			topLeft = TileImage.EMPTY;
		}
		
		if (!Hitbox.isColliding(getEntityImage.get(), topRight.getImage(), offsetX, offsetY)) {
			topRight = TileImage.EMPTY;
		}
		
		if (!Hitbox.isColliding(getEntityImage.get(), bottomLeft.getImage(), 
				offsetX - GlobalSettings.TILE_SIZE + 1, offsetY + GlobalSettings.TILE_SIZE - 1)) {
			bottomLeft = TileImage.EMPTY;
		}
		
		if (!Hitbox.isColliding(getEntityImage.get(), bottomRight.getImage(), 
				offsetX, offsetY + GlobalSettings.TILE_SIZE - 1)) {
			bottomRight = TileImage.EMPTY;
		}
		
		// TODO array or list? which is more time effective
		TileType[] surroundingTiles = {
				topLeft.getTileType(), 
				topRight.getTileType(), 
				bottomLeft.getTileType(), 
				bottomRight.getTileType()
			};

		return getCollision(getPriorityTile(surroundingTiles));
	}
	
	/**
	 * Checks the tile bounds before getting the tile from the map resource.
	 * 
	 * @param x position
	 * @param y position
	 * @return TileType at that position
	 */
	private TileImage findTile(int x, int y) {
		int width = GlobalSettings.WINDOW_WIDTH;
		int height = GlobalSettings.WINDOW_HEIGHT;
		
		if (x >= width || 
			x <= 0 || 
			y >= height + GlobalSettings.TILE_SIZE) {
			return TileImage.PIT;
		}
		else if (y <= 0 || y >= height) {
			return TileImage.EMPTY;
		}
		
		return tileInfo.getTile(x, y);
	}
	
	/**
	 * Checks if the player is pushed.
	 * 
	 * @return if there is ground to the right of the player and they are not smushed
	 */
	public boolean isPushed() {
		return getRelationalX(pDim.getRightBound() + 1) == Collision.GROUND;
	}
	
	/**
	 * Gets the priority tile in order of the tile array.
	 * Order of tile priority:
	 * 		- Pit
	 * 		- Hazard
	 * 		- Solid
	 * 		- None
	 * 
	 * @param tiles - array of tiles
	 * @return the priority tile in the array
	 */
	private TileType getPriorityTile(TileType[] tiles) {
		
		TileType out = TileType.EMPTY;
		
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] == TileType.PIT) {
				return tiles[i];
			}
			else if (tiles[i] == TileType.HAZARD) {
				return tiles[i];
			}
			else if (tiles[i] == TileType.SOLID && out == TileType.EMPTY) {
				out = tiles[i];
			}
		}
		
		return out;
	}
	

	/**
	 * Gets the tile to the right of the player.
	 * 
	 * @return right tile
	 */
	protected Collision getRight() {
		return getRelationalX(pDim.getRightBound() + 1);
	}

	/**
	 * Gets the tile to the left of the player.
	 * 
	 * @return left tile
	 */
	protected Collision getLeft() {
		return getRelationalX(pDim.getLeftBound() - 1);
	}

	/**
	 * Gets the tile above of the player.
	 * 
	 * @return above tile
	 */
	public Collision getAbove() {
		return getRelationalY(pDim.getTopBound() - 1);
	}

	/**
	 * Gets the tile below of the player.
	 * 
	 * @return below tile
	 */
	public Collision getBelow() {
		return getRelationalY(pDim.getBottomBound() + 1);
	}

	/**
	 * Gets the tile to the left or right of the player. Priority on solids over other Tiles.
	 * 
	 * @param horizontal - either pDim.getLeftBound() or pDim.getRightBound(), else right is default
	 * @return tile to the right or left
	 */
	protected Collision getRelationalX(int checkPos) {
		if (checkPos < 0 || checkPos > GlobalSettings.WINDOW_WIDTH ||
				pDim.getBottomBound() >= GlobalSettings.WINDOW_HEIGHT) {
			return Collision.NONE;
		}
		
		int buffer = 1;
		if (checkPos < pDim.getCenter()[0]) {
			buffer *= -1;
		}
		
		TileType tileTop = tileInfo.getTile(checkPos + buffer, pDim.getTopBound()).getTileType();
		TileType tileBottom = tileInfo.getTile(checkPos + buffer, pDim.getBottomBound()).getTileType();
		return tileTop == TileType.SOLID ? getCollision(tileTop) : getCollision(tileBottom);
	}


	/**
	 * Gets the tile to the top or bottom of the player. Priority on solids over other Tiles.
	 * 
	 * @param horizontal - either pDim.getBottomBound() or pDim.getTopBound(), else bottom is default
	 * @return tile to the bottom or top
	 */
	protected Collision getRelationalY(int checkPos) {		
		if (checkPos < 0 || checkPos >= GlobalSettings.WINDOW_HEIGHT) {
			return Collision.NONE;
		}
		
		TileType tileRight = tileInfo.getTile(pDim.getRightBound() + 1, checkPos).getTileType();
		TileType tileLeft = tileInfo.getTile(pDim.getLeftBound() - 1, checkPos).getTileType();
		
		boolean watchForPlanks = false;
		if (checkPos > pDim.getCenter()[1]) {
			watchForPlanks = (tileRight == TileType.PLANK);
		}
		return tileRight == TileType.SOLID || watchForPlanks ? 
				getCollision(tileRight) : getCollision(tileLeft);
	}
	
	/**
	 * Gets the collision type of the tile.
	 * 
	 * @param tile - map tile type
	 * @return collision type
	 */
	private Collision getCollision(TileType tile) {
		switch (tile) {
			case SOLID:
				return Collision.GROUND;
			case HAZARD:
				return Collision.SPIKE; // Fire
			case PIT:
				return Collision.PIT;
			case PLANK:
				return Collision.PLANK;
			default:
				return Collision.NONE;
		}
	}
	
	/**
	 * Calculates the number of pixels between the player and a solid tile below.
	 * 
	 * @return the distance between the player and ground in pixels
	 */
	public int getDistanceToGround() {
		Collision tileCheck = getRelationalY(pDim.getBottomBound() + GlobalSettings.TILE_SIZE);
		if (tileCheck != Collision.GROUND && tileCheck != Collision.PLANK) {
			return GlobalSettings.TILE_SIZE;
		}
		
		int distance = 1;
		for (int i = pDim.getBottomBound() + 1; i < pDim.getBottomBound() + GlobalSettings.TILE_SIZE; i++) {
			tileCheck = getRelationalY(pDim.getBottomBound() + distance);
			if (tileCheck == Collision.GROUND || tileCheck == Collision.PLANK) {
				break;
			}
			distance++;
		}
		return distance - 1;
	}
	
	/**
	 * Calculates the number of pixels between the player and a solid tile above.
	 * 
	 * @return the distance between the player and ceiling in pixels
	 */
	protected int getDistanceToCeiling() {
		if (getRelationalY(pDim.getTopBound() - GlobalSettings.TILE_SIZE) != Collision.GROUND) {
			return GlobalSettings.TILE_SIZE;
		}
		
		int distance = 1;
		for (int i = pDim.getY() - 1; i > pDim.getY() - GlobalSettings.TILE_SIZE; i--) {
			if (getRelationalY(pDim.getTopBound() - distance) == Collision.GROUND) {
				break;
			}
			distance++;
		}
		return distance - 1;
	}
}
