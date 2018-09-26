/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data.util;

/**
 *
 * @author jarne
 */
public class SqlConstants {

    // MySqlBlockRepository
    public static final String SQL_SELECT_ALL_BLOCKS
            = "SELECT * FROM block";
    public static final String SQL_GET_BLOCK_ID
            = "SELECT * FROM block WHERE color = ?";
    public static final String SQL_INSERT_BLOCK
            = "INSERT INTO block(color, lenght, score) VALUES(?, ?, ?)";

    // MySqlBossRepository
    public static final String SQL_SELECT_BOSS
            = "SELECT * FROM bosses WHERE boss_id = ?";
    public static final String SQL_SELECT_BOSS_HEALTH
            = "SELECT health FROM bosses WHERE name = ?";
    public static final String SQL_INSERT_BOSS
            = "INSERT INTO bosses(name,health,difficulty) VALUES(?, ?, ?)";
    public static final String SQL_UPDATE_HEALTH
            = "UPDATE bosses SET health = health - ? WHERE name = ?";

    // MySqlPlayerRepository
    public static final String SQL_SELECT_ALL_PLAYERS
            = "SELECT * FROM player";
    public static final String SQL_SELECT_PLAYER
            = "SELECT * FROM player WHERE playername = ?";
    public static final String SQL_SELECT_SPSCORE_FROM_PLAYER
            = "SELECT singleplayerscore FROM player WHERE playername = ?";
    public static final String SQL_SELECT_MTSCORE_FROM_PLAYER
            = "SELECT multiplayerscore FROM player WHERE playername = ?";
    public static final String SQL_UPDATE_SINGLEPLSCORE
            = "UPDATE player SET singleplayerscore = ? WHERE playername = ?";
    public static final String SQL_UPDATE_MULTIPLSCORE
            = "UPDATE player SET multiplayerscore = ? WHERE playername = ?";
    public static final String SQL_INSERT_PLAYER
            = "INSERT INTO player(playername) VALUES(?)";

    // MySqlPowerRepository
    public static final String SQL_SELECT_ALL_POWERUPS
            = "SELECT * FROM powers WHERE type = 1";
    public static final String SQL_SELECT_ALL_POWERDOWNS
            = "SELECT * FROM powers WHERE type = 0";
    public static final String SQL_SELECT_ALL
            = "SELECT * FROM powers";
    public static final String SQL_GET_POWER_ID
            = "SELECT power_id FROM powers WHERE name = ?";
    public static final String SQL_GET_BLOCK
            = "SELECT * FROM powers p1 JOIN block  p2 ON p2.block_id "
            + "= p1.block_id WHERE p1.name = ?";
    public static final String SQL_SET_BLOCK
            = "UPDATE powers SET block_id = ? WHERE power_id = ?";
    public static final String SQL_INSERT_POWER
            = "INSERT INTO powers(name, type) VALUES(? ,?)";
    public static String SQL_GET_TYPE 
            = "SELECT type FROM powers WHERE name = ?";
    public static String SQL_GET_POWER_BY_ID
            = "SELECT * FROM powers WHERE power_id =?";

}
