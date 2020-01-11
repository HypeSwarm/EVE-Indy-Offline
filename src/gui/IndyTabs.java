/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import indy.*;
import java.awt.Component;
import java.util.Collections;
import javax.swing.table.DefaultTableModel;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public class IndyTabs {
    static JobManufacturing SELECTED_JOB_MANUFACTURING=null;
    static JobReaction SELECTED_JOB_REACTION=null;
    static JobCopying SELECTED_JOB_COPYING=null;
    static JobInvention SELECTED_JOB_INVENTION=null;
    static JobResearch SELECTED_JOB_RESEARCH=null;
    
    static void loadIndyTabs(){
        updateManufacturingList();
        updateReactionsList();
        updateCopyingList();
        updateInventionList();
        updateResearchList();
    }
    
    static void updateManufacturingList(){
        Listeners.LISTENERS_ACTIVE=false;
        DefaultTableModel model =(DefaultTableModel) MainInterface.manufacturingTable.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        DataArrayLists.JOBS_MANUFACTURING.stream().forEach((job) -> {
            model.addRow(job.tableData());
        });
        MainInterface.manufacturingTable.setModel(model);
        Layouts.Manufacturing.updateManufacturingTableMinSize();
        updateManufacturingAllTable();
        Listeners.LISTENERS_ACTIVE=true;
    }
    
    static void updateReactionsList() {
        Listeners.LISTENERS_ACTIVE=false;
        DefaultTableModel model=(DefaultTableModel) MainInterface.reactionsTable.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        DataArrayLists.JOBS_REACTION.stream().forEach((job) -> {
            model.addRow(job.tableData());
        });
        MainInterface.reactionsTable.setModel(model);
        Layouts.Reaction.updateReactionTableMinSize();
        Listeners.LISTENERS_ACTIVE=true;
    }

    static void updateCopyingList() {
        Listeners.LISTENERS_ACTIVE=false;
        DefaultTableModel model =(DefaultTableModel) MainInterface.copyingTable.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        DataArrayLists.JOBS_COPYING.stream().forEach((job) -> {
            model.addRow(job.tableData());
        });
        MainInterface.copyingTable.setModel(model);
        Layouts.Copying.updateCopyTableMinSize();
        Listeners.LISTENERS_ACTIVE=true;
    }

    static void updateInventionList() {
        Listeners.LISTENERS_ACTIVE=false;
        DefaultTableModel model =(DefaultTableModel) MainInterface.inventionTable.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        DataArrayLists.JOBS_INVENTION.stream().forEach((job) -> {
            model.addRow(job.tableData());
        });
        MainInterface.inventionTable.setModel(model);
        Listeners.LISTENERS_ACTIVE=true;
    }

    static void updateResearchList() {
        Listeners.LISTENERS_ACTIVE=false;
        DefaultTableModel model =(DefaultTableModel) MainInterface.researchTable.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        DataArrayLists.JOBS_RESEARCH.stream().forEach((job) -> {
            model.addRow(job.tableData());
        });
        MainInterface.researchTable.setModel(model);
        Listeners.LISTENERS_ACTIVE=true;
    }
        
    static void updateManufacturingSingleTable(){
        DefaultTableModel model =(DefaultTableModel) MainInterface.singleJobMatsTable.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        if(SELECTED_JOB_MANUFACTURING!=null){
            Collections.sort(SELECTED_JOB_MANUFACTURING.getModifiedMaterials());
            for(Materials mat:SELECTED_JOB_MANUFACTURING.getModifiedMaterials()){
                Object[] rowData={SDEDatabase.TYPE_IDS.getTypeName(mat.getTypeID()),mat.getQuantity(),0,0};
                model.addRow(rowData);
            }
        }
        MainInterface.singleJobMatsTable.setModel(model);
    }
    
    static void updateManufacturingAllTable(){
        DataArrayLists.setAllMats();
        DefaultTableModel model =(DefaultTableModel) MainInterface.allJobMatsTable.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        for(Materials mat:DataArrayLists.ALL_MATS){
            Object[] rowData={SDEDatabase.TYPE_IDS.getTypeName(mat.getTypeID()),mat.getQuantity(),0,0};
            model.addRow(rowData);
        }
        MainInterface.allJobMatsTable.setModel(model);
    }
    
    static void updateSelectedManufacturingJob(){
        int index=MainInterface.manufacturingTable.getSelectedRow();
        SELECTED_JOB_MANUFACTURING.setStructureID(DataArrayLists.getStructure(MainInterface.manuSelectedStructure.getSelectedIndex()).getStructureID());
        SELECTED_JOB_MANUFACTURING.setRuns(Integer.parseInt(MainInterface.manuSelectedRuns.getText()));
        SELECTED_JOB_MANUFACTURING.setBlueprintsUsed(Integer.parseInt(MainInterface.manuSelectedBlueprintNo.getText()));
        SELECTED_JOB_MANUFACTURING.setBlueprintME(MainInterface.manuSelectedME.getSelectedIndex());
        SELECTED_JOB_MANUFACTURING.setBlueprintTE(MainInterface.manuSelectedTE.getSelectedIndex()*2);
        SELECTED_JOB_MANUFACTURING.calcModMaterials();
        SELECTED_JOB_MANUFACTURING.durationModify();
        updateManufacturingList();
        MainInterface.manufacturingTable.setRowSelectionInterval(index, index);
    }

    static void updateSelectedReactionsJob() {
        int index=MainInterface.reactionsTable.getSelectedRow();
        SELECTED_JOB_REACTION.setStructureID(DataArrayLists.getStructure(MainInterface.reactionSelectedStructure.getSelectedIndex()).getStructureID());
        SELECTED_JOB_REACTION.setRuns(Integer.parseInt(MainInterface.reactionSelectedRuns.getText()));
        SELECTED_JOB_REACTION.setBlueprintsUsed(Integer.parseInt(MainInterface.reactionSelectedCardNo.getText()));
        SELECTED_JOB_REACTION.calcModMaterials();
        SELECTED_JOB_REACTION.durationModify();
        updateReactionsList();
        MainInterface.reactionsTable.setRowSelectionInterval(index, index);
    }

    static void updateSelectedCopyingJob() {
        int index=MainInterface.copyingTable.getSelectedRow();
        SELECTED_JOB_COPYING.setStructureID(DataArrayLists.getStructure(MainInterface.copyingSelectedStructure.getSelectedIndex()).getStructureID());
        SELECTED_JOB_COPYING.setRuns(Integer.parseInt(MainInterface.copyingSelectedRuns.getText()));
        SELECTED_JOB_COPYING.setBlueprintsUsed(Integer.parseInt(MainInterface.copyingSelectedBlueprintNo.getText()));
        SELECTED_JOB_COPYING.durationModify();
        updateCopyingList();
        MainInterface.copyingTable.setRowSelectionInterval(index, index);
    }

    static void updateSelectedInventionJob() {
        int index=MainInterface.inventionTable.getSelectedRow();
        SELECTED_JOB_INVENTION.setStructureID(DataArrayLists.getStructure(MainInterface.inventionSelectedStructure.getSelectedIndex()).getStructureID());
        SELECTED_JOB_INVENTION.setSelectedOutcome((InventionOutcome)MainInterface.inventionSelectedOutcome.getSelectedItem());
        if(MainInterface.inventionSelectedDecryptor.getSelectedIndex()==0){
            SELECTED_JOB_INVENTION.setSelectedDecryptor(null);
        }else{
            SELECTED_JOB_INVENTION.setSelectedDecryptor((Decryptor)MainInterface.inventionSelectedDecryptor.getSelectedItem());
        }
        SELECTED_JOB_INVENTION.setRuns(Integer.parseInt(MainInterface.inventionSelectedRuns.getText()));
        SELECTED_JOB_INVENTION.calcDecriptorModified();
        SELECTED_JOB_INVENTION.durationModify();
        updateInventionList();
        MainInterface.inventionTable.setRowSelectionInterval(index, index);
    }

    static void updateSelectedResearchJob() {
        int index=MainInterface.researchTable.getSelectedRow();
        SELECTED_JOB_RESEARCH.setStructureID(DataArrayLists.getStructure(MainInterface.researchSelectedStructure.getSelectedIndex()).getStructureID());
        SELECTED_JOB_RESEARCH.setStartME(MainInterface.researchSelectedStartME.getSelectedIndex());
        SELECTED_JOB_RESEARCH.setStartTE(MainInterface.researchSelectedStartTE.getSelectedIndex()*2);
        SELECTED_JOB_RESEARCH.setTargetME(MainInterface.researchSelectedTargetME.getSelectedIndex());
        SELECTED_JOB_RESEARCH.setTargetTE(MainInterface.researchSelectedTargetTE.getSelectedIndex()*2);
        SELECTED_JOB_RESEARCH.durationModify();
        updateResearchList();
        MainInterface.researchTable.setRowSelectionInterval(index, index);
    }

    static void UpdateSelectedJob() {
        Component indySelect=MainInterface.indyJobsTabs.getSelectedComponent();
        if(indySelect==MainInterface.manufacturingTab){
            if(MainInterface.manufacturingTable.getSelectedRow()!=-1)SELECTED_JOB_MANUFACTURING=DataArrayLists.getIndyJob(MainInterface.manufacturingTable.getSelectedRow());
            updateManufacturingSingleTable();
        }
        if(indySelect==MainInterface.reactionsTab&&MainInterface.reactionsTable.getSelectedRow()!=-1){
            SELECTED_JOB_REACTION=DataArrayLists.getReactionJob(MainInterface.reactionsTable.getSelectedRow());
        }
        if(indySelect==MainInterface.copyingTab&&MainInterface.copyingTable.getSelectedRow()!=-1){
            SELECTED_JOB_COPYING=DataArrayLists.getCopyingJob(MainInterface.copyingTable.getSelectedRow());
        }
        if(indySelect==MainInterface.inventionTab&&MainInterface.inventionTable.getSelectedRow()!=-1){
            SELECTED_JOB_INVENTION=DataArrayLists.getInventionJob(MainInterface.inventionTable.getSelectedRow());
        }
        if(indySelect==MainInterface.researchTab&&MainInterface.researchTable.getSelectedRow()!=-1){
            SELECTED_JOB_RESEARCH=DataArrayLists.getResearchJob(MainInterface.researchTable.getSelectedRow());
        }
        InfoPanel.updateInfoPanel();
    }

    static void deleteSelectedJob() {
        Component indySelect=MainInterface.indyJobsTabs.getSelectedComponent();
        if(indySelect==MainInterface.manufacturingTab&&MainInterface.manufacturingTable.getSelectedRow()!=-1){
            DataArrayLists.removeManufacturingJob(MainInterface.manufacturingTable.getSelectedRow());
            SELECTED_JOB_MANUFACTURING=null;
            updateManufacturingList();
        }
        if(indySelect==MainInterface.reactionsTab&&MainInterface.reactionsTable.getSelectedRow()!=-1){
            DataArrayLists.removeReactionJob(MainInterface.reactionsTable.getSelectedRow());
            SELECTED_JOB_REACTION=null;
            IndyTabs.updateReactionsList();
        }
        if(indySelect==MainInterface.copyingTab&&MainInterface.copyingTable.getSelectedRow()!=-1){
            DataArrayLists.removeCopyingJob(MainInterface.copyingTable.getSelectedRow());
            SELECTED_JOB_COPYING=null;
            IndyTabs.updateCopyingList();
        }
        if(indySelect==MainInterface.inventionTab&&MainInterface.inventionTable.getSelectedRow()!=-1){
            DataArrayLists.removeInventionJob(MainInterface.inventionTable.getSelectedRow());
            SELECTED_JOB_INVENTION=null;
            IndyTabs.updateInventionList();
        }
        if(indySelect==MainInterface.researchTab&&MainInterface.researchTable.getSelectedRow()!=-1){
            DataArrayLists.removeResearchJob(MainInterface.researchTable.getSelectedRow());
            SELECTED_JOB_RESEARCH=null;
            IndyTabs.updateResearchList();
        }
        UpdateSelectedJob();
    }
}