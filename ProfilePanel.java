import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ProfilePanel extends JPanel {
    
    static private User currentUser;
    private JLabel usernameLabel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel phoneLabel;
    private JLabel ageLabel;
    private JLabel bioLabel;
    private JList<String> tweetsList;
    private JList<String> followersList;
    private JList<String> followingsList;
    private JButton returnButton;

    public ProfilePanel(String username, StartPage parentFrame, UserPage userPage) {
        Packet.getUser(username);
        currentUser = Packet.getLastReceivedUser();

        setLayout(new BorderLayout());

        // Return button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        returnButton = new JButton("Return");
        returnButton.addActionListener(e -> parentFrame.showUserPage(userPage));
        topPanel.add(returnButton);
        add(topPanel, BorderLayout.NORTH);

        // Create the user info panel
        JPanel userInfoPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        userInfoPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding

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

        // Create a container for the user info panel with a titled border
        JPanel userInfoContainer = new JPanel(new BorderLayout());
        userInfoContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "User Information",
                TitledBorder.LEFT, TitledBorder.TOP));
        userInfoContainer.add(userInfoPanel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(userInfoContainer, BorderLayout.NORTH);

        // Create the tweets list
        tweetsList = new JList<>();
        JScrollPane tweetScrollPane = new JScrollPane(tweetsList);
        tweetScrollPane.setBorder(BorderFactory.createTitledBorder("Tweets"));

        // Create the followers list
        followersList = new JList<>();
        JScrollPane followersScrollPane = new JScrollPane(followersList);
        followersScrollPane.setBorder(BorderFactory.createTitledBorder("Followers"));

        // Create the followings list
        followingsList = new JList<>();
        JScrollPane followingsScrollPane = new JScrollPane(followingsList);
        followingsScrollPane.setBorder(BorderFactory.createTitledBorder("Followings"));

        // Panel for lists
        JPanel listsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        listsPanel.add(tweetScrollPane);
        listsPanel.add(followersScrollPane);
        listsPanel.add(followingsScrollPane);

        centerPanel.add(listsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

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

            // Load tweets
            DefaultListModel<String> tweetModel = new DefaultListModel<>();
            for (Tweet tweet : user.savedTweets) {
                tweetModel.addElement(tweet.toString());
            }
            tweetsList.setModel(tweetModel);

            // Load followers
            DefaultListModel<String> followersModel = new DefaultListModel<>();
            for (String follower : user.getFollowers()) {
                followersModel.addElement(follower);
            }
            followersList.setModel(followersModel);

            // Load followings
            DefaultListModel<String> followingsModel = new DefaultListModel<>();
            for (String following : user.getFollowings()) {
                followingsModel.addElement(following);
            }
            followingsList.setModel(followingsModel);
        } else {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}