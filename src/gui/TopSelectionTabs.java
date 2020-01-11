/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import indy.DataArrayLists;
import indy.Structure;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HypeSwarm
 */
public class TopSelectionTabs {
    static Structure SELECTED_STRUCTURE=null;
    static Character SELECTED_CHARACTER=null;
    
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

    static void deleteStructure() {
        if(MainInterface.structuresTable.getSelectedRow()!=-1){
            DataArrayLists.removeStructure(MainInterface.structuresTable.getSelectedRow());
            updateStructuresList();
        }
    }

    static void deleteCharacter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
