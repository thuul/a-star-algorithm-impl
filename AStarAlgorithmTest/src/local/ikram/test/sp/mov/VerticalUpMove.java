/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.test.sp.mov;

import local.ikram.test.AppConstants;
import local.ikram.test.sp.tiles.ITile;

/**
 *
 * @author walle
 */
public class VerticalUpMove extends AbstractTileMove {

    public VerticalUpMove(ITile tile) {
        super(tile);
        init();
    }

    private void init() {
        setMoveType(AppConstants.VERTICAL_UP);
        setReverseMove(AppConstants.VERTICAL_DOWN);
    }
}
