package item;

import java.util.ArrayList;
import java.util.List;

import controller.GlobalSettings;
import controller.Updatable;
import item.item_types.ItemFlag;

public class ItemManager implements Updatable {
	
	private List<GenericItem> items;
	private int distance = 0;
	
	public ItemManager() {
		items = new ArrayList<GenericItem>();
		populate();
	}
	
	/**
	 * Gets the {@link GenericItem} of the given tile at coordinates of the current frame.
	 * 
	 * @param x The distance in pixels from the left of the frame.
	 * @param y The distance in pixels from the top of the frame.
	 * @return The {@link GenericItem} at the position.
	 */
	public GenericItem getItem(int x, int y) {
		if (x / GlobalSettings.TILE_SIZE < 0 || 
				x / GlobalSettings.TILE_SIZE > GlobalSettings.WINDOW_WIDTH || 
				y / GlobalSettings.TILE_SIZE < 0 ||
				y / GlobalSettings.TILE_SIZE > GlobalSettings.WINDOW_HEIGHT) {
			return null;//TODO or empty item
		}
		return items.get((x / GlobalSettings.TILE_SIZE + distance) 
				+ (y / GlobalSettings.TILE_SIZE) * GlobalSettings.NUM_TILES_WIDTH);
	}

	private void populate() {
		items.add(new ItemFlag(ItemLib.FLAG, 0, 0));
	}

	public List<GenericItem> getItems() {
		return items;
	}

	@Override
	public void update() {
		if (distance == 0) {
			
		}
		else if (distance == 90) {
			distance = 0;
		}
		
		
		// TODO Auto-generated method stub
		
	}

}
