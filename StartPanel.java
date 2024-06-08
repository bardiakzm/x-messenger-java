import java.awt.*;
import javax.swing.*;

public class StartPanel extends JPanel {

    private JButton loginButton;
    private JButton signUpButton;

    public StartPanel(StartPage parentFrame) {
        
        loginButton = new JButton("  Login  ");
        signUpButton = new JButton("Sign Up");

        // Set up layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(100, 100, 10, 100); //for padding

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(loginButton, gbc);

        gbc.insets = new Insets(10, 100, 100, 100);
        gbc.gridy = 3;
        add(signUpButton, gbc);

        // Add action listeners for buttons
        loginButton.addActionListener(e -> handleLogin());
        signUpButton.addActionListener(e -> parentFrame.showSignUpPanel());
    }

    private void handleLogin() {
        // String username = usernameField.getText();
        // String password = new String(passwordField.getPassword());
        
        // Implement your login logic here
        // if (username.equals("user") && password.equals("password")) {
        //     JOptionPane.showMessageDialog(this, "Login successful!");
        // } else {
        //     JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        // }
        JOptionPane.showMessageDialog(this, "Login button clicked!");
    }
}
