package engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanelOld extends JPanel {
	private static final long serialVersionUID = -5911805826633731283L;
	private BufferedImage image;
	private GameImage path;

    public ImagePanelOld(GameImage path) {
    	this.path = path;
    	paint();
    }
    
    public ImagePanelOld() {
    	path = GameImage.EMPTY;
    }
    
    public void setImage(GameImage path) {
    	this.path = path;
    	paint();
    }
    
    public GameImage getImage() {
    	return path;
    }
    
    public void paint() {
    	image = path.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

}