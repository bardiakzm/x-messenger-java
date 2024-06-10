import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    String username;
    String password;
    String name;
    String email;
    String phone;
    int age;
    String bio;
    HashSet<String> followings;
    HashSet<String> followers;
    ArrayList<Tweet> savedTweets;

    public User(String username, String password, String name, String email, String phone, int age, String bio) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.bio = bio;
        this.followings = new HashSet<>();
        this.followers = new HashSet<>();
        this.savedTweets = new ArrayList<>();
    }

    // Add a following
    public void addFollowing(String username) {
        followings.add(username);
    }

    // Remove a following
    public void removeFollowing(String username) {
        followings.remove(username);
    }

    // Add a follower
    public void addFollower(String username) {
        followers.add(username);
    }

    // Remove a follower
    public void removeFollower(String username) {
        followers.remove(username);
    }

    // Get followings
    public Set<String> getFollowings() {
        return followings;
    }

    // Get followers
    public Set<String> getFollowers() {
        return followers;
    }
}
