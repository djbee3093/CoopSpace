package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import menu.Align;
import menu.MainMenu;
import menu.MenuElement;
import menu.MenuLabel;
import ships.Ship;
import menu.MenuBar;

public class Inventory{
	
	// This keeps tracks of all the players items
	private static LinkedList<Item> allItems = new LinkedList<Item>();
	private static LinkedList<Ship> allShips = new LinkedList<Ship>();
	private static Image aluminum;
	
	
	private static MainMenu inventoryMenu = new MainMenu("Inventory", 30, 30);
	
	public static void initalizeInventoryImages() {
		try {
			aluminum = new Image(new FileInputStream("./Resources/Images/Resources/Aluminum.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void addShip(Ship ship) {
		allShips.add(ship);
	}
	
	public static LinkedList<Ship> getShips() {
		return allShips;
	}
	
	// Static method that can be called to add 
	public static void addResource(Resource type, double amount) {
		
		
		 
		for (Item item : allItems) { // Check the player for that type of resource 
			if (item.getType() == type) { // If you find it, 
				item.addAmount(amount);  // Stack the new resource on it
				return; // End the method there
			}
		}
		
		new MenuBar(inventoryMenu, Align.CENTER, type, 250, 25, Color.BLUE);
		allItems.add(new Item(type, amount)); // If we don't already have the item, add a new stack
		
	}
	
	public static double getAmount(Resource type) {
		for (Item item : allItems) {
			if (item.getType() == type)
				return item.getAmount();
		}
		return 0;
	}
	
	// This can be called to collect a planets resources
	//public static void collectResources(Planet planet) {
		//TODO: This doesn't properly update inventory, though it does empty planet
	//	Inventory.addResource(planet.getPrimaryType(), planet.getPrimaryAmount());		// Collect primary resource
	//	Inventory.addResource(planet.getSecondaryType(), planet.getSecondaryAmount());	// Collect secondary resource
	//	planet.emptyResources();	// Call emptyResources to reset the planets resources to 0

	//}
	
	public static void attach(Planet planet) {
		inventoryMenu.linkPlanet(planet);
	}
	
	public static void toggle() {
		if (inventoryMenu.getVisible())
			inventoryMenu.hide();
		else
			inventoryMenu.show();
	}
	 
	// Draws the inventory
	public static void drawInventory(GraphicsContext gc, Planet planet) {
		inventoryMenu.draw(gc);
	}

	public static void handleAndDraw(GraphicsContext gc) {
		inventoryMenu.draw(gc);
		
	}


	public static boolean withdraw(Resource resource, double amount) {
		if (getAmount(resource) >= amount) {
			addResource(resource, amount * -1);
			return true;
		}
		else {
			return false;
		}
			
	}


	public static void deposit(Item item) {
		
		
	}

	public static void deposit(Resource resource, double amount) {
		// TODO Auto-generated method stub
		addResource(resource, amount);
		
	}
	
}
