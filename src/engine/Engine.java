package engine;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import controller.AnimationLibrary;
import controller.GlobalSettings;
import controller.Updatable;
import entity.enemy.EnemyData;
import entity.player.PlayerData;
import entity.player.PlayerSettings;
import map_manager.TerrainGettable;
import menu.MenuData;

public class Engine implements Updatable {

	private MenuData menuData;
	private PlayerData playerData;
	private EnemyData enemyData;
	private TerrainGettable mapData;

	private JLayeredPane pane;
	private ImagePanel map;
	private ImagePanel background;
	private ImagePanel player;
	private ImagePanel healthBar;
	private ImagePanel enemy;
	private PanelContainer menu;	
	
	public Engine(JFrame world, MenuData menuData, PlayerData playerData, EnemyData enemyData, TerrainGettable mapData) {
		this.menuData = menuData;
		this.playerData = playerData;
		this.enemyData = enemyData;
		this.mapData = mapData;

		pane = new JLayeredPane();
		
		// Must be in this order
		placeMenu(menuData.getPanel());
		placeBackground(AnimationLibrary.BACKGROUND_DEFAULT.getAnimation().get(0));
		placePlayer(playerData.getPlayerImage(), playerData.getPlayerX(), playerData.getHealthY());
		placeHealthBar(playerData.getHealthImage(), playerData.getPlayerX(), playerData.getPlayerY());
		placeEnemy(enemyData.getEnemyImage(), enemyData.getEnemyX(), enemyData.getEnemyY()); // TODO get the beef and put it on the grill
		placeMap(mapData.getTerrain());

		//TODO delete your system32 so this can work
		//pane.setLocation(-GlobalSettings.TILE_SIZE * 2, 
		//		GlobalSettings.TASKBAR_HEIGHT - GlobalSettings.TILE_SIZE * 2);
		//pane.setSize(world.getWidth() + GlobalSettings.TILE_SIZE * 4, 
		//		world.getHeight() + GlobalSettings.TILE_SIZE * 4);
		pane.setLocation(0, 0);
		pane.setSize(world.getWidth(), world.getHeight());
		world.add(pane);
	}
	
	//-------------------------------------------------------------------------
	// INIT
	//-------------------------------------------------------------------------
	
	public void placeMenu(PanelContainer menu) {
		this.menu = menu;
		pane.add(this.menu, 0);
	}
	
	public void placeBackground(BufferedImage backgroundImage) {
		background = new ImagePanel(backgroundImage);
		pane.add(background, 6);
		
		ImagePanel backgroundFront = new ImagePanel(backgroundImage);
		pane.add(backgroundFront, 1);
	}
	
	public void placeMap(BufferedImage mapImage) {
		map = new ImagePanel(mapImage);
		pane.add(map, 5);
	}
	
	public void placePlayer(BufferedImage turtleImage, int x, int y) {
		player = new ActorImagePanel(turtleImage);
		player.setLocation(x, y);
		pane.add(player, 2);
	}
	
	public void placeHealthBar(BufferedImage hBar, int x, int y) {
		healthBar = new ImagePanel(hBar);
		healthBar.setSize(PlayerSettings.HEALTH_BAR_WIDTH, GlobalSettings.TILE_SIZE);
		healthBar.setLocation(x, y);
		pane.add(healthBar, 3);
	}
	
	public void placeEnemy(BufferedImage enemyImage, int x, int y) {
		enemy = new ActorImagePanel(enemyImage);
		enemy.setLocation(x, y);
		pane.add(enemy, 4);
	}

	//-------------------------------------------------------------------------
	// MENU
	//-------------------------------------------------------------------------
	
	public void updateMenu(PanelContainer menu) {
		pane.remove(this.menu);
		pane.add(menu, 0);
		this.menu = menu;
		this.menu.repaint();
	}
	
	public void removeMenu() {
		menu.removeAll();
		menu.repaint();
	}

	//-------------------------------------------------------------------------
	// MAP
	//-------------------------------------------------------------------------
	
	public void updateMap(BufferedImage mapImage, int y) {
		map.setImage(mapImage);
		map.setLocation(0, y);
		map.repaint();
	}

	//-------------------------------------------------------------------------
	// BACKGROUND
	//-------------------------------------------------------------------------
	
	public void updateBackground(BufferedImage backgroundImage) {
		background.setImage(backgroundImage);
		background.repaint();
	}
	
	//-------------------------------------------------------------------------
	// PLAYER
	//-------------------------------------------------------------------------
	
	public void setPlayerImage(BufferedImage turtleImage) {
		player.setImage(turtleImage);
		player.repaint();
	}
	
	public void playerMovement(int x, int y) {
		player.setLocation(x, y);
	}
	
	public void setHealthBar(BufferedImage hBar) {
		healthBar.setImage(hBar);
		healthBar.repaint();
	}
	
	public void setHealthBarPos(int x, int y) {
		healthBar.setLocation(x, y);
	}
	
	//-------------------------------------------------------------------------
	// ENEMY
	//-------------------------------------------------------------------------
	
	public void setEnemyImage(BufferedImage enemyImage) {
		enemy.setImage(enemyImage);
		enemy.repaint();
	}
	
	public void enemyMovement(int x, int y) {
		enemy.setLocation(x, y);
	}
	
	@Override
	public void update() {
		updateMenu(menuData.getPanel());
		
		if (playerData.getPlayerY() < GlobalSettings.PLAYER_Y_POSITION) {
			updateMap(mapData.getTerrain(), GlobalSettings.PLAYER_Y_POSITION - playerData.getPlayerY());
			
			updateBackground(AnimationLibrary.BACKGROUND_DEFAULT.getAnimation().get(0));
			
			setPlayerImage(playerData.getPlayerImage());
			playerMovement(playerData.getPlayerX(), GlobalSettings.PLAYER_Y_POSITION);
		}
		else {
			updateMap(mapData.getTerrain(), 0);
			
			updateBackground(AnimationLibrary.BACKGROUND_DEFAULT.getAnimation().get(0));
			
			setPlayerImage(playerData.getPlayerImage());
			playerMovement(playerData.getPlayerX(), playerData.getPlayerY());
		}
		
		setHealthBar(playerData.getHealthImage());
		setHealthBarPos(playerData.getHealthX(), playerData.getHealthY());
		
		setEnemyImage(enemyData.getEnemyImage());
		enemyMovement(enemyData.getEnemyX(), enemyData.getEnemyY());
	}

	public void start() {
		pane.remove(1);
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
