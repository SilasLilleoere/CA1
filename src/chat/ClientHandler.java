package chat;

import java.net.Socket;

public class ClientHandler {

    Socket SOCK;
    String userName;

    public ClientHandler(Socket SOCK, String userName) {
        this.SOCK = SOCK;
        this.userName = userName;

    }

    public Socket getSOCK() {
        return SOCK;
    }

    public String getUserName() {
        return userName;
    }

}
