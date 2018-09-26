/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

/**
 *
 * @author jarne
 */
public class Repositories {
    
    private static final PlayerRepository playerRepository = new MySqlPlayerRepository();
    private static final BlockRepository blockRepository = new MySqlBlockRepository();
    private static final PowersRepository powerRepository = new MySqlPowerRepository();
    private static final BossRepository bossRepository = new MySqlBossRepository();
    
    private Repositories() {
    }

    public static PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    public static BlockRepository getBlockRepository() {
        return blockRepository;
    }  

    public static PowersRepository getPowerRepository() {
        return powerRepository;
    }

    public static BossRepository getBossRepository() {
        return bossRepository;
    }  
}
