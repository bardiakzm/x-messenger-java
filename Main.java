
import java.io.Serializable;


public class Main implements Serializable{
    final static String key = "uyaq2hAbK7tJXfqs";
    public static void main(String[] args) {
        Packet.sendPacket("header", "hi",key);
        // SwingUtilities.invokeLater(new Runnable() {
        //     public void run() {
        //         new LoginPage();
        //     }
        // });
    }
}
