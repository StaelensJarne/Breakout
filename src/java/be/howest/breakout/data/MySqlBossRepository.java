/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.data.util.MySqlConnection;
import be.howest.breakout.domain.Boss;
import be.howest.breakout.util.BreakoutException;
import be.howest.breakout.data.util.SqlConstants;
import be.howest.breakout.util.StringConstants;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jarne
 */
public class MySqlBossRepository extends MySqlConnection implements BossRepository {
    private static final String NAAM = "name";
    private static final String HEALTH = "health";
    private static final String DIFFICULTY = "difficulty";

    @Override
    public Boss getSpecificBoss(int id) {
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_BOSS);) {
            prep.setInt(1, id);
            try (ResultSet res = prep.executeQuery();) {
                Boss bReturned = null;
                while (res.next()) {
                    bReturned = createBoss(res);
                }
                if (bReturned == null) {
                    throw new BreakoutException(StringConstants.UNABLE_TO_GET_BOSS);
                } else {
                    return bReturned;
                }
            }
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_BOSS, ex);
        }
    }

    @Override
    public void addBoss(Boss b) {
        try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_INSERT_BOSS);){           
            prep.setString(1, b.getName());
            prep.setInt(2, b.getHealth());
            prep.setString(3, b.getDifficulty());
            prep.executeUpdate();
        } catch (SQLException ex){
            System.err.println(ex);
            throw new BreakoutException(StringConstants.UNABLE_TO_ADD_BOSS, ex);
        }
    }

    @Override
    public void updateHealth(Boss b, int damage) {
        Boss ExistingBoss = getSpecificBoss(b.getId());
        try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_UPDATE_HEALTH);){
            prep.setInt(1, damage);
            prep.setString(2, ExistingBoss.getName());
            if(getBossHealth(ExistingBoss) >= damage){
                prep.executeUpdate();
            } else {
                updateHealth(ExistingBoss, getBossHealth(b));
                throw new BreakoutException(StringConstants.BOSS_IS_BEATEN);
            }             
        } catch(SQLException ex){     
            throw new BreakoutException(StringConstants.UNABLE_TO_UPDATE_HEALTH,ex);
        }
    }
    
    private int getBossHealth(Boss b){
        try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_BOSS_HEALTH);){
            int health = 0;
            prep.setString(1, b.getName());
            try(ResultSet res = prep.executeQuery();){
                while(res.next()){
                    health = res.getInt(HEALTH);
                }              
            }     
            return health;
        }catch(SQLException ex){
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_HEALTH,ex);
        }         
    }

    private Boss createBoss(ResultSet res) {
        try {
            return new Boss(res.getString(NAAM),res.getInt(HEALTH),res.getString(DIFFICULTY));
        } catch(SQLException ex){
            throw new BreakoutException(StringConstants.UNABLE_TO_CREATE_BOSS, ex);
        }
    }

    
}
