import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RegisterGUI implements ActionListener {
    private JFrame registrationFrame;
    private JPanel registrationPanel;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField newUsernameText;
    private JTextField newPasswordText;
    private JLabel successLabel;

    public RegisterGUI() {
        registrationFrame = new JFrame("User Registration");
        registrationPanel = new JPanel();
        registrationFrame.setSize(350, 300);
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.add(registrationPanel);

        registrationPanel.setLayout(null);

        userLabel = new JLabel("New Username");
        userLabel.setBounds(10, 20, 165, 25);
        registrationPanel.add(userLabel);

        passwordLabel = new JLabel("New Password");
        passwordLabel.setBounds(10, 50, 165, 25);
        registrationPanel.add(passwordLabel);

        newUsernameText = new JTextField(20);
        newUsernameText.setBounds(140, 20, 165, 25);
        registrationPanel.add(newUsernameText);

        newPasswordText = new JTextField(20);
        newPasswordText.setBounds(140, 50, 165, 25);
        registrationPanel.add(newPasswordText);

        JButton registerUserButton = new JButton("Register User");
        registerUserButton.setBounds(10, 80, 150, 25);
        registerUserButton.addActionListener(this);
        registrationPanel.add(registerUserButton);

        successLabel = new JLabel("");
        successLabel.setBounds(10, 110, 300, 25);
        registrationPanel.add(successLabel);
    }

    public void showRegistrationGUI() {
        registrationFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Handle the registration logic when the register button is clicked
        String newUsername = newUsernameText.getText();
        String newPassword = newPasswordText.getText();

        // Implement your registration logic using PrintWriter
        String filePath = "RootDir/Users.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(newUsername + "," + newPassword);
            successLabel.setText("Registration successful :)");
        } catch (IOException ex) {
            ex.printStackTrace();
            successLabel.setText("Error during registration");
        }
    }
}
