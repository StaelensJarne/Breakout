/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.data;

import be.howest.breakout.data.util.MySqlConnection;
import be.howest.breakout.domain.Block;
import be.howest.breakout.util.BreakoutException;
import be.howest.breakout.data.util.SqlConstants;
import be.howest.breakout.util.StringConstants;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarne
 */
public class MySqlBlockRepository extends MySqlConnection implements BlockRepository {

    private static final String COLOR = "color";
    private static final String LENGHT = "lenght";
    private static final String SCORE = "score";
    private static final String BLOCK_ID = "block_id";

    @Override
    public List<Block> getBlocks() {

        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_SELECT_ALL_BLOCKS);
                ResultSet res = prep.executeQuery();) {
            List<Block> blokken = new ArrayList<>();
            while (res.next()) {
                Block b = createBlock(res);
                blokken.add(b);
            }

            return blokken;
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_ALL_BLOCKS, ex);
        }
    }

    @Override
    public void addBlock(Block b) {
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_INSERT_BLOCK);) {
            prep.setString(1, b.getColor());
            prep.setInt(2, b.getLenght());
            prep.setInt(3, b.getScore());
            prep.executeUpdate();
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_ADD_BLOCK, ex);
        }
    }

    @Override
    public int getBlockId(Block b) {
        try (PreparedStatement prep = getConnection().prepareStatement(SqlConstants.SQL_GET_BLOCK_ID)) {
            prep.setString(1, b.getColor());
            Block b2 = null;
            try (ResultSet res = prep.executeQuery()) {
                while (res.next()) {
                    b2 = createBlock(res);
                }
            }
            if (b2 == null) {
                throw new BreakoutException(StringConstants.UNABLE_TO_GET_BLOCK_BY_ID);
            } else {
                return b2.getId();
            }
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_GET_BLOCK_BY_ID, ex);
        }
    }

    private Block createBlock(ResultSet res) {
        try {
            return new Block(res.getInt(BLOCK_ID),res.getString(COLOR), res.getInt(LENGHT),res.getInt(SCORE));
        } catch (SQLException ex) {
            throw new BreakoutException(StringConstants.UNABLE_TO_CREATE_BLOCK, ex);
        }
    }

}
