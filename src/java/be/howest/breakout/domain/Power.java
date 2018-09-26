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
public class Power {  
    protected String name;
    private int type;

    public Power(String name) {
        this.name = name;
    }
    
    public Power(int type,String name) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return ",\"name\":\""+getName().toString()+"\"";
    }
    
    
    
}
