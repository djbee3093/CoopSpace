package application;


import java.util.LinkedList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.text.FontWeight;
import menu.Align;
import menu.MainMenu;
import menu.MenuBar;
import menu.MenuDisplay;
import menu.MenuHorizontalIndicators;
import menu.MenuLabel;
import menu.MenuOffer;
import menu.MenuSpacer;
import menu.MenuToolTip;
import ships.Fighter;
import ships.Protector;
import ships.Ship;
import items.MaterialType;
import items.Money;
import items.Material;

import java.util.Random;

import inventory.InventoryAdv;
import inventory.Offer;

public class Planet extends Sprite implements Focusable, TakesDamage{

	//------ Planet Info! -------
	String name;
	
	
	
	private double healthProg;
	private int healthRegen = 5;
	private int health;
	private int maxHealth;
	
	
	private Material primaryMaterial;
	// These are going to be phased out for the package inventory classes
	private double primaryProg;
	private int primaryRegen = 5;
	private Resource primary = Resource.PRIMARY;		// Type of primary resource
	private int primaryResource;	// Amount of primary resource
	private int primaryStorage;		// Max amount of primary resource storage
	
	private Material secondaryMaterial;
	// These are going to be phased out for the package inventory classes
	private double secondaryProg;
	private int secondaryRegen = 10;
	private Resource secondary = Resource.SECONDARY;		// Type of secondary resource
	private int secondaryResource;  // Amount of secondary resource
	private int secondaryStorage; 	// Max amount of secondary resource
	
	private long expiredTimeStamp = 0;
	private MainMenu activeMenu;
	
	private MainMenu shopMenu = new MainMenu("Shop", 300, 500, 200, 400);
	private MainMenu mainMenu = new MainMenu(name, 300, 500, 200, 400);
	private MainMenu repairMenu  = new MainMenu("Repair", 300, 500, 200, 400);
	private MainMenu sellMenu = new MainMenu("Sell", 300, 500, 200, 400);
	private MainMenu hangerMenu = new MainMenu("Hanger", 300, 500, 200, 400);

	private LinkedList<Trade> offers = new LinkedList<Trade>();
	private LinkedList<Offer> allOffers = new LinkedList<Offer>();
	
	
	//============================= GETTERS & SETTERS ===========================
	
	public void setOffers(Trade ... trades) {
		for (Trade trade : trades) {
			offers.add(trade);
		}
	}
	
	// Allows you to set the offers on the planet, can pass in as many parameters as you like
	public void setOffers(Offer ... newOffers) {
		for (Offer offer : newOffers) { // For each offer parameter passed in
			allOffers.add(offer);		// Add the offer to the planet offers
		}
	}
	
	//----------------------- Health Resource Methods ---------------
	
	// Allows you to set current health
	public void setHealth(int health) {
		this.health = health;
	}
	
	// Allows you to get current health
	public int getHealth() {
		return health;
	}
	
	// Allows you to set max health
	public void setMaxHealth(int maxHealth) { //TODO: Add real error handling here
		if (maxHealth <= 0) { // If someone tries setting max health to less then 0
			System.out.println("Warning, Cannot set max health set to less then 0"); // warn the player
			maxHealth = 1; // set it 1 temporarily
		}
		this.maxHealth = maxHealth; //otherwise set it as desired
	}
	
	// Allows you to get max health
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void incrementHealth() {
		if (health < maxHealth) {
			health++;
		}		
	}
	
	// Allows you to get health as a percentage
	public int getHealthPercent() {
		return (int) ((float) health / (float) maxHealth) * 100;
	}
	
	//-------------------------Generic Resource Methods ---------------
	
	// Allows you to set the primary and secondary resource for a planet
	public void setResources(Resource primary, Resource secondary) {
		this.primary = primary; // Set type of primary resource
		primaryResource = 0; 	// Set amount to 0 
		
		this.secondary = secondary;	// Set type of secondary resource
		secondaryResource = 0;		// Set amount to 0
	}
	
	// Sets the resources of a new planet 
	public void setResources(Material primaryMaterial, Material secondaryMaterial) {
		this.primaryMaterial = primaryMaterial; // Set the type of primary
		primaryResource = 0; 					// Set the amount to 0
		
		this.secondaryMaterial = secondaryMaterial; // Set the type of secondary
		secondaryResource = 0;						// Set the amount to 0
	}

	
	//----------------- Primary Resource Methods -------------------
	
	// Returns the primary type of resource
	public Material getPrimaryType() {
		return primaryMaterial;
	}
	
	// Sets the amount of primary resource
	public void setPrimaryAmount(int primaryResource) {
		this.primaryResource = primaryResource;
	}
	
	public void incrementPrimary() {
		if (primaryResource < primaryStorage) {
			primaryResource++;
		}
	}
	
	// Returns the amount of primary resource
	public int getPrimaryAmount() {
		return primaryResource; // Returns the amount of primary resource
	}

	//sets the primary max storage
	public void setPrimaryMax(int primaryStorage) {
		this.primaryStorage = primaryStorage;
	}
	
	// Returns the max amount of primary resource
	public int getPrimaryMax() {
		return primaryStorage;
	}
	
	// Returns the percent full of primary resource
	public int getPrimaryPercent() {
		
		// Convert these both 
		float resource = primaryResource;
		float storage = primaryStorage;
		return (int) ((resource/storage) * 100);
	}
	
	// ------------------ Secondary Resource methods ----------------
	
	// Returns the secondary type of resource
	public Material getSecondaryType() {
		return secondaryMaterial;
	}
	
	// Sets the amount of secondary resource
	public void setSecondaryAmount(int secondaryResource) {
		this.secondaryResource = secondaryResource;
	}
	
	public void incrementSecondary() {
		if (secondaryResource < secondaryStorage) {
			secondaryResource++;
		}
	}
	
	// Returns the amount of secondary resource
	public int getSecondaryAmount() {
		return secondaryResource; // Returns the amount of secondary resource
	}
	
	// Sets the max storage of secondary resource
	public void setSecondaryMax(int secondaryStorage) {
		this.secondaryStorage = secondaryStorage;
	}
	
	// Returns the max amount of secondary storage
	public int getSecondaryMax() {
		return secondaryStorage;
	}
	
	// Returns the percent full of secondary resource
	public int getSecondaryPercent() {

		float resource = secondaryResource;
		float storage = primaryStorage;
		return (int) ((resource/storage) * 100);
		
	}
	
	public MainMenu getMainMenu() {
		return mainMenu;
	}
	
	//------- Planet Status! -------
	int bombardment = 1; // -1 = safe, 0 = ongoing bombardment, 1 = start bombardment
	double bombardmentDifficulty = 10; //difficulty of the bombardment
	double bombardmentSpeed = 0.2;
	long bombardmentStart; //Time since bombardment started
	int bombardmentSize = 128;
	LinkedList<SpaceRock> rocks = new LinkedList<SpaceRock>();

	Random rand = new Random();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void emptyResources() {
		this.primaryResource = 0;
		this.secondaryResource = 0;
	}
	
	public String getName() {
		return name;
	}
	
	LinkedList<Player> landed = new LinkedList<Player>();
	boolean landing = false;
	int landingProgress = 0;
	
	
	public Planet(int x, int y, Image img) {
		primaryStorage = 200;
		secondaryStorage = 100;
		this.x = x;
		this.y = y;
		this.img = img;
		width = img.getWidth() - 60;
		height = img.getHeight() - 60;
		isFriendly = true;
		
		setOffers(new Trade(Resource.OXYGEN, 0.5, Resource.MONEY), new Trade(Resource.CARBON, 1.5, Resource.MONEY));
		setOffers(new Offer(1, new Material(MaterialType.OXYGEN), 2, new Material(MaterialType.OXYGEN)),
				  new Offer(2, new Material(MaterialType.CARBON), 1, new Material(MaterialType.OXYGEN)));

		hangerMenu.linkPlanet(this);
		repairMenu.linkPlanet(this);
		sellMenu.linkPlanet(this);
		shopMenu.linkPlanet(this);
		mainMenu.linkPlanet(this);
		activeMenu = mainMenu;
		configureMainMenu(this);
		configureShopMenu();
		configureRepairMenu();
		configureSellMenu();
		configureHangerMenu();
	}
	
	public String getType() {
		return "Planet";
	}
	
	public MainMenu getActiveMenu() {
		return activeMenu;
	}

	@Override
	public void update(long timeStamp, LinkedList<Sprite> allSprites) {
		
		if (expiredTimeStamp == 0) { expiredTimeStamp = timeStamp; }
		double elapsed = (timeStamp - expiredTimeStamp); elapsed = elapsed / Math.pow(10, 9);
		expiredTimeStamp = timeStamp;
		
		if (bombardment == 1) {
			bombardment = 0;
			bombardmentStart = System.currentTimeMillis();
		} else if (bombardment == 0){
			int chance = rand.nextInt(2000);
				if (chance < bombardmentDifficulty)
					allSprites.add(new SpaceRock(gameCanvas, this, bombardmentSpeed, bombardmentSize));
		}
				
		incrementResources(elapsed);
		
		rocks.forEach(shot -> shot.update(timeStamp, allSprites));
		super.update(timeStamp, allSprites);
	}
	
	public void setActiveMenu(MainMenu activeMenu) {
		this.activeMenu = activeMenu;
	}
	
	private void incrementResources(double elapsed) {
		
		healthProg += elapsed;
		primaryProg += elapsed;
		secondaryProg += elapsed;

		if (healthProg >= healthRegen) {
			incrementHealth();
			healthProg = 0;
		}
		
		if (primaryProg >= primaryRegen) {
			incrementPrimary();
			primaryProg = 0;
		}
		
		if (secondaryProg >= secondaryRegen) {
			incrementSecondary();
			secondaryProg = 0;
		}

	}
	
	
	

	

	
	public int search(Resource target, String value) {
		//TODO: Proper ERROR management.
		if (value == "MAX") {
			if (target == Resource.HEALTH) {
				return maxHealth;
			} 
			else if (target == Resource.PRIMARY) {
				return primaryStorage;
			} 
			else if (target == Resource.SECONDARY) {
				return secondaryStorage;
			} 
			else {
				System.out.println("ERROR: Called search for invalid resource: " + target.toString());
				return -1;
			}
		}
		else if (value == "ACTUAL") {
			if (target == Resource.HEALTH) {
				return health;
			}
			else if (target == Resource.PRIMARY) {
				return primaryResource;
			} 
			else if (target == Resource.SECONDARY) {
				return secondaryResource;
			} 
			else {
				System.out.println("ERROR: Called search for invalid resource: " + target.toString());
				return -1;
			}
		}
		else {
			System.out.println("Error: called search for unknown value parameter, please enter MAX or ACTUAL, found: " + value);
			return -1;
		}
		
	}
	
	private void configureShopMenu() {
		
		shopMenu.setFillStrokeColor(Color.rgb(0,  255,  255, .2), Color.BLACK);
		shopMenu.setRounding(25, 25);
		shopMenu.setVerticalBuffer(8);
		

		// Title
		new MenuLabel(shopMenu, Align.CENTER, "Shop", 36, FontWeight.BOLD, 10, 40);
		
		new MenuSpacer(shopMenu, 1, 14);
		

		MenuLabel menuShipItem = new MenuLabel(shopMenu, Align.LEFT, "Ship: Protector", true, 24, 10, 40) {
			@Override
			public void select() {
				if (Inventory.withdraw(Resource.MONEY, 200))
					Inventory.addShip(new Protector(landed.element()));
						
			}
		};
		new MenuToolTip(shopMenu, menuShipItem, "A protector ship: Capable of claiming screen focus for long distance travel and armed with support weaponry this is a great addition to any fleet despite it's lower speed and manueverability.");
		
		MenuLabel ml2 = new MenuLabel(shopMenu, Align.LEFT, "Storage Buff", true, 24, 10, 40);
		new MenuToolTip(shopMenu, ml2, "Increase planetary resource storage, allowing the planet to collect more resources while you're away.");
		
		MenuLabel ml3 = new MenuLabel(shopMenu, Align.LEFT, "Production Buff", true, 24, 10, 40);
		new MenuToolTip(shopMenu, ml3, "Increases planetary resource production.");
		
		MenuLabel ml4 = new MenuLabel(shopMenu, Align.LEFT, "SW: Minigun", true, 24, 10, 40);
		new MenuToolTip(shopMenu, ml4, "Secondary weapon upgrade: Minigun. Fire's a barrage of shots in quick succession. Excellent for high health target or enemy rich environments.");
		
		MenuLabel ml5 = new MenuLabel(shopMenu, Align.LEFT, "MW: Boost", true, 24, 10, 40);
		new MenuToolTip(shopMenu, ml5, "Main weapon upgrade: Boost. Increases main weapon damage.");
		
		MenuLabel ml6 = new MenuLabel(shopMenu, Align.LEFT, "Open Portal", true, 24, 10, 40);
		new MenuToolTip(shopMenu, ml6, "Connect this planet with your portal network for fast travel.");
		
		MenuLabel ml7 = new MenuLabel(shopMenu, Align.LEFT, "Back", true, 24, 10, 40) {
			@Override
			public void select() {
				shopMenu.switchActiveMenu(mainMenu);
			}
		};
		new MenuToolTip(shopMenu, ml7, "Return to the main menu.");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	private void configureSellMenu() {
		
		// Set the properties we're using for earth menu (Light blue background, rounded corners, and about 8px of vertical spacing)
		sellMenu.setFillStrokeColor(Color.rgb(0,  255,  255, .2), Color.BLACK);
		sellMenu.setRounding(25, 25);
		sellMenu.setVerticalBuffer(8);
		
		new MenuLabel(sellMenu, Align.CENTER, "Sell", 36, FontWeight.BOLD, 10, 40);
		
		new MenuSpacer(sellMenu, 1, 14);

		/*
		LinkedList<MenuLabel> labelList = new LinkedList<MenuLabel>();
		for (int i = 0; i < offers.size(); i++) {
			labelList.add(new MenuLabel(sellMenu, Align.LEFT, offers.get(i), true, 18, 10, 40) {
				@Override
				public void select() {
					this.getTrade().trade(10);
				}
			});

		}
		*/
		
		// Draw and manage the offers on the sell menu
		LinkedList<MenuOffer> menuOffers = new LinkedList<MenuOffer>(); // Linked list to keep references to all offers
		for (int i = 0; i < allOffers.size(); i++) { // For each offer
			menuOffers.add(new MenuOffer(sellMenu, allOffers.get(i)) {
				@Override // Override the select function
				public void select() {
					this.getOffer().execute(10);
				}
			});
		}
		
		
		// "Trade" options to confirm the decision to trade items
		MenuLabel tradeLabel = new MenuLabel(sellMenu, Align.LEFT, "Trade", true, 24, 10, 40) {
			@Override //overriding the select function of the label
			public void select() {
				
			}
		};
		
		MenuLabel ml7 = new MenuLabel(sellMenu, Align.LEFT, "Back", true, 24, 10, 40) {
			@Override
			public void select() {
				sellMenu.switchActiveMenu(mainMenu);
			}
		};
		new MenuToolTip(sellMenu, ml7, "Return to the main menu.");
		
	}
	
	private void configureHangerMenu() {
		
		// Set the properties we're using for earth menu (Light blue background, rounded corners, and about 8px of vertical spacing)
		hangerMenu.setFillStrokeColor(Color.rgb(0,  255,  255, .2), Color.BLACK);
		hangerMenu.setRounding(25, 25);
		hangerMenu.setVerticalBuffer(8);
		
		new MenuLabel(hangerMenu, Align.CENTER, "Hanger", 36, FontWeight.BOLD, 10, 40);
		
		new MenuSpacer(hangerMenu, 1, 14);
		
		new MenuHorizontalIndicators(hangerMenu, Align.CENTER, "Ships", false , 25, 10, 40);
		

		MenuDisplay display = new MenuDisplay(hangerMenu, Align.CENTER, Inventory.getShips(), 200);
		
		MenuLabel sel = new MenuLabel(hangerMenu, Align.LEFT, "Switch", true, 24, 10, 40) {
			@Override
			public void select() {
				landed.forEach(player -> player.switchShip(display.getSelected()));
			}
		};
		new MenuToolTip(hangerMenu, sel, "Selects the ship shown above.");
		
		MenuLabel ml7 = new MenuLabel(hangerMenu, Align.LEFT, "Back", true, 24, 10, 40) {
			@Override
			public void select() {
				hangerMenu.switchActiveMenu(mainMenu);
			}
		};
		new MenuToolTip(hangerMenu, ml7, "Return to the main menu.");
	}
	
	private void configureRepairMenu() {
		
		// Set the properties we're using for earth menu (Light blue background, rounded corners, and about 8px of vertical spacing)
		repairMenu.setFillStrokeColor(Color.rgb(0,  255,  255, .2), Color.BLACK);
		repairMenu.setRounding(25, 25);
		repairMenu.setVerticalBuffer(8);
		
		new MenuLabel(repairMenu, Align.CENTER, "Repair", 36, FontWeight.BOLD, 10, 40);
		
		new MenuSpacer(repairMenu, 1, 14);
		
		
		MenuLabel ml7 = new MenuLabel(repairMenu, Align.LEFT, "Back", true, 24, 10, 40) {
			@Override
			public void select() {
				repairMenu.switchActiveMenu(mainMenu);
			}
		};
		new MenuToolTip(repairMenu, ml7, "Return to the main menu.");
		
	}
	
	// Sets up the drawing of the main menu
	private void configureMainMenu(Planet planet) {
		
		// Set the properties we're using for earth menu (Light blue background, rounded corners, and about 8px of vertical spacing)
		mainMenu.setFillStrokeColor(Color.rgb(0,  255,  255, .2), Color.BLACK);
		mainMenu.setRounding(25, 25);
		mainMenu.setVerticalBuffer(8);
		
		// Title
		new MenuLabel(mainMenu, Align.CENTER, "Earth", 36, FontWeight.BOLD, 10, 40);
		
		// Health, Primary, Secondary resource bars
		new MenuBar(mainMenu, Align.CENTER, planet, Resource.HEALTH, 180, 30, Color.RED, Color.BLACK);
		new MenuBar(mainMenu, Align.CENTER, planet, Resource.PRIMARY, 180, 30, Color.AZURE, Color.BLACK);
		new MenuBar(mainMenu, Align.CENTER, planet, Resource.SECONDARY, 180, 30, Color.GREEN, Color.BLACK);
		
		// Add some space between bars and labels
		new MenuSpacer(mainMenu, 1, 10);
		
		// Buy button to open the buy menu
		MenuLabel buyLabel = new MenuLabel(mainMenu, Align.LEFT, "Buy", true, 24, 200, 30) {
			@Override 
			public void select() {
				activeMenu.switchActiveMenu(shopMenu);
			}
		};
		
		// Sell button to open selling interface TODO: Implement selling interfact
		new MenuLabel(mainMenu, Align.LEFT, "Sell", true, 24, 200, 30) {
			@Override
			public void select() {
				activeMenu.switchActiveMenu(sellMenu);
			}
		};
		
		// Collect button to pick up resources from the planet you're on
		new MenuLabel(mainMenu, Align.LEFT, "Collect", true, 24, 200, 30) {
			@Override
			public void select() {
				InventoryAdv.collectPlanetaryResources(planet);
			}
		};
		
		// Hanger button for switching ships TODO: Implement hanger
		new MenuLabel(mainMenu, Align.LEFT, "Hanger", true, 24, 200, 30) {
			@Override
			public void select() {
				activeMenu.switchActiveMenu(hangerMenu);
			}
		};
		
		// Repair button TODO: Implement repair system
		new MenuLabel(mainMenu, Align.LEFT, "Repair", true, 24, 200, 30) {
			@Override
			public void select() {
				activeMenu.switchActiveMenu(repairMenu);
			}
		};
		
		// Take off button & select function
		new MenuLabel(mainMenu, Align.LEFT, "Take-Off", true, 24, 200, 30) {
			@Override
			public void select() {
				mainMenu.hide();
				landed.forEach(player -> player.takeOff());
			}
		};
		
	}
	
	@Override
	protected void draw(Canvas gameCanvas) {
		
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		sellMenu.draw(gc);
		mainMenu.draw(gc);
		shopMenu.draw(gc);
		hangerMenu.draw(gc);
		repairMenu.draw(gc);
		super.draw(gameCanvas);
		
		if (landing) {
			drawLandingBar(landingProgress);
		}
		
		if (landed.size() > 0) {
			Inventory.drawInventory(gameCanvas.getGraphicsContext2D(), this);
		}
	}
	
	@Override
	protected void handleCollisions(LinkedList<Sprite> collisions) {

		landing = false;
		collisions.forEach(sprite -> {
			if (sprite.getClass() == Player.class) //if the collision is a landing player
			{
				
				if (landed.size() == 0) {
					landing = true;
					landingProgress = ((Player) sprite).getLandingStatus();
					if (landingProgress == 400) {
						completeLanding((Player) sprite);
					}
				}
				
			}
			
			
		});
		
		
	}
	
	private void completeLanding(Player player) {
		mainMenu.show();
		landed.add(player);
		player.land(this);
	}
	
	private void drawLandingBar(int percent) {
		int width = 200; int height = 25;
		
		GraphicsContext bargc = gameCanvas.getGraphicsContext2D();
		bargc.save();
		
		double col = percent / 400;
		double H = col * 0.4;
		double S = 0.9;
		double B = 0.9;
		bargc.setFill(Color.hsb(H, S, B));
		bargc.fillRect(translateX(x) - (width/2) + 1, translateY(y) - (img.getHeight()*.45) + 1, percent/2, height - 2);
		bargc.restore();
		bargc.strokeRect(translateX(x) - (width/2), translateY(y) - (img.getHeight()*.45), width, height);
		
		
	}

	@Override
	public void Damage(double dmg) {
		
		
	}

	@Override
	public double getX() {
		return x;
		
	}

	@Override
	public double getY() {
		return y;
		
	}




	


}
