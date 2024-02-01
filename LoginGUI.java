import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.*;

public class LoginGUI implements ActionListener {
    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText; // Initialize passwordText here
    private static JButton button;
    private static JLabel success;

    private static JButton registerButton;
    private static JButton startServerButton;
    private static JButton stopServerButton;
    private static JButton loadServerButton;
    private static JButton eraseCommentsButton;


    private static JLabel serverStatusLabel;

    public static boolean runServer = false;

    private static HttpsServer server = new HttpsServer();


    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        JPanel panel = new JPanel();
        JFrame frame = new JFrame("User Login");
        frame.setSize(300, 345);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // Initialize passwordText before setting its bounds
        passwordText = new JTextField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(new LoginGUI());
        panel.add(button);

        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        registerButton = new JButton("Register");
        registerButton.setBounds(160, 80, 100, 25);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openRegistrationGUI();
            }
        });
        panel.add(registerButton);

        startServerButton = new JButton("Start Server");
        startServerButton.setBounds(10, 140, 120, 25);
        startServerButton.setVisible(false); // Initially set to invisible
        startServerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runServer();
            }
        });
        panel.add(startServerButton);

        stopServerButton = new JButton("Stop Server");
        stopServerButton.setBounds(150, 140, 120, 25);
        stopServerButton.setVisible(false);
        stopServerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
            });
        panel.add(stopServerButton);

        //RYAN COMMENT
        eraseCommentsButton = new JButton("Erase Comments");
        eraseCommentsButton.setBounds(10, 180, 260, 25);
        eraseCommentsButton.setVisible(false);
        eraseCommentsButton.setBackground(new Color(255, 120, 120));
        eraseCommentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eraseComments();
            }
            });
        panel.add(eraseCommentsButton);

        serverStatusLabel = new JLabel("Server is running! Go here: https://localhost");
        serverStatusLabel.setBounds(10, 215, 290, 25);
        serverStatusLabel.setVisible(false);
        panel.add(serverStatusLabel);

        loadServerButton = new JButton("Launch Website");
        loadServerButton.setBounds(10, 240, 260, 40);
        loadServerButton.setVisible(false);
        loadServerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadServer();
            }});
        panel.add(loadServerButton);

        // Show the UI Panel!!!
        frame.setVisible(true);
    }

    private static void openRegistrationGUI() {
        // Create an instance of RegisterGUI when the register button is clicked
        serverStatusLabel.setVisible(false);
        startServerButton.setVisible(false);
        eraseCommentsButton.setVisible(false);
        loadServerButton.setVisible(false);
        stopServer();
        success.setVisible(false);
        RegisterGUI registerGUI = new RegisterGUI();
        registerGUI.showRegistrationGUI();
    }

    private static void runServer() {
        stopServerButton.setVisible(true);
        loadServerButton.setVisible(true);
        serverStatusLabel.setText("Server is running at: https://localhost");
        serverStatusLabel.setVisible(true);
        runServer = true;
        try{
            // TODO: Ryan move this copy to an eras comments button!
            new Thread(server).start();
        } catch(Exception E){
            HttpsServer.excLogger.log(Level.WARNING, E.toString());
        }
    }

    private static void stopServer() {
        serverStatusLabel.setText("Server stopped");
        stopServerButton.setVisible(false);
        loadServerButton.setVisible(false);
        runServer = false;
    }

    private static void eraseComments(){
        try {
            Files.copy(new File("RootDir/media/RyanMedia/RyanCommentsDefault.html").toPath(), new File("RootDir/media/RyanMedia/RyanComments.html").toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e){
            HttpsServer.excLogger.log(Level.WARNING, e.toString());
        }
    }

    private static void loadServer() {
        try {
            Desktop.getDesktop().browse(new URI("https://localhost"));
        } catch (Exception e){
            HttpsServer.excLogger.log(Level.WARNING, e.toString());
        }
    }

    public void actionPerformed(ActionEvent e) {
        success.setVisible(true);
        String username = userText.getText();
        String password = passwordText.getText();

        String filePath = "RootDir/Users.csv";

        boolean found = false;

        Logger userLogger = Logger.getLogger("User Login");
        FileHandler fhUser;
        SimpleFormatter formatter = new SimpleFormatter();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            fhUser = new FileHandler("RootDir/Logs/user_login.txt", true);
            fhUser.setFormatter(formatter);
            userLogger.addHandler(fhUser);
            userLogger.setUseParentHandlers(false);
            while ((line = reader.readLine()) != null) {
                // Split the line into fields using a comma as the delimiter
                String[] fields = line.split(",");
                String csvUser = fields[0];
                String csvPass = fields[1].trim();

                String fileDir = "RootDir/Logs/user_login.txt";
				HttpsServer.checkLines(fileDir);

                if (csvUser.equals(username) && csvPass.equals(password)) {
                    success.setText("Login successful :)");
                    found = true;
                    eraseCommentsButton.setVisible(true);
                    startServerButton.setVisible(true);
                    userLogger.log(Level.INFO, "User has logged in to server. Username: " + username + " Password: " + password);
                    break;
                }
                
            }
        if (!found) {
            success.setText("Login unsuccessful :(");
            eraseCommentsButton.setVisible(false);
            startServerButton.setVisible(false);
            serverStatusLabel.setVisible(false);
        }

        } catch (Exception E) {
            // TODO: E.printStackTrace();
            HttpsServer.excLogger.log(Level.WARNING, e.toString());
        }

    }

}
