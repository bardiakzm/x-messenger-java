import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.swing.*;

public class UserPage extends JPanel {
    static private JButton lastPressedParentButton;
    private String username;
    private List<Tweet> followingTweets;
    private List<Tweet> randomTweets;
    private List<Tweet> savedTweets;
    private Set<String> followedUsers;
    private StartPage startPage; // Reference to the StartPage

    private JPanel tweetPanel;
    private JTextField tweetTextField;

    private JButton lastTouchedButton; //

    public UserPage(String username, List<Tweet> followingTweets, List<Tweet> randomTweets,StartPage parentFrame) {
        setLayout(new BorderLayout());
        this.username = username;
        this.followingTweets = followingTweets;
        this.randomTweets = randomTweets;
        Packet.getSavedTweets(username);
        Packet.getUserFollowings(username);
        Packet.getFollowingTweets(username);
        this.followedUsers =  Main.currentUserFollowings;
        this.followingTweets = Main.currentUserFollowingTweets;
        this.savedTweets = Main.currentUserSavedTweets;
        // for (int i=0;i<followingTweets.size();i++){ {
        //     System.out.println("set number" + i);
        //     System.out.println(followingTweets.toArray()[i]);
        // }}
        setLayout(new BorderLayout());


        // Create top panel with buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JButton profileButton = new JButton("Profile");
        JButton savedTweetsButton = new JButton("Saved Tweets");
        JButton logOutButton = new JButton("Log out");
        topPanel.add(profileButton);
        topPanel.add(savedTweetsButton);
        profileButton.addActionListener(e -> parentFrame.showProfilePanel(username,parentFrame,this));

        // Create button panel
        // JPanel buttonPanel = new JPanel();
        JButton followingButton = new JButton("Following Tweets");
        JButton randomButton = new JButton("Random Tweets");
        topPanel.add(followingButton);
        topPanel.add(randomButton);
        topPanel.add(savedTweetsButton);
        topPanel.add(logOutButton,BorderLayout.EAST);
        logOutButton.addActionListener(e -> parentFrame.showStartPanel());

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

        showTweets("Random",randomButton,parentFrame);
        // Add action listeners
        followingButton.addActionListener(e -> showTweets("Following",followingButton,parentFrame));
        randomButton.addActionListener(e -> showTweets("Random",randomButton,parentFrame));
        savedTweetsButton.addActionListener(e -> showTweets("Saved",savedTweetsButton,parentFrame));
        composeButton.addActionListener(e -> composeTweet(tweetTextField.getText(),composeButton));
    }

    public void setStartPage(StartPage startPage) {
        this.startPage = startPage;
    }
    private void showTweets(String tweetType, JButton parentButton,StartPage parentFrame) {
        lastTouchedButton = parentButton;
        List<Tweet> tweets;
        lastPressedParentButton = parentButton;
    switch (tweetType) {
        case "Following" -> {
            Packet.getAllTweets();
            Packet.getFollowingTweets(username);
            Packet.getSavedTweets(username);
            tweets = Main.currentUserFollowingTweets;
            followedUsers = Main.currentUserFollowings;
            }
        case "Random" -> {
            Packet.getAllTweets();
            Packet.getFollowingTweets(username);
            Packet.getSavedTweets(username);
            tweets = Main.allTweets;
            followedUsers = Main.currentUserFollowings;
            }
        case "Saved" -> {
            Packet.getAllTweets();
            Packet.getFollowingTweets(username);
            Packet.getSavedTweets(username);
            tweets = Main.currentUserSavedTweets;
            followedUsers = Main.currentUserFollowings;
            }
        default -> tweets = new ArrayList<>();
    }
        // Sort tweets by timestamp
        Collections.sort(tweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet t1, Tweet t2) {
                return t2.getTimestamp().compareTo(t1.getTimestamp());
            }
        });

        tweetPanel.removeAll();
        for (Tweet tweeti : tweets) {
            final Tweet tweet = getTweetFromList(tweeti, randomTweets);//get the updated tweet from all tweets list
            Packet.getUser(tweet.publisherid);
            final User tweetPublisher=Packet.getLastReceivedUser();
            JPanel tweetContainer = new JPanel(new BorderLayout());
            JPanel profileButtonPanel = new JPanel();
            JButton targetUserProfileButton = new JButton("Profile");
            profileButtonPanel.add(targetUserProfileButton);
            targetUserProfileButton.addActionListener(e -> parentFrame.showProfilePanel(tweet.publisherid,parentFrame,this));
            JButton followButton = new JButton(followedUsers.contains(tweet.publisherid) ? "Unfollow" : "Follow");
            profileButtonPanel.add(followButton);

            JLabel tweetLabel = new JLabel( tweetPublisher.name+" || @"+ tweet.publisherid + ": " + tweet.text + " (Likes: " + tweet.getLikeCount() + ") " + tweet.getFormattedTimestamp());
            JPanel buttonPanel = new JPanel();  
            boolean deleteExists = doesUserOwnTweet(tweet, username);
            JButton saveButton = new JButton(doesListContainTweet(tweet, savedTweets) ? "unSave" : "Save");
            JButton likeButton = new JButton(tweet.likedUsers.contains(username) ? "unLike" : "Like");
            JButton commentButton = new JButton("Comments");
            buttonPanel.add(commentButton);
            buttonPanel.add(likeButton);
            buttonPanel.add(saveButton);
            if(deleteExists){
                JButton deleteButton = new JButton("Delete");
                buttonPanel.add(deleteButton);
                deleteButton.addActionListener(e -> deleteTweet(tweet,deleteButton,parentButton));
            }
            tweetContainer.add(profileButtonPanel, BorderLayout.WEST);
            tweetContainer.add(tweetLabel, BorderLayout.CENTER);
            tweetContainer.add(buttonPanel, BorderLayout.EAST);
            tweetPanel.add(tweetContainer);

            // Add action listeners for save and like buttons
            saveButton.addActionListener(e -> saveTweet(tweet,saveButton,parentButton));
            likeButton.addActionListener(e -> likeTweet(tweet,likeButton,parentButton));

            // Add action listener for follow/unfollow button
            followButton.addActionListener(e -> {
                if (followedUsers.contains(tweet.publisherid)) {
                    Packet.unfollowUser(username, tweet.publisherid);
                    followButton.setText("Follow");
                    parentButton.doClick();
                } else {
                    Packet.followUser(username, tweet.publisherid);
                    followButton.setText("Unfollow");
                    parentButton.doClick();
                }
            });
        }
        tweetPanel.revalidate();
        tweetPanel.repaint();
    }

    private void saveTweet(Tweet tweet,JButton saveButton,JButton parentButton) {
        if(doesListContainTweet(tweet, savedTweets)){
            Packet.unSaveTweet(username, tweet);
            saveButton.setText("Save");
        }
        else{
            Packet.saveTweet(username, tweet);
            saveButton.setText("unSave");
        }
        updateTweetLists();
        parentButton.doClick();
    }

    private void likeTweet(Tweet tweet,JButton likeButton,JButton parentButton) {
        if(tweet.likedUsers.contains(username)){
            Packet.removeLike(tweet, username);
            likeButton.setText("Like");
        }
        else{
            Packet.likeTweet(tweet, username);
            likeButton.setText("unLike");
        }
        updateTweetLists();
        parentButton.doClick();
    }

    private void composeTweet(String text,JButton parentButton) {
        Packet.sendtweet(username, text);
        updateTweetLists();
        lastTouchedButton.doClick();
        System.out.println("Composed Tweet: " + text);
    }

    private void updateTweetLists(){
        Packet.getSavedTweets(username);
        Packet.getAllTweets();
        Packet.getSavedTweets(username);
        this.savedTweets = Main.currentUserSavedTweets;
        this.savedTweets = Main.currentUserSavedTweets;
        this.randomTweets = Main.allTweets;
    }

    public boolean doesListContainTweet(Tweet tweet,List<Tweet> list){
        for (int i=0;i<list.size();i++){ 
            if(list.get(i).tweetNumber == tweet.tweetNumber){
                return true;
            }
        }
        return false;
    }

    private Tweet getTweetFromList(Tweet tweet,List<Tweet> list){
        for (int i=0;i<list.size();i++){
            if(list.get(i).tweetNumber == tweet.tweetNumber){
                return list.get(i);
            }
        }
        return null;
    }

    private void deleteTweet(Tweet tweet,JButton deleteButton,JButton parentButton) {
        Packet.deleteTweet(tweet);
        parentButton.doClick();
    }

    static boolean doesUserOwnTweet(Tweet tweet,String username){
        return tweet.publisherid.equals(username);
    }

    static void pressLastPressedParentButton(){
        lastPressedParentButton.doClick();
    }
}