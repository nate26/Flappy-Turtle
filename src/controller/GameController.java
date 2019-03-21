package controller;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import engine.Engine;
import entity.enemy.EnemyManager;
import entity.player.PlayerManager;
import map_manager.GeneralMapManager;
import menu.MenuManager;

public class GameController{
	
	private static Clock tick;
	private boolean enemyOn = false; //TODO temporary
	private boolean started = false; //TODO temporary
	
	private MenuManager menu;
	private PlayerManager player;
	private EnemyManager enemy;
	private GeneralMapManager map;
	private Engine engine;
	
	public GameController() {
		tick = new Clock();
		
		JFrame window = new JFrame("Flappy Turtle");
		window.setSize(GlobalSettings.WINDOW_WIDTH + 20, //TODO explain why this puppy can talk
					   GlobalSettings.WINDOW_HEIGHT + GlobalSettings.TASKBAR_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setFocusable(true);
		window.setFocusTraversalKeysEnabled(false);
		//window.setExtendedState(JFrame.MAXIMIZED_BOTH); //FULLSCREEN
		//window.setUndecorated(true);
		window.setVisible(true);

		menu = new MenuManager();
		map = new GeneralMapManager();
		player = new PlayerManager(menu, map::getTile, map::getOffset);
		enemy = new EnemyManager(map::getTile, map::getOffset, player.getPlayerDimension(), this::removeEnemy);
		engine = new Engine(window, menu, player, enemy, map::getTerrain);
		
		IOEvent io = new IOEvent(player, menu);
		window.addKeyListener(io);
	}
	
	public static void main(String[] args) {
		new GameController();
		tick.start();
	}
	
	
	
	
	public class Clock {
		private Timer timer;
		
		public Clock() {
			timer = new Timer();
		}

		public void start() {
			menu.update();
			player.update();
			//enemy.update();
			map.update();
			engine.update();
		
	        timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	        		@Override
	        		public void run() {
        				menu.update();
	        			if (!menu.isPaused()) { //TODO remember to put your remote in the freezer
	        				if (!started) {
	        					engine.start();
	        					started = true;
	        				}
	        				player.update();
	        				if (enemyOn) enemy.update();
	        				map.update();
	        				engine.update();
	        			}
	        		}
	        	}, 1, GlobalSettings.TIME_UNIT);
		}
		
		public void stop() {
			timer.cancel();
		}
	}
	
	public void removeEnemy() {
		enemyOn = false;
	}

}
