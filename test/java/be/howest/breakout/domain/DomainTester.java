/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.domain;

import org.junit.*;

/**
 *
 * @author jarne
 */
public class DomainTester {
    
    @Test
    public void testCreatePlayer() {
        String OriginalName = "jarne";
        Player p = new Player(OriginalName);
        
        Assert.assertEquals(p.getNaam(), OriginalName);
    }
    
}
