/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public class Structure{
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private final int structureID;
    private String structureName;
    private final int systemID;
    private final String structureType;
    private final int structureTypeID;
    private final String secLoc;
    private Rig[] rigs;
    private double tax;

    public int getStructureID() {
        return structureID;
    }

    public String getStructureName() {
        return structureName;
    }

    public int getSystemID() {
        return systemID;
    }

    public String getStructureType() {
        return structureType;
    }

    public int getStructureTypeID() {
        return structureTypeID;
    }

    public String getSecLoc() {
        return secLoc;
    }

    public Rig[] getRigs() {
        return rigs;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setRigs(Rig[] rigs) {
        this.rigs = rigs;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Structure(int structureID, String structureName, int systemID, String structureType, String secLoc, Rig[] rigs,double tax) {
        if(structureID!=0){
            this.structureID = structureID;
        }else{
            this.structureID=COUNT.incrementAndGet();
        }
        this.structureName = structureName;
        this.systemID = systemID;
        this.structureType = structureType;
        this.structureTypeID=SDEDatabase.TYPE_IDS.getTypeID(structureType);
        this.secLoc = secLoc;
        this.rigs = rigs;
        this.tax=tax;
    }
    
    public Structure(JsonNode json) throws IOException {
        this(json.get("structureID").intValue(),json.get("structureName").toString().replace("\"",""),json.get("systemID").intValue(),json.get("structureType").toString().replace("\"",""),json.get("secLoc").toString().replace("\"",""),null,json.get("tax").doubleValue());
        JsonNode rigNode=json.get("rigs");
        Rig[] rigsArray=new Rig[3];
        int indexRig=0;
        for(JsonNode objNode :rigNode){
            if(!"null".equals(objNode.toString())){
                rigsArray[indexRig]=new Rig(new ObjectMapper().readTree(objNode.toString()).get("rigID").intValue());
            }else{
                rigsArray[indexRig]=null;
            }
            indexRig++;
        }
        this.rigs=rigsArray;
    }
    
    public double structureMEBonus(int categoryID, int groupID,int activityID){
        double bonus=1.0;
        for(Rig rig:rigs){
            if(rig!=null){
                if(rig.isBonused(categoryID,groupID,"ME")){
                    bonus=bonus*(1-rig.getTotalRigBonus(secLoc,"ME"));
                }
            }
        }
        if(("Raitaru".equals(structureType)||"Azbel".equals(structureType)||"Sotiyo".equals(structureType))&&activityID==1){
            bonus=bonus*0.99;
        }
        int round=(int)Math.round(bonus*100000);
        return (double)round/100000;
    }
    
    public double structureTEBonus(int categoryID, int groupID,int activityID){
        double bonus=1.0;
        for(Rig rig:rigs){
            if(rig!=null){
                if(rig.isBonused(categoryID,groupID,"TE")){
                    bonus=bonus*(1-rig.getTotalRigBonus(secLoc,"TE"));
                }
            }
        }
        switch(structureType){
            case"Raitaru":
                if(activityID!=11){
                    bonus=bonus*0.85;
                }
                break;
            case"Azbel":
                if(activityID!=11){
                    bonus=bonus*0.8;
                }
                break;
            case"Sotiyo":
                if(activityID!=11){
                    bonus=bonus*0.7;
                }
                break;
            case"Tatara":
                if(activityID==11){
                    bonus=bonus*0.75;
                }
                break;
            default:;
        }
        return bonus;
    }
    
    public double structureCOSTBonus(int categoryID, int groupID,int activityID){
        double bonus=1.0;
        for(Rig rig:rigs){
            if(rig!=null){
                if(rig.isBonused(categoryID,groupID,"COST")){
                    bonus=bonus*(1-rig.getTotalRigBonus(secLoc,"COST"));
                }
            }
        }
        switch(structureType){
            case"Raitaru":
                if(activityID!=11){
                    bonus=bonus*0.97;
                }
                break;
            case"Azbel":
                if(activityID!=11){
                    bonus=bonus*0.96;
                }
                break;
            case"Sotiyo":
                if(activityID!=11){
                    bonus=bonus*0.95;
                }
                break;
            default:;
        }
        return this.roundBonus(bonus);
    }
    
    public double roundBonus(double bonus){
        int round=(int)Math.round(bonus*100000);
        return (double)round/100000;
    }
    
    public Object[] tableData(){
        String rig1="No Rig";
        String rig2="No Rig";
        String rig3="No Rig";
        if(this.rigs[0]!=null)rig1=SDEDatabase.TYPE_IDS.getTypeName(this.rigs[0].getRigID());
        if(this.rigs[1]!=null)rig1=SDEDatabase.TYPE_IDS.getTypeName(this.rigs[1].getRigID());
        if(this.rigs[2]!=null)rig1=SDEDatabase.TYPE_IDS.getTypeName(this.rigs[2].getRigID());
        return new Object[]{
            this.structureID,
            this.structureName,
            this.secLoc,
            this.structureType,
            rig1,
            rig2,
            rig3,
            this.tax+"%"
        };
    }

    @Override
    public String toString() {
        return "Structure{" + "structureID=" + structureID + ", structureName=" + structureName + ", structureType=" + structureType + ", secLoc=" + secLoc + '}';
    }
}
