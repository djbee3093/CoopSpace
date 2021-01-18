package inventory;

// An offer is used for buying/trading/selling purposes, it can be stored in store menus and then "executed" to actually make the trade
// Not all items trade on a 1 to 1 value and some items can't be split so Offers have an amount of input quantity and an amount of output quantity, as well as the items being traded
public class Offer {

	private Itemizable input;	// Item input
	private int inputQuantity;	// Amount of input
	
	private Itemizable output;	// Item output
	private int outputQuantity;	// Amount of output
	
	// Constructor for standard offer, with arguments passed (quantityX [of] itemX [for] quantityY [of] itemY) 
	public Offer(int inputQuantity, Itemizable input, int outputQuantity, Itemizable output) {
		this.inputQuantity = inputQuantity;		// Item input
		this.input = input;						// Item input quantity
		this.outputQuantity = outputQuantity;	// Item output
		this.output = output;					// Item output Quantity
	}
	
	// Get method that returns the input item for offer
	public Itemizable getInput() {
		return input; // Return the input item
	}
	
	// Get method that returns the output item for offer
	public Itemizable getOutput() {
		return output; // Return the output item
	}
}
