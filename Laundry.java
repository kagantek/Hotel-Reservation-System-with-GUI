

public class Laundry extends Services {
	int clothingPieces;
	double laundryCost;
	
	public Laundry(int CustomerID, int clothingPieces) {
		setCustomerID(CustomerID);
		this.clothingPieces = clothingPieces;
	}
	
	
	@Override
	String getServiceType() {
		return "Laundry";
	}
	
	int getPiece() {
		return clothingPieces;
	}

}
