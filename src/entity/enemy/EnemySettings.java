package entity.enemy;

import controller.GlobalSettings;
import entity.EntitySettings;

public class EnemySettings implements EntitySettings {

	public final static int STARTING_X = GlobalSettings.TILE_SIZE * 18;

	public final static int OUTLINE_X = 0;
	public final static int OUTLINE_Y = 0;
	public final static int OUTLINE_WIDTH = (int) (23F / (32F / 90)) - OUTLINE_X;
	public final static int OUTLINE_HEIGHT = GlobalSettings.TILE_SIZE - OUTLINE_Y;
	
	public final static int MOVE_PPT_MODIFIER = 2;
	public final static int MOVE_ANIMATION_TICK = 200 / GlobalSettings.TIME_UNIT;
	public final static int MOVE_ANIMATION_MULTIPLIER = 120 / GlobalSettings.TIME_UNIT;
	public final static int INVULNERABLE_ANIMATION_TICK = 200 / GlobalSettings.TIME_UNIT;
	public final static int START_JUMP_SPEED = 3;
	public final static int JUMP_END_ANIMATION_DISTANCE = 10;

	public static final int PLAYER_INTERACTION_BUFFER = 5;

	public static final int WALK_DIST = GlobalSettings.TILE_SIZE * 6;
	
	
	@Override
	public int getOutlineX() { return OUTLINE_X; }
	
	@Override
	public int getOutlineY() { return OUTLINE_Y; }
	
	@Override
	public int getOutlineWidth() { return OUTLINE_WIDTH; }
	
	@Override
	public int getOutlineHeight() { return OUTLINE_HEIGHT; }
	
	@Override
	public int getMoveAnimationTick() { return MOVE_ANIMATION_TICK; }
	
	@Override
	public int getMoveAnimationMultiplier() { return MOVE_ANIMATION_MULTIPLIER; }
	
	@Override
	public int getStartJumpSpeed() { return START_JUMP_SPEED; }
	
	@Override
	public int getMaxDistanceRight() { return GlobalSettings.SCREEN_RIGHT_BOUND; }
	
	@Override
	public int getMaxDistanceLeft() { return GlobalSettings.SCREEN_LEFT_BOUND; }
	
}
