import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.*;
import java.util.List;

public class SearchPanel extends JPanel implements Serializable {

    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton returnButton;
    private final JPanel resultsPanel;
    private final StartPage parentFrame;
    private final UserPage userPage;

    public SearchPanel(StartPage parentFrame, UserPage userPage) {
        Packet.updateLoggedInUser();
        this.parentFrame = parentFrame;
        this.userPage = userPage;

        setLayout(new BorderLayout());

        // Create the search panel at the top
        JPanel searchPanel = new JPanel(new FlowLayout());
        returnButton = new JButton("Return");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(returnButton);
        returnButton.addActionListener(e -> parentFrame.showUserPage(userPage));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Create the results panel in the center
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);
        add(resultsScrollPane, BorderLayout.CENTER);

        // Add action listener to the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username to search for.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Fetch search results from the server
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
}