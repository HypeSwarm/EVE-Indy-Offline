/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public class Materials implements Comparable<Materials>{
    private final int typeID;
    private int quantity;
    private final boolean component;

    public int getTypeID() {
        return typeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isComponent() {
        return component;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Materials(JsonNode json){
        this.typeID=json.get("typeID").intValue();
        this.quantity=json.get("quantity").intValue();
        this.component=false;//json.get("component").booleanValue();
    }
    
    public Materials(int typeID,int quantity){
        this.typeID=typeID;
        this.quantity=quantity;
        this.component=false;//getIsComponent(typeID);
    }
    
    private boolean getIsComponent(int typeID){
        return SDEDatabase.GROUP_IDS.getCategoryID(SDEDatabase.TYPE_IDS.getGroupID(typeID))==17;
    }
    
    public void modifyMats(double totalMEMod,int blueprintRuns,int numBlueprints,int remainderRuns){
        this.quantity=(int)(numBlueprints*Math.max(blueprintRuns,Math.ceil(Math.round(this.quantity*blueprintRuns*totalMEMod*100.0)/100.0)));
        if(remainderRuns!=0)this.quantity+=(int)(Math.max(remainderRuns,Math.ceil(Math.round(this.quantity*remainderRuns*totalMEMod*100.0)/100.0)));
    }

    @Override
    public int compareTo(Materials o) {
        return new Integer(getTypeID()).compareTo(o.getTypeID());
    }
}
