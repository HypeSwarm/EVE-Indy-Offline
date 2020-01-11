/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import java.text.NumberFormat;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public abstract class Job {
    protected final int jobID;
    protected final int blueprintID;
    protected int structureID;
    protected int runs;
    protected final int maxRuns;
    protected int blueprintsUsed;
    protected final int duration;
    protected int durationModified;
    protected double installFee;

    public int getBlueprintID() {
        return blueprintID;
    }

    public int getStructureID() {
        return structureID;
    }

    public int getRuns() {
        return runs;
    }

    public int getBlueprintsUsed() {
        return blueprintsUsed;
    }

    public int getDuration() {
        return duration;
    }

    public int getDurationModified() {
        return durationModified;
    }

    public double getInstallFee() {
        return installFee;
    }

    public void setInstallFee(double installFee) {
        this.installFee = installFee;
    }

    public void setStructureID(int structureID) {
        this.structureID = structureID;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public void setBlueprintsUsed(int blueprintsUsed) {
        this.blueprintsUsed = blueprintsUsed;
    }

    public void setDurationModified(int durationModified) {
        this.durationModified = durationModified;
    }

    public Job(int jobID, int blueprintID,int structureID,int activityID,int runs,int blueprintsUsed) {
        this.jobID=jobID;
        this.blueprintID=blueprintID;
        this.structureID=structureID;
        this.runs=runs;
        this.maxRuns=SDEDatabase.BLUEPRINTS.getMaxRuns(blueprintID);
        this.blueprintsUsed=blueprintsUsed;
        switch(activityID){
            case 1:
                this.duration=SDEDatabase.BLUEPRINTS.getIndyDuration(blueprintID);
                break;
            case 3:
            case 4:
                this.duration=SDEDatabase.BLUEPRINTS.getResearchDuration(blueprintID);
                break;
            case 5:
                this.duration=SDEDatabase.BLUEPRINTS.getCopyDuration(blueprintID);
                break;
            case 8:
                this.duration=SDEDatabase.BLUEPRINTS.getInventionDuration(blueprintID);
                break;
            case 11:
                this.duration=SDEDatabase.BLUEPRINTS.getReactionDuration(blueprintID);
                break;
            default:
                this.duration=0;
        }
        this.installFee=0.0;
    }

    public String getBlueprintName(){
        return SDEDatabase.TYPE_IDS.getTypeName(blueprintID);
    }
    
    public String doubleToCurrency(double value){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(value);
    }
    
    public void durationModify(){

    }
    
    public Object[] tableData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getStructureName() {
        return indy.DataArrayLists.getStructureByID(structureID).getStructureName();
    }
    
    public String ModifiedDurationToString(){
        int sec=this.durationModified;
        int min=(int)Math.floor(sec/60);
        int hour=(int)Math.floor(min/60);
        int days=(int)Math.floor(hour/24);
        hour=hour-days*24;
        min=min-(days*24+hour)*60;
        sec=sec-((days*24+hour)*60+min)*60;
        String sDay=days+"";
        String sHour=hour+"";
        String sMin=min+"";
        String sSec=sec+"";
        if(days<10)sDay="0"+sDay;
        if(hour<10)sHour="0"+sHour;
        if(min<10)sMin="0"+sMin;
        if(sec<10)sSec="0"+sSec;
        return sDay+"D "+sHour+":"+sMin+":"+sSec;
    }
}
