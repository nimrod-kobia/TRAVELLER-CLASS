package gui.panels.admin;


import model.Airline;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AirlineManagementPanel extends JPanel {
    private DefaultTableModel airlineTableModel;
    private JTable airlineTable;
    private List<Airline> airlines;

    public AirlineManagementPanel(List<Airline> airlines) {
        this.airlines = airlines;
        setLayout(new BorderLayout());

        airlineTableModel = new DefaultTableModel(new Object[]{"Code", "Name"}, 0);
        airlineTable = new JTable(airlineTableModel);
        airlineTable.getTableHeader().setResizingAllowed(false);
        airlineTable.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(airlineTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout());
        JTextField codeField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JButton addBtn = new JButton("Add Airline");
        JButton removeBtn = new JButton("Remove Selected Airline");

        form.add(new JLabel("Code:")); form.add(codeField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(addBtn);
        form.add(removeBtn);
        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(_ -> {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            if (!code.isEmpty() && !name.isEmpty()) {
                Airline newAirline = new Airline(name, code);
                if (!airlines.contains(newAirline)) {
                    airlines.add(newAirline);
                    airlineTableModel.addRow(new Object[]{code, name});
                    codeField.setText(""); nameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Airline with this code already exists.", "Duplicate Airline", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all airline details.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeBtn.addActionListener(_ -> {
            int selectedRow = airlineTable.getSelectedRow();
            if (selectedRow >= 0) {
                String codeToRemove = (String) airlineTableModel.getValueAt(selectedRow, 0);
                airlines.removeIf(a -> a.getCode().equals(codeToRemove));
                airlineTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Select an airline to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}