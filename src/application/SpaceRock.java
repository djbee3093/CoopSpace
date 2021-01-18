package application;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class SpaceRock extends Sprite implements TakesDamage {
	
	public SpaceRock() {
		try {
			setImage(new Image(new FileInputStream("./Resources/Images/ROcks/rock_00"),100, 100, false, false), 10);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public SpaceRock(Canvas gameCanvas, Planet target, double bombardmentSpeed, double bombardmentSize) {
		this.gameCanvas = gameCanvas;
		try {

			Random rand = new Random();
			int rock = rand.nextInt(64) + 1;
			String selRock = "";
			if (rock < 10)
				selRock = "rock_0" + Integer.toString(rock) + ".png";
			else 
				selRock = "rock_" + Integer.toString(rock) + ".png";
			
			String imageAddress = "./Resources/Images/Rocks/" + selRock;
			
			setImage(new Image(new FileInputStream(imageAddress), bombardmentSize, bombardmentSize, false, false));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
		
		Random rand = new Random();
		int angleOfAttack = rand.nextInt(360); //the angle from the planet that the attack will come from
		double spawnDistance = gameCanvas.getWidth(); //the distance away this will spawn
		
		int speed = rand.nextInt(3) + 3;
		x = spawnDistance * Math.sin(Math.toRadians(angleOfAttack)) + target.x; //x spawn position 
		y = spawnDistance * Math.cos(Math.toRadians(angleOfAttack)) + target.y; //y spawn position
		orientation = 360 - (180 - angleOfAttack); //get the angle back towards the planet
		
		
		double[] vel = calcVector(orientation, speed);
		xVel = vel[0];
		yVel = vel[1];

		
	}
	
	

	@Override
	public String getType() {
		return "Space Rock";
	}
	
	@Override
	protected void handleCollisions(LinkedList<Sprite> collisions) {
		for (Sprite col : collisions) {
			if (col instanceof TakesDamage) {
				((TakesDamage) col).Damage(0);
				destroy();
			}
		}
		
	}
	

	@Override
	public void Damage(double dmg) {
		if (dmg >= 10)
			destroy();
			
		
	}
	
}
