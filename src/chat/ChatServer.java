/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class ChatServer implements Runnable {

    public static ArrayList<ClientHandler> usersArray = new ArrayList<ClientHandler>();
    public static HashMap<String, Socket> usersHashmap = new HashMap<String, Socket>();

    public static void main(String[] args) throws IOException {

        int PORT = 4321;
        String IP = "localhost";

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
                System.out.println("Er videre efter accept...");
                System.out.println("Client connected from: " + SOCK.getLocalAddress().getHostName());

                ChatServerReturn CHAT = new ChatServerReturn(SOCK);
                Thread X = new Thread(CHAT);
                X.start();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {

    }
}
