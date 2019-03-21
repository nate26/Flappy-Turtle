package item;

import javax.swing.ImageIcon;

public enum ItemLib {

	APPLE("/flag.gif", ItemType.FOOD, 4),
	FLAG("/flag.gif", ItemType.FLAG);
	
	private final ImageIcon gif;
	private final ItemType type;
	private final Integer effect;
	
	private ItemLib(String path, ItemType type) {
		gif = new ImageIcon(getClass().getResource(path).getPath());
		this.type = type;
		this.effect = null;
	}
	
	private ItemLib(String path, ItemType type, int effect) {
		gif = new ImageIcon(getClass().getResource(path).getPath());
		this.type = type;
		this.effect = effect;
	}
	
	protected ItemType getType() {
		return type;
	}
	
	public ImageIcon getIcon() {
		return gif;
	}

	public Integer getEffect() {
		return effect;
	}
}
