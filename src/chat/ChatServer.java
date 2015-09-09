/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import Interface.ChatInterface;
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
                ConnectionArray.add(SOCK);

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
