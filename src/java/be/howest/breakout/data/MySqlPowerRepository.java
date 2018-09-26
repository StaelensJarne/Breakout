/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.data.util.MySqlConnection;
import be.howest.breakout.domain.Block;
import be.howest.breakout.domain.Power;
import be.howest.breakout.domain.PowerDown;
import be.howest.breakout.domain.PowerUp;
import be.howest.breakout.util.BreakoutException;
import be.howest.breakout.data.util.SqlConstants;
import be.howest.breakout.util.StringConstants;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarne
 */
public class MySqlPowerRepository extends MySqlConnection implements PowersRepository {
    private static final String NAAM = "name";
    private static final String POWER_ID = "power_id";
    private static final String TYPE = "type";  
    private static final String COLOR = "color";
    private static final String LENGHT = "lenght";
    private static final String BLOCK_ID = "block_id";

    @Override
    public List<Power> getPowers() {
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_ALL);
             ResultSet res = prep.executeQuery();) {
            List<Power> powers = new ArrayList<>();
            while (res.next()) {
                if(res.getInt(TYPE) == 1){
                    PowerUp pu = createPowerUp(res);                  
                    powers.add(pu);
                } else {
                    PowerDown pd = createPowerDown(res);
                    powers.add(pd);
                }           
            }
            return powers;           
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_POWERS, ex);
        }
    }

    @Override
    public Block getBlockOfPowerUp(Power p) {  
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_GET_BLOCK);) {
            prep.setString(1, p.getName());
            try (ResultSet res = prep.executeQuery();) {
                Block b = null;
                while (res.next()) {
                    b = createBlock(res);
                }
                if (p == null) {
                    throw new BreakoutException(StringConstants.POWER_DONT_EXIST);
                } else {
                    return b;
                }
            }
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_POWERS, ex);
        }
    }

    @Override
    public List<PowerDown> getPowerDowns() {
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_ALL_POWERDOWNS);
             ResultSet res = prep.executeQuery();) {
            List<PowerDown> powerDowns = new ArrayList<>();
            while (res.next()) {
                    PowerDown pd = createPowerDown(res);                 
                    powerDowns.add(pd);
                }           
            return powerDowns;           
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_POWERS, ex);
        }
    }

    @Override
    public List<PowerUp> getPowerUps() {
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_ALL_POWERUPS);
             ResultSet res = prep.executeQuery();) {
            List<PowerUp> powerUps = new ArrayList<>();
            while (res.next()) {
                    PowerUp pd = createPowerUp(res);                
                    powerUps.add(pd);
                }           
            return powerUps;           
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_POWERS, ex);
        }
    }

    @Override
    public void addPowerUpDown(Power p) {
          try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_INSERT_POWER);){   
              Boolean type = p instanceof PowerUp;
              if(p.getName() != null){
                  prep.setString(1, p.getName());
              }
            prep.setBoolean(2, type);
            prep.executeUpdate();
        } catch(SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_ADD_POWER, ex);
        }
    }

    @Override
    public void setBlockOfPower(Block b, Power p) {
        try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SET_BLOCK);){  
            int id = Repositories.getBlockRepository().getBlockId(b);
            prep.setInt(1 , id);
            prep.setInt(2, getIdOfPowerUpDown(p));
            prep.executeUpdate();
        } catch(SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_SET_BLOCK, ex);
        }
    }
    
    private int getIdOfPowerUpDown(Power p) {
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_GET_POWER_ID);) {
            prep.setString(1, p.getName());
            try(ResultSet res = prep.executeQuery();){
                int id = -1;
                while (res.next()) {
                    id = res.getInt(POWER_ID);
                }           
                return id;       
            }     
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_POWERS, ex);
        }
    }

    private PowerUp createPowerUp(ResultSet res) {
        try {
            return new PowerUp(res.getString(NAAM));
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_CREATE_POWERUP, ex);
        }
    }

    private PowerDown createPowerDown(ResultSet res) {
        try {
            return new PowerDown(res.getString(NAAM));
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_CREATE_POWERDOWN, ex);
        }
    }

    private Block createBlock(ResultSet res) {
        try {
            return new Block(res.getInt(BLOCK_ID), res.getString(COLOR), res.getInt(LENGHT));
        } catch(SQLException ex){
            throw new BreakoutException(StringConstants.UNABLE_TO_CREATE_BLOCK, ex);
        }
    }

    @Override
    public int getTypeOfPower(Power pow) {
        try(PreparedStatement stmt = getConnection().prepareStatement(SqlConstants.SQL_GET_TYPE);){
            stmt.setString(1, pow.getName());
            try(ResultSet res = stmt.executeQuery();){
                int type = -1;
                while(res.next()){
                    type = res.getInt(TYPE);
                }
                if(type == -1){
                    throw new BreakoutException(StringConstants.POWER_DONT_EXIST);
                } else {
                    return type;
                }
            }         
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_THE_TYPE, ex);  
        }
    }

    @Override
    public Power getPowerById(int id) {
        try(PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_GET_POWER_BY_ID);){  
            prep.setInt(1, id);
            try(ResultSet res = prep.executeQuery();){
                Power power = null;
                while (res.next()) {
                    String work = res.getString(NAAM);
                    power = new Power(id,work);
                }           
                return power;
               
            }  
        } catch(SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_SET_BLOCK, ex);
        }
    }
    
    
    
     
}
