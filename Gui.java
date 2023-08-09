
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Gui extends JFrame {
	private JMenuItem newMenuItem, newMenuItem2, fileMenuItem, helpMenuItem, helpMenuItem2;
	private JButton disResButton, disServiceButton, disCityButton, multiThreadSearchButton, saveReservationsButton, loadReservationsButton;
	private JTextArea textArea;
	private static ArrayList<Services> serviceList = new ArrayList<Services>();
	private static int id = 1;
    private static final int THREAD_POOL_SIZE = 8;
    private static final String RESERVATIONS_FILE = "reservations.csv";
	
	public Gui() {
		this.setTitle("Hotel Reservation System");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);
		this.setLayout(new FlowLayout());
		this.setLayout(null);
		this.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenu newMenu = new JMenu("New");
		JMenu helpMenu = new JMenu("Help");
		
		textArea = new JTextArea();
		textArea.setBounds(15, 100, 450, 300);
		
		this.add(textArea);
		
		fileMenuItem = new JMenuItem("Exit");
		
		newMenuItem = new JMenuItem("Reservation");
		newMenuItem2 = new JMenuItem("Services");
		
		helpMenuItem = new JMenuItem("Content");
		helpMenuItem2 = new JMenuItem("About");
		
		disResButton = new JButton();
		disServiceButton = new JButton();
		disCityButton = new JButton();
		multiThreadSearchButton = new JButton();
		saveReservationsButton = new JButton();
		loadReservationsButton = new JButton();
		
		disResButton.setText("Display Reservations");
		disResButton.setBounds(30, 40, 200, 20);
		
		disServiceButton.setText("Display Extra Services");
		disServiceButton.setBounds(240, 40, 200, 20);
		
		disCityButton.setText("Disp. Res. For City");
		disCityButton.setBounds(30, 70, 200, 20);
		
		multiThreadSearchButton.setText("Multithread Search");
		multiThreadSearchButton.setBounds(240, 70, 200, 20);
		
		saveReservationsButton.setText("Save Reservations");
		saveReservationsButton.setBounds(30, 10, 200, 20);
		
		loadReservationsButton.setText("Load Reservations");
		loadReservationsButton.setBounds(240, 10, 200, 20);
		
	
		
		this.add(disResButton);
		this.add(disServiceButton);
		this.add(disCityButton);
		this.add(multiThreadSearchButton);
		this.add(saveReservationsButton);
		this.add(loadReservationsButton);
		
		newMenu.add(newMenuItem);
		newMenu.add(newMenuItem2);
		
		fileMenu.add(fileMenuItem);
		
		helpMenu.add(helpMenuItem);
		helpMenu.add(helpMenuItem2);
		
		menuBar.add(fileMenu);
		menuBar.add(newMenu);
		menuBar.add(helpMenu);
		
		InteractiveHandler handler = new InteractiveHandler();
		
		fileMenuItem.addActionListener(handler);
		newMenuItem.addActionListener(handler);
		newMenuItem2.addActionListener(handler);
		disResButton.addActionListener(handler);
		disServiceButton.addActionListener(handler);
		disCityButton.addActionListener(handler);
		helpMenuItem.addActionListener(handler);
		helpMenuItem2.addActionListener(handler);
		multiThreadSearchButton.addActionListener(handler);
		saveReservationsButton.addActionListener(handler);
		loadReservationsButton.addActionListener(handler);
		
		this.setJMenuBar(menuBar);
		this.setVisible(true);
	}
	
	
	
	private class InteractiveHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == fileMenuItem) {
				System.exit(0);
			}
			
			else if(e.getSource() == newMenuItem) {
				String cityName = JOptionPane.showInputDialog("Enter City: ");
				String hotelName = JOptionPane.showInputDialog("Enter Hotel Name: ");
				String reservationMonth = JOptionPane.showInputDialog("Enter Month: ");
				String reservationStart = JOptionPane.showInputDialog("Reservation Start: ");
				String reservationEnd = JOptionPane.showInputDialog("Reservation End: ");
				
				int resStart = Integer.parseInt(reservationStart);
				int resEnd = Integer.parseInt(reservationEnd);
				
				Reservation reservation = new Reservation(cityName, hotelName, reservationMonth, resStart, resEnd);
				serviceList.add(reservation);
				reservation.setCustomerId(id);
				textArea.append("Reservation #" + id + " created!\n");
				id++;
				
			}
			
			else if(e.getSource() == newMenuItem2) {
				String inp = JOptionPane.showInputDialog("Please select one of the extra services from below:\n1. Laundry Service\n2. Spa Service");
				int inpInt = Integer.parseInt(inp);
				
				if(inpInt == 1) {
					String localID = JOptionPane.showInputDialog("Type the reservation ID to credit this service: ");
					int localID_ = Integer.parseInt(localID);
					
					if(localID_ >= Gui.id) {
						textArea.append("You have to rent a room first.\n");
					}
					else {
						String piece = JOptionPane.showInputDialog("How many pieces of clothing?");
						int pieceInt = Integer.parseInt(piece);
						
						Services laundry = new Laundry(localID_, pieceInt);
						serviceList.add(laundry);
					}
				}
				
				else if(inpInt == 2) {
					String localID2 = JOptionPane.showInputDialog("Type the reservation ID to credit this service: ");
					int localID2_ = Integer.parseInt(localID2);
					
					if(localID2_ >= Gui.id) {
						textArea.setText(" ");
						textArea.append("You have to rent a room first.\n");
					}
					
					else {
						String day = JOptionPane.showInputDialog("How many days?");
						int dayInt = Integer.parseInt(day);
						
						Services spa = new Spa(localID2_, dayInt);
						serviceList.add(spa);
					}
				}
			}
			
			else if(e.getSource() == disServiceButton) {
				Iterator<Services> iter2 = serviceList.iterator();
				textArea.setText(" ");
				while(iter2.hasNext()) {
					Services element2 = iter2.next();
					if(element2.getServiceType().equals("Room booking")) 
						continue;
					
					else if (element2.getServiceType().equals("Laundry")) {
						textArea.append("Reservation ID #" + element2.getCustomerID() + "has " + ((Laundry) element2).getPiece() + " pieces assigned for Laundry Service.\n");
					}
					else if(element2.getServiceType().equals("Spa")) {
						textArea.append("Reservation ID #" + element2.getCustomerID() + "has " + ((Spa) element2).getDays() + " days of SPA services.\n");
					}
				}
			}
			
			else if(e.getSource() == disCityButton) {
				String city = JOptionPane.showInputDialog("Type a city name: ");
				Iterator<Services> iter3 = serviceList.iterator();
				textArea.setText(" ");
				while(iter3.hasNext()) {
					Services element3 = iter3.next();
					if(element3.getServiceType() != "Room booking") {
						continue;
					}
					else {
						if(((Reservation) element3).getCityName().equals(city)) {
							textArea.append("Reservations for " + city +"\nReservation ID #" + element3.getCustomerID()
							+ "\nReservation at " + ((Reservation) element3).getHotelName() + " starts on " + ((Reservation) element3).getReservationMonth() + " " + 
									((Reservation) element3).getReservationStart() + " and ends on " + ((Reservation) element3).getReservationMonth() + " " + ((Reservation) element3).getReservationEnd() + "\n");
						}
						else {
							continue;
						}
					}
				}
			}
			
			else if(e.getSource() == disResButton) {
				int copyID = 1;
				Iterator<Services> iter = serviceList.iterator();
				textArea.setText(" ");
				while(iter.hasNext()) {
					textArea.append("Reservation #" + copyID +"\n");
					copyID++;
					Services element = iter.next();
					
					if(element.getServiceType() != "Room booking") {
						continue;
					}
					
					else {
						((Reservation) element).displayInfo();
						textArea.append("Reservation at " + ((Reservation) element).getHotelName() + " starts on " + ((Reservation) element).getReservationMonth() + " " 
						+ ((Reservation) element).getReservationStart() + " and ends on " + ((Reservation) element).getReservationMonth() + " " + ((Reservation) element).getReservationEnd() + "\n");
					}
				}
		}
		
			else if(e.getSource() == helpMenuItem) {
				JOptionPane.showMessageDialog(null, "Welcome to the Hotel Reservation Program. Please click on 'New' in order to create a new reservation or add a service to your existing reservation. In order to see your existing reservations, please click on the display button.");
			}
			
			else if(e.getSource() == helpMenuItem2) {
				JOptionPane.showMessageDialog(null, "Kagan Tek, 20x Year Old 2nd Year CSE Major in Yeditepe University\nStudent ID: 20210702027");
			}
			
			else if (e.getSource() == multiThreadSearchButton) {
			    if (serviceList.size() < 8) {
			        JOptionPane.showMessageDialog(null, "You need at least 8 reservations to perform a multithreaded search.");
			    } else {
			        performMultithreadedSearch();
			    }
			}
			
			else if (e.getSource() == saveReservationsButton) {
	            saveReservationsToFile();
	        }
			
			else if (e.getSource() == loadReservationsButton) {
	            loadReservationsFromFile();
	        }
	}

}
	
	private void performMultithreadedSearch() {
	    ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	    ConcurrentLinkedQueue<Reservation> searchResults = new ConcurrentLinkedQueue<>();

	    String hotelName = JOptionPane.showInputDialog("Enter hotel name to search: ");

	    for (Services service : serviceList) {
	        if (service instanceof Reservation) {
	            executor.submit(() -> {
	                Reservation reservation = (Reservation) service;
	                if (reservation.getHotelName().equals(hotelName)) {
	                    searchResults.add(reservation);
	                }
	            });
	        }
	    }

	    executor.shutdown();
	    try {
	        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	    } catch (InterruptedException ex) {
	        ex.printStackTrace();
	    }

	    displayMultithreadedSearchResults(searchResults);
	}

	private void displayMultithreadedSearchResults(ConcurrentLinkedQueue<Reservation> searchResults) {
	    if (searchResults.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No reservations found for the given hotel name.");
	    } else {
	        StringBuilder resultText = new StringBuilder();
	        resultText.append("Reservations for the given hotel name:\n");

	        for (Reservation reservation : searchResults) {
	            resultText.append("Reservation ID #").append(reservation.getCustomerID())
	                .append("\nReservation at ").append(reservation.getHotelName())
	                .append(" starts on ").append(reservation.getReservationMonth()).append(" ")
	                .append(reservation.getReservationStart()).append(" and ends on ")
	                .append(reservation.getReservationMonth()).append(" ")
	                .append(reservation.getReservationEnd()).append("\n");
	        }

	        JOptionPane.showMessageDialog(null, resultText.toString());
	    }
	}

private void saveReservationsToFile() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(RESERVATIONS_FILE))) {
        for (Services service : serviceList) {
            if (service instanceof Reservation) {
                Reservation reservation = (Reservation) service;
                String line = reservation.getCustomerID() + ","
                        + reservation.getCityName() + ","
                        + reservation.getHotelName() + ","
                        + reservation.getReservationMonth() + ","
                        + reservation.getReservationStart() + ","
                        + reservation.getReservationEnd();
                writer.println(line);
            }
        }
        writer.flush();
        JOptionPane.showMessageDialog(null, "Reservations saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error occurred while saving reservations.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void loadReservationsFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
        String line;
        serviceList.clear();
        id = 1;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 6) {
                int customerID = Integer.parseInt(data[0]);
                String cityName = data[1];
                String hotelName = data[2];
                String reservationMonth = data[3];
                int reservationStart = Integer.parseInt(data[4]);
                int reservationEnd = Integer.parseInt(data[5]);
                
                Reservation reservation = new Reservation(cityName, hotelName, reservationMonth, reservationStart, reservationEnd);
                reservation.setCustomerID(customerID);
                serviceList.add(reservation);
                
                if (customerID >= id) {
                    id = customerID + 1;
                }
            }
        }
        
        displayReservations();
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error occurred while loading reservations.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void displayReservations() {
    Iterator<Services> iter = serviceList.iterator();
    textArea.setText("");
    while (iter.hasNext()) {
        Services element = iter.next();
        if (element instanceof Reservation) {
            Reservation reservation = (Reservation) element;
            reservation.displayInfo();
            textArea.append("Reservation at " + reservation.getHotelName() + " starts on " + reservation.getReservationMonth() + " " + reservation.getReservationStart() + " and ends on " + reservation.getReservationMonth() + " " + reservation.getReservationEnd() + "\n");
        }
    }
}
}
