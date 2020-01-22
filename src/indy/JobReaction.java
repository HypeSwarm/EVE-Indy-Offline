/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public final class JobReaction extends Job {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final ArrayList<Materials> baseMaterials;
    private ArrayList<Materials> modifiedMaterials;
    private final int outcomeID;
    private final String outcomeName;
    private final int categoryID;
    private final int groupID;
    private final int outcomePerRun;
    private double estOutcomeCost;
    private double outComeMarketValue;
    
    public ArrayList<Materials> getBaseMaterials() {
        return baseMaterials;
    }

    public ArrayList<Materials> getModifiedMaterials() {
        return modifiedMaterials;
    }

    public int getOutcomeID() {
        return outcomeID;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public int getOutcomePerRun() {
        return outcomePerRun;
    }

    public double getEstOutcomeCost() {
        return estOutcomeCost;
    }

    public void setEstOutcomeCost(double estOutcomeCost) {
        this.estOutcomeCost = estOutcomeCost;
    }

    public double getOutComeMarketValue() {
        return outComeMarketValue;
    }

    public void setOutComeMarketValue(double outComeMarketValue) {
        this.outComeMarketValue = outComeMarketValue;
    }

    public JobReaction(int blueprintID, int structureID, int runs, int blueprintsUsed) {
        super(count.incrementAndGet(),blueprintID, structureID, 11, runs, blueprintsUsed);
        this.baseMaterials=SDEDatabase.BLUEPRINTS.getReactionMaterials(blueprintID);
        this.outcomeID=SDEDatabase.BLUEPRINTS.getManufactoringOutputID(blueprintID);
        this.outcomeName=SDEDatabase.TYPE_IDS.getTypeName(outcomeID);
        this.groupID=SDEDatabase.TYPE_IDS.getGroupID(outcomeID);
        this.categoryID=SDEDatabase.GROUP_IDS.getCategoryID(groupID);
        this.outcomePerRun=SDEDatabase.BLUEPRINTS.getReactionOutputQuantity(blueprintID);
        this.estOutcomeCost=0.0;
        this.outComeMarketValue=0.0;
        this.calcModMaterials();
        this.durationModify();
    }
    
    JobReaction(JsonNode json){
        this(json.get("blueprintID").intValue(),json.get("structureID").intValue(),json.get("runs").intValue(),json.get("blueprintsUsed").intValue());
    }
    
    public void calcModMaterials(){
        modifiedMaterials=baseMaterials;
        Structure structure=DataArrayLists.getStructureByID(this.structureID);
        double totalMEBonus=structure.structureMEBonus(categoryID, groupID, categoryID);
        for(Materials mats:modifiedMaterials){
            mats.modifyMats(totalMEBonus,this.runs,this.blueprintsUsed);
        }
    }
    
    @Override
    public void durationModify() {
        Structure structure=DataArrayLists.getStructureByID(this.structureID);
        this.durationModified=(int)(this.duration*structure.structureTEBonus(categoryID, groupID,11)*runs*blueprintsUsed);
    }
    
    @Override
    public Object[] tableData() {
        return new Object[] {
            this.jobID,
            super.getBlueprintName(),
            super.getStructureName(),
            super.runs,
            super.blueprintsUsed,
            super.blueprintsUsed*super.runs*this.outcomePerRun,
            "<HTML><center>"+super.ModifiedDurationToString()+"</center></HTML>",
            super.doubleToCurrency(super.installFee),
            super.doubleToCurrency(this.estOutcomeCost),
            super.doubleToCurrency(this.outComeMarketValue)
        };
    }
}
