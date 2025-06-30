package model;

import javax.swing.*;

public class GUITextFieldReader implements InputReader {
    private JTextField textField;

    public GUITextFieldReader(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public String readLine(String prompt) {
        return textField.getText();
    }

    @Override
    public int readInt(String prompt) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public double readDouble(String prompt) {
        try {
            return Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public void close() {
        // No resources to close for GUI
    }
}
