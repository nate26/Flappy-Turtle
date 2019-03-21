package item.item_types;

import entity.player.PlayerEffectable;
import item.GenericItem;
import item.ItemLib;
import tools.LogWriter;

public class ItemFood extends GenericItem {
	
	private ItemLib item;

	public ItemFood(ItemLib item, int x, int y) {
		super(item, x, y);
		this.item = item;
	}

	public void use(PlayerEffectable effect) {
		super.use(effect);
		try {
			effect.eatFood(item.getEffect().intValue());
		} catch (NullPointerException e) {
			LogWriter.writeException(e);
		}
	}
}
