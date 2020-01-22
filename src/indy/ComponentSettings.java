/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

/**
 *
 * @author HypeSwarm
 */
public class ComponentSettings {
    public static final ComponentGroup CAPITAL_CONSTRUCTION_COMPONENTS=new ComponentGroup(null,873,0,0,false);
    public static final ComponentGroup ADVANCED_CONSTRUCTION_COMPONENTS=new ComponentGroup(null,334,0,0,false);
    public static final ComponentGroup ADVANCED_CAPITAL_CONSTRUCTION_COMPONENTS=new ComponentGroup(null,913,0,0,false);
    public static final ComponentGroup STRUCTURE_COMPONENTS=new ComponentGroup(null,536,0,0,false);
    public static final ComponentGroup HYDRID_TECH_COMPONENTS=new ComponentGroup(null,1136,0,0,false);
    
    //Ran when the first structure is added
    public static void setDefaultStructure(Structure structure){
        CAPITAL_CONSTRUCTION_COMPONENTS.setStructure(structure);
        ADVANCED_CONSTRUCTION_COMPONENTS.setStructure(structure);
        ADVANCED_CAPITAL_CONSTRUCTION_COMPONENTS.setStructure(structure);
        STRUCTURE_COMPONENTS.setStructure(structure);
        HYDRID_TECH_COMPONENTS.setStructure(structure);
    }
    
    public static ComponentGroup getEffectingGroup(int groupID){
        switch(groupID){
            case 873:
                return CAPITAL_CONSTRUCTION_COMPONENTS;
            case 334:
                return ADVANCED_CONSTRUCTION_COMPONENTS;
            case 913:
                return ADVANCED_CAPITAL_CONSTRUCTION_COMPONENTS;
            case 536:
                return STRUCTURE_COMPONENTS;
            case 1136:
                return HYDRID_TECH_COMPONENTS;
            default:
                return null;
        }
    }
    
    public static class ComponentGroup{
        private Structure structure;
        private final int groupID;
        private int blueprintME;
        private int blueprintTE;
        private boolean fullBPC;

        public Structure getStructure() {
            return structure;
        }

        public int getGroupID() {
            return groupID;
        }

        public int getBlueprintME() {
            return blueprintME;
        }

        public int getBlueprintTE() {
            return blueprintTE;
        }

        public boolean isFullBPC() {
            return fullBPC;
        }

        public void setStructure(Structure structure) {
            this.structure = structure;
        }

        public void setBlueprintME(int blueprintME) {
            this.blueprintME = blueprintME;
        }

        public void setBlueprintTE(int blueprintTE) {
            this.blueprintTE = blueprintTE;
        }

        public void setFullBPC(boolean fullBPC) {
            this.fullBPC = fullBPC;
        }

        public ComponentGroup(Structure structure, int groupID, int blueprintME, int blueprintTE, boolean fullBPC) {
            this.structure = structure;
            this.groupID = groupID;
            this.blueprintME = blueprintME;
            this.blueprintTE = blueprintTE;
            this.fullBPC = fullBPC;
        }
    }
}
