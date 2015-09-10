/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import Interface.ClientInterface;
import Shared.Protocols;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hjemme
 */
public class Client implements Runnable, ClientInterface {

    Socket SOCK;

    public static void main(String[] args) {

        String ip = "localhost";
        int port = 4444;

        if (args.length == 2) {
            System.out.println("args found");
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }

        try {
            Socket ss = new Socket(ip, port);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        Client c = new Client();

        Thread th1 = new Thread(c);

    }

    @Override
    public void run() {
    }

    @Override
    public void disconnect() {

        try {
            PrintWriter OUT = new PrintWriter(Protocols.STOP);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void sendUsername(String typedUsername) {
    }

    @Override
    public void sendMsg(ArrayList choosenUsers) {
    }

    @Override
    public void receivedMsg() {
    }

    @Override
    public boolean connect(String ip, int port) {
        return true;
    }

}
