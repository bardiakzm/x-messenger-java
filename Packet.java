import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

public class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    
    static private User lastReceivedUser = null;
    String connectionKey;
    String header = "empty header";
    Object data = "empty data";
    static List<String> lastSearchedUsersList;
    static private List<String> lastReceivedSearchHistory;
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
        sendPacket("newUser", newUser, Main.getKey());
    }
    static void sendtweet(String username,String text,boolean saveProtected,boolean followingProtected){
        getCurrentTweetNumber();
        Main.currentTweetNumber++;
        Tweet tweet = new Tweet(username, text,Main.currentTweetNumber,saveProtected,followingProtected);
        sendPacket("newTweet", tweet, Main.getKey());
    }

    static void sendComment(Tweet comment,Tweet tweet){
        CommentPack data = new CommentPack(comment,tweet);
        sendPacket("newComment", data, Main.getKey());
    }
    static void loginUser(String username, String password){
        LoginPacket data = new LoginPacket(username, password);
        sendPacket("loginUser", data, Main.getKey());
    }

    static void getAllTweets(){
        sendPacket("getAllTweets", null, Main.getKey());
    }

    static void getFollowingTweets(String username){
        sendPacket("getFollowingTweets", username, Main.getKey());
    }

    static void getUserFollowings(String username){
        sendPacket("getUserFollowings", username, Main.getKey());
        
    }

    static void getCurrentTweetNumber(){
        sendPacket("getCurrentTweetNumber", null, Main.getKey());
    }

    static void getUser(String username){
        sendPacket("getUser", username, Main.getKey());
    }

    static User getLastReceivedUser(){
        return lastReceivedUser;
    }

    static void updateLoggedInUser(){
        getUser(Main.logedInUserid);
        Main.logedInUser = lastReceivedUser;
    }

    static void unfollowUser(String username,String targetUsername){
        DoubleUserName data = new DoubleUserName(username,targetUsername);
        sendPacket("unfollow", data, Main.getKey());
    }

    static void followUser(String username,String targetUsername){
        DoubleUserName data = new DoubleUserName(username,targetUsername);
        sendPacket("follow", data, Main.getKey());
    }

    static void saveTweet(String saverUsername,Tweet tweet){
        SaveTweetPack data = new SaveTweetPack(saverUsername, tweet);
        sendPacket("saveTweet", data, Main.getKey());
    }

    static void unSaveTweet(String saverUsername,Tweet tweet){
        SaveTweetPack data = new SaveTweetPack(saverUsername, tweet);
        sendPacket("unSaveTweet", data, Main.getKey());
    }

    static void likeTweet(Tweet tweet,String likerUsername){
        SaveTweetPack data = new SaveTweetPack(likerUsername, tweet);
        sendPacket("like", data, Main.getKey());
        System.out.println("sent like request");
    }

    static void removeLike(Tweet tweet,String likerUsername){
        SaveTweetPack data = new SaveTweetPack(likerUsername, tweet);
        sendPacket("removeLike", data, Main.getKey());
        System.out.println("sent remove like request");
    }

    static void getSavedTweets(String username){
        sendPacket("getSavedTweets", username, Main.getKey());
    }
    
    static void deleteTweet(Tweet tweet){
        sendPacket("deleteTweet", tweet, Main.getKey());
    }

    static void searchUsers(String searchText){
        SearchPack data = new SearchPack(searchText,Main.logedInUserid);
        sendPacket("searchUsers", data, Main.getKey());
    }

    static List<String> getLastSearchedUsersList(){
        return lastSearchedUsersList;
    }

    static void getSearchHistory(String searcher){
        sendPacket("getSearchHistory",searcher,Main.getKey());
    }

    static List<String> getLastReceivedSearchHistory(){
        return lastReceivedSearchHistory;
    }

    @SuppressWarnings("unchecked")
    static void handlePacket(Packet packet){
        switch (packet.header) {
            case "userAddFailed" -> {
                Main.lastServerMessage = (String) packet.data;
                System.out.println(packet.header);
            }
            case "userAddSuccessfull" -> {
                Main.lastServerMessage = (String) packet.data;
                System.out.println(packet.header);
            }
            case "sentAllTweets" -> {
                Main.allTweets = (List<Tweet>) packet.data;
                System.out.println(packet.header);
            }
            case "sentUserFollowings" -> {
                Main.currentUserFollowings = (HashSet<String>) packet.data;
                System.out.println("synced user followings with server");
                System.out.println(packet.header);
            }
            case "followed" -> {
                Main.currentUserFollowings = (HashSet<String>) packet.data;
                System.out.println(packet.header);
            }
            case "unfollowed" -> {
                Main.currentUserFollowings = (HashSet<String>) packet.data;
                System.out.println(packet.header);
            }
            case "tweeted" -> {
                Main.lastServerMessage = (String) packet.data;
                System.out.println(packet.header);
            }
            case "sentFollowingTweets" -> {
                Main.currentUserFollowingTweets = (List<Tweet>) packet.data;
                Main.lastServerMessage = (String) packet.header;
                System.out.println(packet.header);
            }
            case "sentCurrentTweetNumber" -> {
                Main.currentTweetNumber= (int) packet.data;
                Main.lastServerMessage = (String) packet.header;
                System.out.println(packet.header);
            }
            case "savedTweet" -> {
                Main.lastServerMessage = (String) packet.data;
                System.out.println(Main.lastServerMessage);
            }
            case "unSavedTweet" -> {
                Main.lastServerMessage = (String) packet.data;
                System.out.println(Main.lastServerMessage);
            }
            case "sentSavedTweets" -> {
                Main.lastServerMessage = (String) packet.header;
                Main.currentUserSavedTweets = (List<Tweet>) packet.data;
                System.out.println(Main.lastServerMessage);
            }
            case "liked" -> {
                Main.lastServerMessage = (String) packet.data;
                System.out.println(Main.lastServerMessage);
            }
            case "removedLike" -> {
                Main.lastServerMessage = (String) packet.data;
                System.out.println(Main.lastServerMessage);
            }
            case "deletedTweet" -> {
                Main.lastServerMessage = (String) packet.data;
                System.out.println(Main.lastServerMessage);
            }
            case "sentUser" -> {
                lastReceivedUser = (User)packet.data;
                Main.lastServerMessage = (String) packet.header;
                System.out.println(Main.lastServerMessage);
            }
            case "sentSearchedUsersList" -> {
                lastSearchedUsersList = (List<String>)packet.data;
                Main.lastServerMessage = (String) packet.header;
                System.out.println(Main.lastServerMessage);
            }
            case "commented" -> {
                Main.lastServerMessage = (String) packet.data;
                System.out.println(Main.lastServerMessage);
            }
            case "sentSearchHistroy" -> {
                lastReceivedSearchHistory = (List<String>) packet.data;
                Main.lastServerMessage = (String) packet.header;
                System.out.println(Main.lastServerMessage);
            }
            default -> {
            }
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

class SaveTweetPack implements Serializable {
    private static final long serialVersionUID = 1L;
    String saverUserame;
    Tweet tweet;
    SaveTweetPack(String saverUserame,Tweet tweet){
        this.saverUserame = saverUserame;
        this.tweet = tweet;
    }
}

class CommentPack implements Serializable {
    private static final long serialVersionUID = 1L;
    Tweet tweet;
    Tweet comment;
    CommentPack(Tweet comment,Tweet tweet){
        this.tweet = tweet;
        this.comment = comment;
    }
}

class SearchPack implements Serializable {
    private static final long serialVersionUID = 1L;
    String text;
    String searcher;
    SearchPack(String text,String searcher){
        this.searcher = searcher;
        this.text = text;
    }
}


