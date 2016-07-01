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
public class SurrogateHashTile extends AbstractTile {

    private final ITile surrogatedTile;

    public SurrogateHashTile(ITile surrogatedTile) {
        super(surrogatedTile.getTilesToTraverse(), surrogatedTile.getCoordinate());
        this.surrogatedTile = surrogatedTile;
        init();
    }

    private void init() {
        setTypeOfTile(AppConstants.SURROGATE_HASH);
        setTileValue(0);
        setTileIcon('#');
        setWalkable(false);
    }

    public ITile getSurrogatedTile() {
        return surrogatedTile;
    }

}
