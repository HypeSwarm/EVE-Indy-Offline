/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author HypeSwarm
 */
public class AdjustedPrice {
    private final int typeID;
    private final double adjustedPrice;

    AdjustedPrice(JsonNode json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getTypeID() {
        return typeID;
    }

    public double getAdjustedPrice() {
        return adjustedPrice;
    }

    public AdjustedPrice(int typeID, double adjustedPrice) {
        this.typeID = typeID;
        this.adjustedPrice = adjustedPrice;
    }
}
