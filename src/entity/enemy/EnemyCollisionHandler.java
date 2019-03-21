package entity.enemy;

import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import entity.EntityCollisionHandler;
import entity.player.PlayerDimension;
import map_manager.MapGettable;
import map_manager.TileGettable;
import map_manager.TileType;

public class EnemyCollisionHandler extends EntityCollisionHandler {
	
	private EnemyDimension eDim;
	private TileGettable mapInfo;
	private PlayerDimension pDim;

	public EnemyCollisionHandler(EnemyDimension eDim, TileGettable mapInfo, MapGettable offsetInfo,
			PlayerDimension pDim, Supplier<BufferedImage> getEnemyImage) {
		super(eDim, mapInfo, offsetInfo, getEnemyImage);
		this.eDim = eDim;
		this.mapInfo = mapInfo;
		this.pDim = pDim;
	}

	protected boolean isUnderPlayer() {
		if ((eDim.getRightBound() > pDim.getLeftBound() && eDim.getLeftBound() < pDim.getLeftBound() ||
				eDim.getLeftBound() < pDim.getRightBound() && eDim.getRightBound() > pDim.getRightBound()) &&
				eDim.getTopBound() > pDim.getBottomBound() - EnemySettings.PLAYER_INTERACTION_BUFFER &&
				eDim.getTopBound() < pDim.getBottomBound()) {
			return true;
		}
		return false;
	}

	public boolean nearCliffRight() {
		int checkPos = eDim.getBottomBound() + 1;
		TileType tileRight = mapInfo.getTile(pDim.getRightBound() + 20, checkPos).getTileType();
		return tileRight == TileType.EMPTY || tileRight == TileType.HAZARD;
	}

	public boolean nearCliffLeft() {
		int checkPos = eDim.getBottomBound() + 1;
		TileType tileLeft = mapInfo.getTile(eDim.getLeftBound() - 20, checkPos).getTileType();
		return tileLeft == TileType.EMPTY || tileLeft == TileType.HAZARD;
	}

}
