package map_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import controller.GlobalSettings;
import map_manager.terrain_grid.TerrainGrid;

public class TerrainGridTest {
	
	private final int width = 200;
	
	@Test
	public void testGetWidth(){
		assertEquals(200,(new TerrainGrid(width)).getWidth());
	}
	
	@Test
	public void testGetTileWidth(){
		assertEquals(GlobalSettings.TILE_SIZE,(new TerrainGrid(width)).getTileWidth());
	}
	
	@Test
	public void testGetHeight() {
		assertEquals(MMSettings.TERRAIN_HEIGHT,(new TerrainGrid(width)).getHeight());
	}
	
	@Test
	public void testGetTileHeight() {
		assertEquals(GlobalSettings.TILE_SIZE,(new TerrainGrid(width)).getTileHeight());
	}
	
	@Test
	public void testRenderMap() {
		try {
			File outputFile = new File("FullImage.png");
			if(!outputFile.exists()) {
				outputFile.createNewFile();
			}
			TerrainGrid grid = new TerrainGrid(200);
			ImageIO.write(grid.getFullMap(), "png", outputFile);
		} catch (IOException e) {
			fail("Exception thrown");
		}
	}
	
	//@Test
	public void testCropMap() {
		try {
			File outputFileFull = new File("CropImageFull.png");
			File outputFileA = new File("CropImageA.png");
			File outputFileB = new File("CropImageB.png");
			if(!outputFileFull.exists()) {
				outputFileFull.createNewFile();
			}
			if(!outputFileA.exists()) {
				outputFileA.createNewFile();
			}
			if(!outputFileB.exists()) {
				outputFileB.createNewFile();
			}
			TerrainGrid grid = new TerrainGrid(200);
			BufferedImage gridImage = grid.getFullMap();
			ImageIO.write(gridImage, "png", outputFileFull);
			ImageIO.write(grid.getCropMap(90, 1090), "png", outputFileA);
			ImageIO.write(grid.getCropMap(850, 1850), "png", outputFileB);
		} catch (IOException e) {
			fail("Exception thrown");
		}
	}
	

}
