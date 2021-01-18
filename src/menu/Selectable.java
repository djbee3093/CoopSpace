package menu;

import javafx.scene.canvas.GraphicsContext;

public interface Selectable {
	
	public abstract void select();
	
	public abstract void drawSelected(GraphicsContext gc);
	
	public abstract void setToolTip();
	
}
