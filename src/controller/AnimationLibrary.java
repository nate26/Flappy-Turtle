package controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Library of all the animation folders.  
 * The folders are uploaded into lists of Buffered Images.
 * 
 * @author Nate Axt
 *
 */
public enum AnimationLibrary {
	
	// Player Reference
	PLAYER_WALKING("/turt", GlobalSettings.TILE_SIZE),
	PLAYER_SPINNING("/spin", GlobalSettings.TILE_SIZE),
	PLAYER_SPINNING_JUMP("/spin", GlobalSettings.TILE_SIZE),
	PLAYER_JUMP_START("/turt", GlobalSettings.TILE_SIZE),
	PLAYER_JUMP_END("/turt", GlobalSettings.TILE_SIZE),
	PLAYER_WALKING_THUG("/turt", GlobalSettings.TILE_SIZE),
	PLAYER_SPINNING_THUG("/spin", GlobalSettings.TILE_SIZE),
	PLAYER_SPINNING_JUMP_THUG("/spin", GlobalSettings.TILE_SIZE),
	PLAYER_JUMP_START_THUG("/turt", GlobalSettings.TILE_SIZE),
	PLAYER_JUMP_END_THUG("/turt", GlobalSettings.TILE_SIZE),
	PLAYER_INVULNERABLE("/invuln", GlobalSettings.TILE_SIZE),

	// Menus Reference
	MENU_TITLE("/title", GlobalSettings.TITLE_WIDTH, GlobalSettings.TITLE_HEIGHT),
	MENU_TURTLE("/turtleMenu", GlobalSettings.TURTLE_MENU_SIZE),
	START_BUTTON("/startButton", GlobalSettings.BUTTON_WIDTH, GlobalSettings.BUTTON_HEIGHT),
	OPTIONS_BUTTON("/optionsButton", GlobalSettings.BUTTON_WIDTH, GlobalSettings.BUTTON_HEIGHT),

	// Background Reference
	BACKGROUND_DEFAULT("/defaultBackground", GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT),
	BACKGROUND_FOREST("/none", GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT),
	BACKGROUND_DESERT("/none", GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT),
	BACKGROUND_CAVE("/none", GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT),
	BACKGROUND_OCEAN("/none", GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT),
	
	// Terrain Reference
	FIRE("/none", GlobalSettings.TILE_SIZE),

	// Item Reference
	STAR("/none", GlobalSettings.TILE_SIZE),
	APPLE("/none", GlobalSettings.TILE_SIZE),
	APPLE_GOLDEN("/none", GlobalSettings.TILE_SIZE),
	KALE("/none", GlobalSettings.TILE_SIZE),
	FLAG("/none", GlobalSettings.TILE_SIZE),

	// Enemy Reference
	ENEMY_LEFT("/enemyLeft", (int) ((23F / 32) * GlobalSettings.TILE_SIZE), GlobalSettings.TILE_SIZE),
	ENEMY_RIGHT("/enemyRight", (int) ((23F / 32) * GlobalSettings.TILE_SIZE), GlobalSettings.TILE_SIZE),
	ENEMY_JUMP_START("/enemyLeft", (int) ((23F / 32) * GlobalSettings.TILE_SIZE), GlobalSettings.TILE_SIZE),
	ENEMY_JUMP_END("/enemyLeft", (int) ((23F / 32) * GlobalSettings.TILE_SIZE), GlobalSettings.TILE_SIZE),
	BEANBOY_SHAKE("/none", GlobalSettings.TILE_SIZE),
	FIREBALL("/none", GlobalSettings.TILE_SIZE);


	private List<BufferedImage> imageList;
	
	/**
	 * Constructs the list of images for a set size.
	 * 
	 * @param path to the animation folder
	 * @param size for the image for width and height
	 */
	private AnimationLibrary(String path, int size) {
		String relativePath = checkPath(path);
		imageList = uploadAnimationImages(relativePath, path, size, size);
	}
	
	/**
	 * Constructs the list of images for a set width and height.
	 * 
	 * @param path to the animation folder
	 * @param w - width for the image
	 * @param h - height for the image
	 */
	private AnimationLibrary(String path, int w, int h) {
		String relativePath = checkPath(path);
		imageList = uploadAnimationImages(relativePath, path, w, h);
	}
	
	/**
	 * Checks and gets the relativePath of the image.
	 * 
	 * @param path - of the image
	 * @throws RuntimeException When an image is not found at the given relative path
	 * @return the relative path
	 */
	private String checkPath(String path) {
		if(path == null) {
			throw new NullPointerException("path not set");
		}
		
		String relativePath;
		try {
			 URI uri = new URI(GameImage.class.getResource(path).toString());
			 relativePath = uri.getPath();
		}
		catch (NullPointerException e) 
		{
			System.out.println("Relative path from the resources folder \"" + 
					path + "\" for \"" + this.toString() + "\" did not find an image.");
			GameController.logger.log("Relative path from the resources folder \"" + 
					path + "\" for \"" + this.toString() + "\" did not find an image.");
			throw new RuntimeException("Relative path from the resources folder \"" + 
					path + "\" for \"" + this.toString() + "\" did not find an image.");
		}
		catch (URISyntaxException e) {
			System.out.println("URI for the resources folder \"" + path + "\" for \"" + 
					this.toString() + "\" did not find an image.");
			GameController.logger.log("URI for the resources folder \"" + path + 
					"\" for \"" + this.toString() + "\" did not find an image.");
			throw new RuntimeException("URI for the resources folder \"" + path + 
					"\" for \"" + this.toString() + "\" did not find an image.");
		}
		return relativePath;
	}
	
	/**
	 * Gets the list of images for the animation.
	 * 
	 * @return list of buffered images
	 */
	public List<BufferedImage> getAnimation() {
		return imageList;
	}
	
	/**
	 * Uploads a folder of images and stores them into a list.
	 * 
	 * @param path - to the folder
	 * @param imageList - a new list to store the images into
	 */
	private List<BufferedImage> uploadAnimationImages(String relativePath, String path, int w, int h) {
		File folder = new File(relativePath);
		File[] listOfFiles = folder.listFiles();
		List<BufferedImage> tempList = new ArrayList<BufferedImage>();

		for (int i = 0; i < listOfFiles.length; i++) {
			
			if (listOfFiles[i].isFile()) {
				
				// makes temp buffered image
				BufferedImage old = getBufferedImage(listOfFiles[i].getPath(), path);
				
				// scales the image
				Image tmp = old.getScaledInstance(w, h, Image.SCALE_SMOOTH);
				
				// inits the final buffered image
				BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				
				// paints the image on
				Graphics2D g = b.createGraphics();
				g.drawImage(tmp, 0, 0, null);
				g.dispose();
				
				tempList.add(b);	
			}
		}
		return tempList;
	}
	
	/**
	 * Creates a buffered image from the given path.
	 * 
	 * @param path - for the image file
	 * @throws RuntimeException When an image is not found at the given absolute path
	 * @return a buffered image
	 */
	private BufferedImage getBufferedImage(String relativePath, String path) {
    	BufferedImage buffered = null;
    	try {
    		buffered = ImageIO.read(new File(relativePath));
		} catch (IOException e) {
			GameController.logger.log("Absolute path \"" + path + "\" for \"" + 
					this.toString() + "\" did not find an image.");
			throw new RuntimeException("Absolute path \"" + path + "\" for \"" + 
					this.toString() + "\" did not find an image.");
		}
    	return buffered;
    }
}
