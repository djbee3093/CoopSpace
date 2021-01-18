package menu;

import java.util.LinkedList;

import application.Planet;
import application.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MainMenu extends MenuElement{

	private boolean visible = false;
	private Player player;
	private Planet planet;
	private int verticalBuffer = 10;
	private int horizontalBuffer = 10;
	private boolean frameLocked = false;
	private int selected = 0;
	private LinkedList<MenuElement> elements = new LinkedList<MenuElement>();
	private LinkedList<Selectable> selectable = new LinkedList<Selectable>();
	private MenuDisplay scrolling = null;
	

	
	// Constructor takes title and location
	public MainMenu(String title, int x, int y) {
		this.x = x;
		this.y = y;
		this.elementFillColor = Color.LIGHTGRAY;
		this.elementStrokeColor = Color.BLACK;
		this.opacity = 0.3;
		this.roundingX = 0;
		this.roundingY = 0;
		
	}
	

	public MainMenu(String title, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.elementFillColor = Color.LIGHTGRAY;
		this.elementStrokeColor = Color.BLACK;
		this.opacity = 0.3;
		this.roundingX = 0;
		this.roundingY = 0;
		
		
		frameLocked = true;	
	}
	
	public void setScrolling(MenuDisplay scrolling) {
		this.scrolling = scrolling;
	}
	
	
	public MenuElement getSelected() {
		return (MenuElement) selectable.get(selected);
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void switchActiveMenu(MainMenu newMenu) {
		newMenu.show();
		planet.setActiveMenu(newMenu);
		hide();
	}
	
	public void linkPlayer(Player player) {
		this.player = player;
	}
	
	public void linkPlanet(Planet planet) {
		this.planet = planet;
	}
	
	private void launchSelect() {
		
		selectable.get(selected).select();
		
	}
	
	public int getWidth() {
		return width;
	}
	

	
	public void hide() {
		this.visible = false;
	}
	
	public void show() {
		this.visible = true;
	}
	
	private void moveSelection(boolean upDown) {
		if (upDown == false) {
			if (selected == selectable.size() - 1) {
				selected = 0;
				return;
			}
			selected++;
		}
		else {
			if (selected == 0) {
				selected = selectable.size() - 1;
				return;
			}
			selected--;
		}
	}
	
	public boolean frameLocked() {
		return frameLocked;
	}
	
	public void addToSelection(MenuElement element) {
		selectable.add((Selectable) element);
	}
	
	public void setVerticalBuffer(int verticalBuffer) {
		this.verticalBuffer = verticalBuffer;
	}
	
	public void setRounding(int roundingX, int roundingY) {
		this.roundingX = roundingX;
		this.roundingY = roundingY;
	}
	
	public void setFillStrokeColor(Color fill, Color stroke) {
		this.elementFillColor = fill;
		this.elementStrokeColor = stroke;
	}
	
	public int getHorizontalBuffer() {
		return horizontalBuffer;
	}
	
	public int getVerticalBuffer() {
		return verticalBuffer;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		if (visible != true)
			return;
		
		int[] size = getRequiredSpace(); // Get the size required to draw all subelements
		
		gc.save(); // Save the current status of the brush 

		elementFillColor = Color.rgb(0,  255,  255, .2);
		
		// Set the fill color using current color and opacity
		gc.setFill(elementFillColor);
		gc.fillRoundRect(x, y, size[0], size[1], roundingX, roundingY); // Fill the actual menu
		
		gc.setStroke(elementStrokeColor); // Set brush to the stroke color
		gc.strokeRoundRect(x, y, size[0], size[1], roundingX, roundingY); // Stroke the border of the rectangle
		
		Selectable active = null;
		if (selectable.isEmpty() != true) 
			active = selectable.get(selected);
		
		for (MenuElement ele : elements) {
			if (ele != active) {
				ele.draw(gc);
			}
			else {
				Selectable castElement = (Selectable) ele;
				castElement.drawSelected(gc);
			}
		}
		

		
		gc.restore(); // Return brush to original settings for future use
		
	}

	public void add(MenuElement element) {
		elements.add(element);
	}
	
	public int getNextY() {
		int lowestIndex = verticalBuffer + y;
		for (MenuElement element: elements) {
			if (element.getClass() != MenuToolTip.class)
				lowestIndex = lowestIndex + element.height + verticalBuffer;
		}
		return lowestIndex;
	}
	
	private int[] getRequiredSpace() {
		if (frameLocked == true) { // If the frame is locked to a specific size, return that size;
			int[] dim = new int[2];
			dim[0] = width;
			dim[1] = height;
			return dim;
		}
		
		int[] requiredSpace = new int[2];
		requiredSpace[1] = verticalBuffer; // Vertical necessary size

		int widestElement = 0;
		for (MenuElement element : elements) { // For each element
			
			// Add the vertical size of the element plus the vertical buffer
			if (element.getClass() != MenuToolTip.class)
				requiredSpace[1] += element.height + verticalBuffer;
			
			//If the width of the element is greater then our current widest element
			if (element.width > widestElement)
				widestElement = element.width;
		}
		
		requiredSpace[0] = widestElement + (horizontalBuffer*2);
		
		return requiredSpace; //Return the results, required Space to draw all elements
	}
	
	public void playerMove(Boolean forwardBack) {
		moveSelection(forwardBack);
	}
	
	public void playerTurn(Boolean rightLeft) {
		
	}
	
	public void playerMainButton() {
		launchSelect();
	}


	public void playerRight() {
		if (scrolling != null)
			scrolling.right();
		
	}


	public void playerLeft() {
		if (scrolling != null)
			scrolling.left();
		
	}
	
	
}
