import java.awt.*;
import java.util.Set;
import javax.swing.*;

public class LikedUsersPanel extends JPanel {
    private JPanel usersPanel;
    
    public LikedUsersPanel(Tweet tweet, UserPage userPage, StartPage parentFrame) {
        setLayout(new BorderLayout());

        // Top panel with return button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton returnButton = new JButton("Return");
        topPanel.add(returnButton);
        returnButton.addActionListener(e -> parentFrame.showUserPage(userPage));

        // Users display panel
        usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(usersPanel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Populate liked users
        showLikedUsers(tweet.likedUsers);
    }

    private void showLikedUsers(Set<String> likedUsers) {
        usersPanel.removeAll();
        for (String user : likedUsers) {
            JLabel userLabel = new JLabel(user);
            usersPanel.add(userLabel);
        }
        usersPanel.revalidate();
        usersPanel.repaint();
    }
}
