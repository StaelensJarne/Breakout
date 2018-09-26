/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.util.BreakoutException;
import be.howest.breakout.data.util.MySqlConnection;
import be.howest.breakout.domain.Player;
import be.howest.breakout.data.util.SqlConstants;
import be.howest.breakout.util.StringConstants;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author A
 */
public class MySqlPlayerRepository extends MySqlConnection implements PlayerRepository { 
    private static final String NAAM = "playername";
    private static final String SINGLEPLAYERSCORE = "singleplayerscore"; 
    private static final String MULTIPLAYERSCORE = "multiplayerscore";

    @Override
    public List<Player> getPlayers() {
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_ALL_PLAYERS);
             ResultSet res = prep.executeQuery();) {
            List<Player> spelers = new ArrayList<>();
            while (res.next()) {
                Player p = createPlayer(res);
                spelers.add(p);
            }
            return spelers;
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_ALL_PLAYERS, ex);
        }
    }

    @Override
    public Player getSpecifickPlayer(String name) {
        try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_PLAYER);) {
            prep.setString(1, name);
            try(ResultSet res = prep.executeQuery();){
                Player p = null;
                while (res.next()) {
                    p = createPlayer(res);
                }
            return ((p == null) ? null : p);    
            }               
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_PLAYER_BY_NAME, ex);
        }
    }

    @Override
    public void insertPlayer(Player p) {
        try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_INSERT_PLAYER);){        
            prep.setString(1, p.getNaam());
            prep.executeUpdate();
        } catch(SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_ADD_PLAYER, ex);
        }
    }

    @Override
    public void setSinglePlayerScore(Player p, int score) {
        try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_UPDATE_SINGLEPLSCORE);){
            if(getExsistingPlayer(p)){                          
                prep.setInt(1, score);
                prep.setString(2, p.getNaam());
                prep.executeUpdate();
            } else {
                throw new BreakoutException(StringConstants.PLAYER_DONT_EXISTS);
            }
        } catch(SQLException ex){
            throw new BreakoutException(StringConstants.UNABLE_TO_UPDATE_SINGLEPLAYERSCORE,ex);
        }
    }

    @Override
    public void setMultiPlayerScore(Player p, int score) {
        try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_UPDATE_MULTIPLSCORE);){
            if(getExsistingPlayer(p)){                          
                prep.setInt(1, score);
                prep.setString(2, p.getNaam());
                prep.executeUpdate();
            } else {
                throw new BreakoutException(StringConstants.PLAYER_DONT_EXISTS);
            }
        } catch(SQLException ex){
            throw new BreakoutException(StringConstants.UNABLE_TO_UPDATE_MULTIPLAYERSCORE,ex);
        }
    }

    @Override
    public int getSinglePlayerScore(Player p) {
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_SPSCORE_FROM_PLAYER)) {
            int score = 0;
            if (getExsistingPlayer(p)) {
                prep.setString(1, p.getNaam());
                ResultSet res = prep.executeQuery();
                while (res.next()) {
                    score += res.getInt(SINGLEPLAYERSCORE);
                }
            } else {
                throw new BreakoutException(StringConstants.PLAYER_DONT_EXISTS);
            }
            return score;
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_SINGLEPLAYERSCORE, ex);
        }
    }

    @Override
    public int getMultiPlayerScore(Player p) {
         try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_MTSCORE_FROM_PLAYER)) {
            int score = 0;
            if (getExsistingPlayer(p)) {
                prep.setString(1, p.getNaam());
                ResultSet res = prep.executeQuery();
                while (res.next()) {
                    score += res.getInt(MULTIPLAYERSCORE);
                }
            } else {
                throw new BreakoutException(StringConstants.PLAYER_DONT_EXISTS);
            }
            return score;
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_MULTIPLAYERSCORE, ex);
        }
    }
    
    private boolean getExsistingPlayer(Player p){
        return (getSpecifickPlayer(p.getNaam())!=null);
    }

    private Player createPlayer(ResultSet res) {
        try {
            return new Player(res.getString(NAAM));
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_CREATE_PLAYER, ex);
        }
    }
}
