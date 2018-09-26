/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.domain.Boss;

/**
 *
 * @author jarne
 */
public interface BossRepository {
    public Boss getSpecificBoss(int bossId);
    public void addBoss(Boss b);
    public void updateHealth(Boss b, int damage);
    
}
