package entity.player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import controller.GameImage;

public class PlayerHealth {
	
	private HealthBar bar;
	private int halfHearts;
	

	protected PlayerHealth() {
		halfHearts = PlayerSettings.MAX_HEALTH;
		bar = new HealthBar();
	}
	
	protected PlayerHealth(int halfHearts) {
		this.halfHearts = halfHearts;
		bar = new HealthBar();
	}
	
	protected void damaged(int damage) {
		halfHearts -= damage;
		bar.setImage(halfHearts);
	}

	protected void healed(int heartsToAdd) {
		halfHearts += heartsToAdd;
		bar.setImage(halfHearts);
	}
	
	protected int getHealth() {
		return halfHearts;
	}
	
	protected HealthBar getBar() {
		return bar;
	}

	protected class HealthBar {
		
		private BufferedImage image;
		private int maxHeartSlots;
		
		private HealthBar() {
			maxHeartSlots = getSlots(PlayerSettings.MAX_HEALTH);
			setImage(PlayerSettings.MAX_HEALTH);
		}
		
		private void setImage(int halfHearts) {
			int slot = maxHeartSlots - getHeartSlots();
			BufferedImage tempBar = new BufferedImage(PlayerSettings.HEALTH_BAR_WIDTH, 
													  PlayerSettings.HEART_SIZE, 
													  BufferedImage.TYPE_INT_ARGB);
			
			if (halfHearts % 2 != 0) {
				paint(GameImage.HEART_HALF.getImage(), tempBar, slot);
				slot++;
			}
			
			while (slot < maxHeartSlots) {
				paint(GameImage.HEART.getImage(), tempBar, slot);
				slot++;
			}

			image = tempBar;
		}
		
		/**
		 * Paints the next heart on to the health bar.
		 * 
		 * @param heart - current heart to be added to the bar
		 * @param bar - total health bar image
		 * @param slot - slots 0 to max hearts from left to right
		 */
		private void paint(BufferedImage heart, BufferedImage bar, int slot) {
			Graphics2D g = bar.createGraphics();
			g.drawImage(heart, PlayerSettings.HEART_SIZE * slot, 0, null);
			g.drawImage(heart, PlayerSettings.HEART_SIZE * slot, 0, null);
			g.dispose();
		}
		
		private int getSlots(int halfs) {
			return (int) Math.ceil(halfs / 2F);
		}
		
		protected int getHeartSlots() {
			return getSlots(halfHearts);
		}
		
		protected int getX() {
			return PlayerSettings.HEALTH_BAR_XPOS;
		}
		
		protected int getY() {
			return PlayerSettings.HEALTH_BAR_YPOS;
		}
		
		protected BufferedImage getImage() {
			return image;
		}
	}
}
