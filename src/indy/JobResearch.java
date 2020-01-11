/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author HypeSwarm
 */
public final class JobResearch extends Job{
    private static final int[] LEVEL_MODIFIERS={0, 105, 250, 595, 1414, 3360, 8000, 19000, 45255, 107700, 256000};
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private int startME;
    private int targetME;
    private int startTE;
    private int targetTE;
    private int durationME;
    private int durationTE;

    public int getStartME() {
        return startME;
    }

    public int getTargetME() {
        return targetME;
    }

    public int getStartTE() {
        return startTE;
    }

    public int getTargetTE() {
        return targetTE;
    }

    public int getDurationME() {
        return durationME;
    }

    public int getDurationTE() {
        return durationTE;
    }

    public void setStartME(int startME) {
        this.startME = startME;
    }

    public void setTargetME(int targetME) {
        this.targetME = targetME;
    }

    public void setStartTE(int startTE) {
        this.startTE = startTE;
    }

    public void setTargetTE(int targetTE) {
        this.targetTE = targetTE;
    }
    
    
    
    public JobResearch(int blueprintID, int structureID,int startME,int targetME,int startTE,int targetTE) {
        super(COUNT.incrementAndGet(),blueprintID, structureID,3,0,0);
        this.startME=startME;
        this.startTE=startTE;
        this.targetME=targetME;
        this.targetTE=targetTE;
        durationModify();
    }

    @Override
    public void durationModify() {
        double bonus=DataArrayLists.getStructureByID(this.structureID).structureTEBonus(9,0,11);
        if(startME>=targetME){
            durationME=0;
        }else{
            durationME=(int)(super.duration*bonus*LEVEL_MODIFIERS[targetME]/105-super.duration*bonus*LEVEL_MODIFIERS[startME]/105);
        }
        if(startTE>=targetTE){
            durationTE=0;
        }else{
            durationTE=(int)(super.duration*bonus*LEVEL_MODIFIERS[targetTE/2]/105-super.duration*bonus*LEVEL_MODIFIERS[startTE/2]/105);
        }
    }
    
     public String ModifiedDurationToString(int input){
        int sec=input;
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
    
    JobResearch(JsonNode json) {
        this(json.get("blueprintID").intValue(),json.get("structureID").intValue(),json.get("startME").intValue(),json.get("targetME").intValue(),json.get("startTE").intValue(),json.get("targetTE").intValue());
    }

    @Override
    public Object[] tableData() {
        return new Object[]{
            super.jobID,
            super.getBlueprintName(),
            super.getStructureName(),
            this.startME,
            this.targetME,
            ModifiedDurationToString(this.durationME),
            this.startTE,
            this.targetTE,
            ModifiedDurationToString(this.durationTE)
        };
    }
}
