/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.domain;

import be.howest.breakout.data.Repositories;

/**
 *
 * @author jarne
 */
public class Player {
    private final String naam;

    public Player(String naam) {
        this.naam = naam; 
    }

    @Override
    public String toString() {
        return "Player: "+getNaam();
    }

    public int getSinglePlayerScore() {
        return Repositories.getPlayerRepository().getSinglePlayerScore(this);
    }

    public int getMultiPlayerScore() {
        return Repositories.getPlayerRepository().getMultiPlayerScore(this);
    }
    
    public String getNaam() {
        return naam;
    }
    
    
    
}
