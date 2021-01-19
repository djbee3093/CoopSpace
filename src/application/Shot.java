package application;

import java.util.LinkedList;

public class Shot extends Sprite {

	int damage = 10;
	boolean persistent = false;
	
	public Shot() {
		
	}
	
	public Shot(Player player, int shotSpeed) {
		gameCanvas = player.gameCanvas;
		orientation = player.orientation;
		x = player.x; y = player.y;
		double[] vel = calcVector(orientation, shotSpeed);
		xVel = vel[0] + player.getXVel(); yVel = vel[1] * -1 + player.getYVel();
		width = 10;
		height = 50;
	}

	@Override
	protected void handleCollisions(LinkedList<Sprite> collisions) {
		for (Sprite col : collisions) {
			if (col instanceof TakesDamage && col.isFriendly != true) {
				((TakesDamage) col).Damage(damage);
				destroy();
			}
		}
		
	}
	
}
