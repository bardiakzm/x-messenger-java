import java.awt.*;
import javax.swing.*;

public class ProfilePanel extends JPanel {

    static private User currentUser;
    private JLabel usernameLabel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel phoneLabel;
    private JLabel ageLabel;
    private JLabel bioLabel;
    private JList<String> tweetsList;

    public ProfilePanel(String username) {
        Packet.getUser(username);
        currentUser = Packet.getLastReceivedUser();

        setLayout(new BorderLayout());
        // Create the user info panel
        JPanel userInfoPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        usernameLabel = new JLabel();
        nameLabel = new JLabel();
        emailLabel = new JLabel();
        phoneLabel = new JLabel();
        ageLabel = new JLabel();
        bioLabel = new JLabel();

        userInfoPanel.add(new JLabel("Username:"));
        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(new JLabel("Name:"));
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(new JLabel("Email:"));
        userInfoPanel.add(emailLabel);
        userInfoPanel.add(new JLabel("Phone:"));
        userInfoPanel.add(phoneLabel);
        userInfoPanel.add(new JLabel("Age:"));
        userInfoPanel.add(ageLabel);
        userInfoPanel.add(new JLabel("Bio:"));
        userInfoPanel.add(bioLabel);

        add(userInfoPanel, BorderLayout.NORTH);

        // Create the tweets list
        tweetsList = new JList<>();
        JScrollPane tweetScrollPane = new JScrollPane(tweetsList);
        tweetScrollPane.setBorder(BorderFactory.createTitledBorder("Tweets"));

        add(tweetScrollPane, BorderLayout.CENTER);

        // Load user data
        loadUserData();
    }

    private void loadUserData() {
        // Fetch the user object from your data source
        User user = currentUser;

        if (user != null) {
            usernameLabel.setText(user.username);
            nameLabel.setText(user.name);
            emailLabel.setText(user.email);
            phoneLabel.setText(user.phone);
            ageLabel.setText(String.valueOf(user.age));
            bioLabel.setText(user.bio);

            DefaultListModel<String> tweetModel = new DefaultListModel<>();
            for (Tweet tweet : user.savedTweets) {
                tweetModel.addElement(tweet.toString());
            }
            tweetsList.setModel(tweetModel);
        } else {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
