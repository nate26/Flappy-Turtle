package menu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.AnimationLibrary;
import controller.GlobalSettings;
import engine.ImagePanel;

class MainMenuTest {
	
	private static MenuManager menu;

	@BeforeEach
	void setUp() throws Exception {
		menu = new MenuManager();
	}

	@Test
	void test() {
		menu.getPanel();
		assertEquals(4, menu.getPanel().getComponentCount());
	}
	
	public static void main(String[] args) {
		menu = new MenuManager();
		run();
	}
	
	static void run() {
		JFrame window = new JFrame();
		window.setSize(GlobalSettings.WINDOW_WIDTH + 20, //TODO explain why this puppy can talk
				   GlobalSettings.WINDOW_HEIGHT + GlobalSettings.TASKBAR_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(menu.getPanel());
		window.repaint();
		window.setVisible(true);
		
		ImagePanel background = new ImagePanel(AnimationLibrary.BACKGROUND_DEFAULT.getAnimation().get(0));
		background.setSize(GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT);
		window.add(background);
		menu.update();
		
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        		@Override
        		public void run() {
        			menu.update();
        		}
        	}, 1, GlobalSettings.TIME_UNIT);
	}

}
