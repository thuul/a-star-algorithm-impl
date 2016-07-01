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
public class ForestTile extends AbstractTile {

    public ForestTile(ITile[][] tilesToTraverse, TileCoordinate coordinate) {
        super(tilesToTraverse, coordinate);
        init();
    }

    private void init() {
        setTypeOfTile(AppConstants.FOREST);
        setTileValue(2);
        setTileIcon('*');
        setWalkable(true);
        setScore(calculateManhattanDistance() + getTileValue());
    }

}
