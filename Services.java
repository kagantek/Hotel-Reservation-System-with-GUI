
public abstract class Services {
	int CustomerID;
	
	abstract String getServiceType();
	
	public void setCustomerID(int CustomerID) {
		this.CustomerID = CustomerID;
	}
	
	public int getCustomerID() {
		return CustomerID;
	}
}
