import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tweet implements Serializable {
    User publisher;
    String publisherid;
    String text;
    int likes;
    Date timestamp;

    Tweet(User publisher, String text) {
        this.publisher = publisher;
        this.publisherid = publisher.username;
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
