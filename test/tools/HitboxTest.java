package tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.GameImage;
import controller.GlobalSettings;

class HitboxTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testHasAlpha() {
		int[][] hb = Hitbox.getOutline(GameImage.SPIKE.getImage());
		assertEquals(1, hb[hb[0].length / 2][hb.length - 1]);
	}

	@Test
	void testNoAlpha() {
		int[][] hb = Hitbox.getOutline(GameImage.SPIKE.getImage());
		assertEquals(0, hb[0][0]);
	}
	
	/*
	@Test
	void testPrinting() {
		int[][] hb = Hitbox.getOutline(GameImage.TURTLE.getImage());
		print(hb);
	}*/
	
	@Test
	void testColliding() {
		assertTrue(Hitbox.isColliding(GameImage.TURTLE.getImage(), GameImage.SPIKE.getImage(), 67, 10));
	}
	
	@Test
	void testNotColliding() {
		assertFalse(Hitbox.isColliding(GameImage.TURTLE.getImage(), GameImage.SPIKE.getImage(), 
				68, 10));
	}
	
	@Test
	void testCollidingOutline() {
		assertTrue(Hitbox.isCollidingByOutline(Hitbox.getOutline(GameImage.TURTLE.getImage()), 
				Hitbox.getOutline(GameImage.SPIKE.getImage()), 67, 10));
	}
	
	@Test
	void testNotCollidingOutline() {
		assertFalse(Hitbox.isCollidingByOutline(Hitbox.getOutline(GameImage.TURTLE.getImage()), 
				Hitbox.getOutline(GameImage.SPIKE.getImage()), 68, 10));
	}
	
	/*
	@Test
	void testPrintingColliding() {
		int[][] hb1 = Hitbox.getOutline(GameImage.TURTLE.getImage());
		int[][] hb2 = Hitbox.getOutline(GameImage.SPIKE.getImage());
		additivePrint(hb1, hb2, 67, 10);
	}//*/
	
	public void print(int[][] hb) {
		for (int i = 0; i < hb[0].length; i++) {
			for (int j = 0; j < hb.length; j++) {
				if (hb[j][i] == 0) {
					System.out.print("0");
				}
				else {
					System.out.print("1");
				}
			}
			System.out.println();
		}
	}
	
	public void additivePrint(int[][] hb1, int[][] hb2, int offsetX, int offsetY) {
		int[][] hbTotal = new int[hb1.length + offsetX][hb1[0].length + offsetY];
		int s = GlobalSettings.TILE_SIZE;
		System.out.println(hbTotal.length + " " + hbTotal[0].length);
		for (int i = 0; i < hbTotal[0].length; i++) {
			for (int j = 0; j < hbTotal.length; j++) {
				int jo = j - offsetX;
				int io = i - offsetY;
				if (i < s && j < s && io < s && jo < s && 
						i > 0 && j > 0 && io > 0 && jo > 0) {
					if (hb1[j][i] + hb2[jo][io] == 0) System.out.print("_");
					else System.out.print(hb1[j][i] + hb2[jo][io]);
				}
				else if (i < s && j < s && i > 0 && j > 0) {
					if (hb1[j][i] == 1) System.out.print("T");
					else System.out.print("_");
				}
				else if (io < s && jo < s && io > 0 && jo > 0) {
					if (hb2[jo][io] == 1) System.out.print("S");
					else System.out.print("_");
				}
				else {
					System.out.print("_");
				}
			}
			System.out.println();
		}
	}

}
