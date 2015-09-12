package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
import utils.Utils;

public class ChatServer {

    public static ArrayList<ClientHandler> usersArray = new ArrayList<ClientHandler>();
    public static HashMap<String, Socket> usersHashmap = new HashMap<String, Socket>();
    private static final Properties properties = Utils.initProperties("server.properties");

    public static void main(String[] args) throws IOException {
	String logFile = properties.getProperty("logFile");
	Utils.setLogFile(logFile,ChatServer.class.getName());

        int PORT = 4321;
        String IP = "191.238.151";

        if (args.length == 2) {
            System.out.println("Args found.");
            IP = args[0];
            PORT = Integer.parseInt(args[1]);
        }

        ServerSocket SERVER = new ServerSocket();
        SERVER.bind(new InetSocketAddress(IP, PORT));

        try {

            System.out.println("Waiting for a client...");

            while (true) {

                Socket SOCK = SERVER.accept();
                System.out.println("Client connected from: " + SOCK.getLocalAddress().getHostName());

                ChatServerReturn CHAT = new ChatServerReturn(SOCK);
                Thread X = new Thread(CHAT);
                X.start();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
