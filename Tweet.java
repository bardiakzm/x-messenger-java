import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


public class Tweet implements Serializable {
    private static final long serialVersionUID = 1L;
    String publisherid;
    String text;
    int likes;
    Date timestamp;
    int tweetNumber;
    HashSet<String> likedUsers;
    boolean saveProtected;
    boolean followingProtected;
    ArrayList<Tweet> comments;

    Tweet(String username, String text,int tweetNumber,boolean saveProtected,boolean followingProtected) {
        this.publisherid = username;
        this.text = text;
        this.likes = 0;
        this.timestamp = new Date(); // Set the current date and time
        this.tweetNumber = tweetNumber;
        this.likedUsers = new HashSet<>();
        this.saveProtected = saveProtected;
        this.followingProtected = followingProtected;
        this.comments = new ArrayList<Tweet>();
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getLikeCount(){
        likes = likedUsers.size();
        return likes;
    }

    public void addLikedUser(String username){
        likedUsers.add(username);
    }

    public void removeLikedUser(String username){
        likedUsers.remove(username);
    }
}
