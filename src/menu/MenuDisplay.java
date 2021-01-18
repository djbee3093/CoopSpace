package menu;

import java.util.LinkedList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import ships.Ship;

public class MenuDisplay extends MenuElement implements Selectable{

	Image displayIcon;
	String displayName;
	Double displaySpeed;
	double displayTurn;
	
	private int scrolled;
	private LinkedList<Ship> ships;
	
	public MenuDisplay(MainMenu main, Align align, Ship ship) {
		x = main.getX() + main.getWidth();
		y = main.getNextY();
		width = main.getWidth();
		height = main.getHeight();
		displayIcon = ship.getShipImage();
		displayName = ship.getShipName();
		main.add(this);

	}
	
	public MenuDisplay(MainMenu main, Align align, Ship ship, int height) {
		x = main.getX() + main.getWidth()/2;
		y = main.getNextY();
		width = main.getWidth();
		this.height = height;
		displayIcon = ship.getShipImage();
		displayName = ship.getShipName();
		displaySpeed = ship.getForwardPower();
		displayTurn = ship.getTurningPower();
		main.add(this);
		 
	}
	
	public MenuDisplay(MainMenu main, Align align, LinkedList<Ship> ships, int height) {
		x = main.getX() + main.getWidth()/2;
		y = main.getNextY();
		width = main.getWidth();
		this.height = height;
		this.ships = ships;
		scrolled = 0;
		displayIcon = ships.getFirst().getShipImage();
		displayName = ships.getFirst().getShipName();
		displaySpeed = ships.getFirst().getForwardPower();
		displayTurn = ships.getFirst().getTurningPower();
		main.add(this);
		main.setScrolling(this);
		
	}
	
	public Ship getSelected() {
		return ships.get(scrolled);
	}
	
	public void left() {
		if (scrolled == 0)
			scrolled = ships.size() - 1;
		else 
			scrolled--;
		
		displayIcon = ships.get(scrolled).getShipImage();
		displayName = ships.get(scrolled).getShipName();
		displaySpeed = ships.getFirst().getForwardPower();
		displayTurn = ships.getFirst().getTurningPower();
		
		
	}
	
	public void right() {
		if (scrolled == ships.size() - 1)
			scrolled = 0;
		else 
			scrolled++;
		
		displayIcon = ships.get(scrolled).getShipImage();
		displayName = ships.get(scrolled).getShipName();
		displaySpeed = ships.getFirst().getForwardPower();
		displayTurn = ships.getFirst().getTurningPower();
	}
	
	@Override
	public void select() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawSelected(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setToolTip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GraphicsContext gc) {

		gc.save();
		
		gc.setFill(Color.BLACK);
		gc.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.ITALIC, 18));
		gc.setTextAlign(TextAlignment.CENTER);
		
		
		gc.fillText(displayName, x, y - 5);
		gc.drawImage(displayIcon, x - displayIcon.getWidth()/2, y - displayIcon.getHeight()/2 + 36);
		
		gc.restore();
		
		
	}

	
	
	
}
