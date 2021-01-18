package application;

import java.util.LinkedList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;


public abstract class Sprite {

	boolean showBoundingLines = false;

	//Int values for x y position and orientation
	double x, y;
	double xVel, yVel;
	double width, height;
	int orientation;
	Image img = null;
	Canvas gameCanvas;
	boolean isDestroyed = false;
	boolean isFriendly;
	
	public void setImage(Image img, int offset) {
		this.img = img;
		width = img.getWidth() - offset;
		height = img.getHeight() - offset;
	}
	
	public void setImage(Image img) {
		this.img = img;
		width = img.getWidth();
		height = img.getHeight();
	}
	
	
	public double getXVel() {
		return xVel;
	}
	
	public double getYVel() {
		return yVel;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void update(long timeStamp, LinkedList<Sprite> allSprites) {
		

		
		x += xVel;
		y += yVel;
		

		LinkedList<Sprite>  collisions = detectCollision(allSprites);
		handleCollisions(collisions);
		
		draw(gameCanvas);
	}
	
	public void destroy() {
		isDestroyed = true;
	}
	
	protected abstract void handleCollisions(LinkedList<Sprite> collisions);

	public void setCanvas(Canvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}
	
	protected double[] calcVector(int angle, double mag) {
		double[] ret = new double[2]; // Create a return array with 2 values (x and y mag)
		double radValue = Math.toRadians(angle);
		ret[0] = mag * Math.sin(radValue);
		ret[1] = mag * Math.cos(radValue);
		return ret;	
	}
	
	private int[] translatePosition() {
		int[] ret = new int[2];
		ret[0] = (int) (this.x - ScreenFocus.getScreenFocus().getX() + gameCanvas.getWidth()/2);
		ret[1] = (int) (this.y - ScreenFocus.getScreenFocus().getY() + gameCanvas.getHeight()/2);
		return ret;
	}
	
	protected double translateX(double input) {
		return (input + gameCanvas.getWidth() / 2);
	}
	
	protected double translateY(double input) {
		return (input + gameCanvas.getHeight() / 2);
	}
	
	private int[] translatePosition(double x, double y) {
		int[] ret = new int[2];
		ret[0] = (int) (x - ScreenFocus.getScreenFocus().getX() + gameCanvas.getWidth()/2);
		ret[1] = (int) (y - ScreenFocus.getScreenFocus().getY() + gameCanvas.getHeight()/2);
		return ret;
	}
	
	
	public String getType() {
		return "Generic Sprite";
	}
	
	public double[] getRect() {
		double[] ret = new double[4];
		
		int[] center = translatePosition(x, y);
		
		ret[0] = center[0] - width/2; //lower left x value
		ret[1] = center[1] + height/2; //lower left y value
		ret[2] = center[0] + width/2;
		ret[3] = center[1] - height/2;
		
		return ret;
	}
	
	public double getSpeed() {
		return Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
	}
	
	protected void draw(Canvas gameCanvas) {
		int[] trans = translatePosition();
		
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.save();
		gc.transform(new Affine(new Rotate(orientation, trans[0], trans[1])));
		
		if (img != null) 
			gc.drawImage(img, trans[0] - (img.getWidth()/2), trans[1] - (img.getHeight()/2));	
		else 
			gc.fillRect(trans[0] - (width/2), trans[1] - (height/2), width, height);
		
		gc.restore();
		
		//Bounding Box testing
		if (showBoundingLines) {
			double[] bounds = getRect();
			gc.strokeLine(bounds[0], bounds[1], bounds[2], bounds[3]);
			gc.strokeRect(bounds[0], bounds[3], bounds[2] - bounds[0], bounds[1] - bounds[3]);
		}
	}
	
	//simple function to detect collision between rects, used for collision detection
	public boolean rectOverlap(double[] rect1, double[] rect2) {
		//rect1 represents coordinates for the diagonal of the first rect
		//rect1[0-1] = x1, y1
		//rect1[2-3] = x2, y2
		
		//simlarly rect2 holds values for the diagonal of the second rect
		//rect2[0-1] = x3, y3
		//rect2[2-3] = x4, y4
		
		//to determine if any of these overlap we simply check if
		//		  x3 > x2		or		 y3 < y2 	   or		x1 > x4		  or	   y1 < y4		otherwise, they are touching
		if (rect2[0] > rect1[2] || rect2[1] < rect1[3] || rect1[0] > rect2[2] || rect1[1] < rect2[3]) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isFriendly() {
		return isFriendly;
	}
	
	public LinkedList<Sprite> detectCollision(LinkedList<Sprite> allSprites) {
		double[] selfRect = getRect();
		LinkedList<Sprite> cols = new LinkedList<Sprite>();
		allSprites.forEach(sprite -> {
			if (rectOverlap(selfRect, sprite.getRect()) == true && sprite != this) {
				cols.add(sprite);
			}
		});
		return cols;
	}
	
}
