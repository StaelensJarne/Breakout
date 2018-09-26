/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.util;

import java.sql.SQLException;

/**
 *
 * @author jarne
 */
public class BreakoutException extends RuntimeException {

    public BreakoutException(String message, Exception ex) {
        super(message,ex);
    }
    
    public BreakoutException(String message){
        super(message);
    }
    
}
