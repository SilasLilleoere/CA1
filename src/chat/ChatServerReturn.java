/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import date.DateTime;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Silas
 */
public class ChatServerReturn implements Runnable {

    DateTime dt = new DateTime();
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";
//------------------------------------------------------------

    public ChatServerReturn(Socket X) {
        this.SOCK = X;
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
            } finally {
                SOCK.close();
            }
        } catch (Exception X) {
            System.out.print(X);
        }
    }
//------------------------------------------------------------
}
