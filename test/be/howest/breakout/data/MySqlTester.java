/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author jarne
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({be.howest.breakout.data.MySqlPlayerRepositoryTester.class, be.howest.breakout.data.MySqlBlockRepositoryTester.class, be.howest.breakout.data.MySqlPowerRepositoryTester.class, be.howest.breakout.data.MySqlBossRepositoryTester.class})
public class MySqlTester {
    
}
