/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import GUI.ChatGUI;
import Interface.ClientInterface;
import Shared.Protocols;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient extends Observable implements Runnable, ClientInterface {

    Socket SOCK;
    Scanner INPUT;
    PrintWriter OUT;
    private String[] hashtag;
    private String[] comma;
    static ChatGUI chatGui;

    public ChatClient(Socket S) {
        this.SOCK = S;
    }

    public ChatClient() {

    }

    public static void main(String[] args) {
        chatGui = new ChatGUI();
        new Thread(chatGui).start();
    }

    @Override
    public void run() {

        
        
        try {
            INPUT = new Scanner(SOCK.getInputStream());
        } catch (IOException ex) {
            System.out.println(ex);
        }        
        
        CommandListenerClass CLC = new CommandListenerClass();
        new Thread(CLC).start();

//        commandListener();

    }

    public void commandListener() {

        
        try {
            INPUT = new Scanner(SOCK.getInputStream());
        } catch (IOException ex) {
            System.out.println(ex);
        }

        if (!INPUT.hasNext()) {
            return;
        }

        String MESSAGE = INPUT.nextLine();

        System.out.println("CommandMessage gotten from server: " + MESSAGE);

        //MSG#--------------------------------------------------------------
        if (MESSAGE.contains(Protocols.MSG)) {
            receivedMsg(MESSAGE);
        }
        //USERLIST#---------------------------------------------------------
        if (MESSAGE.contains(Protocols.UserList)) {
            receivedUserlist(MESSAGE);
        }
        //STOP#-------------------------------------------------------------
        if (MESSAGE.contains(Protocols.STOP)) {
            disconnect();
        }
    }

    @Override
    public void disconnect() {

        try {
            PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());
            OUT.println(Protocols.STOP);
            OUT.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void sendUsername(String typedUsername) {

        try {
            OUT = new PrintWriter(SOCK.getOutputStream());
            OUT.println(Protocols.USERNAME + typedUsername);
            OUT.flush();
            System.out.println("Typed username is: " + typedUsername);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //Takes in string array, and typed usermessage from GUI. Creates command string from that, and sends to server. 
    @Override
    public void sendMsg(List choosenUsers, String typedMsg) {

        String stringUsers = "";

        try {
            if (choosenUsers.size() > 0) {
                for (int i = 0; i < choosenUsers.size(); i++) {
                    if(i == 0){
                    stringUsers += choosenUsers.get(i);
                    }
                    else if (i > 0) {
                       
                        stringUsers += "," + choosenUsers.get(i);
                        System.out.println(stringUsers);
                    }
                }
            } else {
                stringUsers = "*";
            }
           
            
            OUT = new PrintWriter(SOCK.getOutputStream());
            OUT.println(Protocols.MSG + stringUsers + "#" + typedMsg);
            OUT.flush();

        } catch (IOException ex) {
            System.out.println(ex + "...error is from sendMsg method!");
        }
    }

    @Override
    public void receivedMsg(String msg) {

        String msgStringForGUI = "";
        System.out.println("Server said: " + msg);

        hashtag = msg.split("#");

        msgStringForGUI = hashtag[2];

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
        System.out.println("Sends this message to GUI: " + msgStringForGUI);
        notifyGUI(msgStringForGUI);
    }

    private void notifyGUI(Object obj) {
        setChanged();
        notifyObservers(obj);
    }

    //Should be run by the GUI.
    @Override
    public boolean connect(String ip, int port) {

        
        String IP = ip;
        int PORT = port;

        try {
            SOCK = new Socket(ip, port);
            
            Thread th1 = new Thread(this);
            th1.start();

        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
        
        System.out.println("Client Connected to server!");
        return true;
    }

    @Override
    public void receivedUserlist(String MESSAGE) {

        String userListForGUI = MESSAGE.substring(9);
        String[] listOfUsers = userListForGUI.split(",");

        notifyGUI(userListForGUI);
	
    }

    private class CommandListenerClass implements Runnable {

        @Override
        public void run() {
            
            while (true) {
                commandListener();
            }
        }

    }

}
