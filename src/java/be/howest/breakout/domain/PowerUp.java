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
public class PowerUp extends Power {
    
    private int id;
    private boolean ultimate;

    public PowerUp(String name) {
        super(name);
    }
    
    public PowerUp(String name, Boolean ultimate){
        this(name);
        this.ultimate = ultimate;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return this.name;
        //return String.format("{\"name\": %s, \"id\": \"%d\", \"isUltimate\": %b, ");
    }   
}
