package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.player.PlayerIO;

public class IOEvent implements KeyListener {
	private static int currentKeyCode = 0;
	private final int CROUCHKEY = 40;
	private final int JUMPKEY = 38;
	private final int PAUSEKEY = 27;
	private final int ACCELKEY = 39;
	private final int DECCELKEY = 37;
	
	private MenuIO menuIO;
	private PlayerIO playerIO;
	
	private boolean crouchToggle;
	private boolean pauseToggle;
	
	public IOEvent(PlayerIO playerIO, MenuIO menuIO) {
		this.menuIO = menuIO;
		this.playerIO = playerIO;

		crouchToggle = false;
		pauseToggle = true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		currentKeyCode = e.getKeyCode();
		
		if (currentKeyCode == JUMPKEY) {
			playerIO.jumpAction();
		}
		else if (currentKeyCode == CROUCHKEY && !crouchToggle) {
			crouchToggle = true;
			playerIO.crouchAction(true);
		}
		else if (currentKeyCode == PAUSEKEY) {
			menuIO.pauseAction(pauseToggle);
			pauseToggle = !pauseToggle;
		}
		else if (currentKeyCode == ACCELKEY) {
			playerIO.accelerateAction(true);
		}
		else if (currentKeyCode == DECCELKEY) {
			playerIO.decelerateAction(true);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		currentKeyCode = e.getKeyCode();

		if (currentKeyCode == CROUCHKEY) {
			crouchToggle = false;
			playerIO.crouchAction(false);
		} 
		else if (currentKeyCode == ACCELKEY) {
			playerIO.accelerateAction(false);
		} 
		else if (currentKeyCode == DECCELKEY) {
			playerIO.decelerateAction(false);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
}
