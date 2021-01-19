package interactions;

public class Alert {

	private static Alert instance = null;
	
	private Alert() {
		
	}
	
	public static Alert getInstance() {
		if (instance == null)
			instance = new Alert();
		
		return instance;
	}
	
	public void Alert(int time, String message) {
		
	}
	
	public void draw() {
		
	}
	
	
	
}
