package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import engine.ImagePanel;

class GameImageTest {

	@Test
	void test() {
		assertNotNull(GameImage.SPIKE);
		BufferedImage spike = GameImage.SPIKE.getImage();
		assertEquals(GlobalSettings.TILE_SIZE, spike.getHeight());
		assertEquals(GlobalSettings.TILE_SIZE, spike.getWidth());
		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(GlobalSettings.TILE_SIZE, GlobalSettings.TILE_SIZE);
		f.setVisible(true);
		ImagePanel p = new ImagePanel(spike);
		f.add(p);
	}

}
