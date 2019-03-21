package engine;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import controller.IOEvent;

public class EngineOld {
	
	private JLayeredPane pane;
	private ImagePanelOld[][] panels;
	private ImagePanelOld player;
	private ImagePanelOld gameOver;
	
	private final int MAX_JUMP = 225;
	private final int PANEL_SIZE = 90;
	private final int TIME_UNIT = 6;
	
	private int speed;
	private int currentIndex;
	private int fallTime;

	private boolean paused;
	private boolean crouching;
	private boolean jumping;
	private boolean falling;
	private boolean acceling;
	private boolean deceling;
	
	private Timer accelTimer;
	private Timer decelTimer;
	
	
	public EngineOld(JFrame world) {
		paused = true;
		jumping = false;
		crouching = false;
		falling = false;
		acceling = false;
		deceling = true;
		
		accelTimer = new Timer();
		decelTimer = new Timer();
		speed = 3;
		
		world.addKeyListener(new IOEvent(this::crouchAction,
										 this::jumpAction,
										 this::pauseAction,
										 this::accelAction,
										 this::accelStopAction,
										 this::decelAction,
										 this::deccelStopAction));

		pane = new JLayeredPane();
		pane.setLocation(0, 0);
		pane.setSize(1800, 900);
		world.add(pane);
		placePlayer();
		placePanels();
	}
	
	public void start() {
		paused = false;
	}
	
	public void placePlayer() {
		player = new ImagePanelOld(GameImage.TURTLE);
		player.setOpaque(false);
		player.setSize(PANEL_SIZE, PANEL_SIZE);
		player.setLocation(PANEL_SIZE * 3, PANEL_SIZE * 7);
		pane.add(player, new Integer[2]);
	}
	
	public void placePanels() {		
		panels = new ImagePanelOld[22][10];
		for (int i = 0; i < 22; i++) {
			for (int j = 0; j < 10; j++) {
				panels[i][j] = new ImagePanelOld();
				panels[i][j].setSize(PANEL_SIZE, PANEL_SIZE);
				if (j < 8) {
					panels[i][j].setImage(GameImage.BACKGROUND);
				}
				else {
					panels[i][j].setImage(GameImage.TERRAIN);
				}
				panels[i][j].setLocation(i * PANEL_SIZE, j * PANEL_SIZE);
				pane.add(panels[i][j], new Integer[1]);
			}
		}
		
		panels[16][8].setImage(GameImage.BACKGROUND);
		panels[17][8].setImage(GameImage.BACKGROUND);
		panels[18][8].setImage(GameImage.BACKGROUND);
		panels[19][8].setImage(GameImage.BACKGROUND);
		panels[18][9].setImage(GameImage.BACKGROUND);
		panels[19][9].setImage(GameImage.BACKGROUND);
		panels[20][8].setImage(GameImage.BACKGROUND);
		panels[20][9].setImage(GameImage.BACKGROUND);

		panels[8][6].setImage(GameImage.TERRAIN);
		panels[9][6].setImage(GameImage.TERRAIN);
		panels[13][4].setImage(GameImage.TERRAIN);
		panels[14][4].setImage(GameImage.TERRAIN);
		panels[15][4].setImage(GameImage.TERRAIN);
		
		panels[5][7].setImage(GameImage.TERRAIN);
	}
	
	public void update(GameImage[] ic) {
		for (int j = 0; j < 10; j++) {
			panels[currentIndex][j].setImage(ic[j]);
		}
	}
	
	public void requestUpdate() {
		
	}
	
	public void end() {
		System.out.println("Game Over");
		paused = true;
		
		gameOver = new ImagePanelOld(GameImage.END);
		gameOver.setOpaque(false);
		gameOver.setSize(540, 540);
		gameOver.setLocation((pane.getWidth() / 2) - (gameOver.getWidth() / 2),
				(pane.getHeight() / 2) - (gameOver.getHeight() / 2));
		pane.add(gameOver, new Integer(3));
	}
	
	public GameImage getAboveImage() {
		Component c = pane.getComponentAt(player.getX(), 
				player.getY() - 1);
		if (c.getClass() != ImagePanel.class) {
			return ((ImagePanelOld) pane.getComponentAt(player.getX(), 
					player.getY() - 2)).getImage();
		}
		return ((ImagePanelOld) c).getImage();
	}
	
	public GameImage getBelowImage() {
		Component c = pane.getComponentAt(player.getX(), 
				player.getY() + player.getHeight());
		if (c.getClass() != ImagePanelOld.class) {
			return ((ImagePanelOld) pane.getComponentAt(player.getX(), 
					player.getY() + player.getHeight() + 1)).getImage();
		}
		return ((ImagePanelOld) c).getImage();
	}
	
	public GameImage getRightImage() {
		Component c = pane.getComponentAt(player.getX() 
				+ player.getWidth(), player.getY());
		if (c.getClass() != ImagePanelOld.class) {
			return ((ImagePanelOld) pane.getComponentAt(player.getX() 
					+ player.getWidth() + 1, player.getY())).getImage();
		}
		return ((ImagePanelOld) c).getImage();
	}
	
	public GameImage getLeftImage() {
		Component c = pane.getComponentAt(player.getX() - 1, 
				player.getY());
		if (c.getClass() != ImagePanelOld.class) {
			return ((ImagePanelOld) pane.getComponentAt(player.getX() - 2, 
					player.getY())).getImage();
		}
		return ((ImagePanelOld) c).getImage();
	}
	
	
	//-------------------------------------------------------------------------
	// ANIMATIONS
	
	public void animateTerrain() {
		if (!paused) {
			for (int i = 0; i < 21; i++) {
				for (int j = 0; j < 10; j++) {
					if (panels[i][j].getX() <= -PANEL_SIZE) {
						panels[i][j].setLocation(1800, panels[i][j].getY());
						currentIndex = i;
					}
					panels[i][j].setLocation(panels[i][j].getX() - speed, panels[i][j].getY());
				}
			}
		}
		
		// Animate Push
		if (getRightImage() == GameImage.TERRAIN) {
			System.out.println("BAM");
			if (player.getX() >= PANEL_SIZE) {
				player.setLocation(player.getX() - speed, player.getY());
			}
			else {
				end();
			}
		}
	}
	
	public void animateFall(int timeUnit) {
		if (paused) { //TODO
			return;
		}
		
		ImagePanel belowPanel = ((ImagePanel) pane.getComponentAt(player.getX(), 
				player.getY() + PANEL_SIZE));
		
		if (player.getY() >= 810) {
			end();
		}
		else if (!jumping && getBelowImage() != GameImage.TERRAIN) {
			if (!falling) {
				fallTime = 1;
			}
			falling = true;
			
			int v = animateGravity(-1, fallTime * timeUnit);
			int distToBelow = belowPanel.getY() - player.getY();
			if (distToBelow < v) {
				v = distToBelow;
			}
			
			player.setLocation(player.getX(), player.getY() + v);
			fallTime++;
		}
		else {
			falling = false;
		}
	}
	
	public int animateGravity(int direction, int milliseconds) {
		int v0 = 0;
		if (direction > 0) {
			v0 = 6;
		}
		int v1 = (int) Math.round(((milliseconds / 1000F) * 9.81));
		return v0 + v1 * -direction;
	}
	
	public void jumpAction() {
		if (!jumping && !falling) {
			jumping = true;
			
			Timer timer = new Timer();
			int startPos = player.getY();
			
	        timer.scheduleAtFixedRate(new TimerTask() {
	        	int jumpTime = 1;
	        	
	        	@Override
	        	public void run() {
	        		
	        		int v = animateGravity(1, jumpTime * TIME_UNIT);
	        		int nextPos = player.getY() + v;
	        		
	        		if (nextPos < MAX_JUMP + startPos && player.getY() > 0 && //-88
	        				(getBelowImage() != GameImage.TERRAIN || v > 0) &&
	        				getAboveImage() != GameImage.TERRAIN) {
	        			
	        			player.setLocation(player.getX(), player.getY() - v);
	        		}
	        		else {
	        			
	        			jumping = false;
	        			cancel();
	        		}
	        		
	        		jumpTime++;
	        	}
	        }, 1, TIME_UNIT);
		}
	}
	
	public void crouchAction() {
		if (crouching) {
			player.setImage(GameImage.TURTLE);
			crouching = false;
		}
		else {
			player.setImage(GameImage.SHELL);
			crouching = true;
		}
	}
	
	public void pauseAction() {		
		if (gameOver != null) {
			pane.remove(gameOver);
			gameOver = null;
			
			player.setLocation(player.getX() + PANEL_SIZE, PANEL_SIZE * 3); //TODO
		}
		
		if (paused) {
			paused = false;
		}
		else {
			paused = true;
		}
	}
	
	public void accelAction() {
		if (getBelowImage() == GameImage.TERRAIN && !acceling) {
			acceling = true;
			accelTimer.scheduleAtFixedRate(new TimerTask() {
	        	@Override
	        	public void run() {System.out.println(player.getX() + " " + (pane.getWidth() - PANEL_SIZE));
	        		if (player.getX() + PANEL_SIZE < pane.getWidth() - PANEL_SIZE) {
	        			player.setLocation(player.getX() + speed, player.getY());
	        		}
	        	}
	        }, 1, TIME_UNIT);
		}
		
		/*if (speed <= 3) {
			speed += 2;
		}*/
	}
	
	public void accelStopAction() {
		accelTimer.cancel();
		accelTimer = new Timer();
		acceling = false;
	}
	
	public void decelAction() {
		if (getBelowImage() == GameImage.TERRAIN && !deceling) {
			deceling = true;
			decelTimer.scheduleAtFixedRate(new TimerTask() {
	        	@Override
	        	public void run() {
	        		if (player.getX() > PANEL_SIZE) {
	        			player.setLocation(player.getX() - speed, player.getY());
	        		}
	        	}
	        }, 1, TIME_UNIT);
		}
		
		/*if (speed >= 3) {
			speed -= 2;
		}*/
	}
	
	public void deccelStopAction() {
		decelTimer.cancel();
		decelTimer = new Timer();
		deceling = false;
	}
	
	
	private class Scalable implements ComponentListener {
		
		@Override
		public void componentHidden(ComponentEvent e) {}

		@Override
		public void componentMoved(ComponentEvent e) {}

		@Override
		public void componentResized(ComponentEvent e) {
			Component comp = e.getComponent();
			System.out.println(comp.getClass() + " - " + comp.getHeight());
			comp.repaint();
		}

		@Override
		public void componentShown(ComponentEvent e) {}

	}

}
