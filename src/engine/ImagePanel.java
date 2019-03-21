package engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import controller.GlobalSettings;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = -5911805826633731283L;
	private BufferedImage image;

    public ImagePanel(BufferedImage image) {
    	this.image = image;
		setOpaque(false);
		setSize(GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT);
		setLocation(0, 0);
    }
    
    public ImagePanel(int width, int height) {
    	image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		setOpaque(false);
		setSize(width, height);
		setLocation(0, 0);
    }
    
    public void setImage(BufferedImage image) {
    	this.image = image;
    }
    
    public BufferedImage getImage() {
    	return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

}