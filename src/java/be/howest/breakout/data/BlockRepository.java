/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.domain.Block;
import java.util.List;

/**
 *
 * @author jarne
 */
public interface BlockRepository {
    public List<Block> getBlocks();
    public void addBlock(Block b);
    public int getBlockId(Block b);
    
}
