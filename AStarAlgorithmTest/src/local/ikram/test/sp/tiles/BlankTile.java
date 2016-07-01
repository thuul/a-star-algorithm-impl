/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp.tiles;

/**
 *
 * @author walle
 */
public class BlankTile extends AbstractTile {

    public BlankTile() {
        super();
        init();
    }

    private void init() {
        setTileValue(0);
        setWalkable(false);
    }

}
