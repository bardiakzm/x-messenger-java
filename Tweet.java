import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tweet implements Serializable {
    // User publisher;
    String username;
    // String publisherid;
    String text;
    int likes;
    Date timestamp;

    Tweet(String username, String text) {
        this.username = username;
        // this.publisherid = publisher.username;
        this.text = text;
        this.likes = 0;
        this.timestamp = new Date(); // Set the current date and time
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
