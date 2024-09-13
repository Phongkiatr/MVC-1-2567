import java.awt.event.ActionListener;

import javax.swing.*;

public class View extends JFrame {
    private JTextField textField;
    private JButton somethingButton;
    private JComboBox<String> comboBox;

    View() {
        JPanel Panel = new JPanel();

        // Create JTextField
        textField = new JTextField(20); // <-- ใส่ขนาดจ้า

        // Create JButton
        somethingButton = new JButton(" Text jaa ");

        // Create JComboBox
        comboBox = new JComboBox<>();
        enableButton(false);

        this.setTitle("MVC");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        Panel.add(textField);
        Panel.add(somethingButton);
        Panel.add(comboBox);

        this.add(Panel);
    }

    public void addButtonListener(ActionListener actionListener) {
        somethingButton.addActionListener(actionListener);
    }

    public void addComboBoxSelectionListener(ActionListener listener) {
        comboBox.addActionListener(listener);
    }

    public void setComboBoxItems() {
        String[] hardware = { " ", "A", "B", "C" };
        comboBox.setModel(new DefaultComboBoxModel<>(hardware));
    }

    public void enableComboBox(boolean enable) {
        comboBox.setEnabled(enable);
    }

    public void enableButton(boolean enable) {
        somethingButton.setEnabled(enable);
    }
}
