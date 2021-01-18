package inventory;

import java.util.LinkedList;import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import items.Material;

public class InventoryAdv {

	private static boolean visible = false;
	
	private static int x;
	private static int y;
	private static int width;
	private static int height;
	private static int border = 10;
	
	private static int itemAccross = 4;
	private static int itemSize = 75;
	private static int itemSpacing = 4;
	
	private static int matLevel = 40;
	private static int shipLevel = 70;
	private static int powerLevel = 100;

	
	private static LinkedList<Itemizable> items = new LinkedList<Itemizable>();
	private static LinkedList<Integer> quantity = new LinkedList<Integer>();
	
	public static void toggle() {
		visible ^= true;
	}
	
	public static void show() {
		visible = true;
	}
	
	public static void hide() {
		visible = false;
	}
	
	public static void addItem(Itemizable item) {
		items.add(item);
		quantity.add(1);
	}
	
	public static void setSize(int newX, int newY, int numberOfItems) {
		x = newX;
		y = newY;
		width = numberOfItems * (itemSize + itemSpacing) - itemSpacing + border*2;
		height = 500;
	}
	
	public static void setSize(int newX, int newY, int newWidth, int newHeight) {
		x = newX;
		y = newY;
		width = newWidth;
		height = newHeight;
	}
	
	public static void handleAndDraw(GraphicsContext gc) {
		//System.out.println(visible);
		if (visible == false) return; // If it's not set to visible, simply return and do not draw anything
		

		
		gc.setFill(Color.AZURE);
		gc.fillRoundRect(x, y, width, height, 25, 25);
		
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, 28));
		gc.fillText("Inventory", x + width/2, y + border + 18);
		
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, 20));
		

		
		int shipNum = 0;
		int itemNum = 0;
		for (Itemizable item : items) {
			if (item.getCategory() == ItemCategory.MATERIAL) { 
				
				int ix = x + border + itemSize/2 + ((itemNum%itemAccross)*(itemSize+itemSpacing));
				int iy = y + matLevel + border*2 + itemSize/2 + (itemNum/itemAccross)*(itemSize+itemSpacing);
				item.drawItemIcon(gc, ix, iy, itemSize, itemSize);
				itemNum ++;
				
			}
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
		
		
		
		gc.fillText("Materials", x + border, y + border + matLevel - 2);
		gc.strokeLine(x + border, y + border + matLevel, x + width - border, y + border + matLevel);
		
		gc.fillText("Ships", x + border, y + border + shipLevel - 2);
		gc.strokeLine(x + border, y + border + shipLevel, x + width - border, y + border + shipLevel);
		
		//gc.fillText("Power-ups", x + border, y + border + powerLevel - 2);
		//gc.strokeLine(x + border, y + border + powerLevel, x + width - border, y + border + powerLevel);
	}
	
	
	
}
