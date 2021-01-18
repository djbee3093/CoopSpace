package application;

public class Trade {

	private Resource input;
	private Resource output;
	
	private double conversion;
	
	public Trade(Resource input, double conversion, Resource output) {
		this.input = input;
		this.conversion = conversion;
		this.output = output;
	}
	
	public String toString() {
		String ret = "";
		ret = ret + input.toString() + " --> " + conversion + " " + output.toString();
		return ret;
	}
	
	public void trade(double amount) {
		if (Inventory.getAmount(input) >= amount) {
			Inventory.withdraw(input, amount);
			Inventory.deposit(output, amount*conversion);
		}
		
		
	}
	
	
}
