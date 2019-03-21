package entity.player;

import controller.GlobalSettings;
import entity.EntitySettings;

public class PlayerSettings implements EntitySettings {
	
	public final static int STARTING_X = GlobalSettings.TILE_SIZE * 3;

	public final static int OUTLINE_X = 12;
	public final static int OUTLINE_Y = 53;
	public final static int OUTLINE_WIDTH = 79 - OUTLINE_X;
	public final static int OUTLINE_HEIGHT = 89 - OUTLINE_Y;
	
	public final static int MOVE_PPT_MODIFIER = 3;
	public static final int MOVE_PPT_MODIFIER_AIR = (int) Math.ceil(MOVE_PPT_MODIFIER / 2F);
	public final static int MOVE_ANIMATION_TICK = 200 / GlobalSettings.TIME_UNIT;
	public final static int MOVE_ANIMATION_MULTIPLIER = 120 / GlobalSettings.TIME_UNIT;
	public final static int INVULNERABLE_ANIMATION_TICK = 200 / GlobalSettings.TIME_UNIT;
	public final static int START_JUMP_SPEED = 6;
	public final static int JUMP_END_ANIMATION_DISTANCE = 10;

	public final static int MAX_HEALTH = 10;
	public final static int HEART_SIZE = GlobalSettings.TILE_SIZE / 2;
	public final static int HEALTH_BAR_BUFFER = 10;
	public final static int HEALTH_BAR_WIDTH = HEART_SIZE * MAX_HEALTH / 2;
	public final static int HEALTH_BAR_XPOS = GlobalSettings.WINDOW_WIDTH - HEALTH_BAR_WIDTH - HEALTH_BAR_BUFFER;
	public final static int HEALTH_BAR_YPOS = HEALTH_BAR_BUFFER;
	public final static int INVULNERABLE_TIME = INVULNERABLE_ANIMATION_TICK * 6;
	
	
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
