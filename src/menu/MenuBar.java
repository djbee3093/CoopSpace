package menu;

import application.Inventory;
import application.Planet;
import application.Resource;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


public class MenuBar extends MenuElement{

	Planet trackingSource = null;
	Resource tracking = null; // Set to resource if tracking inventory
	int percent;
	String text = null;
	
	public MenuBar(MainMenu main, Align align, Resource resource, int width, int height, Color fColor) {
		this.width = width;
		this.height = height;
		this.elementFillColor = fColor;
		this.elementStrokeColor = Color.BLACK;
		this.x = main.x + main.getHorizontalBuffer();
		if (main.frameLocked())
			this.x = (main.x + main.width/2) - width/2;
			
		this.y = main.getNextY();
		this.percent = 100;
		this.text = resource.toString();
		this.tracking = resource;
		main.add(this);
	}
	
	public MenuBar(MainMenu main, Align align, Planet planet, Resource resource, int width, int height, Color fColor, Color sColor) {
		this.width = width;
		this.height = height;
		this.elementFillColor = fColor;
		this.elementStrokeColor = sColor;
		this.x = main.x + main.getHorizontalBuffer();
		if (main.frameLocked())
			this.x = (main.x + main.width/2) - width/2;
		
		this.trackingSource = planet;
			
		this.y = main.getNextY();
		this.percent = 100;
		this.text = resource.toString();
		this.tracking = resource;
		main.add(this);
	}
	
	public void draw(GraphicsContext gc, int percent) {
		
		double percentDraw = 0;
		String drawText = "";
		if (tracking == Resource.PRIMARY) { 
			drawText = trackingSource.getPrimaryType().toString();
		}
		else if (tracking == Resource.SECONDARY) {
			drawText = trackingSource.getSecondaryType().toString();
		}
		else {
			drawText = tracking.toString();
		}

		if (trackingSource != null) {
			percentDraw = (float) trackingSource.search(tracking, "ACTUAL") /  (float) trackingSource.search(tracking, "MAX");
			drawText = drawText + ": " + trackingSource.search(tracking, "ACTUAL") + " / " + trackingSource.search(tracking, "MAX");
			
		} 
		else if (tracking != null) {
			percentDraw = (float) Inventory.getAmount(tracking) / (float) 500;
			drawText = drawText + ": " + Inventory.getAmount(tracking) + " / 500";
		}
		
		gc.save(); // Save the starting state of the GC
		
		
		
		gc.setFill(elementFillColor); // Set fill to the fill color
		gc.fillRect(x, y, width * (percentDraw), height);		// Fill the rect, according to percent
		
		gc.setStroke(elementStrokeColor); 					// Set the stroke to outline color
		gc.strokeRect(x, y, width, height); 	// Stroke the outline of the rect on top
		

		
		if (text != null) {
			gc.setFill(elementStrokeColor);
			gc.setTextBaseline(VPos.CENTER);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setFont(Font.font("veranda", FontWeight.NORMAL, FontPosture.REGULAR, height/2));
			gc.fillText(drawText, x + width/2, y + height/2);
		}
		
		gc.restore(); // Restore the starting value of GC			
		
	}
	
	public void draw(GraphicsContext gc) {
		draw(gc, 0);
	}
	

	
}