package items;

import inventory.ItemCategory;
import inventory.Itemizable;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Material implements Itemizable{

	MaterialType type;
	
	public Material(MaterialType type) {
		this.type = type;
	}
	
	@Override
	public void drawItemIcon(GraphicsContext gc, int x, int y, int width, int height) {
		
		gc.save();
		gc.setFill(Color.LIGHTGREEN);
		gc.setStroke(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, 36));
		gc.fillRoundRect(x - width/2, y - height/2, width, height, Itemizable.rounding, Itemizable.rounding);
		gc.strokeRoundRect(x - width/2, y - height/2, width, height, Itemizable.rounding, Itemizable.rounding);
		gc.setFill(Color.BLACK);
		gc.fillText(getType(), x, y - 2);
		
		gc.restore();
	}
	
	// To string just returns the material type
	@Override
	public String toString() {
		return type.toString(); // Return type of material
	}
	
	private String getType() {
		if (type == MaterialType.ALUMINUM) {
			return "Al";
		}
		else if (type == MaterialType.CARBON) {
			return "C";
		}
		else if (type == MaterialType.HYDROGEN) {
			return "H";
		}
		else if (type == MaterialType.LITHIUM) {
			return "Li";
		}
		else if (type == MaterialType.OXYGEN) {
			return "O";
		}
		else if (type == MaterialType.SILICON) {
			return "Si";
		}
		else if (type == MaterialType.STEEL) { 
			return "St";
		}
		else if (type == MaterialType.URANIUM) {
			return "U";
		}
		return null;
	}

	@Override
	public ItemCategory getCategory() {
		return ItemCategory.MATERIAL;
	}


	
}
