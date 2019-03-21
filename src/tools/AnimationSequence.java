package tools;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores a list of images for animation.
 * Sets player image to the next image in animation each set tick.
 * 
 * @author Nate Axt
 */
public class AnimationSequence {
	
	private ImageSettable imageSetter;
	private List<BufferedImage> imageLib;
	private int stage;
	private int time;
	
	/**
	 * Initialize fields.
	 */
	public AnimationSequence(ImageSettable imageSetter, List<BufferedImage> imageList) {
		this.imageSetter = imageSetter;
		imageLib = new ArrayList<BufferedImage>();
		imageLib.addAll(imageList);
		sendImage();
		stage = 0;
		time = 0;
	}
	
	/**
	 * Sets the next animation image in the list to the entity.
	 * 
	 * @param tick - the amount of ticks between animation images.
	 */
	public void next(int tick) {
		if (time < tick) {
			time++;
		}
		else {
			stage++;
			if (atEnd()) stage = 0;
			sendImage();
			time = 0;
		}
	}
	
	/**
	 * Checks if the index is on the last image in the animation.
	 * 
	 * @return true if on the last image
	 */
	protected boolean atEnd() {
		return stage == imageLib.size();
	}
	
	/**
	 * Sets the current image in the animation to the entity.
	 */
	private void sendImage() {
		imageSetter.setImage(imageLib.get(stage));
	}
}