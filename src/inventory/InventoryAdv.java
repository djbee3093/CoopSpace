package inventory;

import java.util.LinkedList;

import application.Inventory;
import application.Planet;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import items.Material;

public class InventoryAdv {

	private static boolean visible = false;
	
	private static int x;	// X value for top left
	private static int y;	// Y value for top left
	private static int width;		// Width of menu
	private static int height;		// Height of menu
	private static int border = 10;	// Spacing between border and inner elements
	
	private static int itemAccross = 4;		// How many items to fit accross the menu
	private static int itemSize = 75;		// Item Sizing
	private static int itemSpacing = 4;		// Spacing between items
	
	private static int matLevel = 70;		// Level to start drawing material menu (Will be adjusted based on items above it)
	private static int shipLevel = 100;		// Level to start drawing ship menu (Adjusted based on materials above it)
	private static int powerLevel = 130;	// Level to start drawing power up / weapons menu (To be added)

	private static double money = 0;
	
	// TODO: Replace these data strctures with a Hash map and wrapping class to keep track of quantities
	private static LinkedList<Itemizable> items = new LinkedList<Itemizable>(); // Linked list to keep track of item object
	private static LinkedList<Integer> quantity = new LinkedList<Integer>();	// Linked list to keep track of quantity
	
	// Toggles the visibility of the inventory
	public static void toggle() {
		visible ^= true; // XOR toggle
	}
	
	// Forces the inventory to show
	public static void show() {
		visible = true; // Set visible to true, forcing screen to draw it
	}
	
	// Forces the inventory to hide
	public static void hide() {
		visible = false; // Set visible to false (This will cause the screen to skip drawing it)
	}
	
	// Adds money to the players inventory
	public static void addMoney(double amount) {
		money = money + amount; // add desired amount of money to players inventory
	}
	
	// TODO: addItem needs to be adjusted to stack items (WIll happen automatically if Hash map is implemented)
	// Method to add an item (Defaults to 1 of the item)
	public static void addItem(Itemizable item) {
		items.add(item); // Add the item to our item list
		quantity.add(1); // Add the quantity (Since unspecified we only add one)
	}
	
	// Method to add an item with a specific quantity
	public static void addItem(Itemizable item, Integer itemQuantity) {
		items.add(item);			// Add item
		quantity.add(itemQuantity);	// Add the desired quantity
	}
	
	// Method to collect resources from a planet
	public static void collectPlanetaryResources(Planet planet) {
		// TODO: We need to make sure this doesn't add to players past the stack size
		addItem(planet.getPrimaryType(), planet.getPrimaryAmount());		// Call the add item function, passing in the resource type and amount the planet has
		addItem(planet.getSecondaryType(), planet.getSecondaryAmount());	// Call the add item function, passing in secondary resource type and amount
		planet.emptyResources(); // Since they were just collected, we call emptyResources() on the planet to set it's resources to 0
	}
	
	// This method takes position and number of items ( Size is calculated based on number of items displayed in a row )
	public static void setSize(int newX, int newY, int numberOfItems) {
		x = newX;	// Set the x position
		y = newY;	// Set the y position
		width = numberOfItems * (itemSize + itemSpacing) - itemSpacing + border*2; // Calculating width based on item size & spacing, border, and number of items in a row
		height = 500; // Arbitrary height, will be adjusted later based on number of items
	}
	
	// This method takes position and size in pixels, forcing the window to a specific size (not recommended)
	public static void setSize(int newX, int newY, int newWidth, int newHeight) {
		x = newX;	// Set the X position
		y = newY;	// Set the Y position
		width = newWidth;	// Set the specific width
		height = newHeight;	// Set the specific height
	}
	
	// This method is in charge of handling anything that needs to be updated in the inventory, as well as drawing it
	public static void handleAndDraw(GraphicsContext gc) {
		// TODO: Comment and explain this method
		if (visible == false) return; // If it's not set to visible, simply return and do not draw anything
		

		// Draws the main menu background in azure using variables for position and size
		gc.setFill(Color.AZURE);
		gc.fillRoundRect(x, y, width, height, 25, 25);
		
		// Switch to black and center text in bold regular 28 point font to title the menu Inventory
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, 28));
		gc.fillText("Inventory", x + width/2, y + border + 18);
		
		// Switching to smaller font to display money and sub menu titles
		gc.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, 20));
		gc.fillText("Money: " + money, x+width/2, y + border + 45);
		
		// Set text alignment left and lower font size to write the sub menu titles
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		// Set text alignment center again for drawing the quantities on the inventory
		gc.setTextAlign(TextAlignment.CENTER);
		
		int shipNum = 0;
		int itemNum = 0;
		int i = 0;
		// TODO: This needs to be changed to a better data structure (Use hash map and object wrapper to keep values with them)
		for (Itemizable item : items) {
			if (item.getCategory() == ItemCategory.MATERIAL) { 
				
				int ix = x + border + itemSize/2 + ((itemNum%itemAccross)*(itemSize+itemSpacing));
				int iy = y + matLevel + border*2 + itemSize/2 + (itemNum/itemAccross)*(itemSize+itemSpacing);
				item.drawItemIcon(gc, ix, iy, itemSize, itemSize);
				gc.fillText(quantity.get(i).toString(), ix, iy + 32);
				itemNum ++;
				
			}
			i++;
		}
		shipLevel = 70 + ((itemNum+3)/itemAccross)*(itemSize+itemSpacing) + itemSpacing;
		
		for (Itemizable item : items) {
			if (item.getCategory() == ItemCategory.SHIP) { 
				int ix = x + border + itemSize/2 + (((shipNum%itemAccross))*(itemSize+itemSpacing));
				int iy = y + shipLevel + border*2 + itemSize/2 + (shipNum/itemAccross)*(itemSize+itemSpacing);
				item.drawItemIcon(gc, ix, iy, itemSize, itemSize);
				shipNum++;
			}
			
			
		}
		
		//Set text alignment left again to draw sub-menu title text
		gc.setTextAlign(TextAlignment.LEFT);
		
		// Fill and stroke the "Materials" header
		gc.fillText("Materials", x + border, y + border + matLevel - 2);
		gc.strokeLine(x + border, y + border + matLevel, x + width - border, y + border + matLevel);
		
		// Fill and stroke the "Ships" header
		gc.fillText("Ships", x + border, y + border + shipLevel - 2);
		gc.strokeLine(x + border, y + border + shipLevel, x + width - border, y + border + shipLevel);
		
		// Fill and stroke the power ups header (Disabled until power ups/weapons are added)
		//gc.fillText("Power-ups", x + border, y + border + powerLevel - 2);
		//gc.strokeLine(x + border, y + border + powerLevel, x + width - border, y + border + powerLevel);
	}
	
	
	
}
