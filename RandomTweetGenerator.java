import java.util.ArrayList;
import java.util.List;

public class RandomTweetGenerator {

    public static List<Tweet> generateRandomTweets() {
        List<Tweet> randomTweets = new ArrayList<>();

        // Sample users
        User user1 = new User("user1", "password1", "John", "john@example.com", "123456789", 30, "Bio of user1");
        User user2 = new User("user2", "password2", "Alice", "alice@example.com", "987654321", 25, "Bio of user2");
        User user3 = new User("user3", "password3", "Bob", "bob@example.com", "456789123", 35, "Bio of user3");

        String[] randomTweetTexts = {
            "I'm feeling productive today!",
            "Just had a wonderful conversation with an old friend.",
            "Exploring new music genres this weekend.",
            "Looking forward to trying out a new recipe for dinner.",
            "Finished reading an inspiring book. Time for some reflection.",
            "Feeling grateful for the little things in life.",
            "Starting a new project today. Excited to see where it goes!",
            "Had a great workout session at the gym.",
            "Spending the day outdoors, enjoying nature.",
            "Planning a spontaneous road trip with friends."
        };

        for (int i = 0; i < randomTweetTexts.length; i++) {
            // Pick a random user
            User randomUser = getRandomUser(i, user1, user2, user3);
            Tweet tweet = new Tweet(randomUser, randomTweetTexts[i]);
            randomTweets.add(tweet);
        }

        return randomTweets;
    }

    public static List<Tweet> generateRandomTweets2() {
        List<Tweet> randomTweets = new ArrayList<>();

        // Sample users
        User user4 = new User("user4", "password4", "Emma", "emma@example.com", "1122334455", 28, "Bio of user4");
        User user5 = new User("user5", "password5", "Michael", "michael@example.com", "9988776655", 40, "Bio of user5");
        User user6 = new User("user6", "password6", "Sophia", "sophia@example.com", "5566778899", 22, "Bio of user6");

        String[] randomTweetTexts = {
            "Studying for exams. Wish me luck!",
            "Celebrating a milestone achievement today.",
            "Spent the day volunteering at a local charity.",
            "Trying out a new hobby. It's never too late to learn something new!",
            "Feeling inspired after attending a motivational seminar.",
            "Enjoying a quiet evening at home with a good book.",
            "Reflecting on the past year and setting goals for the future.",
            "Feeling nostalgic listening to old music playlists.",
            "Grateful for the support of friends and family.",
            "Taking a break from technology and spending time in nature."
        };

        for (int i = 0; i < randomTweetTexts.length; i++) {
            // Pick a random user
            User randomUser = getRandomUser(i, user4, user5, user6);
            Tweet tweet = new Tweet(randomUser, randomTweetTexts[i]);
            randomTweets.add(tweet);
        }

        return randomTweets;
    }

    private static User getRandomUser(int index, User... users) {
        int randomIndex = index % users.length;
        return users[randomIndex];
    }
}
