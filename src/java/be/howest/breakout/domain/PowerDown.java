/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.domain;

/**
 *
 * @author jarne
 */
public class PowerDown extends Power {
    
    public PowerDown(String name) {
        super(name);
    }
    
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
