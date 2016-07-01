/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextArea;
import local.ikram.test.sp.mov.ITileMove;
import local.ikram.test.sp.tiles.ITile;

/**
 *
 * @author walle
 */
public class TilePathGraph {

    private static volatile TilePathGraph instance;
    private final Map<Integer, ITileMove> pathTraversedMap = new HashMap<>();

    private ITile startTile;
    private ITile endTile;
    private ITile currentTile;

    private int cummulativeMoveCost;
    private int lastSingularMove;

    private boolean pathSearchCompleted;

    private JTextArea textArea;

    private TilePathGraph() {
    }

    public static TilePathGraph newInstance() {
        synchronized (TilePathGraph.class) {
            if (instance == null) {
                instance = new TilePathGraph();
            }
        }
        return instance;
    }

    public static TilePathGraph getInstance() {
        return instance;
    }

    public Map<Integer, ITileMove> getPathTraversedMap() {
        return pathTraversedMap;
    }

    public int getCummulativeMoveCost() {
        return cummulativeMoveCost;
    }

    public void setCummulativeMoveCost(int cummulativeMoveCost) {
        this.cummulativeMoveCost += cummulativeMoveCost;
    }

    public int getEstimatedMoveCost(int cost) {
        return (cummulativeMoveCost + cost);
    }

    public int getLastSingularMove() {
        return (lastSingularMove + 1);
    }

    public ITile getStartTile() {
        return startTile;
    }

    public void setStartTile(ITile startTile) {
        this.startTile = startTile;
    }

    public ITile getEndTile() {
        return endTile;
    }

    public void setEndTile(ITile endTile) {
        this.endTile = endTile;
    }

    public ITile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(ITile currentTile) {
        this.currentTile = currentTile;
    }

    public boolean isPathSearchCompleted() {
        return pathSearchCompleted;
    }

    public void setPathSearchCompleted(boolean pathSearchCompleted) {
        this.pathSearchCompleted = pathSearchCompleted;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void resetGraph() {
        lastSingularMove = 0;
        cummulativeMoveCost = 0;
        pathSearchCompleted = false;
        pathTraversedMap.clear();
    }

}
