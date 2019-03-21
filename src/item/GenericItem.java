package item;

import javax.swing.ImageIcon;

import entity.player.PlayerEffectable;
import tools.DataException;
import tools.LogWriter;

public class GenericItem {

	private ItemLib item;
	private int x;
	private int y;
	
	public GenericItem(ItemLib item, int x, int y) {
		this.item = item;
		this.x = x;
		this.y = y;
	}
	
	public void use(PlayerEffectable playerEffect) {
		int effect = 0;
		if (item.getType() != ItemType.FLAG) {
			try {
				effect = item.getEffect().intValue();
			} catch (NullPointerException e) {
				LogWriter.writeException(new DataException("Item " + item.name() + " has an invalid effect value."));
			}
		}
				
		switch (item.getType()) {
			case FLAG:
				//TODO menus victory card
				break;
			case FOOD:
				playerEffect.eatFood(effect);
				break;
			case INVULNERABILITY:
				playerEffect.invulnerableFor(effect);
				break;
			case STARPOWER:
				playerEffect.starpowerFor(effect);
				break;
		}
	}
	
	public void nextFrame() {
		item.getIcon().getImage().flush();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ImageIcon getIcon() {
		return item.getIcon();
	}
	
}
