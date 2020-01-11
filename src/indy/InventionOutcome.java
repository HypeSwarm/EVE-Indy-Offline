/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public class InventionOutcome{
    private int blueprintID;
    private int runs;
    private double probability;

    public int getBlueprintID() {
        return blueprintID;
    }

    public int getRuns() {
        return runs;
    }

    public double getProbability() {
        return probability;
    }
    
    public InventionOutcome(JsonNode json) throws IOException{
        try{
            blueprintID=json.get("blueprintID").intValue();
        }catch(NullPointerException e){
            blueprintID=0;
        }
        try{
            runs=json.get("runs").intValue();
        }catch(NullPointerException e){
            runs=0;
        }
        try{
            probability=json.get("probability").doubleValue();
        }catch(NullPointerException e){
            probability=0;
        }
    }

    public InventionOutcome(int blueprintID, int runs, double probability) {
        this.blueprintID = blueprintID;
        this.runs = runs;
        this.probability = probability;
    }

    @Override
    public String toString() {
        return SDEDatabase.TYPE_IDS.getTypeName(blueprintID);
    }
}
