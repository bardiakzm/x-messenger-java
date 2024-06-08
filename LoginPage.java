import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.*;
import java.net.Socket;
import  java.io.ObjectOutputStream;

public class LoginPage extends JFrame implements Serializable {
    
    public LoginPage() {
        // Set up the frame
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add content to the window.
        add(new LoginPanel());

        // Display the window.
        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }
}

class LoginPanel extends JPanel {

    // private JTextField usernameField;
    // private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;

    public LoginPanel() {
        // Create and set up components
        // JLabel usernameLabel = new JLabel("Username:");
        // JLabel passwordLabel = new JLabel("Password:");
        
        // usernameField = new JTextField(20);
        // passwordField = new JPasswordField(20);
        
        loginButton = new JButton("  Login  ");
        signUpButton = new JButton("Sign Up");

        // Set up layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(100, 100, 10, 100); //for padding

        // Add components to the panel
        // gbc.gridx = 0;
        // gbc.gridy = 0;
        // add(usernameLabel, gbc); 

        // gbc.gridx = 1;
        // add(usernameField, gbc);

        // gbc.gridx = 0;
        // gbc.gridy = 1;
        // add(passwordLabel, gbc);

        // gbc.gridx = 1;
        // add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(loginButton, gbc);

        gbc.insets = new Insets(10, 100, 100, 100);
        gbc.gridy = 3;
        add(signUpButton, gbc);

        // Add action listeners for buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });
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
        JOptionPane.showMessageDialog(this, "Sign!! Up button clicked!");
    }

    private void handleSignUp() {
        // Implement your sign-up logic here
        JOptionPane.showMessageDialog(this, "Sign Up button clicked!");
    }
}
