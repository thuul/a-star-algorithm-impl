/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp.tiles;

import java.util.List;
import local.ikram.test.sp.mov.ITileMove;

/**
 *
 * @author walle
 */
public interface ITile {

    /**
     *
     * @return ITileMove
     */
    ITileMove moveToNextTile();

    /**
     *
     * @param tile
     * @return Object
     */
    Object determinePathOnNextMove(ITile tile);

    /**
     *
     * @param tileToMove
     * @param tileToMoveOneStepForwrd
     * @return ITile
     */
    ITile checkForRedundantPath(ITile tileToMove, ITile tileToMoveOneStepForwrd);

    /**
     *
     * @return int
     */
    int calculateManhattanDistance();

    /**
     *
     * @return ITileMove
     */
    ITileMove getHorizontalRightTile();

    /**
     *
     * @return ITileMove
     */
    ITileMove getHorizontalLeftTile();

    /**
     *
     * @return ITileMove
     */
    ITileMove getVerticalDownTile();

    /**
     *
     * @return ITileMove
     */
    ITileMove getVerticalUpTile();

    /**
     *
     * @return ITileMove
     */
    ITileMove getDiagonalDownRightTile();

    /**
     *
     * @return ITileMove
     */
    ITileMove getDiagonalDownLeftTile();

    /**
     *
     * @return ITileMove
     */
    ITileMove getDiagonalUpRightTile();

    /**
     *
     * @return ITileMove
     */
    ITileMove getDiagonalUpLeftTile();

    /**
     *
     * @param tileList
     * @param bestCostTile
     */
    void outputFullHeutistic(List<ITile> tileList, ITile bestCostTile);

    /**
     *
     * @param walkable
     */
    void setWalkable(boolean walkable);

    /**
     *
     * @return boolean
     */
    boolean isWalkable();

    /**
     *
     * @return String
     */
    String getTypeOfTile();

    /**
     *
     * @param typeOfTile
     */
    void setTypeOfTile(String typeOfTile);

    /**
     *
     * @return int
     */
    int getTileValue();

    /**
     *
     * @param tileValue
     */
    void setTileValue(int tileValue);

    /**
     *
     * @return Integer
     */
    Integer getScore();

    /**
     *
     * @param score
     */
    void setScore(Integer score);

    /**
     *
     * @return char
     */
    char getTileIcon();

    /**
     *
     * @param tileIcon
     */
    void setTileIcon(char tileIcon);

    /**
     *
     * @return ITileMove
     */
    ITileMove getTileMove();

    /**
     *
     * @param tileMove
     */
    void setTileMove(ITileMove tileMove);

    /**
     *
     * @return ITile[][]
     */
    ITile[][] getTilesToTraverse();

    /**
     *
     * @return TileCoordinate
     */
    TileCoordinate getCoordinate();

}
