package menu;

import java.util.LinkedList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MenuToolTip extends MenuElement{

	MainMenu mainMenu;
	MenuElement element;
	String titleString;
	String infoString;
	Font infoFont;
	Font titleFont;
	String[] wrappedText;
	double maxStringChars = 25;
	int maxWidth;
	int maxHeight;
	
	public MenuToolTip(MainMenu mainMenu, MenuElement element) {
		wrappedText = new String[1]; wrappedText[0] = "No information available.";
		this.mainMenu = mainMenu;
		this.element = element;
		
		
		
		width = (int) (mainMenu.getWidth() * .75);
		
		titleFont = Font.font("veranda", FontWeight.BOLD, FontPosture.ITALIC, 20);
		infoFont = Font.font("veranda", FontWeight.NORMAL, FontPosture.REGULAR, 12);
		
		roundingX = mainMenu.roundingX;
		roundingY = mainMenu.roundingY;
		height = 75;
		this.x = element.x - mainMenu.getHorizontalBuffer() - width;
		this.y = element.y;
		mainMenu.add(this);
	}
	
	public MenuToolTip(MainMenu mainMenu, MenuElement element, String infoString) {
		this.mainMenu = mainMenu;
		this.element = element;
		titleString = element.getName();
		
		titleFont = Font.font("veranda", FontWeight.BOLD, FontPosture.ITALIC, 20);
		

		
		wrappedText = textWrapper(infoString, 25); 
		
		width = (int) (mainMenu.getWidth() * .75);
		
		
		infoFont = Font.font("veranda", FontWeight.NORMAL, FontPosture.REGULAR, 12);
		
		roundingX = mainMenu.roundingX;
		roundingY = mainMenu.roundingY;
		height = wrappedText.length * 15 + 30;
		this.x = element.x - mainMenu.getHorizontalBuffer() - width;
		this.y = element.y;
		mainMenu.add(this);
	}
	

	
	@Override
	public void draw(GraphicsContext gc) {
		
		
		if (mainMenu.getSelected() == this.element) {
			gc.save();
			gc.fillRoundRect(x, y, width, height, roundingX, roundingY);
			
			gc.setFill(Color.BLACK);
			
			gc.setFont(titleFont);
			gc.fillText(titleString, x, y);
			
			gc.setFont(infoFont);
			

			
			for (int i = 0; i < wrappedText.length; i ++) {
				gc.fillText(wrappedText[i], x+6, y+23+i*15);
			}
			gc.fillText(infoString, x+4, y+23);
			
			
			
			gc.restore();
		}
	}
	
		private String[] textWrapper(String input, int maxLength) {
		
		int lines = (int) Math.ceil((double) input.length() / (double) maxLength);
		String[] wrappedReturn = new String[lines + 1];
		
		LinkedList<String> wordlist = new LinkedList<String>();
		
		String word = "";
		for (int i = 0; i < input.length(); i++) {
			word = word + input.charAt(i);
			if (input.charAt(i) == ' ') {
				wordlist.add(word);
				word = "";
			}
		}
		wordlist.add(word);
		
		int line = 0; wrappedReturn[0] = "";
		while (wordlist.isEmpty() != true) {
			String wordInsert = wordlist.getFirst();
			if (wrappedReturn[line].length() + wordInsert.length() > maxLength) {
				line++;
				wrappedReturn[line] = "";
				continue;
			} 
			else {
				wrappedReturn[line] = wrappedReturn[line] + wordInsert;
				wordlist.removeFirst();
			}
		}
		
		return wrappedReturn;
		
	}
	


}
