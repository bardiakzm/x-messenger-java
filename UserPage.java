import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.swing.*;

public class UserPage extends JFrame {
    private String username;
    private List<Tweet> followingTweets;
    private List<Tweet> randomTweets;
    private List<Tweet> savedTweets;
    private Set<String> followedUsers;

    private JPanel tweetPanel;
    private JTextField tweetTextField;

    public UserPage(String username, List<Tweet> followingTweets, List<Tweet> randomTweets) {
        this.username = username;
        this.followingTweets = followingTweets;
        this.randomTweets = randomTweets;
        this.savedTweets = new ArrayList<>();
        this.followedUsers =  Main.currentUserFollowings;
        // this.followedUsers.add("ali");
        setTitle("User Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create top panel with buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JButton profileButton = new JButton("Profile");
        JButton savedTweetsButton = new JButton("Saved Tweets");
        topPanel.add(profileButton);
        topPanel.add(savedTweetsButton);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton followingButton = new JButton("Following Tweets");
        JButton randomButton = new JButton("Random Tweets");
        topPanel.add(followingButton);
        topPanel.add(randomButton);
        topPanel.add(savedTweetsButton);

        // Create tweet panel
        tweetPanel = new JPanel();
        tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(tweetPanel);

        // Create bottom panel for composing tweets
        JPanel bottomPanel = new JPanel(new BorderLayout());
        tweetTextField = new JTextField();
        JButton composeButton = new JButton("Compose");
        bottomPanel.add(tweetTextField, BorderLayout.CENTER);
        bottomPanel.add(composeButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners
        followingButton.addActionListener(e -> showTweets(followingTweets));
        randomButton.addActionListener(e -> showTweets(randomTweets));
        savedTweetsButton.addActionListener(e -> showTweets(savedTweets));
        composeButton.addActionListener(e -> composeTweet(tweetTextField.getText()));

        // Show the window
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showTweets(List<Tweet> tweets) {
        // Sort tweets by timestamp
        Collections.sort(tweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet t1, Tweet t2) {
                return t2.getTimestamp().compareTo(t1.getTimestamp());
            }
        });

        tweetPanel.removeAll();
        for (Tweet tweet : tweets) {
            JPanel tweetContainer = new JPanel(new BorderLayout());
            JLabel tweetLabel = new JLabel(tweet.username + ": " + tweet.text + " (Likes: " + tweet.likes + ") " + tweet.getFormattedTimestamp());
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("Save");
            JButton likeButton = new JButton("Like");
            JButton followButton = new JButton(followedUsers.contains(tweet.username) ? "Unfollow" : "Follow");
            buttonPanel.add(saveButton);
            buttonPanel.add(likeButton);
            buttonPanel.add(followButton);
            tweetContainer.add(tweetLabel, BorderLayout.CENTER);
            tweetContainer.add(buttonPanel, BorderLayout.EAST);
            tweetPanel.add(tweetContainer);

            // Add action listeners for save and like buttons
            saveButton.addActionListener(e -> saveTweet(tweet));
            likeButton.addActionListener(e -> likeTweet(tweet));

            // Add action listener for follow/unfollow button
            followButton.addActionListener(e -> {
                if (followedUsers.contains(tweet.username)) {
                    followedUsers.remove(tweet.username); //unfollow user
                    Packet.unfollowUser(username, tweet.username);
                    Packet.getUserFollowings(username);
                    followButton.setText("Follow");
                } else {
                    followedUsers.add(tweet.username); //follow user
                    Packet.followUser(username, tweet.username);
                    Packet.getUserFollowings(username);
                    followButton.setText("Unfollow");
                }
            });
        }
        tweetPanel.revalidate();
        tweetPanel.repaint();
    }

    private void saveTweet(Tweet tweet) {
        savedTweets.add(tweet);
        JOptionPane.showMessageDialog(this, "Tweet saved successfully!");
    }

    private void likeTweet(Tweet tweet) {
        tweet.likes++;
        showTweets(followingTweets);
        showTweets(randomTweets);
        showTweets(savedTweets);
    }

    private void composeTweet(String text) {
        Packet.sendtweet(username, text);
        Packet.getAllTweets();
        randomTweets = Main.allTweets;
        System.out.println("Composed Tweet: " + text);
    }
}