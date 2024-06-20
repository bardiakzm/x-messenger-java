
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


public class Main implements Serializable{
    static final private String KEY = "uyaq2hAbK7tJXfqs";
    static Packet lastReceivedPacket = null;
    static String lastServerMessage = "no message yet";
    static List<Tweet> allTweets;
    static List<Tweet> currentUserFollowingTweets;
    static Set<String> currentUserFollowings = new HashSet<String>();
    static List<Tweet> currentUserSavedTweets;
    static int currentTweetNumber = 0;
    static String logedInUserid = null;
    static User logedInUser = null;

    public static void main(String[] args) {

        Packet.sendPacket("clientHello", "connected successfully",getKey());

        SwingUtilities.invokeLater(StartPage::new);
    }

    public static String getKey() {
        return KEY;
    }
}
