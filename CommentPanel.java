import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;

public class CommentPanel extends JPanel {
    private ArrayList<Tweet> comments;
    private JPanel commentsPanel;
    private JTextField composeTextField;
    static private HashSet<String> commentids= new HashSet<String>();


    public CommentPanel(Tweet tweet, UserPage userPage, StartPage parentFrame) {
        this.comments = tweet.comments != null ? tweet.comments : new ArrayList<>();  // Initialize comments from the tweet
        setLayout(new BorderLayout());

        // Top panel with return button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton returnButton = new JButton("Return");
        topPanel.add(returnButton);
        returnButton.addActionListener(e -> parentFrame.showUserPage(userPage));

        // Comments display panel
        commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(commentsPanel);

        // Bottom panel with compose text field and button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        composeTextField = new JTextField();
        JButton composeButton = new JButton("Compose");

        bottomPanel.add(composeTextField, BorderLayout.CENTER);
        bottomPanel.add(composeButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Populate comments
        showComments();

        // Add compose button action listener
        composeButton.addActionListener(e -> composeComment(tweet));
    }

    private void showComments() {
        commentsPanel.removeAll();
        for (Tweet comment : comments) {
            if (!commentids.contains(String.valueOf(comment.tweetNumber))) {
                JLabel commentLabel = new JLabel(comment.publisherid + ": " + comment.text);
                commentsPanel.add(commentLabel);
                commentids.add(String.valueOf(comment.tweetNumber));
            }
        }
        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

    private void composeComment(Tweet tweet) {
        String text = composeTextField.getText();
        if (!text.isEmpty()) {
            // Assuming the `Tweet` constructor takes a publisher, text, and other parameters
            Packet.getCurrentTweetNumber();
            Main.currentTweetNumber++;
            Tweet newComment = new Tweet(Main.logedInUserid, text, Main.currentTweetNumber, false, false);
            Packet.sendComment(newComment, tweet);  // Send the new comment to the server
            comments.add(newComment);  // Add the new comment to the local list
            composeTextField.setText("");
            showComments();  // Refresh the comments display
        }
    }
}
