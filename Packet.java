import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String connectionKey;
    String header = "empty header";
    Object data = "empty data";
    Packet(String header,Object data,String key){
        this.header = header;
        this.data = data;
        this.connectionKey = key;
    }

    static void sendPacket(String header,Object data,String key){
        final String serverip = "127.0.0.1"; 
        final int serverPort = 8080; 
        Packet packet = new Packet(header, data, key);
        try {
            Socket socket = new Socket(serverip, serverPort);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            // ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeObject(packet);
            outputStream.flush();

            // Packet feedbackPacket = (Packet) inputStream.readObject();
            // handlePacket(feedbackPacket);
            receiveFeedback(socket);
            outputStream.close();
            // inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } //catch (ClassNotFoundException ex) {
        // }
    }
    static Packet receiveFeedback(Socket socket){
        final String serverip = "127.0.0.1"; 
        final int serverPort = 8080;
        Packet feedbackPacket=null;
        try {
            // Socket socket = new Socket(serverip, serverPort);
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());


            feedbackPacket = (Packet) inputStream.readObject();
            handlePacket(feedbackPacket);
            Main.lastServerMessage = feedbackPacket.header;
            inputStream.close();
            // socket.close();
            Main.lastServerMessage = (String) feedbackPacket.data;
            Main.lastReceivedPacket = feedbackPacket;
            return feedbackPacket;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
         }
         return feedbackPacket;
    }

    // static Packet receiveFeedback() {
    //     final String serverip = "127.0.0.1";
    //     final int serverPort = 8080;
    //     Packet feedbackPacket = null;

    //     try (Socket socket = new Socket(serverip, serverPort);
    //          BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

    //         String line;
    //         StringBuilder response = new StringBuilder();
    //         while ((line = reader.readLine()) != null) {
    //             response.append(line);
    //         }

    //         // Assuming your feedback is sent as a string, you can process it accordingly
    //         String feedbackData = response.toString();
    //         feedbackPacket = new Packet("feedback", feedbackData, "");
    //         Main.lastServerMessage = feedbackData;

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return feedbackPacket;
    // }

    static void sendNewUser(String username, String password, String name, String email, String phone, int age, String bio){
        User newUser = new User(username, password, name, email, phone, age, bio);
        sendPacket("newUser", newUser, Main.key);
    }
    static void loginUser(String username, String password){
        LoginPacket data = new LoginPacket(username, password);
        sendPacket("loginUser", data, Main.key);
    }
    static void handlePacket(Packet packet){
        switch (packet.header) {
            case "userAddFailed":
                System.out.println(packet.header);
                break;
            case "userAddSuccessfull":
                System.out.println(packet.header);
                break;
        
            default:
                break;
        }
    }
    
}


