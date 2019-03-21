package map_manager;

import controller.GlobalSettings;

/**
 * Holds settings for the map manager. To be replaced using either JSON or XML.
 * 
 * @author Brian Towne
 *
 */
public final class MMSettings {

	private MMSettings() {}
	
	///////////////////////////////////////////////////////////////////////////
	////////////////// GROUND SETTINGS ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * The starting height of the ground.
	 */
	public static final int GROUND_DEFAULT = 3;
	
	/**
	 * The maximum height the ground will raise too.
	 */
	public static final int GROUND_MAX = 6;
	
	/**
	 * The minimum height the ground will decend to.
	 */
	public static final int GROUND_MIN = 2;
	
	/**
	 * How frequent the ground's height will change in tiles.
	 */
	public static final int GROUND_FREQ_MIN = 0;
	
	/**
	 * How close to the frequency in tiles the ground is allowed to change.
	 */
	public static final int GROUND_FREQ_MAX = 30;
	public static final int GROUND_CHANGE_MAX = 1;
	public static final int GROUND_CHANGE_MIN = 1;
	
	///////////////////////////////////////////////////////////////////////////
	////////////////// SPIKE SETTINGS /////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * The frequency of how often spikes should appear.
	 */
	public static final int SPIKE_FREQ_MIN = 2;
	
	public static final int SPIKE_FREQ_MAX = 5;
	
	///////////////////////////////////////////////////////////////////////////
	////////////////// PIT SETTINGS ///////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * The minimum allowed width of pits.
	 */
	public static final int PIT_MIN = 1;
	
	/**
	 * The maximum allowed width of pits.
	 */
	public static final int PIT_MAX = 4;
	
	/**
	 * The frequency of pits.
	 */
	public static final int PIT_FREQ_MIN = 10;
	public static final int PIT_FREQ_MAX = 20;
	
	///////////////////////////////////////////////////////////////////////////
	////////////////// PLATFORM SETTINGS //////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public static final int PLATFORM_FREQ_MIN = 5;
	public static final int PLATFORM_FREQ_MAX = 15;
	
	public static final int PLATFORM_HEIGHT_MAX = 3;
	public static final int PLATFORM_HEIGHT_MIN = 1;
	
	public static final int PLATFORM_LENGTH_MIN = 5;
	public static final int PLATFORM_LENGTH_MAX = 8;
	
	public static final double PLATFORM_PLANK_FREQ = 0.7;
	public static final boolean PLATFORM_SPIKES = true;
	public static final double PLATFORM_SPIKES_FREQ = 0.4;
	
	///////////////////////////////////////////////////////////////////////////
	////////////////// VOLCANO SETTINGS ///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public static final boolean VOLCANO_ACTIVE = true;
	public static final int VOLCANO_FREQ_MIN = 40;
	public static final int VOLCANO_FREQ_MAX = 100;
	
	public static final int VOLCANO_MIN_START = 2;
	public static final int VOLCANO_TARGET = MMSettings.TERRAIN_HEIGHT - 1;
	
	public static final int VOLCANO_PIT_HEIGHT = 1;
	
	public static final int VOLCANO_WIDTH_MIN = 2;
	public static final int VOLCANO_WIDTH_MAX = 6;
	public static final int VOLCANO_BUFFER = 6;
	public static final boolean VOLCANO_SPIKES = true;
	
	
	///////////////////////////////////////////////////////////////////////////
	////////////////// GENERAL SETTINGS ///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * The maximum high of the terrain in tiles.
	 */
	public static final int TERRAIN_HEIGHT = GlobalSettings.NUM_TILES_HEIGHT;
	
	/**
	 * The maximum high of the terrain in tiles.
	 */
	public static final int TERRAIN_WIDTH = GlobalSettings.NUM_TILES_WIDTH;
	
	/**
	 * The amount of tiles that are initially generated.
	 */
	public static final int INITIAL_TILES = 500;
	
	/**
	 * The amount of pixels we scroll to the right per frame.
	 */
	public static final int SCROLL_SPEED = GlobalSettings.SPEED;
	
	/**
	 * The seed to be used by the random number generator.
	 */
	public static final int SEED = 11;
}
