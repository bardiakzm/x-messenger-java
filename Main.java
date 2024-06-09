
import java.io.Serializable;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


public class Main implements Serializable{
    final static String key = "uyaq2hAbK7tJXfqs";
    static Packet lastReceivedPacket = null;
    static String lastServerMessage = "no message yet";
    static List<Tweet> allTweets;
    static Set<String> currentUserFollowings;

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
