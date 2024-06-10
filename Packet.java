import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

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

            outputStream.writeObject(packet);
            outputStream.flush();

            receiveFeedback(socket);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    static Packet receiveFeedback(Socket socket){
        Packet feedbackPacket=null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            feedbackPacket = (Packet) inputStream.readObject();
            handlePacket(feedbackPacket);
            Main.lastServerMessage = feedbackPacket.header;
            inputStream.close();
            Main.lastReceivedPacket = feedbackPacket;
            return feedbackPacket;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
         }
         return feedbackPacket;
    }

    static void sendNewUser(String username, String password, String name, String email, String phone, int age, String bio){
        User newUser = new User(username, password, name, email, phone, age, bio);
        sendPacket("newUser", newUser, Main.key);
    }
    static void sendtweet(String username,String text){
        Tweet tweet = new Tweet(username, text);
        sendPacket("newTweet", tweet, Main.key);
    }
    static void loginUser(String username, String password){
        LoginPacket data = new LoginPacket(username, password);
        sendPacket("loginUser", data, Main.key);
    }

    static void getAllTweets(){
        sendPacket("getAllTweets", null, Main.key);
    }

    static void getUserFollowings(String username){
        sendPacket("getUserFollowings", username, Main.key);
        
    }

    static void unfollowUser(String username,String targetUsername){
        DoubleUserName data = new DoubleUserName(username,targetUsername);
        sendPacket("unfollow", data, Main.key);
    }

    static void followUser(String username,String targetUsername){
        DoubleUserName data = new DoubleUserName(username,targetUsername);
        sendPacket("follow", data, Main.key);
    }
    @SuppressWarnings("unchecked")
    static void handlePacket(Packet packet){
        switch (packet.header) {
            case "userAddFailed":
                Main.lastServerMessage = (String) packet.data;
                System.out.println(packet.header);
                break;
            case "userAddSuccessfull":
                Main.lastServerMessage = (String) packet.data;
                System.out.println(packet.header);
                break;
            case "sentAllTweets":
                Main.allTweets = (List<Tweet>) packet.data;
                System.out.println(packet.header);
                break;
            case "sentUserFollowings":
                Main.currentUserFollowings = (HashSet<String>) packet.data;
                System.out.println("synced user followings with server");
                System.out.println(packet.header);
                break;
            case "followed":
                Main.currentUserFollowings = (HashSet<String>) packet.data;
                System.out.println(packet.header);
                break;
            case "unfollowed":
                Main.currentUserFollowings = (HashSet<String>) packet.data;
                System.out.println(packet.header);
                break;
            default:
                break;
        }
    }
    
}

class DoubleUserName implements Serializable {
    private static final long serialVersionUID = 1L;
    String username;
    String targetUsername;
    DoubleUserName(String username,String targetUsername){
        this.username = username;
        this.targetUsername = targetUsername;
    }
}

