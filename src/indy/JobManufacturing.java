/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Collections;
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
    private final ArrayList<Materials> componentsNeed=new ArrayList<>();
    private final ArrayList<JobComponent> components=new ArrayList<>();
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
    
    public ArrayList<Materials> getRawModifiedMaterials(){
        ArrayList<Materials> rawMats=new ArrayList<>();
        modifiedMaterials.stream().filter((mat) -> (!mat.isComponent())).forEach((mat) -> {
            rawMats.add(new Materials(mat.getTypeID(),mat.getQuantity()));
        });
        components.stream().forEach((component) -> {
            ArrayList<Materials>componentMats=component.getComponentModifiedMaterials();
            componentMats.stream().forEach((mat) -> {
                if(rawMats.isEmpty()){
                    rawMats.add(new Materials(mat.getTypeID(),mat.getQuantity()));
                }else{
                    boolean isAdded=false;
                    for(Materials raw:rawMats){
                        if(raw.getTypeID()==mat.getTypeID()){
                            raw.setQuantity(raw.getQuantity()+mat.getQuantity());
                            isAdded=true;
                        }
                    }
                    if(!(isAdded))rawMats.add(new Materials(mat.getTypeID(),mat.getQuantity()));
                }
            });
        });
        Collections.sort(rawMats);
        return rawMats;
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
    

    public JobManufacturing(int blueprintID, Structure structure,int blueprintME,int blueprintTE, int runs, int blueprintsUsed) {
        super(COUNT.incrementAndGet(),blueprintID, structure,1, runs, blueprintsUsed);
        this.baseMaterials.addAll(SDEDatabase.BLUEPRINTS.getManufactoringMaterials(blueprintID));
        this.blueprintME=blueprintME;
        this.blueprintTE=blueprintTE;
        this.outcomeID=SDEDatabase.BLUEPRINTS.getManufactoringOutputID(blueprintID);
        this.outcomeName=SDEDatabase.TYPE_IDS.getTypeName(outcomeID);
        this.groupID=SDEDatabase.TYPE_IDS.getGroupID(outcomeID);
        this.categoryID=SDEDatabase.GROUP_IDS.getCategoryID(groupID);
        this.outcomePerRun=SDEDatabase.BLUEPRINTS.getManufactoringOutputQuantity(blueprintID);
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
        double totalMEBonus=this.structure.structureMEBonus(categoryID,groupID,1)*(1-blueprintME*0.01);
        modifiedMaterials.stream().forEach((mats) -> {
            mats.modifyMats(totalMEBonus,this.runs,this.blueprintsUsed,0);
        });
        calcComponentMats();
    }
    
    private void calcComponentMats(){
        componentsNeed.clear();
        //Update the component quantities after ModifyMats is ran
        modifiedMaterials.stream().filter((mat) -> (mat.isComponent())).forEach((mat) -> {
            componentsNeed.add(mat);
        });
        //Only true on first run
        //Adds all the component Jobs
        if(components.isEmpty()){
            componentsNeed.stream().forEach((component) -> {
                components.add(new JobComponent(component.getTypeID(),component.getQuantity()));
            });
        //Updates the quantity of the previous component jobs
        }else{
            componentsNeed.stream().forEach((component) -> {
                components.stream().filter((job) -> (job.getComponentID()==component.getTypeID())).forEach((job) -> {
                    job.setQuantity(component.getQuantity());
                });
            });
        }
    }
        
    @Override
    public void durationModify() {
        this.durationModified=(int)(duration*(1-blueprintTE*0.01)*structure.structureTEBonus(categoryID,groupID,1)*0.8*0.85)*runs*blueprintsUsed;
    }
    
    @Override
    public Object[] tableData() {
        return new Object[] {
            this.jobID,
            super.getBlueprintName(),
            super.structure.getStructureName(),
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
