/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esi;



/**
 *
 * @author HypeSwarm
 */
public class AssetsCharacter extends Assets{
    private final int characterID;

    public int getCharacterID() {
        return characterID;
    }

    public AssetsCharacter(int characterID, boolean bluePrintCopy, boolean singleton, int itemID, String locationFlag, int locationID, String locationType, int quantity, int typeID) {
        super(bluePrintCopy, singleton, itemID, locationFlag, locationID, locationType, quantity, typeID);
        this.characterID = characterID;
    }
}
