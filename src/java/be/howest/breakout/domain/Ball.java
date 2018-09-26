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
public class Ball {

    private int speedX;
    private int speedY;
    private final int RADIUS = 10;

    public Ball(String speed) {
        switch (speed) {
            case "easy":
                this.speedX = 4;
                break;
            case "medium":
                this.speedX = 7;
                break;
            case "hard":
                this.speedX = 10;
                break;
        }
        this.speedY = -speedX;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getRADIUS() {
        return RADIUS;
    }

}
