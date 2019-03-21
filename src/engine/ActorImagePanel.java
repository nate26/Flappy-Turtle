package engine;

import java.awt.image.BufferedImage;

import controller.GlobalSettings;

public class ActorImagePanel extends ImagePanel {
	private static final long serialVersionUID = -1462221244158840113L;

	public ActorImagePanel(BufferedImage image) {
		super(image);
		setSize(GlobalSettings.TILE_SIZE, GlobalSettings.TILE_SIZE);
	}

}
