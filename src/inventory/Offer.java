package inventory;

public class Offer {

	Itemizable input;	// Item input
	int inputQuantity;	// Amount of input
	
	Itemizable output;	// Item output
	int outputQuantity;	// Amount of output
	
	// Constructor for standard offer, with arguments passed (quantityX [of] itemX [for] quantityY [of] itemY) 
	public Offer(int inputQuantity, Itemizable input, int outputQuantity, Itemizable output) {
		this.inputQuantity = inputQuantity;
		this.input = input;
		this.outputQuantity = outputQuantity;
		this.output = output;
	}
}
