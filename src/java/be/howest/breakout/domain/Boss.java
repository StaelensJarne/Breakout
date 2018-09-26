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
public class Boss {
    private String name;
    private int health;
    private int id;
    private String difficulty;

    public Boss(String name, int health, String difficulty) {
        this.name = name;
        this.health = health;
        this.difficulty = difficulty;
    }
    
    public Boss(int id){
        this.id = id; 
    }

    public Boss(String name, int health) {
        this(name, health, "not set");
    }

    public Boss(String name) {
        this(name, 0);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return "Boss{" + "name=" + name + ", health=" + health + ", difficulty=" + difficulty + '}';
    }
    
    
    
}
