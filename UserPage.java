import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserPage extends JFrame {
    private User user;
    private List<Tweet> followingTweets;
    private List<Tweet> randomTweets;
    private List<Tweet> savedTweets;

    private JPanel tweetPanel;

    public UserPage(User user, List<Tweet> followingTweets, List<Tweet> randomTweets) {
        this.user = user;
        this.followingTweets = followingTweets;
        this.randomTweets = randomTweets;
        this.savedTweets = new ArrayList<>();
        setTitle("User Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create top panel with buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JButton profileButton = new JButton("Profile");
        JButton savedTweetsButton = new JButton("Saved Tweets");
        topPanel.add(profileButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(savedTweetsButton);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton followingButton = new JButton("Following Tweets");
        JButton randomButton = new JButton("Random Tweets");
        buttonPanel.add(followingButton);
        buttonPanel.add(randomButton);

        // Create tweet panel
        tweetPanel = new JPanel();
        tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(tweetPanel);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listeners
        followingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTweets(followingTweets);
            }
        });
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTweets(randomTweets);
            }
        });
        savedTweetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTweets(savedTweets);
            }
        });


        // Show the window
        setSize(800, 810);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showTweets(List<Tweet> tweets) {
        tweetPanel.removeAll();
        for (Tweet tweet : tweets) {
            JPanel tweetContainer = new JPanel(new BorderLayout());
            JLabel tweetLabel = new JLabel(tweet.publisherid + ": " + tweet.text + " (Likes: " + tweet.likes + ")");
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("Save");
            JButton likeButton = new JButton("Like");
            buttonPanel.add(saveButton);
            buttonPanel.add(likeButton);
            tweetContainer.add(tweetLabel, BorderLayout.CENTER);
            tweetContainer.add(buttonPanel, BorderLayout.EAST);
            tweetPanel.add(tweetContainer);

            // Add action listeners for save and like buttons
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveTweet(tweet);
                }
            });
            likeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    likeTweet(tweet);
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
}