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
public abstract class AbstractTileMove implements ITileMove {

    protected String moveType;
    protected ITile tile;
    
    public AbstractTileMove(ITile tile) {
        this.tile = tile;
        init();
    }

    private void init() {
        tile.setTileMove(this);
    }

    @Override
    public String getMoveType() {
        return moveType;
    }

    @Override
    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    @Override
    public ITile getTile() {
        return tile;
    }

    @Override
    public void setTile(ITile tile) {
        this.tile = tile;
    }

}
