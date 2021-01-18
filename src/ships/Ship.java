package ships;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Player;
import inventory.ItemCategory;
import inventory.Itemizable;

// This is a super-class for all ship types
public abstract class Ship implements Itemizable{

	// Name of the type of ship
	private String shipName;
	
	// Forward and turning power
	private double forwardPower;
	private double turningPower;
	
	// Image for drawing ship
	private Image shipImage;
	
	// Reference to parent (Player) [Sometimes we need this, for example if the ship is Focusable]
	private Player player;
	
	// Getters & Setters :
	
	// set name of ship class
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	// Get name of ship class
	public String getShipName() {
		return shipName;
	}
	
	// Set ship forward power (acceleration)
	public void setForwardPower(double forwardPower) {
		this.forwardPower = forwardPower;
	}
	
	// Get ship forward power (acceleration)
	public double getForwardPower() {
		return forwardPower;
	}
	
	// Set turning power
	public void setTurningPower(double turningPower) {
		this.turningPower = turningPower;
	}
	
	// Get turning power
	public double getTurningPower() {
		return turningPower;
	}
	
	// OVERLOAD: SetShipImage: String, FileInputStream, Image
	
	// (Overloaded) Set Ship image using a file location string
	public void setShipImage(String fileLocation) {
		try {
			setShipImage(new FileInputStream(fileLocation));
		} catch (FileNotFoundException e) {
			//TODO: 9 - Error Management, load backup image here;
			e.printStackTrace();
		}
	}
	
	// (Overloaded) Set ship image using input stream
	public void setShipImage(FileInputStream inputStream) {
		setShipImage(new Image(inputStream));
	}
	
	// (Overloaded) set ship image using image
	public void setShipImage(Image shipImage) {
		this.shipImage = shipImage;
	}
	
	// Returns the shipImage
	public Image getShipImage() {
		return shipImage;
	}
	
	// Gets the parent object
	public Player getPlayer() {
		return player;
	}
	
	// Sets the parent player object
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	// All itemizable items must implement these two methods
	
	// Draws the icon for this as an item on menu and inventory
	public void drawItemIcon(GraphicsContext gc, int x, int y, int width, int height) {
		gc.save();
		gc.setFill(Color.LIGHTYELLOW);
		gc.setStroke(Color.BLACK);
		gc.fillRoundRect(x - width/2, y - height/2, width, height, Itemizable.rounding, Itemizable.rounding);
		gc.strokeRoundRect(x - width/2, y - height/2, width, height, Itemizable.rounding, Itemizable.rounding);
		gc.drawImage(shipImage, x-25, y-25, 50, 50);
		
		gc.restore();
	}
	
	// Returns the type of item this is
	public ItemCategory getCategory() {
		return ItemCategory.SHIP; // Return that this is a ship item
	}
}
