package controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import entity.player.PlayerSettings;

/**
 * @author Brian Towne
 *
 * Holds all images used in the game.
 */
public enum GameImage {
	// Terrain Reference
	GRASS("/grass.png", GlobalSettings.TILE_SIZE), 
	DIRT("/dirt.png", GlobalSettings.TILE_SIZE), 
	PLANK("/planks.png", GlobalSettings.TILE_SIZE), 
	SPIKE("/spikeup.png", GlobalSettings.TILE_SIZE),
	EMPTY("/empty.png", GlobalSettings.TILE_SIZE),

	// Player Reference
	HEART("/heart.png", PlayerSettings.HEART_SIZE),
	HEART_HALF("/halfheart.png", PlayerSettings.HEART_SIZE),
	SHELL("/shell.png", GlobalSettings.TILE_SIZE), 
	TURTLE("/turtle.png", GlobalSettings.TILE_SIZE), 

	// Menu Reference
	DEFAULT_BUTTON("/button.png", GlobalSettings.BUTTON_WIDTH, GlobalSettings.BUTTON_HEIGHT),
	CURSOR("/cursor.png"),
	END("/end.png", GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT),
	
	// Item Reference
	APPLE("/default.png", GlobalSettings.ITEM_SIZE),
	FLAG("/flag.gif", GlobalSettings.ITEM_SIZE),

	NONE("/default.png", GlobalSettings.TILE_SIZE);
	
	final private BufferedImage image;
	
	/**
	 * Sets the {@link BufferedImage} of the associated enum.
	 * Resizes the image to a given size.
	 * 
	 * @param path The relative path to the image using the "resources" folder as the root
	 * @param size for the image
	 * @throws RuntimeException When an image is not found at the given absolute path
	 */
	private GameImage(String path, int size) {
		String relativePath = checkPath(path);
		try {
			BufferedImage old = ImageIO.read(new File(relativePath));
			image = resizeImage(old, size, size);
		} catch (IOException e) {
			throw new RuntimeException("Absolute path \"" + relativePath + "\" for \"" + this.toString() + "\" did not find an image.");
		}
	}
	
	/**
	 * Sets the {@link BufferedImage} of the associated enum.
	 * Does not resize.
	 * 
	 * @param path The relative path to the image using the "resources" folder as the root
	 * @throws RuntimeException When an image is not found at the given absolute path
	 */
	private GameImage(String path) {
		String relativePath = checkPath(path);
		try {
			image = ImageIO.read(new File(relativePath));
		} catch (IOException e) {
			throw new RuntimeException("Absolute path \"" + path + "\" for \"" + this.toString() + "\" did not find an image.");
		}
	}

	/**
	 * Sets the {@link BufferedImage} of the associated enum.
	 * Resizes the image to a given width and height.
	 * 
	 * @param path The relative path to the image using the "resources" folder as the root
	 * @param w - width for the image
	 * @param h - height for the image
	 * @throws RuntimeException When an image is not found at the given absolute path
	 */
	private GameImage(String path, int w, int h) {
		String relativePath = checkPath(path);
		try {
			BufferedImage old = ImageIO.read(new File(relativePath));
			image = resizeImage(old, w, h);
		} catch (IOException e) {
			throw new RuntimeException("Absolute path \"" + path + "\" for \"" + this.toString() + "\" did not find an image.");
		}
	}
	
	/**
	 * Gets the relative path of the image.
	 * 
	 * @param path The relative path to the image using the "resources" folder as the root
	 * @throws RuntimeException When an image is not found at the given relative path
	 */
	private String checkPath(String path) {
		if (path == null) {
			throw new NullPointerException("path not set");
		}
		
		String relativePath;
		try {
			 URI uri = new URI(GameImage.class.getResource(path).toString());
			 relativePath = uri.getPath();
		}
		catch (NullPointerException e) 
		{
			System.out.println("Relative path from the resources folder \"" + path + "\" for \"" + this.toString() + "\" did not find an image.");
			throw new RuntimeException("Relative path from the resources folder \"" + path + "\" for \"" + this.toString() + "\" did not find an image.");
		}
		catch (URISyntaxException e) {
			System.out.println("URI for the resources folder \"" + path + "\" for \"" + this.toString() + "\" did not find an image.");
			throw new RuntimeException("URI for the resources folder \"" + path + "\" for \"" + this.toString() + "\" did not find an image.");
		}
		return relativePath;
	}
	
	/**
	 * @return The {@link BufferedImage} associated with the given image.
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	private BufferedImage resizeImage(BufferedImage old, int w, int h) {
		Image tmp = old.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		
		// inits the final buffered image
		BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		// paints the image on
		Graphics2D g = b.createGraphics();
		g.drawImage(tmp, 0, 0, null);
		g.dispose();
		
		return b;
	}
}
