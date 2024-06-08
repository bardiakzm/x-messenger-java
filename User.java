import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    String username;
    String password;
    String name;
    String email;
    String phone;
    int age;
    String bio;

    public User(String username, String password, String name, String email, String phone, int age, String bio) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.bio = bio;
    }
}
