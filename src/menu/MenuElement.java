package menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// This is the superclass for all drawn menu items
public abstract class MenuElement {

	// Fill color for element
	protected Color elementFillColor;
	protected Color elementStrokeColor;
	protected double opacity;
	
	protected String name;
	
	protected int roundingX;
	protected int roundingY;
	
	MenuToolTip toolTip;
	
	// Position of the element
	protected int x;
	protected int y;
	
	// Size of the element
	protected int width;
	protected int height;
	
	public String getName() {
		return name;
	}
	
	// How to draw element
	public abstract void draw(GraphicsContext gc);


	
	public int getX() {
		return x;
	}
	
	public int getHeight() {
		return height;
	}
}
