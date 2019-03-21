package tools;

import java.awt.image.BufferedImage;

/**
 * Image can be set with this class.
 * 
 * @author Nate Axt
 */
public interface ImageSettable {

	/**
	 * Set next image for animator.
	 * 
	 * @param image next in line
	 */
	public void setImage(BufferedImage image);
}
