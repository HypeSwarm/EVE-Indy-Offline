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
public final class JobManufacturing extends Job{
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private final ArrayList<Materials> baseMaterials=new ArrayList<>();
    private final ArrayList<Materials> modifiedMaterials=new ArrayList<>();
    private int blueprintME;
    private int blueprintTE;
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

    public int getBlueprintME() {
        return blueprintME;
    }

    public int getBlueprintTE() {
        return blueprintTE;
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

    public void setBlueprintME(int blueprintME) {
        this.blueprintME = blueprintME;
    }

    public void setBlueprintTE(int blueprintTE) {
        this.blueprintTE = blueprintTE;
    }
    

    public JobManufacturing(int blueprintID, int structureID,int blueprintME,int blueprintTE, int runs, int blueprintsUsed) {
        super(COUNT.incrementAndGet(),blueprintID, structureID,1, runs, blueprintsUsed);
        this.baseMaterials.addAll(SDEDatabase.BLUEPRINTS.getIndyMaterials(blueprintID));
        this.blueprintME=blueprintME;
        this.blueprintTE=blueprintTE;
        this.outcomeID=SDEDatabase.BLUEPRINTS.getIndyOutputID(blueprintID);
        this.outcomeName=SDEDatabase.TYPE_IDS.getTypeName(outcomeID);
        this.groupID=SDEDatabase.TYPE_IDS.getGroupID(outcomeID);
        this.categoryID=SDEDatabase.GROUP_IDS.getCategoryID(groupID);
        this.outcomePerRun=SDEDatabase.BLUEPRINTS.getIndyOutputQuantity(blueprintID);
        this.estOutcomeCost=0.0;
        this.outComeMarketValue=0.0;
        this.calcModMaterials();
        this.durationModify();
    }
    
    
    public JobManufacturing(JsonNode json){
        this(json.get("blueprintID").intValue(),json.get("structureID").intValue(),json.get("blueprintME").intValue(),json.get("blueprintTE").intValue(),json.get("runs").intValue(),json.get("blueprintsUsed").intValue());
    }
    
    public void calcModMaterials(){
        modifiedMaterials.clear();
        baseMaterials.stream().forEach((mat) -> {
            modifiedMaterials.add(new Materials(mat.getTypeID(),mat.getQuantity()));
        });
        double totalMEBonus=DataArrayLists.getStructureByID(this.structureID).structureMEBonus(categoryID,groupID,1)*(1-blueprintME*0.01);
        modifiedMaterials.stream().forEach((mats) -> {
            mats.modifyMats(totalMEBonus,this.runs,this.blueprintsUsed);
        });
    }
    
    @Override
    public void durationModify() {
        Structure structure=DataArrayLists.getStructureByID(this.structureID);
        this.durationModified=(int)(duration*(1-blueprintTE*0.01)*structure.structureTEBonus(categoryID,groupID,1)*0.8*0.85)*runs*blueprintsUsed;
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
            this.blueprintME,
            this.blueprintTE,
            "<HTML><center>"+super.ModifiedDurationToString()+"</center></HTML>",
            super.doubleToCurrency(super.installFee),
            super.doubleToCurrency(this.estOutcomeCost),
            super.doubleToCurrency(this.outComeMarketValue)
        };
    }
    
}
