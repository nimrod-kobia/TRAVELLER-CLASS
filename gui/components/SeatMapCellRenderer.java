package gui.components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SeatMapCellRenderer extends JLabel implements TableCellRenderer {
    private Set<String> bookedSeats = new HashSet<>();

    public void setBookedSeats(Set<String> bookedSeats) {
        this.bookedSeats = bookedSeats != null ? bookedSeats : new HashSet<>();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value == null ? "" : value.toString());
        String seatId = value == null ? "" : value.toString();

        if (seatId.isEmpty()) { // Aisle
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.LIGHT_GRAY); // Make text invisible on aisle
        } else if (bookedSeats.contains(seatId)) {
            setBackground(Color.RED);
            setForeground(Color.WHITE);
        } else if (isSelected) {
            setBackground(Color.BLUE);
            setForeground(Color.WHITE);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        return this;
    }
}