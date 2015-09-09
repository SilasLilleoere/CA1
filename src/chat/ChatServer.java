/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Silas
 */
public class ChatServer {

    public static ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();
    public static ArrayList<String> CurrentUsers = new ArrayList<String>();

/////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws IOException {

        int PORT = 4321;
        String IP = "localhost";
        
        if(args.length==2){
            System.out.println("Args found.");
            IP = args[0];
            PORT = Integer.parseInt(args[1]);
        }
        
        ServerSocket SERVER = new ServerSocket();
        SERVER.bind(new InetSocketAddress(IP, PORT));
        
        
        try {
//            final int PORT = 4321;
//            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("Waaaaaaaaaaaiitin' brahs... No users yet.");

            while (true) {
                Socket SOCK = SERVER.accept();
                ConnectionArray.add(SOCK);
                addUserName(SOCK);

                System.out.println("Client connected from: " + SOCK.getLocalAddress().getHostName());

                ChatServerReturn CHAT = new ChatServerReturn(SOCK);
                Thread X = new Thread(CHAT);
                X.start();

            }

        } catch (Exception e) {
        }

    }

    public static void addUserName(Socket X) throws IOException {

        Scanner INPUT = new Scanner(X.getInputStream());
        String USERNAME = INPUT.nextLine();
        CurrentUsers.add(USERNAME);

        for (int i = 1; i <= ChatServer.ConnectionArray.size(); i++) {
            Socket TEMPSOCK = ChatServer.ConnectionArray.get(i - 1);
            PrintWriter OUT = new PrintWriter(TEMPSOCK.getOutputStream());
            OUT.println("#!?" + CurrentUsers);
            OUT.flush();
        }
    }
}
