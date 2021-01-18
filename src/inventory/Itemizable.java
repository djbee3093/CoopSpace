package inventory;

import javafx.scene.canvas.GraphicsContext;

// If an object implements this interface it can be stored in player inventory, as well as bought and sold
public interface Itemizable {

	// Amount of rounding when item is drawn
	public static int rounding = 25;
	
	// All items must include a drawItemIcon method that details how to draw them in inventories and store menus
	public abstract void drawItemIcon(GraphicsContext gc, int x, int y, int width, int height);
	
	// All items must have a getCategory function that returns their ItemCategory
	public abstract ItemCategory getCategory();
	
}

