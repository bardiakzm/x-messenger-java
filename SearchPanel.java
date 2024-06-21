import java.awt.*;
import java.io.Serializable;
import java.util.List;
import javax.swing.*;

public class SearchPanel extends JPanel implements Serializable {

    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton returnButton;
    private final JButton historyButton;
    private final JPanel resultsPanel;
    private final StartPage parentFrame;
    private final UserPage userPage;
    private static List<String> searchHistory;

    public SearchPanel(StartPage parentFrame, UserPage userPage) {
        Packet.updateLoggedInUser();
        this.parentFrame = parentFrame;
        this.userPage = userPage;

        setLayout(new BorderLayout());
        JPanel searchPanel = new JPanel(new BorderLayout()); // create the search panel at the top
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));//create left panel containing return button and search field
        returnButton = new JButton("Return");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        leftPanel.add(returnButton);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);

        // Right panel containing history button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        historyButton = new JButton("History");
        rightPanel.add(historyButton);

        // Add left and right panels to the search panel
        searchPanel.add(leftPanel, BorderLayout.WEST);
        searchPanel.add(rightPanel, BorderLayout.EAST);
        
        returnButton.addActionListener(e -> parentFrame.showUserPage(userPage));
        historyButton.addActionListener(e -> showHistory());
        searchButton.addActionListener(e -> performSearch());

        add(searchPanel, BorderLayout.NORTH);

        // Create the results panel in the center
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);
        add(resultsScrollPane, BorderLayout.CENTER);
    }

    private void performSearch() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username to search for.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Packet.searchUsers(searchText);
        List<String> searchResults = Packet.getLastSearchedUsersList();

        resultsPanel.removeAll();

        if (searchResults == null || searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No users found with the username: " + searchText, "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (String username : searchResults) {
                resultsPanel.add(createUserPanel(username));
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel createUserPanel(String username) {
        JPanel userPanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel(username);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton profileButton = new JButton("Profile");
        JButton followButton = new JButton(isFollowing(username) ? "Unfollow" : "Follow");

        profileButton.addActionListener(e -> {
            System.out.println("Profile button clicked for user: " + username);
            parentFrame.showProfilePanel(username, parentFrame, userPage);
        });

        followButton.addActionListener(e -> {
            if (isFollowing(username)) {
                unfollowUser(username);
            } else {
                followUser(username);
            }
            followButton.setText(isFollowing(username) ? "Unfollow" : "Follow");
        });

        buttonPanel.add(profileButton);
        buttonPanel.add(followButton);

        userPanel.add(nameLabel, BorderLayout.CENTER);
        userPanel.add(buttonPanel, BorderLayout.EAST);

        return userPanel;
    }

    private boolean isFollowing(String username) {
        // Check if the logged-in user is following the given username
        return Main.logedInUser.followings.contains(username);
    }

    private void followUser(String username) {
        Packet.followUser(Main.logedInUserid, username);
        JOptionPane.showMessageDialog(this, "You are now following " + username, "Follow Successful", JOptionPane.INFORMATION_MESSAGE);
        Packet.updateLoggedInUser();
    }

    private void unfollowUser(String username) {
        Packet.unfollowUser(Main.logedInUserid, username);
        JOptionPane.showMessageDialog(this, "You have unfollowed " + username, "Unfollow Successful", JOptionPane.INFORMATION_MESSAGE);
        Packet.updateLoggedInUser();
    }

    private void showHistory() {
        Packet.getSearchHistory(Main.logedInUserid);
        searchHistory = Packet.getLastReceivedSearchHistory();
        
        if (searchHistory == null || searchHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No search history available.", "Search History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JDialog historyDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Search History", true);
            historyDialog.setSize(300, 200);
            historyDialog.setLocationRelativeTo(this);
            
            JPanel historyPanel = new JPanel();
            historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
            
            for (String historyItem : searchHistory) {
                JLabel historyLabel = new JLabel(historyItem);
                historyPanel.add(historyLabel);
            }
            
            JScrollPane historyScrollPane = new JScrollPane(historyPanel);
            historyDialog.add(historyScrollPane);
            historyDialog.setVisible(true);
        }
    }
}
