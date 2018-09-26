/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;
import be.howest.breakout.domain.Block;
import be.howest.breakout.domain.PowerDown;
import be.howest.breakout.domain.PowerUp;
import be.howest.breakout.util.BreakoutException;
import org.junit.*;

/**
 *
 * @author jarne
 */
public class MySqlPowerRepositoryTester {
    
    @Test
    public void testGetAllPowerUpsDowns() {
        try {
            Repositories.getPowerRepository().getPowers();
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testGetPowerUps() {
        try {
            Repositories.getPowerRepository().getPowerUps();
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testGetPowerDowns() {
        try {
            Repositories.getPowerRepository().getPowerDowns();
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testAddPowerUp() {
        try {
            PowerUp p = new PowerUp("lazer");
            Repositories.getPowerRepository().addPowerUpDown(p);
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testAddPowerDown() {
        try {
            PowerDown p = new PowerDown("many balls");
            Repositories.getPowerRepository().addPowerUpDown(p);
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
}
