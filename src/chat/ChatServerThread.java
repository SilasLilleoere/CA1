/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import Interface.ServerInterface;
import Shared.Protocols;
import date.DateTime;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class ChatServerThread implements Runnable, ServerInterface {

    private String[] hashtag;
    private String[] comma;
    private String userName;
    private ArrayList<String> tempArray = new ArrayList<String>();
    DateTime dt = new DateTime();
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    private String MESSAGE = "";
//------------------------------------------------------------

    public ChatServerThread(Socket X) {
        this.SOCK = X;

    }

//  Check for closed connections ------------------------------------------------------------
    public void checkConnection() {

        try {
            if (SOCK.isClosed()) {
                for (int i = 0; i <= ChatServer.usersArray.size(); i++) {
                    if (ChatServer.usersArray.get(i).getSOCK() == SOCK) {

                        ChatServer.usersHashmap.remove(userName, SOCK);
                        ChatServer.usersArray.remove(i);
                        System.out.println("User " + userName + " is disconnected and removed from server");
                    }
                }
                userList();
            }
        } catch (Exception ex) {
            System.out.println(ex + "--- error in checkConnection method");
        }
    }

//------------------------------------------------------------
    @Override
    public void run() {

        try {

            INPUT = new Scanner(SOCK.getInputStream());
            OUT = new PrintWriter(SOCK.getOutputStream());

            while (true) {

                checkConnection();

                if (!INPUT.hasNext()) {
                    return;
                }

                MESSAGE = INPUT.nextLine();

                //USER#-----------------------------------------------------                
                if (MESSAGE.contains(Protocols.USERNAME)) {
                    System.out.println("Got a USER# command");
                    user(MESSAGE);
                }

                //MSG#------------------------------------------------------
                if (MESSAGE.contains(Protocols.MSG)) {

                    System.out.println("Got a MSG# command");

                    hashtag = MESSAGE.split("#");
                    System.out.println(hashtag[1]);

                    ArrayList<String> list = new ArrayList();

                    //If more than one person, then add to list.
                    if (hashtag[1].contains(",")) {
                        comma = hashtag[1].split(",");
                        for (int i = 0; i < comma.length; i++) {
                            System.out.println("comma: " + comma[i]);
                            list.add(comma[i]);
                        }

                        //if only one persons name or * then only place that in list.
                    } else {
                        list.add(hashtag[1]);
                    }

                    msgServer(list, MESSAGE);

                    //STOP#-----------------------------------------------------
                } else if (MESSAGE.contains(Protocols.STOP)) {
                    System.out.println("Got a STOP# command");
                    stop();
                    checkConnection();
                    break;
                }
            }
        } catch (Exception X) {
            System.out.print(X);
        }
        System.out.println("ending serverThread!");
    }
//------------------------------------------------------------

    //Ikke færdig! 
    @Override
    public void msgServer(ArrayList<String> receivers, String msg) {

        System.out.println("receivers size: " + receivers.size());
        //Message is sent to spicifik people, unless that receivers array is empty or has * on the first index.        
        if (!receivers.isEmpty() && !receivers.get(0).contains("*")) {
            try {
                for (int i = 0; i < receivers.size(); i++) {
                    System.out.println("i indeni forloop: " + i);
                    Socket TEMPSOCK = ChatServer.usersHashmap.get(receivers.get(i));
                    PrintWriter TEMPOUT = new PrintWriter(TEMPSOCK.getOutputStream());
                    TEMPOUT.println(MESSAGE);
                    TEMPOUT.flush();
                    
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("MSG send to some users!: " + MESSAGE);
        } //Message sent to all.
        else {
            try {
                for (int i = 1; i <= ChatServer.usersArray.size(); i++) {

                    Socket TEMPSOCK = ChatServer.usersArray.get(i - 1).getSOCK();
                    PrintWriter TEMPOUT = new PrintWriter(TEMPSOCK.getOutputStream());
                    TEMPOUT.println(MESSAGE);
                    TEMPOUT.flush();
                    
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("MSG send to all users: " + MESSAGE);
        }
    }

    @Override
    public void userList() {

        String usersString = "";

        if (ChatServer.usersArray.isEmpty()) {
            System.out.println("No clients is connected to the server anymore!");
            return;
        }

        //Makes a String with all the users currently connected to the server, seperated by ",".
        for (int i = 1; i <= ChatServer.usersArray.size(); i++) {

            if (i > 1) {
                usersString += "," + ChatServer.usersArray.get(i - 1).getUserName();
            } else {
                usersString += ChatServer.usersArray.get(i - 1).getUserName();
            }
        }

        //Sends USER# command out to all connected users.
        try {
            for (int i = 1; i <= ChatServer.usersArray.size(); i++) {
                Socket TEMPSOCK = ChatServer.usersArray.get(i - 1).getSOCK();
                PrintWriter OUT = new PrintWriter(TEMPSOCK.getOutputStream());
                OUT.println(Protocols.UserList + usersString);
                OUT.flush();
            }
        } catch (Exception e) {
            System.out.println(e + " ....In userList Method");
        }
    }

    @Override
    public void stop() {
        try {
            SOCK.close();

        } catch (IOException ex) {
            System.out.println("Stop failed" + ex);
        }

    }

    @Override
    public void user(String MESSAGE) {

        //if userName already has a value, then user already tped in username.
        if (userName == null && !MESSAGE.isEmpty()) {

            userName = MESSAGE.substring(5);

            if (!userName.equals("")) {

                ClientHandler CH = new ClientHandler(SOCK, userName);
                ChatServer.usersArray.add(CH);
                ChatServer.usersHashmap.put(userName, SOCK);
                userList();
                System.out.println("USERNAME is " + userName);
            }
        } else {
            System.out.println("User already typed in name");
        }
    }
}
