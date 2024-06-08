
import java.io.Serializable;
import javax.swing.SwingUtilities;


public class Main implements Serializable{
    final static String key = "uyaq2hAbK7tJXfqs";
    static Packet lastReceivedPacket = null;
    static String lastServerMessage = "no message yet";


    // public static Socket socket;
    // public static ObjectOutputStream outputStream;
    
    public static void main(String[] args) {
        // try {
        //     socket = new Socket("127.0.0.1", 8080); // Connect to the server
        //     outputStream = new ObjectOutputStream(socket.getOutputStream());
        //     // Other initialization code...
        // } catch (IOException e) {
        //     e.printStackTrace();
        //     System.exit(1); // Exit the program if the socket cannot be created
        // }

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
