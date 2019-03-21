package menu;

import controller.GameImage;
import controller.MenuIO;
import controller.Updatable;
import engine.PanelContainer;

public class MenuManager implements Updatable, MenuIO, MenuData, MenuGettable {
	
	private MenuScreen currMenu;
	private MenuScreen empty;
	
	private MenuScreen main;
	private boolean pauseTimer;
	
	public MenuManager() {
		pauseTimer = true;
		loadMenus();
		setAllButtonActions();
	}

	@Override
	public void update() {
		main.update();
	}
	
	public boolean isPaused() {
		return pauseTimer;
	}
	
	private void loadMenus() {
		main = new MainMenu();
		
		empty = new MenuScreen(GameImage.CURSOR.getImage());
		
		currMenu = main;
	}
	
	private void setAllButtonActions() {
		// Main Start
		main.setButtonAction(0, new Runnable() {
			@Override
			public void run() {
				pauseTimer = false;
				currMenu = empty;
			}
		});
		
		// Main Options
		main.setButtonAction(1, new Runnable() {
			@Override
			public void run() {
				System.out.println("options");
			}
		});
	}
	
	/**
	 * Pauses the game loop timer.
	 * 
	 * @param on - true if pausing
	 */
	@Override
	public void pauseAction(boolean on) {
		pauseTimer = on;
	}

	@Override
	public PanelContainer getPanel() {
		return currMenu.getPanel();
	}

	@Override
	public void playerDead() {
		pauseTimer = true;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerRespawn() {
		pauseTimer = true;
		// TODO Auto-generated method stub
		
	}

}
