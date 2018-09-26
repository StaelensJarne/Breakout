/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.util;

/**
 *
 * @author jarne
 */
public class JSONMessages {
    
    // MenuEndpoint + GameEndpoint
    public static final String connected = "{ \"from\":\"server\" , \"player\":\"%s connected\" }"; // 1 parameter
    public static final String disconnected = "{ \"from\":\"server\" , \"msg\":\"%s disconnected\" }"; // 1 parameter
    
    // MenuEndpoint 
    public static final String playerAdded = "{ \"from\":\"server\" , \"msg\":\"player %s added\" }"; //1 parameter
    public static final String setDifficulty = "{ \"from\":\"server\" , \"msg\":\"ball is traveling at %s speed\" }";    
    public static final String showScores = "{ \"from\":\"server\" , \"msg\":\"highscores send\" }";
    
    // GameEndpoint 
    public static final String generateArrayOfBlocks = "{ \"from\":\"server\" , \"msg\": \" %s \" }";
    public static final String genereateArrayOfPowers = "{ \"from\":\"server\" , \"msg\": \" %s \" }";
    public static final String generateBoss = "{ \"from\":\"server\" , \"msg\": \" generated boss %s \"}";
    public static final String updateHealth = "{ \"from\":\"server\" , \"msg\": \" boss %s has %d health left \"}";
    public static final String spScore = "{ \"from\":\"server\" , \"msg\": \" player %s has has a singlePlayerScore of %s \"}";
    public static final String mtScore = "{ \"from\":\"server\" , \"msg\": \" player %s has has a multiPlayerScore of %s \"}";
}
