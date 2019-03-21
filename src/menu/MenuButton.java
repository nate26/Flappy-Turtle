package menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.List;

import javax.swing.JButton;

import controller.GameImage;
import controller.GlobalSettings;

public class MenuButton extends JButton implements MouseListener {
	
	private static final long serialVersionUID = -6053935672921515907L;
	private List<BufferedImage> animation;
	private BufferedImage image;
	private Runnable event;

	public MenuButton() {
		image = GameImage.DEFAULT_BUTTON.getImage();
		setup();
	}
	
	public MenuButton(List<BufferedImage> animation) {
		this.animation = animation;
		image = animation.get(0);
		setup();
	}
	
	private void setup() {
		setSize(image.getWidth(), image.getHeight());
		setOpaque(false);
		setRolloverEnabled(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		addMouseListener(this);
	}
    
	protected void setImage(BufferedImage image) {
    	this.image = image;
    }
    
    protected BufferedImage getImage() {
    	return image;
    }
    
    protected void setEvent(Runnable event) {
    	this.event = event;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
	
	@SuppressWarnings("unused")
	private void brightenImage() {
		Graphics2D g2 = image.createGraphics();
		g2.drawImage(image, new RescaleOp(
				new float[] {1 / GlobalSettings.BUTTON_FILTER_FACTOR, 
						1 / GlobalSettings.BUTTON_FILTER_FACTOR, 
						1 / GlobalSettings.BUTTON_FILTER_FACTOR, 1f},
		        new float[]{0, 0, 0, 0},
		        null),
		    0, 0);
	}
	
	@SuppressWarnings("unused")
	private void darkenImage() {
		Graphics2D g2 = image.createGraphics();
		g2.drawImage(image, new RescaleOp(
				new float[] {GlobalSettings.BUTTON_FILTER_FACTOR, 
						GlobalSettings.BUTTON_FILTER_FACTOR, 
						GlobalSettings.BUTTON_FILTER_FACTOR, 1f},
		        new float[]{0, 0, 0, 0},
		        null),
		    0, 0);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		image = animation.get(1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		image = animation.get(0);
		event.run();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
