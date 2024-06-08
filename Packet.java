import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String connectionKey;
    String header = "enmpty header";
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

            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
