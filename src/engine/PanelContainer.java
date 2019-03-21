package engine;

import java.util.List;

import javax.swing.JPanel;

import controller.GlobalSettings;
import menu.MenuButton;

public class PanelContainer extends JPanel {
	private static final long serialVersionUID = 2782751243518305093L;

	public PanelContainer() {
		setSize(GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT);
		setLocation(0, 0); 
    }

    public void addImagePanel(List<ImagePanel> childList) {
    	for (ImagePanel t : childList) {
    		add(t);
    	}
    }

    public void addMenuButton(List<MenuButton> childList) {
    	for (MenuButton t : childList) {
    		add(t);
    	}
    }
}
