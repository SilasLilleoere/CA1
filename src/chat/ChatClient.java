/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import Interface.ClientInterface;
import Shared.Protocols;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hjemme
 */
public class ChatClient implements Runnable, ClientInterface {

    Socket SOCK;
    Scanner INPUT;
    PrintWriter OUT;
    private String[] hashtag;
    private String[] comma;

    public ChatClient(Socket S) {
        this.SOCK = S;
    }

    public static void main(String[] args) {

    }

    @Override
    public void run() {

        try {
            INPUT = new Scanner(SOCK.getInputStream());
//            OUT = new PrintWriter(SOCK.getOutputStream());
        } catch (IOException ex) {
            System.out.println(ex);
        }

        while (true) {

            if (!INPUT.hasNext()) {
                return;
            }
            try {
                INPUT = new Scanner(SOCK.getInputStream());
            } catch (IOException ex) {
                System.out.println(ex);
            }

            String MESSAGE = INPUT.nextLine();

            System.out.println("Message gotten from server: " + MESSAGE);

            //#MSG
            if (MESSAGE.contains(Protocols.MSG)) {

                System.out.println("Server said: " + MESSAGE);

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
                receivedMsg();
            }
            //#USERLIST
            if (MESSAGE.contains(Protocols.UserList)) {
                receivedUserlist();
            }
        }
    }

    @Override
    public void disconnect() {

        try {
            PrintWriter OUT = new PrintWriter(Protocols.STOP);
            OUT.flush();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void sendUsername(String typedUsername) {
        //Skal modtage indtastet navn fra brugeren, og sendes videre til serveren med USER#.
    }

    @Override
    public void sendMsg(ArrayList choosenUsers, String typedMsg) {
        //Skal modtage besked fra bruger, og hvilken brugere beskeden skal sende til. 
        //Derefter sende en tekstStreng til Serveren.
    }

    @Override
    public void receivedMsg() {
        //Skal sende beskeden modtaget fra run(), op til GUI i brugervenlig tekst form.

    }

    //Should be run by the GUI.
    @Override
    public boolean connect(String ip, int port) {

        String IP = ip;
        int PORT = port;

        try {
            SOCK = new Socket(ip, port);
            ChatClient client = new ChatClient(SOCK);
            Thread th1 = new Thread(client);
            th1.start();

        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    @Override
    public void receivedUserlist() {

    }

}
