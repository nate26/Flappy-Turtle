package controller;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class GlobalSettings {

	public final static int TIME_UNIT = 8;
	public final static int TILE_SIZE = 90;
	public final static int NUM_TILES_WIDTH = 20;
	public final static int NUM_TILES_HEIGHT = 10;
	public final static int WINDOW_WIDTH = NUM_TILES_WIDTH * TILE_SIZE;
	public final static int WINDOW_HEIGHT = NUM_TILES_HEIGHT * TILE_SIZE;
	public final static int SPEED = 2;
	
	private final static Dimension SCRN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private final static Rectangle WIN_SIZE = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	public final static int TASKBAR_HEIGHT = SCRN_SIZE.height - WIN_SIZE.height - 10;

	public static final int SCREEN_LEFT_BOUND = GlobalSettings.TILE_SIZE;
	public static final int SCREEN_RIGHT_BOUND = GlobalSettings.WINDOW_WIDTH - GlobalSettings.TILE_SIZE;

	public static final int CURSOR_WIDTH = 10;
	public static final int CURSOR_HEIGHT = CURSOR_WIDTH * 2;
	public static final int TITLE_WIDTH = 1200;
	public static final int TITLE_HEIGHT = 250;
	public static final int TITLE_TICK = 60;
	public static final int TURTLE_MENU_SIZE = TILE_SIZE * 2;
	public static final int TURTLE_TICK = TITLE_TICK / 2 * 3;
	public static final int BUTTON_WIDTH = 220;
	public static final int BUTTON_HEIGHT = 90;
	public static final float BUTTON_FILTER_FACTOR = 0.8f;
	
	public static final int ITEM_SIZE = 90;
	
	public static final int COLLISION_BUFFER = 5; //TODO put in entity somewhere
	public static final int PLAYER_Y_POSITION = TILE_SIZE * 5;

}
