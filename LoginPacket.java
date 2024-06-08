import java.io.Serializable;
public class LoginPacket implements Serializable {
    String username;
    String pass;
    LoginPacket(String username,String pass){
        this.username = username;
        this.pass = pass;
    }
}
