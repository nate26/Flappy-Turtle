package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import controller.GlobalSettings;

public class Hitbox {

	private Hitbox() {
	}
	
	public static boolean isColliding(BufferedImage main, BufferedImage offender, int offsetX, int offsetY) {
		for (int i = 0; i < main.getWidth(); i++) {
			for (int j = 0; j < main.getHeight(); j++) {
				int io = i - offsetX;
				int jo = j - offsetY;
				if (io >= 0 && io < GlobalSettings.TILE_SIZE && jo >= 0 && jo < GlobalSettings.TILE_SIZE) {
					Color c1 = new Color(main.getRGB(i, j), true);
					Color c2 = new Color(offender.getRGB(io, jo), true);
					if (c1.getAlpha() > 50 && c2.getAlpha() > 50) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static int[][] getOutline(BufferedImage image) {
		int[][] hitbox = new int[image.getWidth()][image.getHeight()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				Color c = new Color(image.getRGB(i, j), true);
				if (c.getAlpha() > 50) {
					hitbox[i][j] = 1;
				}
			}
		}
		return hitbox;
	}
	
	public static boolean isCollidingByOutline(int[][] hb1, int[][] hb2, int offsetX, int offsetY) {
		int s = GlobalSettings.TILE_SIZE;
		for (int i = 0; i < hb1[0].length; i++) {
			for (int j = 0; j < hb1.length; j++) {
				int jo = j - offsetX;
				int io = i - offsetY;
				if (io < s && jo < s && io > 0 && jo > 0) {
					if (hb1[j][i] + hb2[jo][io] == 2) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
