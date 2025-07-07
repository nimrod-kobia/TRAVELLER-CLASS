package gui.dialogs;

import javax.swing.*;
import java.awt.*;

public class PaymentDialog extends JDialog {
    private String paymentMethodChoice = "Cash";
    private JTextField cardNumberField = new JTextField();
    private JTextField expiryDateField = new JTextField();
    private JTextField cvvField = new JTextField();
    private boolean paymentConfirmed = false;

    public PaymentDialog(Window parent, double amount) {
        super((Frame) parent, "Payment", true); // Cast to Frame for the super constructor
        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(350, 250);
        setMinimumSize(new Dimension(350, 250));
        setLocationRelativeTo(parent);

        ButtonGroup group = new ButtonGroup();
        JRadioButton cashRadio = new JRadioButton("Cash", true);
        JRadioButton cardRadio = new JRadioButton("Card");
        group.add(cashRadio);
        group.add(cardRadio);

        add(new JLabel("Amount:"));
        add(new JLabel("Ksh " + String.format("%.2f", amount)));
        add(new JLabel("Payment Method:"));
        JPanel radioPanel = new JPanel();
        radioPanel.add(cashRadio);
        radioPanel.add(cardRadio);
        add(radioPanel);

        add(new JLabel("Card Number:"));
        add(cardNumberField);
        add(new JLabel("Expiry (MM/YY):"));
        add(expiryDateField);
        add(new JLabel("CVV:"));
        add(cvvField);

        cardNumberField.setEnabled(false);
        expiryDateField.setEnabled(false);
        cvvField.setEnabled(false);

        cashRadio.addActionListener(e -> {
            paymentMethodChoice = "Cash";
            cardNumberField.setEnabled(false);
            expiryDateField.setEnabled(false);
            cvvField.setEnabled(false);
        });
        cardRadio.addActionListener(e -> {
            paymentMethodChoice = "Card";
            cardNumberField.setEnabled(true);
            expiryDateField.setEnabled(true);
            cvvField.setEnabled(true);
        });

        JButton payBtn = new JButton("Pay");
        JButton cancelBtn = new JButton("Cancel");
        add(cancelBtn);
        add(payBtn);

        payBtn.addActionListener(e -> {
            if (paymentMethodChoice.equals("Card")) {
                if (cardNumberField.getText().trim().isEmpty() ||
                    expiryDateField.getText().trim().isEmpty() ||
                    cvvField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all card details.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!cardNumberField.getText().matches("\\d{16}")) {
                    JOptionPane.showMessageDialog(this, "Card number must be 16 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!expiryDateField.getText().matches("(0[1-9]|1[0-2])/(\\d{2})")) {
                    JOptionPane.showMessageDialog(this, "Expiry date must be in MM/YY format (e.g., 12/25).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!cvvField.getText().matches("\\d{3,4}")) {
                    JOptionPane.showMessageDialog(this, "CVV must be 3 or 4 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            paymentConfirmed = true;
            dispose();
        });

        cancelBtn.addActionListener(e -> {
            paymentConfirmed = false;
            dispose();
        });
    }

    public String getPaymentMethodChoice() { return paymentMethodChoice; }
    public String getCardNumber() { return cardNumberField.getText(); }
    public String getExpiryDate() { return expiryDateField.getText(); }
    public String getCvv() { return cvvField.getText(); }
    public boolean isPaymentConfirmed() { return paymentConfirmed; }
}