/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import java.util.ArrayList;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public final class JobComponent {
    private final ComponentSettings.ComponentGroup affectingGroupSettings;
    private final int componentID;
    private final int componentGroupID;
    private final int maxBPCRuns;
    private int quantity;
    private final ArrayList<Materials> componentBaseMaterials;
    private final ArrayList<Materials> componentModifiedMaterials=new ArrayList<>();

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        modifyMats();
    }

    public int getComponentID() {
        return componentID;
    }

    public ArrayList getComponentModifiedMaterials() {
        return componentModifiedMaterials;
    }
    
    public JobComponent(int componentID,int quantity){
        this.componentID=componentID;
        this.componentGroupID=SDEDatabase.TYPE_IDS.getGroupID(componentID);
        this.quantity=quantity;
        this.affectingGroupSettings=ComponentSettings.getEffectingGroup(componentGroupID);
        this.maxBPCRuns=SDEDatabase.BLUEPRINTS.getBlueprintByOutputID(componentID).getMaxProductionLimit();
        this.componentBaseMaterials=SDEDatabase.BLUEPRINTS.getBlueprintByOutputID(componentID).getActivities().getManufacturing().getMaterials();
        modifyMats();
    }
    public void modifyMats(){
        componentModifiedMaterials.clear();
        componentBaseMaterials.stream().forEach((mat) -> {
            componentModifiedMaterials.add(new Materials(mat.getTypeID(),mat.getQuantity()));
        });
        double totalMEBonus=affectingGroupSettings.getStructure().structureMEBonus(17,componentGroupID,1);
        int copiesNeeded;
        int remainder;
        if(affectingGroupSettings.isFullBPC()){
            copiesNeeded=(int)Math.ceil(quantity/maxBPCRuns);
            remainder=0;
        }else{
            copiesNeeded=(int)Math.floor(quantity/maxBPCRuns);
            remainder=quantity-(int)Math.floor(quantity/maxBPCRuns);
        }
        componentModifiedMaterials.stream().forEach((mat)->{
            mat.modifyMats(totalMEBonus,maxBPCRuns,copiesNeeded,remainder);
        });
    }
}
