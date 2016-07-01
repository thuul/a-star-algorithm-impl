/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp.tiles;

import local.ikram.test.AppConstants;

/**
 *
 * @author walle
 */
public class WaterTile extends AbstractTile {

    public WaterTile(ITile[][] tilesToTraverse, TileCoordinate coordinate) {
        super(tilesToTraverse, coordinate);
        init();
    }

    private void init() {
        setTypeOfTile(AppConstants.WATER);
        setTileValue(0);
        setTileIcon('~');
        setWalkable(false);
    }

}
