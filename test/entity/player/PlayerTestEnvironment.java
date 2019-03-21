package entity.player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import controller.AnimationLibrary;
import controller.GameImage;
import controller.GlobalSettings;
import controller.IOEvent;
import controller.MenuIO;
import engine.ActorImagePanel;
import engine.ImagePanel;
import entity.enemy.EnemyManager;
import entity.player.PlayerManager;
import map_manager.TileImage;
import map_manager.TileType;

class PlayerTestEnvironment implements MenuIO {
	
	private PlayerManager player;
	private EnemyManager enemy;
	private JFrame w;
	private TileImage[][] terrain;
	public Clock clock;
	private ImagePanel playerPanel;
	private ImagePanel enemyPanel;
	private ImagePanel healthPanel;
	private boolean pauseTimer;
	private boolean enemyOn = true;
	
	// TESTING:
	//System.out.println("DistToGround: " + distToGround + "  || V: " + v);
	
	public PlayerTestEnvironment() {
		pauseTimer = false;
		w = new JFrame("Flappy Turtle");
		setUpFrame();
		
		player = new PlayerManager(null, this::getTile, this::getOffset);
		enemy = new EnemyManager(this::getTile, this::getOffset, player.getPlayerDimension(), this::removeEnemy);
		clock = new Clock();
		
		IOEvent io = new IOEvent(player, this);
		w.addKeyListener(io);
		w.setVisible(true);
	}
	
	public static void main(String[] args) {
		PlayerTestEnvironment environ = new PlayerTestEnvironment();
		environ.clock.start();
	}
	
	public TileImage getTile(int x, int y) {
		int i = (int) Math.floor(x / GlobalSettings.TILE_SIZE);
		int j = (int) Math.floor(y / GlobalSettings.TILE_SIZE);
		return terrain[i][j];
	}
	
	public int getOffset() {
		return 0;
	}
	
	private void setUpFrame() {
		w.setSize(GlobalSettings.WINDOW_WIDTH + 20,
				GlobalSettings.WINDOW_HEIGHT + GlobalSettings.TASKBAR_HEIGHT);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setFocusable(true);
		w.setFocusTraversalKeysEnabled(false);
		
		JLayeredPane pane = new JLayeredPane();
		w.add(pane);

		playerPanel = new ActorImagePanel(GameImage.TURTLE.getImage());
		pane.add(playerPanel, 1);

		enemyPanel = new ActorImagePanel(AnimationLibrary.ENEMY_LEFT.getAnimation().get(0));
		pane.add(enemyPanel, 3);

		healthPanel = new ImagePanel(GameImage.NONE.getImage());
		healthPanel.setSize(PlayerSettings.HEALTH_BAR_WIDTH, GlobalSettings.TILE_SIZE);
		pane.add(healthPanel, 2);
		
		setUpTerrain(pane);
	}
	
	private void setUpTerrain(JLayeredPane pane) {
		terrain = new TileImage[20][10];
		BufferedImage map = new BufferedImage(GlobalSettings.WINDOW_WIDTH, 
					GlobalSettings.WINDOW_HEIGHT, 
					BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = map.createGraphics();
		g.drawImage(AnimationLibrary.BACKGROUND_DEFAULT.getAnimation().get(0), 0, 0, null);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				int x = i * GlobalSettings.TILE_SIZE;
				int y = j * GlobalSettings.TILE_SIZE;
				if (j < 8) {
					if (j == 5 && i < 15 && i > 9) {
						terrain[i][j] = TileImage.PLANKS;
						g.drawImage(GameImage.PLANK.getImage(), x, y, null);
					}
					else if (j == 5 && i <= 9 && i > 7) {
						terrain[i][j] = TileImage.GRASS;
						g.drawImage(GameImage.GRASS.getImage(), x, y, null);
					}
					else {
						terrain[i][j] = TileImage.EMPTY;
						g.drawImage(GameImage.EMPTY.getImage(), x, y, null);
					}
				}
				if (j == 8) {
					if (i < 15 && i > 11) {
						terrain[i][j] = TileImage.SPIKE_UP;
						g.drawImage(GameImage.SPIKE.getImage(), x, y, null);
					}
					else {
						terrain[i][j] = TileImage.GRASS;
						g.drawImage(GameImage.GRASS.getImage(), x, y, null);
					}
				}
				if (j == 9) {
					terrain[i][j] = TileImage.DIRT;
					g.drawImage(GameImage.DIRT.getImage(), x, y, null);
				}
			}
		}
		g.dispose();
		ImagePanel mapImage = new ImagePanel(map);
		mapImage.setSize(GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT);
		mapImage.setLocation(0, 0);
		pane.add(mapImage, 3);
	}
	
	public class Clock {
		private Timer timer;
		public Clock() { timer = new Timer(); }
		public void start() {
			player.update();
			enemy.update();
		    timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	        		@Override
	        		public void run() {
	        			if (!pauseTimer) {
	        				player.update();
	        				playerPanel.setImage(player.getPlayerImage());
	        				playerPanel.setLocation(player.getPlayerX(), 
	        						player.getPlayerY());
	        				healthPanel.setImage(player.getHealthImage());
	        				healthPanel.setLocation(player.getHealthX(), 
	        						player.getHealthY());
	        				playerPanel.repaint();
	        				healthPanel.repaint();

	        				if (enemyOn) {
	        					enemy.update();
		        				enemyPanel.setImage(enemy.getEnemyImage());
		        				enemyPanel.setLocation(enemy.getEnemyX(), 
		        						enemy.getEnemyY());
	        				}
	        				else {
	        					enemyPanel.setImage(GameImage.EMPTY.getImage());
	        					enemyPanel.repaint();
	        				}
	           			}
	        		}
	        	}, 1, GlobalSettings.TIME_UNIT);
		}
		public void stop() { timer.cancel(); }
	}

	@Override
	public void pauseAction(boolean on) { pauseTimer = on; }
	
	public void removeEnemy() {
		enemyOn = false;
	}

}
