package gui;


import gui.panels.admin.AdminPanel;
import gui.panels.admin.RoleSelectionPanel;
import gui.panels.admin.UserPanel;
import model.Flight;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PlaneBookingSystemGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // Centralized data (can be passed to panels or managed by a controller)
    private List<Flight> flights = new ArrayList<>();

    public PlaneBookingSystemGUI() {
        setTitle("Plane Booking System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeDummyData();
        setupMainPanels();
        add(mainPanel, BorderLayout.CENTER);
    }

    private void initializeDummyData() {
        flights.add(new Flight("KQ101", "Nairobi", "London", 8500));
        flights.add(new Flight("KQ202", "Nairobi", "Paris", 7000));
        flights.add(new Flight("KQ303", "Nairobi", "Dubai", 7500));

        flights.get(0).bookSeat("1A");
        flights.get(0).bookSeat("1B");
    }

    private void setupMainPanels() {
        // Pass references to the main panel and card layout for switching
        RoleSelectionPanel roleSelectionPanel = new RoleSelectionPanel(mainPanel, cardLayout);
        UserPanel userPanel = new UserPanel(mainPanel, cardLayout, flights);
        AdminPanel adminPanel = new AdminPanel(mainPanel, cardLayout, flights); // Pass flights list

        mainPanel.add(roleSelectionPanel, "ROLE_SELECTION");
        mainPanel.add(userPanel, "USER_PANEL");
        mainPanel.add(adminPanel, "ADMIN_PANEL");

        // Initial view
        cardLayout.show(mainPanel, "ROLE_SELECTION");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlaneBookingSystemGUI().setVisible(true));
    }
}