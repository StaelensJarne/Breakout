/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.domain.Block;
import be.howest.breakout.util.BreakoutException;
import org.junit.*;

/**
 *
 * @author jarne
 */
public class MySqlBlockRepositoryTester {
    
    @Test
    public void testGetBlocks() {
        try {
            Repositories.getBlockRepository().getBlocks();
        } catch (BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testAddBlock(){
        try {
            Block b = new Block("paars",22, 20);
            Repositories.getBlockRepository().addBlock(b);
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testGetBlockId(){
        try{
            Block b = new Block("#F7B32B", 75, 40);
            int id = Repositories.getBlockRepository().getBlockId(b);
        } catch(BreakoutException ex){
            Assert.fail();
        }
    }
    
    @Test
    public void testGetBlockId_BLOCK_NOT_EXSIST(){
        try{
            Block b = new Block("zwart");
            Repositories.getBlockRepository().getBlockId(b);
            Assert.fail();
        } catch(BreakoutException ex){}
    }
    
}
