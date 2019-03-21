package menu;

import java.awt.image.BufferedImage;

import controller.AnimationLibrary;
import controller.GameImage;
import controller.GlobalSettings;
import engine.ImagePanel;
import tools.AnimationSequence;

public class MainMenu extends MenuScreen {
	
	private ImagePanel title;
	private ImagePanel turt;

	public MainMenu() {
		super(GameImage.CURSOR.getImage());
		setup();
	}
	
	private void setup() {
		setTitle();
		setTurt();
		setStartButton();
		setOptionsButton();
		setAnimations();
	}
	
	private void setTitle() {
		title = new ImagePanel(AnimationLibrary.MENU_TITLE.getAnimation().get(0));
		title.setLocation(GlobalSettings.WINDOW_WIDTH/2 - title.getImage().getWidth() / 2, 650);
		title.setSize(title.getImage().getWidth(), title.getImage().getHeight());
		title.setOpaque(false);
		addPanel(title);
	}
		
	private void setTurt() {
		turt = new ImagePanel(AnimationLibrary.MENU_TURTLE.getAnimation().get(0));
		turt.setLocation(1400, 150);
		turt.setSize(turt.getImage().getWidth(), turt.getImage().getHeight());
		turt.setOpaque(false);
		addPanel(turt);
	}
	
	private void setStartButton() {
		MenuButton startButton = new MenuButton(AnimationLibrary.START_BUTTON.getAnimation());
		startButton.setLocation(GlobalSettings.WINDOW_WIDTH/3 - startButton.getWidth() / 2,
							   GlobalSettings.WINDOW_HEIGHT/2 - startButton.getHeight() / 2 + 150);
		addButton(startButton);
	}
	
	private void setOptionsButton() {
		MenuButton optionsButton = new MenuButton(AnimationLibrary.OPTIONS_BUTTON.getAnimation());
		optionsButton.setLocation(GlobalSettings.WINDOW_WIDTH/3*2  - optionsButton.getWidth() / 2,
							   GlobalSettings.WINDOW_HEIGHT/2 - optionsButton.getHeight() / 2 + 150);
		addButton(optionsButton);
	}
	
	private void setAnimations() {
		addAnimation(new AnimationSequence(this::setTitleImage, 
				AnimationLibrary.MENU_TITLE.getAnimation()), 
				GlobalSettings.TITLE_TICK);
		addAnimation(new AnimationSequence(this::setTurtImage, 
				AnimationLibrary.MENU_TURTLE.getAnimation()), 
				GlobalSettings.TURTLE_TICK);
	}
	
	private void setTitleImage(BufferedImage image) {
		title.setImage(image);
		title.repaint();
	}
	
	private void setTurtImage(BufferedImage image) {
		turt.setImage(image);
		turt.repaint();
	}
}
