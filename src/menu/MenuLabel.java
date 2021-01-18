package menu;

import application.Trade;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MenuLabel extends MenuElement implements Selectable{


	String text;
	int fontSize = 12;
	Align align;
	Font fontChoice = Font.font("veranda");
	FontWeight fontWeight = FontWeight.NORMAL;
	FontPosture fontPosture = FontPosture.REGULAR;
	boolean selectable = false;
	Color fontColor = Color.BLACK;
	Color selectFillColor = Color.RED;
	Trade trade;
	
	
	public MenuLabel(MainMenu main, Align align, String text, int fontSize, FontWeight fontWeight, int width, int height) {
		this.width = width;
		this.height = height;
		this.elementFillColor = Color.BLACK;
		this.elementStrokeColor = Color.BLACK;
		this.x = main.x + main.width/2;
		this.y = main.getNextY();
		this.text = text;
		this.name = text;
		this.fontSize = fontSize;
		this.fontWeight = fontWeight;
		this.align = align;
		selectable = false;
		main.add(this);
		
	}
	
	public MenuLabel(MainMenu main, Align align, String text, boolean selectable, int fontSize, int width, int height) {
		this.width = width;
		this.height = height;
		this.elementFillColor = Color.BLACK;
		this.elementStrokeColor = Color.BLACK;
		this.x = main.x + main.getHorizontalBuffer();
		this.y = main.getNextY();
		this.text = text;
		this.name = text;
		this.fontSize = fontSize;
		this.align = align;
		if (selectable == true) {
			main.addToSelection(this);
		}
		main.add(this);
		
	}
	
	public Trade getTrade() {
		return trade;
	}
	
	// Implements a trade value
	public MenuLabel(MainMenu main, Align align, Trade trade, boolean selectable, int fontSize, int width, int height) {
		this.width = width;
		this.height = height;
		this.elementFillColor = Color.BLACK;
		this.elementStrokeColor = Color.BLACK;
		this.x = main.x + main.getHorizontalBuffer();
		this.y = main.getNextY();
		this.trade = trade;
		this.text = trade.toString();
		this.name = text;
		this.fontSize = fontSize;
		this.align = align;
		if (selectable == true) {
			main.addToSelection(this);
		}
		main.add(this);
		
	}
	
	public MenuLabel(String text, int fontSize, FontWeight fontWeight) {
		this.text = text;
		this.name = text;
		this.fontSize = fontSize;
		this.fontWeight = fontWeight;
	}

	@Override
	public void draw(GraphicsContext gc) {
	
		gc.save();
		
		gc.setFill(fontColor);
		gc.setFont(Font.font("veranda", fontWeight, fontPosture, fontSize));
		
		
		if (align == Align.CENTER) {
			gc.setTextBaseline(VPos.CENTER);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText(text,  x + width/2,  y + height/2);
		}
		else if (align == Align.LEFT) {
			gc.setTextBaseline(VPos.CENTER); 
			gc.setTextAlign(TextAlignment.LEFT);
			gc.fillText(text, x, y);
		}
		
		


		
		gc.restore();
		
		
		
	}

	@Override
	public void drawSelected(GraphicsContext gc) {
		gc.save();
		
		gc.setFill(selectFillColor);
		gc.setFont(Font.font("veranda", fontWeight, fontPosture, fontSize));
		
		
		if (align == Align.CENTER) {
			gc.setTextBaseline(VPos.CENTER);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText(text,  x + width/2,  y + height/2);
		}
		else if (align == Align.LEFT) {
			gc.setTextBaseline(VPos.CENTER); 
			gc.setTextAlign(TextAlignment.LEFT);
			gc.fillText(text, x, y);
		}
		
		


		
		gc.restore();
		
		
	}

	@Override
	public void select() {

		
	}

	@Override
	public void setToolTip() {
		// TODO Auto-generated method stub
		
	}

}
