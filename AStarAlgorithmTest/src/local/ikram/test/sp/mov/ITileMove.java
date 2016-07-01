/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp.mov;

import local.ikram.test.sp.tiles.ITile;

/**
 *
 * @author walle
 */
public interface ITileMove {

    /**
     *
     * @return String
     */
    String getMoveType();

    /**
     *
     * @param moveType
     */
    void setMoveType(String moveType);

    /**
     *
     * @return ITile
     */
    ITile getTile();

    /**
     *
     * @param tile
     */
    void setTile(ITile tile);
}
