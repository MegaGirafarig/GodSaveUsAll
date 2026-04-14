import javax.swing.*;
import java.awt.event.*;

public class ControlsDialog extends JDialog {
    private JTextField keyField;
    private JButton saveButton;
    private JButton cancelButton;
    private String action;

    public ControlsDialog(String action) {
        this.action = action;
        setTitle("Customize Keybinds");
        setModal(true);
        setLayout(null);

        JLabel label = new JLabel("Change key for: " + action);
        label.setBounds(10, 10, 200, 25);
        add(label);

        keyField = new JTextField();
        keyField.setBounds(10, 40, 150, 25);
        add(keyField);

        saveButton = new JButton("Save");
        saveButton.setBounds(10, 80, 80, 25);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveKeybind();
            }
        });
        add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(100, 80, 80, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(cancelButton);

        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    private void saveKeybind() {
        String newKey = keyField.getText();
        // Logic to save the new keybind for the action
        System.out.println("New keybind for " + action + ": " + newKey);
        dispose();
    }

    public static void main(String[] args) {
        ControlsDialog dialog = new ControlsDialog("Jump");
        dialog.setVisible(true);
    }
}