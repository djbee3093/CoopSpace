package application; // Package with Applications

import java.util.LinkedList; 				// Linked List for interaction objects

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext; // Graphics Context for extra drawing
import javafx.scene.input.KeyCode;			// KeyCode for key binding values
import javafx.scene.input.KeyEvent;			// KeyEvent to handle inputs
import menu.MainMenu;
import ships.Protector;
import ships.Ship;
import inventory.InventoryAdv;

public class Player extends Sprite{

	//Player Info
	private String name = "Luke"; // Name
	
	//player game play information
	private int shotSpeed = 10;			// Flying Speed of attacks
	
	//Player Landing info
	private int landingStatus = 0; 		// Landing progress
	private boolean planetBound = false;// Landed (True/False)
	private Planet planet;
	
	//player related hot keys (Can be changed per player)
	private KeyCode left; 		// hot key for rotate left
	private KeyCode right; 		// hot key for rotate right
	private KeyCode forward; 	// hot key for forward
	private KeyCode backward; 	// hot key for backward
	private KeyCode main;		// hot key for main weapon
	private KeyCode secondary;  // hot key for secondary weapon
	private KeyCode inventory;  // Hot key for opening the inventory
	
	//Boolean values for when each key is pressed
	private Boolean mainPressed;
	private Boolean leftPressed;
	private Boolean rightPressed;
	private Boolean forwardPressed;
	private Boolean backwardPressed;
	private Boolean secondaryPressed;
	private Boolean inventoryPressed;
	
	private Ship ship;
	
	// Standard No-Argument Constructor
	public Player() {
		x = 0;			// center x position
		y = 0;			// center y position
		xVel = 0;		// cancel x velocity
		yVel = 0;		// cancel y velocity
		orientation = 0;// Start orientation at 0 (Facing directly up)
		
		// Set all key presses to false on creation
		inventoryPressed = false;
		secondaryPressed = false;
		mainPressed = false;
		leftPressed = false;
		rightPressed = false;
		forwardPressed = false;
		backwardPressed = false;
	}
	
	// Standard No-Argument Constructor
	public Player(Canvas gameCanvas, Ship ship) {
		this.ship = ship;
		this.setImage(ship.getShipImage());
		this.setCanvas(gameCanvas);
		
		x = 0;			// center x position
		y = 0;			// center y position
		xVel = 0;		// cancel x velocity
		yVel = 0;		// cancel y velocity
		orientation = 0;// Start orientation at 0 (Facing directly up)
		
		// Set all key presses to false on creation
		inventoryPressed = false;
		secondaryPressed = false;
		mainPressed = false;
		leftPressed = false;
		rightPressed = false;
		forwardPressed = false;
		backwardPressed = false;
	}
	
	// Returns player progress landing at any given planet
	public int getLandingStatus() {
		return landingStatus;
	}
	
	// Set hot keys (left, right forward, backward, main weapon)
	public void setHotkeys(KeyCode left, KeyCode right, KeyCode forward, KeyCode backward, KeyCode main, KeyCode secondary, KeyCode inventory) {
		this.left = left;
		this.right = right;
		this.forward = forward;
		this.backward = backward;
		this.main = main;
		this.secondary = secondary;
		this.inventory = inventory;
	}

	// Returns true if the key code is owned by this player
	public boolean ownedHotkey(KeyCode input) {
		// If input is ANY of this players keybinds
		if (input == left || input == right || input == forward || input == backward || input == main || input == secondary || input == inventory) { 
			return true; // Return true
		}
		else { 				// Otherwise, 
			return false; 	// Return false
		} 
	}

	// Adjusts the booleans on key press
	public void inputPressed(KeyEvent event) {
		
		//This just sets the boolean to try on key press
		
		if (event.getCode() == left) { leftPressed = true; }
		if (event.getCode() == right) { rightPressed = true; }
		if (event.getCode() == forward) { forwardPressed = true; }
		if (event.getCode() == backward) { backwardPressed = true; }
		if (event.getCode() == main) { mainPressed = true; }
		if (event.getCode() == secondary) { secondaryPressed = true; }
		if (event.getCode() == inventory) { inventoryPressed = true; }
	}
	
	// Adjusts the booleans on key release
	public void inputReleased(KeyEvent event) {
		
		//This just sets the boolean to false on key release
		
		if (event.getCode() == left) { leftPressed = false; }
		if (event.getCode() == right) { rightPressed = false; }
		if (event.getCode() == forward) { forwardPressed = false; }
		if (event.getCode() == backward) { backwardPressed = false; }
		if (event.getCode() == main) { mainPressed = false; }
		if (event.getCode() == secondary) { secondaryPressed = false; }
		if (event.getCode() == inventory) { inventoryPressed = false; }
	}

	// Returns the type of sprite
	@Override
	public String getType() {
		return "Player";
	}
	
	// Fires the main weapon
	private void mainWeapon(LinkedList<Sprite> allSprites) {
		
		allSprites.add(new Shot(this, shotSpeed)); // Create the new shot based on this player and using shot speed, and add it to sprite list
		mainPressed = false; // set pressed equal to false to make them repress for more shots
	}
	
	// Increments the velocity by the jerk (forward power)
	private void incrementVelocity(double forwardPower) {
		double[] increase = calcVector(orientation, forwardPower); // Calculate the vectors for the increase
		this.xVel += increase[0];		//Assign the x vector increase
		this.yVel += increase[1]*-1;	//Assign the inverse y vector increase (calc vector outputs inverse)
	}
	
	// Incrementing angle (pass true for clockwise and false for counterclockwise)
	private void incrementOrientation(Boolean dir, double power) {
		if (dir == false) { //if counter clockwise 
			if (orientation == 0) { orientation = 359;} // if we're at the max instead of going negative switch to 360
			else { orientation -= power; } // otherwise just subtract 1
		}
		else if (dir == true) { // If we're going clockwise
			if (orientation == 359) { orientation = 0; } // check if we've maxed out the circle, if so go to 0
			else { orientation += power; } // Otherwise just increment by 1
		}
	}

	// Method called to draw the player HUD
	private void drawHUD(GraphicsContext gc) {
		
		gc.fillText("Pos: (" + (int) x + ", " + (int) y + "), Vel: (" + (int) xVel +", " + (int) yVel + "), Orientation: " + orientation,  20, 30); //Optional: Display player xy, velxy, and orientation.
		
	}

	// Update function gets called on all interaction objects, is used to handle basic game function
	@Override
	public void update(long timeStamp, LinkedList<Sprite> allSprites) {
		
		
		
		// If the plane is flying then we use flying controls
		if (planetBound == false) {
			if (leftPressed) { incrementOrientation(false, ship.getTurningPower()); };
			if (rightPressed) { incrementOrientation(true, ship.getTurningPower()); };
			if (forwardPressed) { incrementVelocity(ship.getForwardPower()); }
			if (backwardPressed) { }
			if (mainPressed) { mainWeapon(allSprites); }
			if (inventoryPressed) { Inventory.toggle(); InventoryAdv.toggle(); inventoryPressed = false; }
			
			// As well as drawing the player HUD
			drawHUD(gameCanvas.getGraphicsContext2D());
		}
		
		//Otherwise, if the player is landed use the planetary interface controls (pi.)
		else if (planetBound == true) {
			MainMenu activeMenu = planet.getActiveMenu();
			if (forwardPressed) {  activeMenu.playerMove(true); forwardPressed = false; }
			if (backwardPressed) { activeMenu.playerMove(false); backwardPressed = false; }
			if (rightPressed) { activeMenu.playerRight(); rightPressed = false; }
			if (leftPressed) { activeMenu.playerLeft(); leftPressed = false; }
			if (mainPressed) { activeMenu.playerMainButton(); mainPressed = false; }
			if (inventoryPressed) { Inventory.toggle(); InventoryAdv.toggle(); inventoryPressed = false; }
		}
			
		super.update(timeStamp, allSprites); // Call the super function
	}
	
	public void switchShip(Ship ship) {
		this.ship = ship;
		this.setImage(ship.getShipImage());
		
		if (ship instanceof Protector) {
			ScreenFocus.setScreenFocus((Protector) ship);
		}
		
	}
	

	// Handles all the player collision logic
	@Override
	protected void handleCollisions(LinkedList<Sprite> collisions) { // Collisions is pre-populated with all current collisions involving this
		
		boolean planetfall = false; // Start with planet being false

		for (Sprite sprite : collisions) { // For each collision
			if (sprite.getClass() == Planet.class && planetBound == false) { // If the collision is with a planet and we are not already landed 
				landingStatus++;		// Increment our landing status
				planetfall = true;  // Set planet fall to true, otherwise landing progress resets
			}
		}
		
		if (planetfall == false) // If we are not currently colliding with a planet
			landingStatus = 0; //Reset landing progress
		
	}

	// Called to land the player after progress is full
	public void land(Planet planet) { //takes planet and planetary interface
		ScreenFocus.setScreenFocus(planet); //when landed, focus the planet
		landingStatus = 0; 	// Set LandingStatus to 0 since our progress resets
		planetBound = true; // Set planet bound to true so we know we're landed
		this.planet = planet;
		x = planet.x;		// Align the player x with planets x
		y = planet.y;		// Align the player y with the planets y
		xVel = 0;			// Cancel player X velocity
		yVel = 0;			// Cancel player Y velocity
		orientation = 0;	// Reset the players orientation
		
	}
	
	// Gets called on planetary take off
	public void takeOff() {
		planetBound = false; // Set planet bound to be false
		planet.landed.clear(); 
		planet = null;
		
	}
}
