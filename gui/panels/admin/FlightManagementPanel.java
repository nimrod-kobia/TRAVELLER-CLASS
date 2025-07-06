package gui.panels.admin;


import model.Flight;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class FlightManagementPanel extends JPanel {
    private DefaultTableModel adminFlightTableModel;
    private JTable adminFlightTable;
    private List<Flight> flights;
    private Runnable flightUpdateCallback; // Callback to notify other panels

    public FlightManagementPanel(List<Flight> flights, Runnable flightUpdateCallback) {
        this.flights = flights;
        this.flightUpdateCallback = flightUpdateCallback;
        setLayout(new BorderLayout());

        adminFlightTableModel = new DefaultTableModel(new Object[]{"Flight No", "From", "To", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        adminFlightTable = new JTable(adminFlightTableModel);
        adminFlightTable.getTableHeader().setResizingAllowed(false); // Fix 2: Prevent column resizing
        adminFlightTable.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(adminFlightTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout());
        JTextField flightNoField = new JTextField(8);
        JTextField departureField = new JTextField(10);
        JTextField arrivalField = new JTextField(10);
        JTextField priceField = new JTextField(8);
        JButton addBtn = new JButton("Add Flight");
        JButton removeBtn = new JButton("Remove Selected Flight");

        form.add(new JLabel("Flight No:")); form.add(flightNoField);
        form.add(new JLabel("From:")); form.add(departureField);
        form.add(new JLabel("To:")); form.add(arrivalField);
        form.add(new JLabel("Price:")); form.add(priceField);
        form.add(addBtn);
        form.add(removeBtn);
        add(form, BorderLayout.SOUTH);

        updateAdminFlightTable(); // Initial population

        addBtn.addActionListener(_ -> {
            String flightNo = flightNoField.getText().trim();
            String departure = departureField.getText().trim();
            String arrival = arrivalField.getText().trim();
            String priceStr = priceField.getText().trim();

            if (flightNo.isEmpty() || departure.isEmpty() || arrival.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all flight details.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                Flight newFlight = new Flight(flightNo, departure, arrival, price);
                if (!flights.contains(newFlight)) {
                    flights.add(newFlight);
                    updateAdminFlightTable();
                    if (flightUpdateCallback != null) {
                        flightUpdateCallback.run(); // Notify main GUI or UserPanel to update
                    }
                    flightNoField.setText(""); departureField.setText(""); arrivalField.setText(""); priceField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Flight with this number already exists.", "Duplicate Flight", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeBtn.addActionListener(_ -> {
            int selectedRow = adminFlightTable.getSelectedRow();
            if (selectedRow >= 0) {
                String flightNoToRemove = (String) adminFlightTableModel.getValueAt(selectedRow, 0);
                flights.removeIf(f -> f.getFlightNumber().equals(flightNoToRemove));
                updateAdminFlightTable();
                if (flightUpdateCallback != null) {
                    flightUpdateCallback.run(); // Notify main GUI or UserPanel to update
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a flight to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void updateAdminFlightTable() {
        adminFlightTableModel.setRowCount(0); // Clear existing data
        for (Flight f : flights) {
            adminFlightTableModel.addRow(new Object[]{f.getFlightNumber(), f.getDepartureLocation(), f.getArrivalLocation(), f.getPrice()});
        }
    }
}