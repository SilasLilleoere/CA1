package Interface;

import java.util.ArrayList;

/**
 *
 * @author Christian
 */
public interface ChatInterface {
    
    public void stop();
    
    public void user();
    
//    public void msgClient(String msg);
    
    //---------------------------------
    
    public void userList();
    
    public void msgServer(ArrayList receivers, String msg);
    
}