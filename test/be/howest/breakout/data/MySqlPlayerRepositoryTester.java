/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.domain.Player;
import be.howest.breakout.util.BreakoutException;
import org.junit.*;
import org.junit.runners.MethodSorters;

/**
 *
 * @author jarne
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MySqlPlayerRepositoryTester {   
    private Player DatabasePlayer1;
    private Player DatabasePlayer2;
    private Player DatabasePlayer3;
    
    private Player falsePlayer1; //not in database
    private Player falsePlayer2; //not in database
    private Player falsePlayer3; //not in database
   
    @Before
    public void setUp(){
        DatabasePlayer1 = new Player("renke");
        DatabasePlayer2 = new Player("renke2");
        DatabasePlayer3 = new Player("renke3");
        
        falsePlayer1 = new Player("jarne");
        falsePlayer2 = new Player("jarne2");
        falsePlayer3 = new Player("jarne3"); 
    }
    
    @Test
    public void testGetSpecifickPlayer_PLAYER_EXSISTS(){
        Repositories.getPlayerRepository().insertPlayer(DatabasePlayer1); // DatabasePlayer 1 toegevoegd in databank 
        try {        
            String naam = DatabasePlayer1.getNaam();
            Repositories.getPlayerRepository().getSpecifickPlayer(naam); 
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test 
    public void testSingleAndMultiPlayerScore_PLAYER_EXISTS(){  
        Repositories.getPlayerRepository().insertPlayer(DatabasePlayer3);  // DatabasePlayer 3 toegevoegd in databank   
        try {
            Repositories.getPlayerRepository().setSinglePlayerScore(DatabasePlayer3, 200);
            Repositories.getPlayerRepository().setMultiPlayerScore(DatabasePlayer3, 300);
        } catch(BreakoutException ex){
            Assert.fail();
        }       
    }
    
    @Test
    public void testGetPlayers(){
        try {
            for(Player p : Repositories.getPlayerRepository().getPlayers()){
                //System.err.println(p.toString());
            }
        }  catch(BreakoutException ex){
           Assert.fail();
        }
    }
    
    
    @Test
    public void testGetSpecifiekPlayer_PLAYER_NOT_EXISTS(){
        try {
            String naam = falsePlayer1.getNaam();
            Player p = Repositories.getPlayerRepository().getSpecifickPlayer(naam); 
            Assert.assertNull(p);
        } catch(BreakoutException ex){}
    }
    
    
    
    @Test
    public void testSinglePlayerScore_PLAYER_EXISTS(){
        Repositories.getPlayerRepository().insertPlayer(DatabasePlayer2);
        try {
            Repositories.getPlayerRepository().setSinglePlayerScore(DatabasePlayer2, 100);
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testSinglePlayerScore_PLAYER_NOT_EXSISTS(){        
        try {
            Repositories.getPlayerRepository().setSinglePlayerScore(falsePlayer3, 100);
            Assert.fail();
        } catch(BreakoutException ex){}
    }
    
    @Test
    public void testMultiPlayerScore_PLAYER_EXISTS(){
        try {
            Repositories.getPlayerRepository().setMultiPlayerScore(DatabasePlayer1, 100);
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testMultiPlayerScore_PLAYER_NOT_EXSISTS(){
        try {
            Repositories.getPlayerRepository().setMultiPlayerScore(falsePlayer1, 100);
            Assert.fail();
        } catch(BreakoutException ex){}
    }
    
    
    @Test 
    public void testSingleAndMultiPlayerScore_PLAYER_NOT_EXISTS(){
        try {
            Repositories.getPlayerRepository().setSinglePlayerScore(falsePlayer2, 200);
            Repositories.getPlayerRepository().setMultiPlayerScore(falsePlayer2, 300);
            Assert.fail();
        } catch(BreakoutException ex){}      
    }
    
    @Test
    public void testgetSinglePlayerScore_PLAYER_EXISTS(){  // Strange bug: werkt bij een lege databank van de eerste keer
        try {
            Repositories.getPlayerRepository().getSinglePlayerScore(DatabasePlayer2);
        } catch (BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testgetSinglePlayerScore_PLAYER_NOT_EXISTS(){
        try {
            Repositories.getPlayerRepository().getSinglePlayerScore(falsePlayer3);
            Assert.fail();
        } catch (BreakoutException ex){}
    }
    
    @Test
    public void testgetMultiPlayerScore_PLAYER_EXISTS(){ // Strange bug: werkt niet van de eerste keer
        try {
            Repositories.getPlayerRepository().getMultiPlayerScore(DatabasePlayer3);
        } catch (BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testgetMultiPlayerScore_PLAYER_NOT_EXISTS(){
        try {
            Repositories.getPlayerRepository().getMultiPlayerScore(falsePlayer3);
            Assert.fail();
        } catch (BreakoutException ex){}
    }
    
    @Test
    public void testInsertPlayer(){
        try {       
            Repositories.getPlayerRepository().insertPlayer(DatabasePlayer2); // DatabasePlayer 2 toegevoegd in databank 
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
}
