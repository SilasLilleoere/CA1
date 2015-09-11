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


public class ChatServerReturn implements Runnable, ServerInterface {

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

    public ChatServerReturn(Socket X) {
        this.SOCK = X;

    }

//  Check for dead connections ------------------------------------------------------------
    public void checkConnection() throws IOException {
        if (!SOCK.isConnected()) {
            for (int i = 1; i <= ChatServer.usersArray.size(); i++) {
                if (ChatServer.usersArray.get(i).getSOCK() == SOCK) {
                    
                    ChatServer.usersHashmap.remove(userName, SOCK);
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
            userList();
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
                    System.out.println("Runs user#");
                    user(MESSAGE);
                }

                //MSG#------------------------------------------------------
                if (MESSAGE.contains(Protocols.MSG)) {

                    System.out.println("Client said: " + MESSAGE);

                    hashtag = MESSAGE.split("#");
                    System.out.println(hashtag[1]);

                    ArrayList<String> list = new ArrayList();

                    if (hashtag[1].contains(",")) {
                        comma = hashtag[1].split(",");
                        for (int i = 0; i < comma.length; i++) {
                            System.out.println("comma: " + comma[i]);
                            list.add(comma[i]);
                        }

                    } else {
                        list.add(hashtag[1]);
                    }

                    msgServer(list, MESSAGE);

                    //STOP#-----------------------------------------------------
                } else if (MESSAGE.contains(Protocols.STOP)) {
                    stop();
                }
            }
        } catch (Exception X) {
            System.out.print(X);
        }
    }
//------------------------------------------------------------

    //Ikke færdig! 
    @Override
    public void msgServer(ArrayList receivers, String msg) {

        tempArray = receivers;
        MESSAGE = msg;
        System.out.println("receivers size: " + receivers.size());
        //Message is sent to spicifik people.
        if (!receivers.isEmpty() || receivers != null) {
            try {
                for (int i = 0; i < receivers.size(); i++) {
                    System.out.println("i sout: " + i);
                    Socket TEMPSOCK = ChatServer.usersHashmap.get(receivers.get(i));
                    PrintWriter TEMPOUT = new PrintWriter(TEMPSOCK.getOutputStream());
                    TEMPOUT.println(MESSAGE);
                    System.out.println(userName + ": \t" + MESSAGE);
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
                    TEMPOUT.println(MESSAGE);
                    TEMPOUT.flush();

                    System.out.println("Message sent to: " + TEMPSOCK.getLocalAddress().getHostName());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void userList() {

        String usersString = "";

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
            System.out.println(e+ " ....In userList Method");
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
        if (userName == null || !MESSAGE.isEmpty()) {
            
            userName = MESSAGE.substring(5);

            if (!userName.equals("")) {  

                ClientHandler CH = new ClientHandler(SOCK, userName);
                ChatServer.usersArray.add(CH);
                ChatServer.usersHashmap.put(userName, SOCK);
                userList();
            }
        } else {
            System.out.println("User already typed in name");
        }
  
    }
}
