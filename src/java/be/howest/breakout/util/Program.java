/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.util;

import be.howest.breakout.data.Repositories;
import be.howest.breakout.domain.Boss;
import be.howest.breakout.domain.PowerUp;
import be.howest.breakout.socket.GameEndpoint;



/**
 *
 * @author jarne
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Program().run();
    }

    private void run() {
//        GameEndpoint end = new GameEndpoint();
//        end.updateBossHealth("kkkk Phoenix 2");

//    PowerUp p1 = new PowerUp("lazer");
//    Repositories.getPowerRepository().addPowerUpDown(p1);

    Boss b1 = new Boss(1);
    Repositories.getBossRepository().updateHealth(b1, 20);

    }
   
}
