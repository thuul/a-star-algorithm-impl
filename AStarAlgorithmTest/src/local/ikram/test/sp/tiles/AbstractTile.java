/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp.tiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import local.ikram.test.AppConstants;
import local.ikram.test.sp.TilePathGraph;
import local.ikram.test.sp.mov.BlankTileMove;
import local.ikram.test.sp.mov.DiagonalDownLeftMove;
import local.ikram.test.sp.mov.DiagonalDownRightMove;
import local.ikram.test.sp.mov.DiagonalUpLeftMove;
import local.ikram.test.sp.mov.DiagonalUpRightMove;
import local.ikram.test.sp.mov.HorizontalLeftMove;
import local.ikram.test.sp.mov.HorizontalRightMove;
import local.ikram.test.sp.mov.ITileMove;
import local.ikram.test.sp.mov.VerticalDownMove;
import local.ikram.test.sp.mov.VerticalUpMove;

/**
 *
 * @author walle
 */
public abstract class AbstractTile implements ITile {

    protected ITile[][] tilesToTraverse;
    protected TileCoordinate coordinate;
    protected ITileMove tileMove;
    protected String typeOfTile;
    protected boolean walkable;
    protected char tileIcon;
    protected int tileValue;
    protected Integer score;

    public AbstractTile() {
    }

    public AbstractTile(ITile[][] tilesToTraverse, TileCoordinate coordinate) {
        this.tilesToTraverse = tilesToTraverse;
        this.coordinate = coordinate;
    }

    @Override
    public ITileMove moveToNextTile() {

        List<ITile> tileList = new ArrayList<>();
        TilePathGraph instance = TilePathGraph.getInstance();

        tileList.add(getDiagonalDownRightTile().getTile());
        tileList.add(getVerticalDownTile().getTile());
        tileList.add(getHorizontalRightTile().getTile());
        tileList.add(getVerticalUpTile().getTile());
        tileList.add(getDiagonalDownLeftTile().getTile());
        tileList.add(getHorizontalLeftTile().getTile());
        tileList.add(getDiagonalUpLeftTile().getTile());
        tileList.add(getDiagonalUpRightTile().getTile());

        Iterator<ITile> iterator = tileList.iterator();
        while (iterator.hasNext()) {
            ITile next = iterator.next();
            if (next == null || !next.isWalkable()) {
                iterator.remove();
            }
        }

        if (tileList.isEmpty()) {
            return null;
        }

        tileList.sort((ITile o1, ITile o2) -> {
            return o1.getScore().compareTo(o2.getScore());
        });

        /**
         * provision for a loop structure that allows each object in the list to
         * be evaluated if the need arises. The point from line 92 to the exit
         * point in the method must be evaluated within the bounds of that loop.
         *
         */
        for (ITile bestCostTile : tileList) {

            if (bestCostTile instanceof EndTile) {
                instance.setPathSearchCompleted(true);
            } else {
                Object nextOnMove;
                boolean nextMovePathUnwalkable = false;

                if ((nextOnMove = determinePathOnNextMove(bestCostTile)) != null) {
                    if (nextOnMove instanceof Boolean) {
                        nextMovePathUnwalkable = (boolean) nextOnMove;
                    } else {
                        bestCostTile = (ITile) nextOnMove;
                    }
                }
                if (nextMovePathUnwalkable) {
                    if (tileList.size() > 1) {
                        continue;
                    }
                    break;
                }
            }
            instance.setCummulativeMoveCost(bestCostTile.getTileValue());
            instance.setCurrentTile(bestCostTile);
            ITileMove innerTileMove = bestCostTile.getTileMove();
            instance.getPathTraversedMap().put(instance.getLastSingularMove(), innerTileMove);

            outputFullHeuristics(tileList, bestCostTile);

            return innerTileMove;
        }
        return null;
    }

    @Override
    public Object determinePathOnNextMove(ITile tile) {

        List<ITile> tileList = new ArrayList<>();

        tileList.add(tile.getDiagonalDownRightTile().getTile());
        tileList.add(tile.getVerticalDownTile().getTile());
        tileList.add(tile.getHorizontalRightTile().getTile());
        tileList.add(tile.getVerticalUpTile().getTile());
        tileList.add(tile.getDiagonalDownLeftTile().getTile());
        tileList.add(tile.getHorizontalLeftTile().getTile());
        tileList.add(tile.getDiagonalUpLeftTile().getTile());
        tileList.add(tile.getDiagonalUpRightTile().getTile());

        Iterator<ITile> iterator = tileList.iterator();
        while (iterator.hasNext()) {
            ITile next = iterator.next();
            if (tile.getTileMove().getReverseMove()
                    .equals(next.getTileMove().getMoveType())
                    || (!next.isWalkable())) {
                iterator.remove();
            }
        }

        if (tileList.isEmpty()) {
            return true;
        }
        tileList.sort((ITile o1, ITile o2) -> {
            return o1.getScore().compareTo(o2.getScore());
        });
        ITile bestCostTile = tileList.get(0);

        return checkForRedundantPath(tile, bestCostTile);
    }

    @Override
    public ITile checkForRedundantPath(ITile tileToMove, ITile bestCostTile) {
        if ((tileToMove.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_RIGHT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.VERTICAL_DOWN))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_LEFT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.VERTICAL_DOWN))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_RIGHT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.VERTICAL_UP))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_LEFT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.VERTICAL_UP))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_RIGHT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.DIAGONAL_DOWN_LEFT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_LEFT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.DIAGONAL_DOWN_RIGHT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_RIGHT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.DIAGONAL_UP_LEFT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_LEFT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.DIAGONAL_UP_RIGHT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.VERTICAL_DOWN)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_RIGHT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.VERTICAL_DOWN)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_LEFT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.VERTICAL_UP)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_RIGHT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.VERTICAL_UP)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_LEFT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.DIAGONAL_DOWN_LEFT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_RIGHT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.DIAGONAL_DOWN_RIGHT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_LEFT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.DIAGONAL_UP_LEFT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_RIGHT))
                || (tileToMove.getTileMove().getMoveType().equals(AppConstants.DIAGONAL_UP_RIGHT)
                && bestCostTile.getTileMove().getMoveType().equals(AppConstants.HORIZONTAL_LEFT))) {
            return bestCostTile;
        }
        return tileToMove;
    }

    @Override
    public int calculateManhattanDistance() {
        return Math.abs((tilesToTraverse.length - 1) - coordinate.getX()) + Math.abs((tilesToTraverse[0].length - 1) - coordinate.getY());
    }

    public boolean verifyArrayIndexBound(ITile[][] tilesToTraverse) {
        return false;
    }

    @Override
    public ITileMove getHorizontalRightTile() {
        try {
            return new HorizontalRightMove(tilesToTraverse[coordinate.getX()][coordinate.getY() + 1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(AbstractTile.class.getName()).info("Array Index Issue @ coordinate beyond :- ".concat(String.valueOf(coordinate.getY())));
        }
        return new BlankTileMove(new BlankTile());
    }

    @Override
    public ITileMove getHorizontalLeftTile() {
        try {
            return new HorizontalLeftMove(tilesToTraverse[coordinate.getX()][coordinate.getY() - 1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(AbstractTile.class.getName()).info("Array Index Issue @ coordinate beyond :- ".concat(String.valueOf(coordinate.getY())));
        }
        return new BlankTileMove(new BlankTile());
    }

    @Override
    public ITileMove getVerticalDownTile() {
        try {
            return new VerticalDownMove(tilesToTraverse[coordinate.getX() + 1][coordinate.getY()]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(AbstractTile.class.getName()).info("Array Index Issue @ coordinate beyond :- ".concat(String.valueOf(coordinate.getY())));
        }
        return new BlankTileMove(new BlankTile());
    }

    @Override
    public ITileMove getVerticalUpTile() {
        try {
            return new VerticalUpMove(tilesToTraverse[coordinate.getX() - 1][coordinate.getY()]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(AbstractTile.class.getName()).info("Array Index Issue @ coordinate beyond :- ".concat(String.valueOf(coordinate.getY())));
        }
        return new BlankTileMove(new BlankTile());
    }

    @Override
    public ITileMove getDiagonalDownRightTile() {
        try {
            return new DiagonalDownRightMove(tilesToTraverse[coordinate.getX() + 1][coordinate.getY() + 1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(AbstractTile.class.getName()).info("Array Index Issue @ coordinate beyond :- ".concat(String.valueOf(coordinate.getY())));
        }
        return new BlankTileMove(new BlankTile());
    }

    @Override
    public ITileMove getDiagonalDownLeftTile() {
        try {
            return new DiagonalDownLeftMove(tilesToTraverse[coordinate.getX() + 1][coordinate.getY() - 1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(AbstractTile.class.getName()).info("Array Index Issue @ coordinate beyond :- ".concat(String.valueOf(coordinate.getY())));
        }
        return new BlankTileMove(new BlankTile());
    }

    @Override
    public ITileMove getDiagonalUpRightTile() {
        try {
            return new DiagonalUpRightMove(tilesToTraverse[coordinate.getX() - 1][coordinate.getY() + 1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(AbstractTile.class.getName()).info("Array Index Issue @ coordinate beyond :- ".concat(String.valueOf(coordinate.getY())));
        }
        return new BlankTileMove(new BlankTile());
    }

    @Override
    public ITileMove getDiagonalUpLeftTile() {
        try {
            return new DiagonalUpLeftMove(tilesToTraverse[coordinate.getX() - 1][coordinate.getY() - 1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(AbstractTile.class.getName()).info("Array Index Issue @ coordinate beyond :- ".concat(String.valueOf(coordinate.getY())));
        }
        return new BlankTileMove(new BlankTile());
    }

    @Override
    public void outputFullHeuristics(List<ITile> tileList, ITile bestCostTile) {
        TilePathGraph instance = TilePathGraph.getInstance();
        instance.getTextArea().append("\n\n\n==========================================\n");
        instance.getTextArea().append("Path Heuristic evaluation\n\n");

        int i = 0;
        for (ITile tile : tileList) {
            instance.getTextArea().append(String.valueOf(i).concat("\n"));
            instance.getTextArea().append(String.valueOf(tile.getTileValue()).concat("\n"));
            instance.getTextArea().append(tile.getScore().toString().concat("\n"));
            instance.getTextArea().append(tile.getTileMove().getMoveType().concat("\n"));
            i++;
        }
        instance.getTextArea().append("==========================================\n");
        instance.getTextArea().append(bestCostTile.getTileMove().getMoveType().concat("\n"));
        instance.getTextArea().append("==========================================\n");
    }

    @Override
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    @Override
    public boolean isWalkable() {
        return walkable;
    }

    @Override
    public String getTypeOfTile() {
        return typeOfTile;
    }

    @Override
    public void setTypeOfTile(String typeOfTile) {
        this.typeOfTile = typeOfTile;
    }

    @Override
    public int getTileValue() {
        return tileValue;
    }

    @Override
    public void setTileValue(int tileValue) {
        this.tileValue = tileValue;
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public char getTileIcon() {
        return tileIcon;
    }

    @Override
    public void setTileIcon(char tileIcon) {
        this.tileIcon = tileIcon;
    }

    @Override
    public ITileMove getTileMove() {
        return tileMove;
    }

    @Override
    public void setTileMove(ITileMove tileMove) {
        this.tileMove = tileMove;
    }

    @Override
    public ITile[][] getTilesToTraverse() {
        return tilesToTraverse;
    }

    @Override
    public TileCoordinate getCoordinate() {
        return coordinate;
    }

}
