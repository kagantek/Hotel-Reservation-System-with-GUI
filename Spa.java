
public class Spa extends Services{
	int days;
	double spaCost;
	
	public Spa(int CustomerID, int days) {
		setCustomerID(CustomerID);
		this.days = days;
	}

	@Override
	String getServiceType() {
		return "Spa";
	}
	
	int getDays() {
		return days;
	}

}
