
import java.io.Serializable;
import javax.swing.SwingUtilities;


public class Main implements Serializable{
    final static String key = "uyaq2hAbK7tJXfqs";
    static String lastServerMessage = "no message yet";
    public static void main(String[] args) {
        // Packet.sendPacket("header", "hi",key);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartPage();
            }
        });
    }
}
