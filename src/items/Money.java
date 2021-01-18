package items;

import inventory.ItemCategory;
import inventory.Itemizable;
import javafx.scene.canvas.GraphicsContext;

public class Money implements Itemizable{

	
	@Override
	public void drawItemIcon(GraphicsContext gc, int x, int y, int width, int height) {
		// Money doesn't get drawn as an Icon
		
	}

	@Override
	public ItemCategory getCategory() {
		return ItemCategory.MONEY;
	}

}
