package Interface;

import java.util.ArrayList;

/**
 *
 * @author Christian
 */
public interface ChatInterface {
    
    public void Stop(String msg);
    
    public void User(String msg);
    
    public void msgClient(String msg);
    
    //---------------------------------
    
    public void UserList();
    
    public void msgServer(ArrayList receivers, String msg);
    
}