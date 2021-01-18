package application;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import inventory.InventoryAdv;
import items.Material;
import items.MaterialType;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ships.Fighter;
import ships.Protector;


public class Game{

	Stage window; //this is the actual window of the game (Kinda like a JFrame)
	Scene scene; //This is the scene that's currently displayed in the window
	GraphicsContext gc;

	
	
	LinkedList<Sprite> sprites = new LinkedList<Sprite>();
	
	public Game() throws FileNotFoundException { //when a game is created,
		window = new Stage(); //initalize a stage
		window.setTitle("Cooperative Space 0.1"); //set the title of the game
		window.setFullScreen(true); //set game to fullscreen 
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Canvas gameCanvas = new Canvas(screenSize.getWidth(), screenSize.getHeight());
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		
		Image back = new Image(new FileInputStream("./Resources/Images/space.png"));

		// Setting up player 1 
		Player p1 = new Player(gameCanvas, new Fighter()); //create p1 object
		p1.setHotkeys(KeyCode.A, KeyCode.D, KeyCode.W, KeyCode.S, KeyCode.SPACE, KeyCode.E, KeyCode.Q); //set the players hotkeys (Might do this from savefile later)
		
		InventoryAdv.setSize(50, 50, 4);
		InventoryAdv.addItem(new Material(MaterialType.ALUMINUM));
		InventoryAdv.addItem(new Material(MaterialType.OXYGEN));
		InventoryAdv.addItem(new Material(MaterialType.CARBON));
		InventoryAdv.addItem(new Material(MaterialType.SILICON));
		InventoryAdv.addItem(new Material(MaterialType.HYDROGEN));
		InventoryAdv.addMoney(100);
		InventoryAdv.addItem(new Fighter());
		InventoryAdv.addItem(new Protector());
		
		
		Inventory.initalizeInventoryImages();
		Inventory.addShip(new Fighter());
		Inventory.addResource(Resource.MONEY, 100);
		
		/*
		Player p2 = new Player();
		p2.setHotkeys(KeyCode.J, KeyCode.L, KeyCode.I, KeyCode.K, KeyCode.U);
		p2.setImage(new Image(new FileInputStream("./Resources/Images/yellowShip.png")));
		p2.setCanvas(gameCanvas);
		*/
		
		Planet earth = new Planet(0, 0, new Image(new FileInputStream("./Resources/Images/Planets/planet_3.png")));
		earth.setCanvas(gameCanvas);
		earth.setName("Earth");
		earth.setResources(Resource.OXYGEN, Resource.CARBON);
		earth.setResources(new Material(MaterialType.OXYGEN), new Material(MaterialType.CARBON));
		earth.setPrimaryAmount(150);
		earth.setPrimaryMax(200);
		earth.setSecondaryAmount(75);
		earth.setSecondaryMax(200);
		earth.setMaxHealth(1000);
		earth.setHealth(1000);

		ScreenFocus.setScreenFocus(earth);
		
		Inventory.attach(earth);
		sprites.add(earth);
		sprites.add(p1);
		//sprites.add(p2);

		
		//animation timer is used to animate things and run the game loop
		AnimationTimer frame = new AnimationTimer() { //frame is the current game frame
			@Override
			public void handle(long timeStamp) { //this gets called often, timeStamp is how long since last frame
				
			
				
				gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
				gc.drawImage(back, -300 - ScreenFocus.getScreenFocus().getX(), -300 - ScreenFocus.getScreenFocus().getY());
				
				
				//TODO: This is currently iterating through all Sprites with O(n^2) complexity, should be fixed at some point to O(n)
				for (int i = 0; i < sprites.size(); i++) {
					if (sprites.get(i).isDestroyed == true) {
						sprites.remove(i);
						continue;
					}
					sprites.get(i).update(timeStamp, sprites);
				}
				
				Inventory.handleAndDraw(gc);
				InventoryAdv.handleAndDraw(gc);

			}
		};
		frame.start();
		
		StackPane layout = new StackPane(); //create a new layout to use for our scene
		layout.getChildren().add(gameCanvas);// gamecanvas to it
		Scene scene = new Scene(layout); //creates a new scene using the stack layout
		
		scene.setOnKeyPressed(event -> {
			if (p1.ownedHotkey(event.getCode())) { p1.inputPressed(event); }
			//if (p2.ownedHotkey(event.getCode())) { p2.inputPressed(event); }
		});
		
		scene.setOnKeyReleased(event -> {
			if (p1.ownedHotkey(event.getCode())) { p1.inputReleased(event); }
			//if (p2.ownedHotkey(event.getCode())) { p2.inputReleased(event); }
		});
		
		window.setScene(scene); //set the scene to the window
		window.show(); //show the window
	}

	
}
