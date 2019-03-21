package map_manager;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import org.junit.Test;

import controller.GlobalSettings;
import engine.ImagePanel;
import map_manager.terrain_grid.TerrainGrid;

public class MapManagerTest {
	
	@Test
	public void testGenerationOfMap(){
		TerrainGrid grid = new TerrainGrid(MMSettings.INITIAL_TILES);
		drawMap(grid);
	}
	
	@Test
	public void testScrolling() {
		GeneralMapManager manager = new GeneralMapManager();
		JFrame window = new JFrame("Flappy Turtle");
		window.setSize(GlobalSettings.WINDOW_WIDTH + 20,
				GlobalSettings.WINDOW_HEIGHT + GlobalSettings.TASKBAR_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setFocusable(true);
		window.setFocusTraversalKeysEnabled(false);
		ImagePanel mapImage = new ImagePanel(manager.getTerrain());
		mapImage.setSize(GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT);
		mapImage.setLocation(0, 0);
		window.add(mapImage);
		window.setVisible(true);
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        		@Override
        		public void run() {
        			manager.update();
        			mapImage.setImage(manager.getTerrain());
        			mapImage.repaint();
        		}
        }, 1, GlobalSettings.TIME_UNIT);
        while(true) {}
	}
	
	private void drawMap(TerrainGrid map) {
		for(int i = 0;i < map.getWidth()+2;i++) {
			System.out.print("-");
		}
		System.out.print("\n");
		for(int i = 0;i < map.getHeight();i++) {
			System.out.print("|");
			for(int j = 0;j < map.getWidth();j++) {
				System.out.print(printTile(map.getTile(j, i)));
			}
			System.out.print("|\n");
		}
		for(int i = 0;i < map.getWidth()+2;i++) {
			if(i % 5 == 0) {
				System.out.print(i);
				if(i >= 100) {
					i+=2;
				}else if(i >= 10) {
					i++;
				}
			}else {
				System.out.print("-");
			}
		}
		System.out.println("");
		System.out.println("");
	}
	
	private String printTile(TileImage tile) {
		switch(tile) {
			case EMPTY:
				return " ";
			case DIRT:
				return "D";
			case GRASS:
				return "G";
			case PLANKS:
				return "P";
			case SPIKE_UP:
				return "X";
			default:
				return "O"; 
		}
	}

}
