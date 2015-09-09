/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import Interface.ChatInterface;
import static chat.ChatServer.usersArray;
import date.DateTime;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author Silas
 */
public class ChatServerReturn implements Runnable, ChatInterface {

    String username;
    ArrayList<String> tempArray = new ArrayList<String>();
    DateTime dt = new DateTime();
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";
//------------------------------------------------------------

    public ChatServerReturn(Socket X, String username) {
        this.SOCK = X;
        this.username = username;
        UserList();
    }

//  Check for dead connections ------------------------------------------------------------
    public void checkConnection() throws IOException {
        if (!SOCK.isConnected()) {
            for (int i = 1; i <= ChatServer.usersArray.size(); i++) {
                if (ChatServer.usersArray.get(i).getSOCK() == SOCK) {
                    ChatServer.usersArray.remove(i);
                }
            }
            for (int i = 1; i <= ChatServer.usersArray.size(); i++) {

                Socket TEMPSOCK = ChatServer.usersArray.get(i - 1).getSOCK();
                PrintWriter TEMPOUT = new PrintWriter(TEMPSOCK.getOutputStream());
                TEMPOUT.println(TEMPSOCK.getLocalAddress().getHostName() + " disconnected!");
                TEMPOUT.flush();

                System.out.println(TEMPSOCK.getLocalAddress().getHostName() + " disconnected!");
            }
            UserList();
        }

    }

//------------------------------------------------------------
    @Override
    public void run() {

        try {
            try {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());

                while (true) {

                    checkConnection();

                    if (!INPUT.hasNext()) {
                        return;
                    }

                    MESSAGE = INPUT.nextLine();

                    System.out.println("Client said: " + MESSAGE);

                    //--
                }
            } finally {
                SOCK.close();
            }
        } catch (Exception X) {
            System.out.print(X);
        }
    }
//------------------------------------------------------------

    @Override
    public void Stop(String msg) {
        try {
            SOCK.close();
        } catch (IOException ex) {
            System.out.println("Stop failed" + ex);
        }
    }

    @Override
    public void User(String msg) {
        Scanner INPUT;
        try {
            INPUT = new Scanner(SOCK.getInputStream());
            String USERNAME = INPUT.nextLine();
            OUT.println("USER#" + USERNAME);
        } catch (IOException ex) {
            System.out.println("User failed" + ex);
        }

    }

    //Ikke færdig! 
    @Override
    public void msgServer(ArrayList receivers, String msg) {

        tempArray = receivers;

        //Message is sent to spicifik people.
        if (!receivers.isEmpty() || receivers != null) {
            try {
                for (int i = 1; i < receivers.size(); i++) {
                    Socket TEMPSOCK = ChatServer.usersHashmap.get(receivers.get(i - 1));
                    PrintWriter TEMPOUT = new PrintWriter(TEMPSOCK.getOutputStream());
                    TEMPOUT.println(username + ": \t" + MESSAGE);
                    TEMPOUT.flush();
                    System.out.println("Message sent to: " + TEMPSOCK.getLocalAddress().getHostName());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } //Message sent to all.
        else {

            try {
                for (int i = 1; i <= ChatServer.usersArray.size(); i++) {

                    Socket TEMPSOCK = ChatServer.usersArray.get(i - 1).getSOCK();
                    PrintWriter TEMPOUT = new PrintWriter(TEMPSOCK.getOutputStream());
                    TEMPOUT.println("");
                    TEMPOUT.println(dt.timeStamp());
                    TEMPOUT.println(username + ": \t" + MESSAGE);
                    TEMPOUT.flush();

                    System.out.println("Message sent to: " + TEMPSOCK.getLocalAddress().getHostName());

                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void UserList() {

        try {
            for (int i = 1; i <= ChatServer.usersArray.size(); i++) {
                Socket TEMPSOCK = ChatServer.usersArray.get(i - 1).getSOCK();
                PrintWriter OUT = new PrintWriter(TEMPSOCK.getOutputStream());
                OUT.println("USERLIST#" + usersArray);
                OUT.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void msgClient(String msg) {

    }
}
