import java.awt.*;
import javax.swing.*;


public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton backButton;
    private StartPage parentFrame;

    public LoginPanel(StartPage parentFrame) {
        // Create and set up components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        this.parentFrame = parentFrame;
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        submitButton = new JButton("login");
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
        submitButton.addActionListener(e -> handleSubmit());
        backButton.addActionListener(e -> parentFrame.showStartPanel());
    }

    private void handleSubmit() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        Packet.loginUser(username, password);
        JOptionPane.showMessageDialog(this, Main.lastServerMessage);
        if(Main.lastReceivedPacket.header.equals("loginSuccessfull")){
            //successfully logged in
            // List<Tweet> followingTweets = RandomTweetGenerator.generateRandomTweets();
            // List<Tweet> randomTweets = RandomTweetGenerator.generateRandomTweets2();
            Packet.getAllTweets();
            Packet.getFollowingTweets(username);
            
            UserPage userPage = new UserPage(username, Main.currentUserFollowingTweets, Main.allTweets);
            parentFrame.showUserPage(userPage);
        }
    }
}
