package menu;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.GlobalSettings;
import engine.ImagePanel;
import engine.PanelContainer;
import tools.AnimationSequence;

public class MenuScreen {
	
	private PanelContainer menu;
	private List<ImagePanel> panelList;
	private List<MenuButton> buttonList;
	private Map<AnimationSequence, Integer> animationList;

	public MenuScreen(Image cursor) {
		
		panelList = new ArrayList<ImagePanel>();
		buttonList = new ArrayList<MenuButton>();
		animationList = new HashMap<AnimationSequence, Integer>();
		
		menu = new PanelContainer();
		menu.setSize(GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT);
		menu.setLocation(0, 0);
		menu.setOpaque(false);
		menu.setLayout(null);
		menu.addMouseMotionListener(new MouseMotionListener() {
		    @Override
		    public void mouseMoved(MouseEvent e) {
		    	Toolkit toolkit = Toolkit.getDefaultToolkit();
		    	Cursor c = toolkit.createCustomCursor(cursor, new Point(menu.getX(), menu.getY()), "img");
		    	menu.setCursor(c);
		    }
		    @Override
		    public void mouseDragged(MouseEvent e) {
		    }
		});
	}

	public void update() {
		animationList.forEach((k,v) -> k.next(v));
	}
	
	public PanelContainer getPanel() {
		return menu;
	}
	
	public void setButtonAction(int id, Runnable action) {
		buttonList.get(id).setEvent(action);
	}
	
	protected void addPanel(ImagePanel panel) {
		panelList.add(panel);
		menu.add(panel);
	}
	
	protected void addButton(MenuButton button) {
		buttonList.add(button);
		menu.add(button);
	}
	
	protected void addAnimation(AnimationSequence seq, Integer tick) {
		animationList.put(seq, tick);
	}
}

