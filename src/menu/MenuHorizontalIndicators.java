package menu;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MenuHorizontalIndicators extends MenuElement implements Selectable{

	int menuBorder;
	int menuWidth;
	int fontSize;
	String text;
	Align align;
	Color fontColor = Color.BLACK;

	
	public MenuHorizontalIndicators(MainMenu main, Align center, String text, boolean selectable, int fontSize, int width, int height) {
		menuBorder = main.getHorizontalBuffer();
		this.width = width;
		this.height = height;
		this.elementFillColor = Color.BLACK;
		this.elementStrokeColor = Color.BLACK;
		this.x = main.x + main.getWidth()/2;
		this.y = main.getNextY();
		this.text = text;
		this.name = text;
		this.fontSize = fontSize;
		this.align = align;
		if (selectable == true) {
			main.addToSelection(this);
		}
		menuWidth = main.getWidth();
		main.add(this);
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.save();
			
		gc.setFill(fontColor);
		gc.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, fontSize));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		
		
		double[][] leftArrow = createLeftArrow(x - menuWidth/2 + menuBorder, y - 7, 40, 25);
		double[][] rightArrow = createRightArrow(x + menuWidth/2 - menuBorder - 40, y - 7, 40, 25);
		gc.fillPolygon(leftArrow[0], leftArrow[1], 8);
		gc.fillPolygon(rightArrow[0], rightArrow[1], 8);
		
		
		gc.fillText(text, x, y);
		
		gc.restore();
		
	}

	
	private double[][] createLeftArrow(int x, int y, int width, int height) {
		double[][] ret = new double[2][8];
		
		ret[0][0] = x;
		ret[0][1] = x + width/3;
		ret[0][2] = x + width/3;
		ret[0][3] = x + width;
		ret[0][4] = x + width;
		ret[0][5] = x + width/3;
		ret[0][6] = x + width/3;
		ret[0][7] = x;
		
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

}
