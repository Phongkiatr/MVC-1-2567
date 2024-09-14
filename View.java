import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JLabel[] machineLabel;
    private JLabel angryCowCountLabel;
    private JLabel completeCowCountLabel;
    private JLabel milkQuantityLabel;
    private JLabel interruptCountLabel;
    private JLabel releaseCowCountLabel;
    private JButton startButton;

    View() {
        this.setLayout(new BorderLayout());

        JPanel Panel = new JPanel();

        // Create JLabel for Machine
        machineLabel = new JLabel[10];
        for (int i = 0; i < machineLabel.length; i++) {
            machineLabel[i] = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
            machineLabel[i].setPreferredSize(new Dimension(100, 200)); // กำหนดขนาดที่ต้องการ
            machineLabel[i].setOpaque(true);
            machineLabel[i].setBackground(Color.LIGHT_GRAY); // ตั้งค่าพื้นหลังให้เห็นขนาดชัดเจน
            machineLabel[i].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // ขอบเพื่อให้เห็นขอบเขตของ JLabel
            Panel.add(machineLabel[i]);
        }

        // Create JLabel for Counting วัวหงุดหงิด
        angryCowCountLabel = new JLabel("Angry Cow: 0 ");

        // Create JLabel for Counting วัวที่ถูกรีดแล้ว
        completeCowCountLabel = new JLabel("Complete Cow: 0 ");

        // Create JLabel for จำนวนน้ำนมที่รีดได้แล้ว
        milkQuantityLabel = new JLabel("Milk Quantity: 0 Liter.");

        // Create JLabel for จำนวนครั้งที่โปรแกรมถูกแทรกแซง
        interruptCountLabel = new JLabel("Interrupt: 0 ");

        // Create JLabel for จำนวนวัวที่มีปัญหาที่ถูกนำออกจากระบบ
        releaseCowCountLabel = new JLabel("Release Cow: 0 ");

        // Create JButton for Start Button
        startButton = new JButton(" Start ");

        this.setTitle("Cow Strike");
        this.setSize(1100, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        Panel.add(angryCowCountLabel);
        Panel.add(completeCowCountLabel);
        Panel.add(milkQuantityLabel);
        Panel.add(interruptCountLabel);
        Panel.add(releaseCowCountLabel);
        Panel.add(startButton);

        this.add(Panel);

        // ทำให้ JFrame โผล่มากลางจอ
        this.setLocationRelativeTo(null);
    }

    public void setMachineLabelText(int idx, String str) {
        machineLabel[idx].setText(str);
    }

    public void setAngryCowCountLabelText(String str) {
        angryCowCountLabel.setText("Angry Cow: " + str);
    }

    public void setCompleteCowCountLabelText(String str) {
        completeCowCountLabel.setText("Complete Cow: " + str);
    }

    public void setMilkQuantityLabelText(String str) {
        milkQuantityLabel.setText("Milk Quantity: " + str);
    }

    public void setReleaseCowCountLabelText(String str) {
        releaseCowCountLabel.setText("Release Cow: " + str);
    }

    public void setInterruptCountLabelText(String str) {
        interruptCountLabel.setText("Interrupt Count: " + str);
    }

    public void addButtonListener(ActionListener actionListener) {
        startButton.addActionListener(actionListener);
    }

    public void enableButton(boolean enable) {
        startButton.setEnabled(enable);
    }
}
