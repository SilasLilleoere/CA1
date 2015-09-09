/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package date;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Silas G L
 */
public class DateTime {

    public String timeStamp(){
        String currTime = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss").format(new Date());
        return currTime;
    }
    
}
