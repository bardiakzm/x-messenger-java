import java.awt.*;
import javax.swing.*;

public class SignUpPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField ageField;
    private JTextArea bioField;
    private JButton submitButton;
    private JButton backButton;

    public SignUpPanel(StartPage parentFrame) {
        // Create and set up components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel bioLabel = new JLabel("Bio:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        ageField = new JTextField(20);
        bioField = new JTextArea(5, 20);
        bioField.setLineWrap(true);
        bioField.setWrapStyleWord(true);
        JScrollPane bioScrollPane = new JScrollPane(bioField);

        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        // Set up layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(nameLabel, gbc);

        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(emailLabel, gbc);

        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(phoneLabel, gbc);

        gbc.gridx = 1;
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(ageLabel, gbc);

        gbc.gridx = 1;
        add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(bioLabel, gbc);

        gbc.gridx = 1;
        add(bioScrollPane, gbc);

        gbc.insets = new Insets(10, 50, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(submitButton, gbc);

        gbc.insets = new Insets(10, 10, 10, 50);
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(backButton, gbc);

        // Add action listeners for buttons
        submitButton.addActionListener(e -> handleSubmit(parentFrame));
        backButton.addActionListener(e -> parentFrame.showStartPanel());
    }

    private void handleSubmit(StartPage parentFrame) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        int age = Integer.parseInt(ageField.getText());
        String bio = bioField.getText();
        Packet.sendNewUser(username, password, name, email, phone, age, bio);
        // Packet.receiveFeedback();
        if (Main.lastServerMessage.equals("signupSuccessfull")) {
            JOptionPane.showMessageDialog(this, Main.lastServerMessage);
            parentFrame.showLoginPanel();
        } else {
            JOptionPane.showMessageDialog(this, Main.lastServerMessage);
        }
        // JOptionPane.showMessageDialog(this, "Sign Up successful!");
    }
}
