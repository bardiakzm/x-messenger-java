import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;

public class SearchPanel extends JPanel {

    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton returnButton;
    private final JList<String> resultsList;
    private final StartPage parentFrame;
    private final UserPage userPage;

    public SearchPanel(StartPage parentFrame, UserPage userPage) {
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

        // Create the results list in the center
        resultsList = new JList<>(new DefaultListModel<>());
        resultsList.setCellRenderer(new UserCellRenderer());
        JScrollPane resultsScrollPane = new JScrollPane(resultsList);
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

        if (searchResults == null || searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No users found with the username: " + searchText, "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            DefaultListModel<String> resultsModel = new DefaultListModel<>();
            for (String username : searchResults) {
                resultsModel.addElement(username);
            }
            resultsList.setModel(resultsModel);
        }
    }

    // Custom cell renderer to display the user with profile button
    private class UserCellRenderer extends JPanel implements ListCellRenderer<String> {
        private final JLabel nameLabel = new JLabel();
        private final JButton profileButton = new JButton("Profile");

        public UserCellRenderer() {
            setLayout(new BorderLayout());
            add(nameLabel, BorderLayout.CENTER);
            add(profileButton, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            nameLabel.setText(value);

            // Remove previous action listeners to avoid duplication
            for (ActionListener al : profileButton.getActionListeners()) {
                profileButton.removeActionListener(al);
            }

            profileButton.addActionListener(e -> {
                System.out.println("Profile button clicked for user: " + value); // Debug statement
                parentFrame.showProfilePanel(value, parentFrame, userPage);
            });
            
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            return this;
        }
    }
}
