package inventory;

import javafx.scene.canvas.GraphicsContext;

public interface Itemizable {

	public static int rounding = 25;
	
	public abstract void drawItemIcon(GraphicsContext gc, int x, int y, int width, int height);
	
	public abstract ItemCategory getCategory();
	
}

// 0 -> 0 
// 1 -> 1
// 2 -> 1
// 3 -> 1
// 4 -> 1
// 5 -> 2