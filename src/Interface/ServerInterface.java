/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.ArrayList;

/**
 *
 * @author Hjemme
 */
public interface ServerInterface {

    //Stops the socket-connection, when clients writes STOP# 
    public void stop();

    //Gets the username from client, and adds username and socket to usersArray and usersHashmap. 
    public void user(String msg);

    //Sends list of connected users to all
    public void userList();

    //Takes in a message send from a client, and sends it on to other connected clients.  
    public void msgServer(ArrayList<String> receivers, String msg);

}
