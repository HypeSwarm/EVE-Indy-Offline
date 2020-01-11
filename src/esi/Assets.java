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
public class Assets {
    private final boolean bluePrintCopy;
    private final boolean singleton;
    private final int itemID;
    private final String locationFlag;
    private final int locationID;
    private final String locationType;
    private final int quantity;
    private final int typeID;

    public boolean isBluePrintCopy() {
        return bluePrintCopy;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public int getItemID() {
        return itemID;
    }

    public String getLocationFlag() {
        return locationFlag;
    }

    public int getLocationID() {
        return locationID;
    }

    public String getLocationType() {
        return locationType;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTypeID() {
        return typeID;
    }

    public Assets(boolean bluePrintCopy, boolean singleton, int itemID, String locationFlag, int locationID, String locationType, int quantity, int typeID) {
        this.bluePrintCopy = bluePrintCopy;
        this.singleton = singleton;
        this.itemID = itemID;
        this.locationFlag = locationFlag;
        this.locationID = locationID;
        this.locationType = locationType;
        this.quantity = quantity;
        this.typeID = typeID;
    }
}
