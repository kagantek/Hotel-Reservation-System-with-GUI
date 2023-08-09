
public class Reservation extends Services {
	private String hotelName, cityName, reservationMonth;
	private int reservationStart, reservationEnd;
	
	public Reservation(String cityName, String hotelName, String reservationMonth, int reservationStart, int reservationEnd) {
		this.hotelName = hotelName;
		this.reservationMonth = reservationMonth;
		this.reservationStart = reservationStart;
		this.reservationEnd = reservationEnd;
		this.cityName = cityName;
		
	}
	
	public String getHotelName() {
		return hotelName;
	}
	
	public String getReservationMonth() {
		return reservationMonth;
	}
	
	public int getReservationStart() {
		return reservationStart;
	}
	
	public int getReservationEnd() {
		return reservationEnd;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	
	public void displayInfo() {
		System.out.printf("Reservation at %s starts on %s %d and ends on %s %d.\n", this.getHotelName(), this.getReservationMonth(), this.getReservationStart(), this.getReservationMonth(), this.getReservationEnd());
	}

	@Override
	String getServiceType() {
		return "Room booking";
	}
	
	void setCustomerId(int id) {
		this.CustomerID = id;
	}


}