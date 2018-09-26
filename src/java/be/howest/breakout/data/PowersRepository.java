/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.domain.Block;
import be.howest.breakout.domain.Power;
import be.howest.breakout.domain.PowerDown;
import be.howest.breakout.domain.PowerUp;
import java.util.List;

/**
 *
 * @author jarne
 */
public interface PowersRepository {
    
    public List<Power> getPowers();
    public List<PowerDown> getPowerDowns();
    public List<PowerUp> getPowerUps();
    public void addPowerUpDown(Power p);
    public void setBlockOfPower(Block b, Power p);
    public Block getBlockOfPowerUp(Power p);

    public int getTypeOfPower(Power aThis);
    public Power getPowerById(int id);
    
    
    
}
