/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.DefaultComboBoxModel;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public final class JobInvention extends Job{
    private static final int[] SHIP_BASE_STATS={1,2,4};
    private static final int[] MODULE_BASE_STATS={10,2,4};    
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private final ArrayList<Materials> dataCores;
    private final ArrayList<InventionOutcome> inventionOutcomes;
    private final int categoryID;
    private final int groupID;
    private InventionOutcome selectedOutcome;
    private Decryptor selectedDecryptor;
    private int modME;
    private int modTE;
    private int modRuns;
    private double modProbability;

    public ArrayList<Materials> getDataCores() {
        return dataCores;
    }

    public ArrayList<InventionOutcome> getInventionOutcomes() {
        return inventionOutcomes;
    }

    public InventionOutcome getSelectedOutcome() {
        return selectedOutcome;
    }

    public Decryptor getSelectedDecryptor() {
        return selectedDecryptor;
    }

    public int getModME() {
        return modME;
    }

    public int getModTE() {
        return modTE;
    }

    public int getModRuns() {
        return modRuns;
    }

    public double getModProbability() {
        return modProbability;
    }

    public void setSelectedOutcome(InventionOutcome selectedOutcome) {
        this.selectedOutcome = selectedOutcome;
    }

    public void setSelectedDecryptor(Decryptor selectedDecryptor) {
        this.selectedDecryptor = selectedDecryptor;
    }

    
    public JobInvention(int blueprintID, int structureID, int runs,InventionOutcome selectedOutcome,Decryptor selectedDecryptor) {
        super(COUNT.incrementAndGet(),blueprintID, structureID,8, runs, 0);
        this.selectedOutcome=selectedOutcome;
        this.dataCores=SDEDatabase.BLUEPRINTS.getInventionMaterials(blueprintID);
        this.inventionOutcomes=SDEDatabase.BLUEPRINTS.getInventionOutputs(blueprintID);
        this.groupID=SDEDatabase.TYPE_IDS.getGroupID(blueprintID);
        this.categoryID=SDEDatabase.GROUP_IDS.getCategoryID(blueprintID);
        this.selectedDecryptor=selectedDecryptor;
        calcDecriptorModified();
        durationModify();
    }
    
    public JobInvention(JsonNode json) throws IOException {
        this(json.get("blueprintID").intValue(),json.get("structureID").intValue(),json.get("runs").intValue(),new InventionOutcome(json.get("selectedOutcome")),new Decryptor(json.get("selectedDecryptor")));
    }
        
    public void calcDecriptorModified(){
        if(SDEDatabase.GROUP_IDS.getCategoryID(SDEDatabase.TYPE_IDS.getGroupID(SDEDatabase.BLUEPRINTS.getManufactoringOutputID(blueprintID)))==6){
            modRuns=SHIP_BASE_STATS[0];
            modME=SHIP_BASE_STATS[1];
            modTE=SHIP_BASE_STATS[2];
        }else{
            modRuns=MODULE_BASE_STATS[0];
            modME=MODULE_BASE_STATS[1];
            modTE=MODULE_BASE_STATS[2];
        }
        modProbability=selectedOutcome.getProbability()*(1+((5/40)+((5+5)/30)));
        if(selectedDecryptor!=null){
            modRuns=modRuns+selectedDecryptor.getRunsMod();
            modME=modME+selectedDecryptor.getMeMod();
            modTE=modTE+selectedDecryptor.getTeMod();
            modProbability=selectedOutcome.getProbability()*(1+((5/40)+((5+5)/30)))*selectedDecryptor.getProbabilityMod();
        }
    }
    
    public DefaultComboBoxModel inventionPosibilities(){
        DefaultComboBoxModel options = new DefaultComboBoxModel();
        inventionOutcomes.stream().forEach((outcome) -> {
            options.addElement(outcome);
        });
        return options;
    }
    
    public String probabilityToString(){
        int round1=(int)(this.modProbability*10000);
        return((double)round1/100)+"%";
    }
    
    @Override
    public void durationModify() {
        Structure structure=DataArrayLists.getStructureByID(this.structureID);
        this.durationModified=(int)(this.duration*structure.structureTEBonus(categoryID,groupID,8)*super.runs);
    }

    @Override
    public Object[] tableData() {
        return new Object[]{
            super.jobID,
            super.getBlueprintName(),
            super.getStructureName(),
            SDEDatabase.TYPE_IDS.getTypeName(this.selectedOutcome.getBlueprintID()),
            this.selectedDecryptor.getName(),
            super.runs,
            super.ModifiedDurationToString(),
            probabilityToString(),
            this.modRuns,
            this.modME,
            this.modTE,
            super.doubleToCurrency(super.installFee)
        };
    }

    public int getOutcomeIndex(){
        for(int i=0;i<inventionOutcomes.size();i++){
            if(inventionOutcomes.get(i).getBlueprintID()==selectedOutcome.getBlueprintID()){
                return i;
            }
        }
        return 0;
    }

    public int getDecryptorIndex() {
        if (this.selectedDecryptor==null)return 0;
        for(int i=0;i<Decryptor.DECRYPTORS.size();i++){
            if(Decryptor.DECRYPTORS.get(i).getItemID()==selectedDecryptor.getItemID()){
                return i+1;
            }
        }
        return 0;
    }
}
