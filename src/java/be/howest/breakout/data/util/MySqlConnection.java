/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data.util;

import be.howest.breakout.util.BreakoutException;
import be.howest.breakout.util.StringConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jarne
 */
public class MySqlConnection {
    
    private static final String USR = "usrbreakout";
    private static final String PWD = "TIbreakout2017";    
    private static final String URL = "jdbc:mysql://localhost:8080/breakout&useSSL=false";
    
    private static Connection conn; 
    
    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException ex)
        {
            throw new BreakoutException("Unable to load database driver.", ex);
        }
    }
    
    protected static Connection getConnection(){
        try {
            conn = DriverManager.getConnection(URL, USR, PWD);
        } catch(SQLException ex){
            throw new BreakoutException(StringConstants.UNABLE_TO_LOAD_DRIVER, ex);
        }
        return conn;
    }
}
