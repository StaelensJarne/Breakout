/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.domain.Player;
import java.util.List;

/**
 *
 * @author jarne
 */
public interface PlayerRepository {
    
    public List<Player> getPlayers();
    public Player getSpecifickPlayer(String name);
    public void insertPlayer(Player p);
    public void setSinglePlayerScore(Player p, int score);
    public void setMultiPlayerScore(Player p, int score);
    public int getSinglePlayerScore(Player p);
    public int getMultiPlayerScore(Player p);
    
}
