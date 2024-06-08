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
    
    private JPanel tweetPanel;

    public UserPage(User user, List<Tweet> followingTweets, List<Tweet> randomTweets) {
        this.user = user;
        this.followingTweets = followingTweets;
        this.randomTweets = randomTweets;

        setTitle("User Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create buttons
        JPanel buttonPanel = new JPanel();
        JButton followingButton = new JButton("Following Tweets");
        JButton randomButton = new JButton("Random Tweets");

        buttonPanel.add(followingButton);
        buttonPanel.add(randomButton);

        // Create tweet panel
        tweetPanel = new JPanel();
        tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(tweetPanel);

        add(buttonPanel, BorderLayout.NORTH);
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

        // Show the window
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showTweets(List<Tweet> tweets) {
        tweetPanel.removeAll();
        for (Tweet tweet : tweets) {
            tweetPanel.add(new JLabel(tweet.publisherid + ": " + tweet.text + " (Likes: " + tweet.likes + ")"));
        }
        tweetPanel.revalidate();
        tweetPanel.repaint();
    }

    public static void main(String[] args) {
        User dummyUser = new User("dummyUser", "password", "Dummy User", "dummy@example.com", "1234567890", 30, "This is a dummy user.");
        List<Tweet> followingTweets = new ArrayList<>();
        List<Tweet> randomTweets = new ArrayList<>();
        
        // Add some dummy tweets
        User dummyPublisher1 = new User("publisher1", "password", "Publisher 1", "pub1@example.com", "0987654321", 25, "This is publisher 1.");
        User dummyPublisher2 = new User("publisher2", "password", "Publisher 2", "pub2@example.com", "0123456789", 28, "This is publisher 2.");

        followingTweets.add(new Tweet(dummyPublisher1, "Following tweet 1"));
        followingTweets.add(new Tweet(dummyPublisher1, "Following tweet 2"));
        randomTweets.add(new Tweet(dummyPublisher2, "Random tweet 1"));
        randomTweets.add(new Tweet(dummyPublisher2, "Random tweet 2"));

        new UserPage(dummyUser, followingTweets, randomTweets);
    }
}
