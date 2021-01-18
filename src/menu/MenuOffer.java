package menu;

import inventory.Offer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MenuOffer extends MenuElement{

	Offer offer;
	int vBuffer;
	
	public MenuOffer(MainMenu main, Offer offer) {
		this.width = main.width - main.getHorizontalBuffer()*2;
		this.height = 60;
		this.x = main.x + main.getHorizontalBuffer();
		this.y = main.getNextY();
		vBuffer = main.getVerticalBuffer();
		this.offer = offer;
		main.add(this);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		
		// Save the GC
		gc.save();
		
		// Set to drawing color
		gc.setFill(Color.BLACK);
		
		// Draw the input item
		offer.getInput().drawItemIcon(gc, x+height/2, y + vBuffer, height, height);
		
		// Generate and draw an arrow indicating conversion direction
		int aw = 46;  int ah = 34; // Variables to make changing arrow position easy
		double[][] arr=createRightArrow(x+width/2-(aw/2), y + vBuffer - (ah/2), aw, ah); // Generating arrow coordinates
		gc.fillPolygon(arr[0], arr[1], 8); // Draws the arrow
		
		// Draw the output item
		offer.getOutput().drawItemIcon(gc, x+width-height/2, y + vBuffer, height, height);
		
		// Return the gc to default value
		gc.restore();
		
	}

	private double[][] createRightArrow(int x, int y, int width, int height) {
		double[][] ret = new double[2][8];

		ret[0][0] = x + width;
		ret[0][1] = x + width - width/3;
		ret[0][2] = x + width - width/3;
		ret[0][3] = x;
		ret[0][4] = x;
		ret[0][5] = x + width - width/3;
		ret[0][6] = x + width - width/3;
		ret[0][7] = x + width;

		ret[1][0] = y + height/2;
		ret[1][1] = y;
		ret[1][2] = y + height/3;
		ret[1][3] = y + height/3;
		ret[1][4] = y + height - height/3;
		ret[1][5] = y + height - height/3;
		ret[1][6] = y + height;
		ret[1][7] = y + height/2;
		
	    return ret; 
		
		
	}
	
}
