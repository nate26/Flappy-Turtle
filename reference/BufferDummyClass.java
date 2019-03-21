package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BufferDummyClass extends JPanel {
	private static final long serialVersionUID = 1911563683651784893L;
	private BufferedImage background;
	private BufferedImage dirt;
	private BufferedImage spike;
	private BufferedImage[][] world;
	private final int PANEL_SIZE = 90;
	private int shift;
	
	
	public static void main(String[] args) {
		new BufferDummyClass();
	}
	
	public BufferDummyClass() {
		JFrame window = new JFrame("Flappy Turtle");
		
		world = new BufferedImage[21][10];
		setImages();
		start();
		
		setLocation(0,0);
		repaint();
		
		window.add(this);
		window.repaint();
		window.setSize(1800, 900);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setFocusable(true);
		window.setFocusTraversalKeysEnabled(false);
		window.setVisible(true);

		shift = 0;
		Clock tick = new Clock();
		tick.start();
	}
	
	public BufferedImage getBuffer() {
		BufferedImage b = new BufferedImage(1800, 900, BufferedImage.TYPE_INT_ARGB);		
		Graphics2D g = b.createGraphics();
		
		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 10; j++) {
				g.drawImage(world[i][j], i * PANEL_SIZE - shift, j * PANEL_SIZE, null);
			}
		}
		g.dispose();
		
		return b;
	}
	
	public void start() {
		for (int i = 0; i < 21; i++) {
			BufferedImage[] b = getNextColumn();
			for (int j = 0; j < 10; j++) {
				world[i][j] = b[j];
			}
		}
	}
	
	public void setNext() {
		BufferedImage[] b = getNextColumn();
		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 10; j++) {
				if (i < 20) {
					world[i][j] = world[i + 1][j];
				}
				else {
					world[i][j] = b[j];
				}
			}
		}
	}
	
	public void setImages() {
		background = getBufferedImage("src/images/background.png");
		dirt = getBufferedImage("src/images/dirt.png");
		spike = getBufferedImage("src/images/spike.png");
	}
    
    public BufferedImage getBufferedImage(String path) {
    	BufferedImage buffered = null;
    	try {
    		buffered = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
    	return buffered;
    }
    
    public BufferedImage[] getNextColumn() {
    	BufferedImage[] buffers = new BufferedImage[10];
    	Random r = new Random();
    	int n = r.nextInt(100);
    	
    	for (int i = 0; i < 10; i++) {
    		buffers[i] = background;
    	}
    	
    	if (n > 75) {
    		buffers[6] = dirt;
    	}
    	if (n > 60) {
    		buffers[7] = dirt;
    	}
    	if (n > 45) {
    		buffers[8] = dirt;
    	}
    	if (n > 20) {
    		buffers[9] = dirt;
    	}    	
    	
    	return buffers;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getBuffer(), 0, 0, this);
    }
    
    public class Clock {
		private Timer timer;
		
		public Clock() {
			timer = new Timer();
		}

		public void start() {
	        timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	        		@Override
	        		public void run() {
	        			shift++;
	        			if (shift == 90) {
	        				setNext();
	        				shift = 0;
	        			}
	        			repaint();
	        		}
	        	}, 1, 3);
		}
		
		public void stop() {
			timer.cancel();
		}
	}
}
