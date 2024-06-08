import java.io.Serializable;

public class Tweet implements Serializable {
    User publisher;
    String publisherid;
    String text;
    int likes;
    Tweet(User publisher, String text) {
        this.publisher = publisher;
        this.publisherid = publisher.username;
        this.text = text;
        this.likes = 0;
    }    
}
