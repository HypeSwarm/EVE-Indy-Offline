/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public class Rig {
    private static final double TECH1_ME_MOD=0.02;
    private static final double TECH2_ME_MOD=0.024;
    private static final double TECH1_TE_MOD=0.2;
    private static final double TECH2_TE_MOD=0.24;
    private static final double TECH1_COST_MOD=0.2;
    private static final double TECH2_COST_MOD=0.24;
    private static final double HS_LOC_MOD=1.0;
    private static final double LS_LOC_MOD=1.9;
    private static final double NS_LOC_MOD=2.1;
    
    private final int rigID;
    private final boolean isTech2;

    public int getRigID() {
        return rigID;
    }

    public boolean isIsTech2() {
        return isTech2;
    }
    
    
    public Rig(int rigID){
        this.rigID=rigID;
        this.isTech2=SDEDatabase.TYPE_IDS.getMetaGroup(rigID)==53;
    }
    
    public double getTotalRigBonus(String secLoc, String type){
        double bonus=this.baseBonus(isTech2, type);
        switch(secLoc){
            case "High Sec":
                bonus=bonus*HS_LOC_MOD;
                break;
            case "Low Sec":
                bonus=bonus*LS_LOC_MOD;
                break;
            case "Null Sec":
            case "Wormhole":
                bonus=bonus*NS_LOC_MOD;
                break;
            default:;
        }
        return bonus;
    }
    
    public boolean isBonused(int categoryID, int groupID, String type){
        boolean isBonused=false;
        switch (type){
            case "ME":
                isBonused=SDEDatabase.RIG_BONUSES.isMEBonused(rigID, categoryID, groupID);
                break;
            case "TE":
                isBonused=SDEDatabase.RIG_BONUSES.isTEBonused(rigID, categoryID, groupID);
                break;
            case "COST":
                isBonused=SDEDatabase.RIG_BONUSES.isCOSTBonused(rigID, categoryID, groupID);
                break;
        }
        return isBonused;
    }
    
    
    public double baseBonus(boolean techLv,String type){
        double bonus = 0;
        switch(type){
            case "ME":
                if(techLv){
                    bonus=TECH2_ME_MOD;
                }else{
                    bonus=TECH1_ME_MOD;
                }
                break;
            case "TE":
                if(techLv){
                    bonus=TECH2_TE_MOD;
                }else{
                    bonus=TECH1_TE_MOD;
                }
                break;
            case "COST":
                if(techLv){
                    bonus=TECH2_COST_MOD;
                }else{
                    bonus=TECH1_COST_MOD;
                }
                break;
        }
        return bonus;
    }
}
