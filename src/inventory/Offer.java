package inventory;

// An offer is used for buying/trading/selling purposes, it can be stored in store menus and then "executed" to actually make the trade
// Not all items trade on a 1 to 1 value and some items can't be split so Offers have an amount of input quantity and an amount of output quantity, as well as the items being traded
public class Offer {

	Itemizable input;	// Item input
	int inputQuantity;	// Amount of input
	
	Itemizable output;	// Item output
	int outputQuantity;	// Amount of output
	
	// Constructor for standard offer, with arguments passed (quantityX [of] itemX [for] quantityY [of] itemY) 
	public Offer(int inputQuantity, Itemizable input, int outputQuantity, Itemizable output) {
		this.inputQuantity = inputQuantity;		// Item input
		this.input = input;						// Item input quantity
		this.outputQuantity = outputQuantity;	// Item output
		this.output = output;					// Item output Quantity
	}
}
