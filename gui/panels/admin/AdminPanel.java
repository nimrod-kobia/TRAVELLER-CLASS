package gui.panels.admin;


import model.Airline;
import model.Airport;
import model.Flight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends JPanel {
    private List<Airline> airlines = new ArrayList<>(); // Admin-specific data
    private List<Airport> airports = new ArrayList<>(); // Admin-specific data

    // References to the flight management panel to allow updates
    private FlightManagementPanel flightManagementPanel;
    private UserPanel userPanel; // We need a way to tell UserPanel to refresh its flight table

    public AdminPanel(JPanel mainPanel, CardLayout cardLayout, List<Flight> flights) {
        // To allow AdminPanel to tell UserPanel to refresh its data
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof UserPanel) {
                this.userPanel = (UserPanel) comp;
                break;
            }
        }

        setLayout(new BorderLayout());
        JTabbedPane adminTabs = new JTabbedPane();

        adminTabs.addTab("Airlines", new AirlineManagementPanel(airlines));
        adminTabs.addTab("Airports", new AirportManagementPanel(airports));
        
        flightManagementPanel = new FlightManagementPanel(flights, this::updateUserFlightTableOnFlightChange);
        adminTabs.addTab("Flights", flightManagementPanel);
        
        adminTabs.addTab("Bookings", new BookingManagementPanel());

        add(adminTabs, BorderLayout.CENTER);

        JButton adminBack = new JButton("Back");
        adminBack.addActionListener(_ -> cardLayout.show(mainPanel, "ROLE_SELECTION"));
        add(adminBack, BorderLayout.SOUTH);
    }

    // Callback method for FlightManagementPanel to notify of flight changes
    private void updateUserFlightTableOnFlightChange() {
        if (userPanel != null) {
            userPanel.updateUserFlightTable();
        }
    }
}