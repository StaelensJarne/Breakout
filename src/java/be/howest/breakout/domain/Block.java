/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.domain;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jarne
 */
public class Block extends Rectangle{

    private String color;
    private int lenght;
    private int id;
    private int score;
    private int xCoordination;
    private int yCoordination;
    private final boolean shown = true;

    public Block(String color) {
        this.color = color;
    }
    
    private Block(String color, int lenght) {
        this.color = color;
        this.lenght = lenght;
    }

    public Block(int id, String color, int lenght) {
        this(color, lenght);
        this.id = id;
    }

    public Block(String color, int lenght, int score) {
        this(color, lenght);
        this.score = score;
    }

    public Block(int id, String color, int lenght, int score) {
        this(id,color,lenght);
        this.score = score;
        
    }
    
    public int getxCoordination() {
        return xCoordination;
    }

    public void setxCoordination(int xCoordination) {
        this.xCoordination = xCoordination;
    }

    public int getyCoordination() {
        return yCoordination;
    }

    public void setyCoordination(int yCoordination) {
        this.yCoordination = yCoordination;
    }

    public boolean isShown() {
        return shown;
    }

    public String getColor() {
        return color;
    }

    public int getLenght() {
        return lenght;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }
    
    public int generateId() {
        int number = 0;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 11; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i = 0; i < 1; i++) {
            number = list.get(i);
        }
        return number;
    }

    @Override
    public String toString() {
        return String.format("{\"msg\":\"generateBlocks\", \"length\": %d, \"color\": \"%s\", \"xCoordination\": %d, "
                + "\"yCoordination\": %d, \"score\": %d,\"shown\":%b, \"id\": %d,\"height\": %d}",
                lenght,
                color,
                xCoordination,
                yCoordination,
                score,
                shown,
                generateId(),
                getHEIGHT());
    }
}
