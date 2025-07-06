package gui.panels.admin;


import javax.swing.*;
import java.awt.*;

public class RoleSelectionPanel extends JPanel {
    public RoleSelectionPanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Welcome to Plane Booking System");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JButton userButton = new JButton("User Portal");
        userButton.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(userButton, gbc);

        JButton adminButton = new JButton("Admin Portal");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1; gbc.gridy = 1;
        add(adminButton, gbc);

        userButton.addActionListener(_ -> cardLayout.show(mainPanel, "USER_PANEL"));
        adminButton.addActionListener(_ -> cardLayout.show(mainPanel, "ADMIN_PANEL"));
    }
}