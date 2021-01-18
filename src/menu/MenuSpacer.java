package menu;

import javafx.scene.canvas.GraphicsContext;

public class MenuSpacer extends MenuElement{

	public MenuSpacer(MainMenu mainMenu, int width, int height) {
		this.width = width;
		this.height = height;
		mainMenu.add(this);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		
	}

}
