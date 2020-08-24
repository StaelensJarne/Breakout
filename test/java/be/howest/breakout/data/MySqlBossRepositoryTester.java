/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.domain.Boss;
import be.howest.breakout.util.BreakoutException;
import org.junit.*;

/**
 *
 * @author jarne
 */
public class MySqlBossRepositoryTester {
    
    @Test
    public void testGetSpecificBoss_BOSS_NOT_EXISTS() {
        try {
            Boss b = new Boss("blablabla");
            Repositories.getBossRepository().getSpecificBoss(b.getId());
            Assert.fail();
        } catch(BreakoutException ex){}
        
    }
    
    @Test
    public void testGetSpecificBoss_BOSS_EXISTS() {
        try {
            Boss b = new Boss(1);
            Repositories.getBossRepository().getSpecificBoss(b.getId());
        } catch(BreakoutException ex){
            Assert.fail();
        }
        
    }
    
    @Test
    public void testAddBoss() {
        try {
            Boss b = new Boss("Hagerid");
            Repositories.getBossRepository().addBoss(b);
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
   
    
}
