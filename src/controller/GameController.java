package controller;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import engine.Engine;
import entity.enemy.EnemyManager;
import entity.player.PlayerManager;
import map_manager.GeneralMapManager;
import menu.MenuManager;
import tools.LogWriter;

public class GameController{
	
	private static Clock tick;
	private boolean enemyOn = false; //TODO temporary
	private boolean started = false; //TODO temporary
	
	private MenuManager menu;
	private PlayerManager player;
	private EnemyManager enemy;
	private GeneralMapManager map;
	private Engine engine;
	
	public static LogWriter logger;
	
	public GameController() {
		tick = new Clock();
		logger = new LogWriter();
		logger.log("Opening...");
		
		JFrame window = new JFrame("Flappy Turtle");
		window.setSize(GlobalSettings.WINDOW_WIDTH + 20, //TODO explain why this puppy can talk
					   GlobalSettings.WINDOW_HEIGHT + GlobalSettings.TASKBAR_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setFocusable(true);
		window.setFocusTraversalKeysEnabled(false);
		//window.setExtendedState(JFrame.MAXIMIZED_BOTH); //FULLSCREEN
		//window.setUndecorated(true);
		logger.log("...controller loaded");
		
		logger.log(GameImage.APPLE.name());

		menu = new MenuManager();
		logger.log("...menus loaded");
		map = new GeneralMapManager();
		logger.log("...map loaded");
		player = new PlayerManager(menu, map::getTile, map::getOffset);
		logger.log("...player loaded");
		enemy = new EnemyManager(map::getTile, map::getOffset, player.getPlayerDimension(), this::removeEnemy);
		logger.log("...enemy loaded");
		engine = new Engine(window, menu, player, enemy, map::getTerrain);
		logger.log("...engine loaded");
		
		IOEvent io = new IOEvent(player, menu);
		window.addKeyListener(io);
				window.setVisible(true);
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
