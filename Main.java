
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


public class Main implements Serializable{
    final static String key = "uyaq2hAbK7tJXfqs";
    static Packet lastReceivedPacket = null;
    static String lastServerMessage = "no message yet";
    static List<Tweet> allTweets;
    static List<Tweet> currentUserFollowingTweets;
    static Set<String> currentUserFollowings = new HashSet<String>();
    static int currentTweetNumber = 0;

    public static void main(String[] args) {

        Packet.sendPacket("clientHello", "connected successfully",key);
        // Packet.receiveFeedback();
        Packet.sendPacket("startPage", "entered startpage",key);
        // Packet.receiveFeedback();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartPage();
            }
        });
    }
}
