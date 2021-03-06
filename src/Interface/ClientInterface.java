/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public interface ClientInterface {
    
    //Sends a "STOP#" command to the server.
    public void disconnect();
    
    //Sends username "USER#{NAME}" to the server.
    public void sendUsername(String typedUsername);
    
    //Sends message "MSG#{receivers}#{message} to the server.
    public void sendMsg(List choosenUsers, String typedMsg);
    
    //Takes the received message gotten from server, and makes new string from it, that is send too GUI.
    public void receivedMsg(String msg);
    
    //Connect to server using input.
    public boolean connect(String ip, int port);
    
    public void receivedUserlist(String msg);
    
}
