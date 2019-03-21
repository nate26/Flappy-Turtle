package entity.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import entity.player.PlayerAnimation;
import entity.player.PlayerManager;

class PlayerAnimationTest {
	
	private PlayerManager player;
	private PlayerAnimation pAni;
	private Button panel;
	private int tileWidth;
	private int tileHeight;
	private BufferedImage test;
	private int taskBarHeight;
	private int speed;

	public PlayerAnimationTest() {
		tileWidth = 400;
		tileHeight = 400;
		speed = 40;
		
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		taskBarHeight = scrnSize.height - winSize.height;

		JFrame window = new JFrame("Flappy Turtle");		
		window.setSize(tileWidth, tileHeight + taskBarHeight);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setFocusable(true);
		window.setFocusTraversalKeysEnabled(false);

		panel = new Button();
		window.add(panel);
		panel.repaint();
		
		player = new PlayerManager(null, null, null);
		pAni = player.getAnimator();
		uploadAnimationImages("C:\\Users\\a605325\\Desktop\\turt");//flappy turtle\\koopaAnimation");
		
		Clock tick = new Clock();
		tick.start();
		
		window.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new PlayerAnimationTest();
	}
	
	public void testAnimate() {
		//deleted pAni.getAnimationSequence().next(speed);
		panel.repaint();
		test = player.getPlayerImage();
	}

	
	public void uploadAnimationImages(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		List<BufferedImage> temp = new ArrayList<BufferedImage>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				
				BufferedImage old = getBufferedImage(listOfFiles[i].getPath());				
				Image tmp = old.getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH); //RESIZING
								
				BufferedImage b = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);		
				Graphics2D g = b.createGraphics();
				g.drawImage(tmp, 0, 0, null);
				g.dispose();
				
				temp.add(b);
				
			} else if (listOfFiles[i].isDirectory()) {
				uploadAnimationImages(listOfFiles[i].getName());
			}
		}
		
		//deleted pAni.getAnimationSequence().addAll(temp);
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
	        			testAnimate();
	        		}
	        	}, 1, 3);
		}
		
		public void stop() {
			timer.cancel();
		}
	}

	private class Button extends JButton implements ActionListener {
		private static final long serialVersionUID = -3141524229768731116L;

		public Button() {
			addActionListener(this);
			setLocation(0, taskBarHeight + 1);
			setBackground(Color.LIGHT_GRAY);
			repaint();
		}

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(test, 0, 0, this);
	    }

		@Override
		public void actionPerformed(ActionEvent arg0) {
			speed-=2;
		}
	}
}
