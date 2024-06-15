import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ProfilePanel extends JPanel {

    static private User currentUser;
    private final JLabel usernameLabel;
    private final JLabel nameLabel;
    private final JLabel emailLabel;
    private final JLabel phoneLabel;
    private final JLabel ageLabel;
    private final JLabel bioLabel;
    private final JList<Tweet> tweetsList;
    private final JList<String> followersList;
    private final JList<String> followingsList;
    private final JButton returnButton;
    private final StartPage parentFrame;
    private final UserPage userPage;

    public ProfilePanel(String username, StartPage parentFrame, UserPage userPage) {
        this.parentFrame = parentFrame;
        this.userPage = userPage;

        Packet.getUser(username);
        currentUser = Packet.getLastReceivedUser();
        setLayout(new BorderLayout());

        // Return button and follow/unfollow button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        returnButton = new JButton("Return");
        returnButton.addActionListener(e -> parentFrame.showUserPage(userPage));
        topPanel.add(returnButton);

        if (username != null && !username.equals(Main.logedInUserid)) {
            boolean doesLogedUserFollowProfileUser = currentUser.followers.contains(Main.logedInUserid);
            JButton followButton = new JButton(doesLogedUserFollowProfileUser ? "Unfollow" : "Follow");
            topPanel.add(followButton);
            followButton.addActionListener(e -> {
                if (doesLogedUserFollowProfileUser) {
                    Packet.unfollowUser(Main.logedInUserid, currentUser.username);
                } else {
                    Packet.followUser(Main.logedInUserid, currentUser.username);
                }
                parentFrame.showProfilePanel(username, parentFrame, userPage);
            });
        }
        add(topPanel, BorderLayout.NORTH);

        // Create the user info panel
        JPanel userInfoPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        userInfoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

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

        JPanel userInfoContainer = new JPanel(new BorderLayout());
        userInfoContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "User Information",
                TitledBorder.LEFT, TitledBorder.TOP));
        userInfoContainer.add(userInfoPanel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(userInfoContainer, BorderLayout.NORTH);

        tweetsList = new JList<>(new DefaultListModel<>());
        tweetsList.setCellRenderer(new TweetCellRenderer());
        JScrollPane tweetScrollPane = new JScrollPane(tweetsList);
        tweetScrollPane.setBorder(BorderFactory.createTitledBorder("Tweets"));

        followersList = new JList<>();
        followersList.setCellRenderer(new UserCellRenderer());
        followersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane followersScrollPane = new JScrollPane(followersList);
        followersScrollPane.setBorder(BorderFactory.createTitledBorder("Followers"));

        followingsList = new JList<>();
        followingsList.setCellRenderer(new UserCellRenderer());
        followingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane followingsScrollPane = new JScrollPane(followingsList);
        followingsScrollPane.setBorder(BorderFactory.createTitledBorder("Followings"));

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList<String> list = (JList<String>) e.getSource();
                int index = list.locationToIndex(e.getPoint());
                if (index >= 0) {
                    String username = list.getModel().getElementAt(index);
                    Rectangle bounds = list.getCellBounds(index, index);
                    if (bounds != null) {
                        int totalWidth = bounds.width;
                        int buttonWidth = 80; // Approximate width of "Profile" or "Follow/Unfollow" button
                        int profileButtonX = bounds.x + totalWidth - 2 * buttonWidth - 20; // 20 is for padding
                        int actionButtonX = bounds.x + totalWidth - buttonWidth - 10; // 10 is for padding

                        if (e.getX() >= actionButtonX) {
                            boolean isFollowing = currentUser.followings.contains(username);
                            if (isFollowing) {
                                Packet.unfollowUser(Main.logedInUserid, username);
                            } else {
                                Packet.followUser(Main.logedInUserid, username);
                            }
                            // Refresh the current user data
                            Packet.getUser(currentUser.username);
                            currentUser = Packet.getLastReceivedUser();
                            loadUserData();
                            repaint();
                        } else if (e.getX() >= profileButtonX && e.getX() < actionButtonX) {
                            openUserProfile(username);
                        }
                    }
                }
            }
        };

        followersList.addMouseListener(mouseAdapter);
        followingsList.addMouseListener(mouseAdapter);

        JPanel listsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        listsPanel.add(tweetScrollPane);
        listsPanel.add(followersScrollPane);
        listsPanel.add(followingsScrollPane);

        centerPanel.add(listsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        loadUserData();
    }

    private void loadUserData() {
        User user = currentUser;

        if (user != null) {
            usernameLabel.setText(user.username);
            nameLabel.setText(user.name);
            emailLabel.setText(user.email);
            phoneLabel.setText(user.phone);
            ageLabel.setText(String.valueOf(user.age));
            bioLabel.setText(user.bio);

            initiateTweetList();

            DefaultListModel<String> followersModel = new DefaultListModel<>();
            for (String follower : user.getFollowers()) {
                followersModel.addElement(follower);
            }
            followersList.setModel(followersModel);

            DefaultListModel<String> followingsModel = new DefaultListModel<>();
            for (String following : user.getFollowings()) {
                followingsModel.addElement(following);
            }
            followingsList.setModel(followingsModel);

            // Update the titles of the scroll panes
            ((TitledBorder) ((JScrollPane) followersList.getParent().getParent()).getBorder()).setTitle("Followers: " + user.getFollowers().size());
            ((TitledBorder) ((JScrollPane) followingsList.getParent().getParent()).getBorder()).setTitle("Followings: " + user.getFollowings().size());

            revalidate();
        } else {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initiateTweetList() {
        DefaultListModel<Tweet> tweetListModel = new DefaultListModel<>();
        for (Tweet tweet : Main.allTweets) {
            if (tweet.publisherid.equals(currentUser.username)) {
                tweetListModel.addElement(tweet);
            }
        }
        tweetsList.setModel(tweetListModel);
    }

    private void openUserProfile(String username) {
        parentFrame.showProfilePanel(username, parentFrame, userPage);
    }

    private static class TweetCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Tweet tweet) {
                setText(tweet.text);
            }
            return this;
        }
    }

    private class UserCellRenderer extends JPanel implements ListCellRenderer<String> {
        private final JLabel nameLabel = new JLabel();
        private final JLabel actionLabel = new JLabel();
        private final JLabel profileLabel = new JLabel();

        public UserCellRenderer() {
            setLayout(new BorderLayout());
            add(nameLabel, BorderLayout.CENTER);
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            buttonsPanel.setOpaque(false);
            buttonsPanel.add(profileLabel);
            buttonsPanel.add(actionLabel);
            add(buttonsPanel, BorderLayout.EAST);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            nameLabel.setText(value);
            boolean isFollowing = currentUser.followings.contains(value);
            actionLabel.setText(isFollowing ? "Unfollow" : "Follow");
            actionLabel.setForeground(Color.BLUE);
            profileLabel.setText("Profile");
            profileLabel.setForeground(Color.GREEN);

            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            return this;
        }
    }
}