package chat;

import java.net.Socket;

public class ClientHandler {

    Socket SOCK;
    String UserName;

    public ClientHandler(Socket SOCK, String UserName) {
        this.SOCK = SOCK;
        this.UserName = UserName;
    }

    public Socket getSOCK() {
        return SOCK;
    }

    public String getUserName() {
        return UserName;
    }

}
