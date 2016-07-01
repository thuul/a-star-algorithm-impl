/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import local.ikram.test.sp.factory.TileObjectFactory;
import local.ikram.test.sp.mov.ITileMove;
import local.ikram.test.sp.tiles.EndTile;
import local.ikram.test.sp.tiles.ITile;
import local.ikram.test.sp.tiles.SurrogateHashTile;
import local.ikram.test.sp.tiles.TileCoordinate;

/**
 *
 * @author walle
 */
public class PathAlgorithmEngine {

    public void searchHashPathToX(String fileName, String startTileIcon, String endTileIcon) {

        TilePathGraph.getInstance().getTextArea().setText("");
        TilePathGraph.getInstance().getTextArea().append("path search initiating ....\n\n\n");

        int numOfCharPerLine;
        int numOfLineOnMap = 0;
        List<String> strList = new ArrayList<>();

        TilePathGraph instance = TilePathGraph.getInstance();

        try (BufferedReader reader = new BufferedReader(fileName != null ? new FileReader(fileName)
                : new InputStreamReader(getClass().getResourceAsStream("large_map.txt"), Charset.forName("UTF8")))) {
            try {
                String s;
                while ((s = reader.readLine()) != null) {
                    strList.add(s);
                    numOfLineOnMap++;
                }
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(PathAlgorithmEngine.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }

        numOfCharPerLine = strList.get(0).length();
        int i = 0;
        char[][] stringToChar = new char[numOfLineOnMap][numOfCharPerLine];
        for (String s : strList) {
            stringToChar[i] = s.toCharArray();
            i++;
        }

        boolean startTileKnown = false;
        boolean endTileKnown = false;

        ITile[][] pathToTraverse = new ITile[numOfLineOnMap][numOfCharPerLine];
        for (i = 0; i < pathToTraverse.length; i++) {
            for (int j = 0; j < pathToTraverse[i].length; j++) {
                char iconChar = stringToChar[i][j];
                pathToTraverse[i][j] = TileObjectFactory.getInstance().createATile(pathToTraverse, new TileCoordinate(i, j), iconChar);
                if (!startTileKnown) {
                    if (String.valueOf(iconChar).equals(startTileIcon)) {
                        instance.setStartTile(pathToTraverse[i][j]);
                        startTileKnown = true;
                    }
                }
                if (!endTileKnown) {
                    if (String.valueOf(iconChar).equals(endTileIcon)) {
                        instance.setEndTile(pathToTraverse[i][j]);
                        endTileKnown = true;
                    }
                }
            }
        }

        ITile startTile = instance.getStartTile();
        outputText(startTile);

        ITileMove moveToNextTile = instance.getStartTile().moveToNextTile();
        outputText(moveToNextTile.getTile());
        replaceTileWithSurrogate(moveToNextTile);

        while (true) {
            if (instance.isPathSearchCompleted()) {
                break;
            }
            moveToNextTile = moveToNextTile.getTile().moveToNextTile();
            if (moveToNextTile == null) {
                break;
            }
            outputText(moveToNextTile.getTile());

            replaceTileWithSurrogate(moveToNextTile);
        }
        writeResultToFile(pathToTraverse, fileName);
        instance.getTextArea().append("\nCummulative Path Cost: ".concat(String.valueOf(instance.getCummulativeMoveCost())));
        instance.getTextArea().append("\n\ncompleted");
        instance.resetGraph();
    }

    public void replaceTileWithSurrogate(ITileMove tileMove) {
        ITile tile = tileMove.getTile();
        if (tile instanceof EndTile) {
            return;
        }
        SurrogateHashTile surroTile = new SurrogateHashTile(tile);
        surroTile.getTilesToTraverse()[surroTile.getCoordinate().getX()][surroTile.getCoordinate().getY()] = surroTile;
    }

    private void writeResultToFile(ITile[][] pathToTraverse, String resultFile) {

        try (FileOutputStream fous = new FileOutputStream(resultFile != null
                ? new File(new File(resultFile).getParentFile(), "result_map.txt").getAbsolutePath() : "result_map.txt")) {

            try {
                for (ITile[] pathToTraverseV : pathToTraverse) {
                    for (ITile pathToTraverseH : pathToTraverseV) {
                        fous.write(pathToTraverseH.getTileIcon());
                    }
                    fous.write(System.getProperty("line.separator").getBytes());
                }
            } finally {
                fous.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(PathAlgorithmEngine.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    public void initPathSearch(File selectedFile) {
        Thread thread = new Thread(() -> {
            searchHashPathToX(selectedFile != null ? selectedFile.getAbsolutePath() : null, "@", "X");
        });
        thread.start();
    }

    private void outputText(ITile tile) {
        TilePathGraph.getInstance().getTextArea().append("Path :- ".concat(String.valueOf(tile.getTileIcon()))
                .concat(" @ [ ").concat(String.valueOf(tile.getCoordinate().getX())).concat(" ]")
                .concat("[ ").concat(String.valueOf(tile.getCoordinate().getY())).concat(" ]").concat("\n"));
    }

}
