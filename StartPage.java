import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.Serializable;
import javax.swing.*;

public class StartPage extends JFrame implements Serializable {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public StartPage() {
        // Set up the frame
        setTitle("Start page X");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add content to the window
        mainPanel.add(new StartPanel(this), "StartPanel");
        mainPanel.add(new LoginPanel(this), "LoginPanel");
        mainPanel.add(new SignUpPanel(this), "SignUpPanel");
        add(mainPanel);

        // Display the window
        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public void showStartPanel() {
        cardLayout.show(mainPanel, "StartPanel");
    }

    public void showSignUpPanel() {
        cardLayout.show(mainPanel, "SignUpPanel");
    }

    public void showLoginPanel() {
        cardLayout.show(mainPanel, "LoginPanel");
    }

    public void showUserPage(UserPage userPage) {
        setPreferredSize(new Dimension(1200, 800));
        pack();
        setLocationRelativeTo(null); // Center the window
        userPage.pressLastPressedParentButton();
        mainPanel.add(userPage, "UserPage");
        cardLayout.show(mainPanel, "UserPage");
    }

    public void showProfilePanel(String username,StartPage parentFrame,UserPage userPage) {
        ProfilePanel profilePanel = new ProfilePanel(username,parentFrame,userPage);
        mainPanel.add(profilePanel, "ProfilePanel");
        cardLayout.show(mainPanel, "ProfilePanel");
        revalidate();
        repaint();
    }

    public void showSearchPanel(StartPage parentFrame,UserPage userPage) {
        SearchPanel searchPanel = new SearchPanel(parentFrame,userPage);
        mainPanel.add(searchPanel, "SearchPanel");
        cardLayout.show(mainPanel, "SearchPanel");
        revalidate();
        repaint();
    }

    public void showCommentPanel(Tweet tweet,UserPage userPage,StartPage parentFrame) {
        CommentPanel commentPanel = new CommentPanel(tweet, userPage,this);
        mainPanel.add(commentPanel, "CommentPanel");
        cardLayout.show(mainPanel, "CommentPanel");
        revalidate();
        repaint();
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartPage::new);
    }
}
