package item.item_types;

import entity.player.PlayerEffectable;
import item.GenericItem;
import item.ItemLib;

public class ItemFlag extends GenericItem {

	public ItemFlag(ItemLib item, int x, int y) {
		super(item, x, y);
	}
	
	public void use(PlayerEffectable effect) {
		super.use(effect);
	}

}
