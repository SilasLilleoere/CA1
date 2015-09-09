/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import Interface.ChatInterface;
import static chat.ChatServer.CurrentUsers;
import date.DateTime;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Silas
 */
public class ChatServerReturn implements Runnable, ChatInterface {

    DateTime dt = new DateTime();
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";
//------------------------------------------------------------

    public ChatServerReturn(Socket X) {
        this.SOCK = X;
        try {
            addUserName(SOCK);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        UserList();
    }

//  Check for dead connections ------------------------------------------------------------
    public void checkConnection() throws IOException {
        if (!SOCK.isConnected()) {
            for (int i = 1; i <= ChatServer.ConnectionArray.size(); i++) {
                if (ChatServer.ConnectionArray.get(i) == SOCK) {
                    ChatServer.ConnectionArray.remove(i);
                }
            }
            for (int i = 1; i <= ChatServer.ConnectionArray.size(); i++) {

                Socket TEMPSOCK = ChatServer.ConnectionArray.get(i - 1);
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

    @Override
    public void msgClient(String msg) {

    }

    //Ikke færdig!
    @Override
    public void msgServer(String msg) {

        try{
        for (int i = 1; i <= ChatServer.ConnectionArray.size(); i++) {
            Socket TEMPSOCK = ChatServer.ConnectionArray.get(i - 1);
            PrintWriter TEMPOUT = new PrintWriter(TEMPSOCK.getOutputStream());
            TEMPOUT.println("");
            TEMPOUT.println(dt.timeStamp());
            TEMPOUT.println(TEMPSOCK.getLocalAddress().getHostName() + ": \t" + MESSAGE);
            TEMPOUT.flush();

            System.out.println("Message sent to: " + TEMPSOCK.getLocalAddress().getHostName());
        }
        }
        catch(Exception e){
        
        }
    }

    @Override
    public void UserList() {

        try {
            for (int i = 1; i <= ChatServer.ConnectionArray.size(); i++) {
                Socket TEMPSOCK = ChatServer.ConnectionArray.get(i - 1);
                PrintWriter OUT = new PrintWriter(TEMPSOCK.getOutputStream());
                OUT.println("USERLIST#" + CurrentUsers);
                OUT.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addUserName(Socket X) throws IOException {

        Scanner INPUT = new Scanner(X.getInputStream());
        String USERNAME = INPUT.nextLine();
        CurrentUsers.add(USERNAME);
    }
}
