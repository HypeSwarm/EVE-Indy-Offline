/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author HypeSwarm
 */
public class Materials implements Comparable<Materials>{
    private int typeID;
    private int quantity;

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Materials(JsonNode json){
        this.typeID=json.get("typeID").intValue();
        this.quantity=json.get("quantity").intValue();
    }
    
    public Materials(int typeID,int quantity){
        this.typeID=typeID;
        this.quantity=quantity;
    }
    
    public void modifyMats(double totalMEMod,int blueprintRuns,int numBlueprints){
        this.quantity=(int)(numBlueprints*Math.max(blueprintRuns,Math.ceil(Math.round(this.quantity*blueprintRuns*totalMEMod*100.0)/100.0)));
    }

    @Override
    public int compareTo(Materials o) {
        return new Integer(getTypeID()).compareTo(o.getTypeID());
    }
}
