/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp.factory;

import local.ikram.test.sp.tiles.EndTile;
import local.ikram.test.sp.tiles.FlatLandTile;
import local.ikram.test.sp.tiles.ForestTile;
import local.ikram.test.sp.tiles.ITile;
import local.ikram.test.sp.tiles.MountainTile;
import local.ikram.test.sp.tiles.StartTile;
import local.ikram.test.sp.tiles.TileCoordinate;
import local.ikram.test.sp.tiles.WaterTile;

/**
 *
 * @author walle
 */
public class TileObjectFactory {

    private static volatile TileObjectFactory instance = new TileObjectFactory();

    private TileObjectFactory() {
    }

    public static TileObjectFactory getInstance() {
        return instance;
    }

    public ITile createATile(ITile[][] tilesToTraverse, TileCoordinate coordinate, char tileIcon) {
        switch (tileIcon) {
            case '.':
                return new FlatLandTile(tilesToTraverse, coordinate);
            case '*':
                return new ForestTile(tilesToTraverse, coordinate);
            case '^':
                return new MountainTile(tilesToTraverse, coordinate);
            case '~':
                return new WaterTile(tilesToTraverse, coordinate);
            case '@':
                return new StartTile(tilesToTraverse, coordinate);
            case 'X':
                return new EndTile(tilesToTraverse, coordinate);
            default:
                break;
        }
        return null;
    }
}
