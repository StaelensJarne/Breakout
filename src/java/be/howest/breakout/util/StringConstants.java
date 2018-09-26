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
public class StringConstants {
    // MySqlConnection
    public static final String UNABLE_TO_LOAD_DRIVER = "Could not load database driver.";
    
    // MySqlBlockRepository
    public static final String UNABLE_TO_GET_ALL_BLOCKS = "Could not retrieve blocks.";
    public static final String UNABLE_TO_ADD_BLOCK = "Could not add block.";
    public static final String UNABLE_TO_GET_BLOCK_BY_ID = "Block with this id don't exist in database.";
    public static final String UNABLE_TO_CREATE_BLOCK = "Could not create blocks from the recieved data.";
    
    // MySqlBossRepository
    public static final String UNABLE_TO_CREATE_BOSS = "Could not create a boss from the recieved data.";
    public static final String UNABLE_TO_GET_BOSS = "Could not retrieve the specified boss.";
    public static final String UNABLE_TO_ADD_BOSS = "Could not add a boss.";
    public static final String BOSS_IS_BEATEN = "The current boss is beaten.";
    public static final String UNABLE_TO_UPDATE_HEALTH = "Could not update (change) the boss health.";
    public static final String UNABLE_TO_GET_HEALTH = "Could not retrieve the health for this boss.";
    
    // MySqlPlayerRepository
    public static final String UNABLE_TO_GET_ALL_PLAYERS = "Could not retieve the players.";
    public static final String UNABLE_TO_CREATE_PLAYER = "Could not create player from the recieved data.";
    public static final String UNABLE_TO_GET_PLAYER_BY_NAME = "Could not retrieve the specified player.";
    public static final String UNABLE_TO_ADD_PLAYER = "Could not add a player to the database.";
    public static final String PLAYER_DONT_EXISTS = "The specified player don't exist, cannot (set/get) score.";
    public static final String UNABLE_TO_UPDATE_SINGLEPLAYERSCORE = "Could not change the singleplayerscore for the specified player.";
    public static final String UNABLE_TO_UPDATE_MULTIPLAYERSCORE = "Could not change the multiplayerscore for the specified player.";
    public static final String UNABLE_TO_GET_SINGLEPLAYERSCORE = "Could not retrieve the singleplayerscore.";
    public static final String UNABLE_TO_GET_MULTIPLAYERSCORE = "Could not retrieve the multiplayerscore.";
    
    // MySqlPowerRepository
    public static final String UNABLE_TO_GET_POWERS = "Could not retrieve the powerup/downs.";
    public static final String UNABLE_TO_CREATE_POWERUP = "Could not create powerup from the recieved data.";
    public static final String UNABLE_TO_CREATE_POWERDOWN = "Could not create powerdown from the recieved data.";
    public static final String POWER_DONT_EXIST = "The power you specified dont't exist in the database.";
    public static final String UNABLE_TO_ADD_POWER = "Could not add a power (up/down) to the database.";
    public static final String UNABLE_TO_SET_BLOCK = "Failed to set a block for the power.";
    public static final String UNABLE_TO_GET_THE_TYPE = "Failed to get the type of the Specified power.";
    
    // Sockets (GameEndpoint en MenuEndpoint)
    public static final String SOCKET_ON_ERROR = "Somting in the sockets went wrong.";
    public static final String SOCKET_SEND_TO_SESSION = "Failed to send something to the session.";
    public static final String SOCKET_UNKNOW_MESSAGE = "Message recieved is not known.";
    
}
