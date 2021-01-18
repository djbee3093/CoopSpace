package application;

//The class is for tracking Items: A resource & an amount
public class Item {

	private Resource type;
	private double amount;
	
	public Item(Resource type, double amount) {
		this.type = type;
		this.amount = amount;
	}
	
	public Resource getType() {
		return type;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void addAmount(double addition) {
		amount += addition;
	}
	
}
