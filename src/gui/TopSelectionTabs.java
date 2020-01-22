/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import indy.DataArrayLists;
import indy.Structure;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public class TopSelectionTabs {
    static Structure SELECTED_STRUCTURE=null;
    static Character SELECTED_CHARACTER=null;
    
    public static final String[] RIGGROUPS={"Combat Rig","Engineering Rig","Drilling Rig","Reactor Rig","Resource Rig"};
    public static final String[] RIGGROUPS_XL={"Combat Rig","Engineering Rig"};
    public static final ArrayList<Integer> STRUCTURE_RIG_GROUPIDS=SDEDatabase.GROUP_IDS.getStructureRiqGroupIDs();
    public static final ArrayList<String> STRUCTURE_M_RIGS=new ArrayList<>();
    public static final ArrayList<String> STRUCTURE_L_RIGS=new ArrayList<>();
    public static final ArrayList<String> STRUCTURE_XL_RIGS=new ArrayList<>();
    public static boolean isArraysLoaded=false;
    
    public static void loadStructureLists(){
        STRUCTURE_RIG_GROUPIDS.stream().map((id) -> SDEDatabase.GROUP_IDS.getGroupName(id)).map((name) -> {
                if(name.contains("Rig M")){
                    STRUCTURE_M_RIGS.add(name);
                }
                return name;
            }).map((name) -> {
                if(name.contains("Rig L")){
                    STRUCTURE_L_RIGS.add(name);
                }
                return name;
            }).filter((name) -> (name.contains("Rig XL"))).forEach((name) -> {
                STRUCTURE_XL_RIGS.add(name);
            });
            isArraysLoaded=true;
    }
    
    static void loadSelectionTabs(){
        updateStructuresList();
        updateCharacterList();
    }
    
    static void updateCharacterList(){
        
    }
    
    static void updateStructuresList(){
        Listeners.LISTENERS_ACTIVE=false;
        DefaultTableModel model =(DefaultTableModel) MainInterface.structuresTable.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        DataArrayLists.STRUCTURES.stream().forEach((structure) -> {
            model.addRow(structure.tableData());
        });
        MainInterface.structuresTable.setModel(model);
        Layouts.Structure.updateStructuresTableMinSize();
        Listeners.LISTENERS_ACTIVE=true;
    }
    
    static void updateSelectedTabs(){
        Component tabSelected=MainInterface.selectionTabs.getSelectedComponent();
        if(tabSelected==MainInterface.charactersTab){
            
        }
        if(tabSelected==MainInterface.structuresTab&&MainInterface.structuresTable.getSelectedRow()!=-1){
            SELECTED_STRUCTURE=DataArrayLists.getStructure(MainInterface.structuresTable.getSelectedRow());
        }
        InfoPanel.updateInfoPanel();
    }
    
    static DefaultComboBoxModel getStructuresModel() {
        DefaultComboBoxModel structureNames = new DefaultComboBoxModel();
        DataArrayLists.STRUCTURES.stream().forEach((structure) -> {
            structureNames.addElement(structure.getStructureName());
        });
        return structureNames;
    }
    
    static DefaultComboBoxModel getStructureTypeModel(){
        DefaultComboBoxModel types = new DefaultComboBoxModel();
        
        return types;
    }
    
    static DefaultComboBoxModel getStructureLocationModel(){
        DefaultComboBoxModel structureNames = new DefaultComboBoxModel();
        DataArrayLists.STRUCTURES.stream().forEach((structure) -> {
            structureNames.addElement(structure.getStructureName());
        });
        return structureNames;
    }

    static void deleteStructure() {
        if(MainInterface.structuresTable.getSelectedRow()!=-1){
            DataArrayLists.removeStructure(MainInterface.structuresTable.getSelectedRow());
            SELECTED_STRUCTURE=null;
            updateStructuresList();
            updateSelectedTabs();
        }
    }

    static void deleteCharacter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void addStructure() {
        if(new WindowAddStructure().newStructureWindow())updateStructuresList();
    }
}
