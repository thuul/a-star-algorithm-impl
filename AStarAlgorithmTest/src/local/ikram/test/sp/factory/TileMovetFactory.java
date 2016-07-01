/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp.factory;

import local.ikram.test.AppConstants;
import local.ikram.test.sp.mov.DiagonalDownLeftMove;
import local.ikram.test.sp.mov.DiagonalDownRightMove;
import local.ikram.test.sp.mov.HorizontalLeftMove;
import local.ikram.test.sp.mov.HorizontalRightMove;
import local.ikram.test.sp.mov.ITileMove;
import local.ikram.test.sp.mov.VerticalDownMove;
import local.ikram.test.sp.tiles.ITile;

/**
 *
 * @author walle
 */
public class TileMovetFactory {

    private static volatile TileMovetFactory instance;

    private TileMovetFactory() {
    }

    public static TileMovetFactory newInstance() {
        synchronized (TileMovetFactory.class) {
            if (instance == null) {
                instance = new TileMovetFactory();
            }
        }
        return instance;
    }

    public static TileMovetFactory getInstance() {
        return instance;
    }

    public ITileMove createTileMove(String moveType, ITile tile) {
        switch (moveType) {
            case AppConstants.HORIZONTAL_RIGHT:
                return new HorizontalRightMove(tile);
            case AppConstants.HORIZONTAL_LEFT:
                return new HorizontalLeftMove(tile);
            case AppConstants.VERTICAL_DOWN:
                return new VerticalDownMove(tile);
            case AppConstants.DIAGONAL_DOWN_RIGHT:
                return new DiagonalDownRightMove(tile);
            case AppConstants.DIAGONAL_DOWN_LEFT:
                return new DiagonalDownLeftMove(tile);
            default:
                break;
        }
        return null;
    }
}
