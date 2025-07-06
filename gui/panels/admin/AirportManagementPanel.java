package gui.panels.admin;


import model.Airport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AirportManagementPanel extends JPanel {
    private DefaultTableModel airportTableModel;
    private JTable airportTable;
    public AirportManagementPanel(List<Airport> airports) {
        setLayout(new BorderLayout());

        airportTableModel = new DefaultTableModel(new Object[]{"Code", "Name", "City", "Country", "IATA Code"}, 0);
        airportTable = new JTable(airportTableModel);
        airportTable.getTableHeader().setResizingAllowed(false);
        airportTable.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(airportTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout());
        JTextField codeField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JTextField cityField = new JTextField(10);
        JTextField countryField = new JTextField(10);
        JTextField iataField = new JTextField(5);
        JButton addBtn = new JButton("Add Airport");
        JButton removeBtn = new JButton("Remove Selected Airport");

        form.add(new JLabel("Code:")); form.add(codeField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("City:")); form.add(cityField);
        form.add(new JLabel("Country:")); form.add(countryField);
        form.add(new JLabel("IATA Code:")); form.add(iataField);
        form.add(addBtn);
        form.add(removeBtn);
        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(_ -> {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            String city = cityField.getText().trim();
            String country = countryField.getText().trim();
            String iata = iataField.getText().trim();
            if (!(code.isEmpty() || name.isEmpty() || city.isEmpty() || country.isEmpty() || iata.isEmpty())) {
                Airport newAirport = new Airport(code, name, city, country, iata);
                if (!airports.contains(newAirport)) {
                    airports.add(newAirport);
                    airportTableModel.addRow(new Object[]{code, name, city, country, iata});
                    codeField.setText(""); nameField.setText(""); cityField.setText(""); countryField.setText(""); iataField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Airport with this code already exists.", "Duplicate Airport", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all airport details.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeBtn.addActionListener(_ -> {
            int selectedRow = airportTable.getSelectedRow();
            if (selectedRow >= 0) {
                String codeToRemove = (String) airportTableModel.getValueAt(selectedRow, 0);
                airports.removeIf(a -> a.getCode().equals(codeToRemove));
                airportTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Select an airport to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}