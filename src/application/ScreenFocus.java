package application;

public class ScreenFocus {
	
	private static Focusable screenFocus;
	
	public static void setScreenFocus(Focusable newFocus) {
		screenFocus = newFocus;
	}
	
	public static Focusable getScreenFocus() {
		return screenFocus;
	}
	
}
