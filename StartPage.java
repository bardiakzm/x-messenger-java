import java.io.Serializable;
import javax.swing.*;

public class StartPage extends JFrame implements Serializable {

    public StartPage() {
        // Set up the frame
        setTitle("Start page X");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add content to the window.
        add(new StartPanel(this));

        // Display the window.
        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public void showSignUpPanel() {
        setContentPane(new SignUpPanel(this));
        revalidate();
        repaint();
    }

    public void showLoginPanel() {
        setContentPane(new StartPanel(this));
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartPage());
    }
}
