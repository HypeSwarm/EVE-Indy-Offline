/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import indy.*;
import java.awt.Component;
import java.awt.Image;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import sde.FileHandler;

/**
 *
 * @author HypeSwarm
 */
public class InfoPanel {
    static void changeTab() {
        Component topSelect=MainInterface.selectionTabs.getSelectedComponent();
        Component indySelect=MainInterface.indyJobsTabs.getSelectedComponent();
        JTabbedPane infoSwitcher=MainInterface.infoSwitcher;
        if(topSelect==MainInterface.industryTab){
            if(indySelect==MainInterface.manufacturingTab){
                infoSwitcher.setSelectedComponent(MainInterface.infoManuScrollPane);
            }
            if(indySelect==MainInterface.reactionsTab){
                infoSwitcher.setSelectedComponent(MainInterface.infoReactionsScollPane);
            }
            if(indySelect==MainInterface.copyingTab){
                infoSwitcher.setSelectedComponent(MainInterface.infoCopyingScollPane);
            }
            if(indySelect==MainInterface.inventionTab){
                infoSwitcher.setSelectedComponent(MainInterface.infoInventionScollPane);
            }
            if(indySelect==MainInterface.researchTab){
                infoSwitcher.setSelectedComponent(MainInterface.infoResearchScollPane);
            }
            if(indySelect==MainInterface.componentsTab){
                infoSwitcher.setSelectedComponent(MainInterface.infoComponentScollPane);
            }
        }
        if(topSelect==MainInterface.assetsTab){
            infoSwitcher.setSelectedComponent(MainInterface.infoAssetsScollPane);
        }
        if(topSelect==MainInterface.structuresTab){
            infoSwitcher.setSelectedComponent(MainInterface.infoStructureScollPane);
        }
        if(topSelect==MainInterface.charactersTab){
            infoSwitcher.setSelectedComponent(MainInterface.infoCharacterScollPane);
        }
        updateInfoPanel();
    }
    
    static void updateInfoPanel(){
        Listeners.LISTENERS_ACTIVE=false;
        Component topSelect=MainInterface.selectionTabs.getSelectedComponent();
        Component indySelect=MainInterface.indyJobsTabs.getSelectedComponent();
        JLabel selectedIcon=MainInterface.selectedIcon;
        JLabel selectedLabel=MainInterface.selectedLabel;
        if(topSelect==MainInterface.industryTab){
            if(indySelect==MainInterface.manufacturingTab){
                JobManufacturing selectedJob=IndyTabs.SELECTED_JOB_MANUFACTURING;
                if(selectedJob!=null){
                    selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("Types/"+selectedJob.getBlueprintID()+"_64.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
                    selectedLabel.setText("<HTML><center>"+selectedJob.getBlueprintName()+"</center></HTML>");
                    MainInterface.manuSelectedStructure.setModel(TopSelectionTabs.getStructuresModel());
                    MainInterface.manuSelectedStructure.setSelectedIndex(DataArrayLists.getStructureIndex(selectedJob.getStructureID()));
                    MainInterface.manuSelectedRuns.setText(selectedJob.getRuns()+"");
                    MainInterface.manuSelectedBlueprintNo.setText(selectedJob.getBlueprintsUsed()+"");
                    MainInterface.manuSelectedME.setSelectedIndex(selectedJob.getBlueprintME());
                    MainInterface.manuSelectedTE.setSelectedIndex(selectedJob.getBlueprintTE()/2);
                    MainInterface.manuSelecedDuration.setText("Job Duration: "+selectedJob.ModifiedDurationToString());
                }else{
                    selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("UI/manufacturing.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_REPLICATE)));
                    selectedLabel.setText("<HTML><center>Manufacturing Tab</center></HTML>");
                    MainInterface.manuSelectedStructure.setModel(new DefaultComboBoxModel());
                    MainInterface.manuSelectedStructure.setSelectedIndex(-1);
                    MainInterface.manuSelectedRuns.setText("0");
                    MainInterface.manuSelectedBlueprintNo.setText("0");
                    MainInterface.manuSelectedME.setSelectedIndex(0);
                    MainInterface.manuSelectedTE.setSelectedIndex(0);
                    MainInterface.manuSelecedDuration.setText("Job Duration: ");
                }
            }
            if(indySelect==MainInterface.reactionsTab){
                JobReaction selectedJob=IndyTabs.SELECTED_JOB_REACTION;
                if(selectedJob!=null){
                    ImageIcon icon=FileHandler.getIconFile("Types/"+selectedJob.getBlueprintID()+"_64.png");
                    Image resized=icon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    selectedIcon.setIcon(new ImageIcon(resized));
                    selectedLabel.setText("<HTML><center>"+selectedJob.getBlueprintName()+"</center></HTML>");
                    MainInterface.reactionSelectedStructure.setModel(TopSelectionTabs.getStructuresModel());
                    MainInterface.reactionSelectedStructure.setSelectedIndex(DataArrayLists.getStructureIndex(selectedJob.getStructureID()));
                    MainInterface.reactionSelectedRuns.setText(selectedJob.getRuns()+"");
                    MainInterface.reactionSelectedCardNo.setText(selectedJob.getBlueprintsUsed()+"");
                    MainInterface.reactionSelecedDuration.setText("Job Duration: "+selectedJob.ModifiedDurationToString());
                }else{
                    selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("UI/reaction.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_REPLICATE)));
                    selectedLabel.setText("<HTML><center>Reactions Tab</center></HTML>");
                    MainInterface.reactionSelectedStructure.setModel(new DefaultComboBoxModel());
                    MainInterface.reactionSelectedStructure.setSelectedIndex(-1);
                    MainInterface.reactionSelectedRuns.setText("0");
                    MainInterface.reactionSelectedCardNo.setText("0");
                    MainInterface.reactionSelecedDuration.setText("Job Duration: ");
                }
            }
            if(indySelect==MainInterface.copyingTab){
                JobCopying selectedJob=IndyTabs.SELECTED_JOB_COPYING;
                if(selectedJob!=null){
                    ImageIcon icon=FileHandler.getIconFile("Types/"+selectedJob.getBlueprintID()+"_64.png");
                    Image resized=icon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    selectedIcon.setIcon(new ImageIcon(resized));
                    selectedLabel.setText("<HTML><center>"+selectedJob.getBlueprintName()+"</center></HTML>");
                    MainInterface.copyingSelectedStructure.setModel(TopSelectionTabs.getStructuresModel());
                    MainInterface.copyingSelectedStructure.setSelectedIndex(DataArrayLists.getStructureIndex(selectedJob.getStructureID()));
                    MainInterface.copyingSelectedRuns.setText(selectedJob.getRuns()+"");
                    MainInterface.copyingSelectedBlueprintNo.setText(selectedJob.getBlueprintsUsed()+"");
                    MainInterface.copyingSelecedDuration.setText("Job Duration: "+selectedJob.ModifiedDurationToString());
                }else{
                    selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("UI/copying.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
                    selectedLabel.setText("<HTML><center>Copying Tab</center></HTML>");
                    MainInterface.copyingSelectedStructure.setModel(new DefaultComboBoxModel());
                    MainInterface.copyingSelectedStructure.setSelectedIndex(-1);
                    MainInterface.copyingSelectedRuns.setText("0");
                    MainInterface.copyingSelectedBlueprintNo.setText("0");
                    MainInterface.copyingSelecedDuration.setText("Job Duration: ");
                }
            }
            if(indySelect==MainInterface.inventionTab){
                JobInvention selectedJob=IndyTabs.SELECTED_JOB_INVENTION;
                if(selectedJob!=null){
                    ImageIcon icon=FileHandler.getIconFile("Types/"+selectedJob.getBlueprintID()+"_64.png");
                    Image resized=icon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    selectedIcon.setIcon(new ImageIcon(resized));
                    selectedLabel.setText("<HTML><center>"+selectedJob.getBlueprintName()+"</center></HTML>");
                    MainInterface.inventionSelectedStructure.setModel(TopSelectionTabs.getStructuresModel());
                    MainInterface.inventionSelectedOutcome.setModel(selectedJob.inventionPosibilities());
                    MainInterface.inventionSelectedDecryptor.setModel(indy.Decryptor.getDecryptorModel());
                    MainInterface.inventionSelectedStructure.setSelectedIndex(DataArrayLists.getStructureIndex(selectedJob.getStructureID()));
                    MainInterface.inventionSelectedOutcome.setSelectedIndex(selectedJob.getOutcomeIndex());
                    MainInterface.inventionSelectedDecryptor.setSelectedIndex(selectedJob.getDecryptorIndex());
                    MainInterface.inventionSelectedRuns.setText(selectedJob.getRuns()+"");
                    MainInterface.inventionSelecedDuration.setText("Job Duration: "+selectedJob.ModifiedDurationToString());
                    MainInterface.inventionSelectedProbability.setText("Probability: "+selectedJob.probabilityToString());
                    MainInterface.inventionSelectedBPCRuns.setText("BPC Runs: "+selectedJob.getModRuns());
                    MainInterface.inventionSelectedBPCME.setText("BPC ME: "+selectedJob.getModME());
                    MainInterface.inventionSelectedBPCTE.setText("BPC TE: "+selectedJob.getModTE());
                }else{
                    selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("UI/invention.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
                    selectedLabel.setText("<HTML><center>Invention Tab</center></HTML>");
                    MainInterface.inventionSelectedStructure.setModel(new DefaultComboBoxModel());
                    MainInterface.inventionSelectedOutcome.setModel(new DefaultComboBoxModel());
                    MainInterface.inventionSelectedDecryptor.setModel(new DefaultComboBoxModel());
                    MainInterface.inventionSelectedStructure.setSelectedIndex(-1);
                    MainInterface.inventionSelectedOutcome.setSelectedIndex(-1);
                    MainInterface.inventionSelectedDecryptor.setSelectedIndex(-1);
                    MainInterface.inventionSelectedRuns.setText("0");
                    MainInterface.inventionSelecedDuration.setText("Job Duration: ");
                    MainInterface.inventionSelectedProbability.setText("Probability: ");
                    MainInterface.inventionSelectedBPCRuns.setText("BPC Runs: ");
                    MainInterface.inventionSelectedBPCME.setText("BPC ME: ");
                    MainInterface.inventionSelectedBPCTE.setText("BPC TE: ");
                }
            }
            if(indySelect==MainInterface.researchTab){
                JobResearch selectedJob=IndyTabs.SELECTED_JOB_RESEARCH;
                if(selectedJob!=null){
                    ImageIcon icon=FileHandler.getIconFile("Types/"+selectedJob.getBlueprintID()+"_64.png");
                    Image resized=icon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    selectedIcon.setIcon(new ImageIcon(resized));
                    selectedLabel.setText("<HTML><center>"+selectedJob.getBlueprintName()+"</center></HTML>");
                    MainInterface.researchSelectedStructure.setModel(TopSelectionTabs.getStructuresModel());
                    MainInterface.researchSelectedStructure.setSelectedIndex(DataArrayLists.getStructureIndex(selectedJob.getStructureID()));
                    MainInterface.researchSelectedStartME.setSelectedIndex(selectedJob.getStartME());
                    MainInterface.researchSelectedStartTE.setSelectedIndex(selectedJob.getStartTE()/2);
                    MainInterface.researchSelectedTargetME.setSelectedIndex(selectedJob.getTargetME());
                    MainInterface.researchSelectedTargetTE.setSelectedIndex(selectedJob.getTargetTE()/2);
                    MainInterface.researchSelecedDurationME.setText("Job Duration: "+selectedJob.ModifiedDurationToString(selectedJob.getDurationME()));
                    MainInterface.researchSelecedDurationTE.setText("Job Duration: "+selectedJob.ModifiedDurationToString(selectedJob.getDurationTE()));
                }else{
                    selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("UI/researchTime.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
                    selectedLabel.setText("<HTML><center>Research Tab</center></HTML>");
                    MainInterface.researchSelectedStructure.setModel(new DefaultComboBoxModel());
                    MainInterface.researchSelectedStructure.setSelectedIndex(-1);
                    MainInterface.researchSelectedStartME.setSelectedIndex(0);
                    MainInterface.researchSelectedStartTE.setSelectedIndex(0);
                    MainInterface.researchSelectedTargetME.setSelectedIndex(0);
                    MainInterface.researchSelectedTargetTE.setSelectedIndex(0);
                    MainInterface.researchSelecedDurationME.setText("Job Duration: ");
                    MainInterface.researchSelecedDurationTE.setText("Job Duration: ");
                }
            }
            if(indySelect==MainInterface.componentsTab){
                selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("27_64_1.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
                selectedLabel.setText("<HTML><center>Components Tab</center></HTML>");
            }
        }
        if(topSelect==MainInterface.structuresTab){
            Structure selectedStructure=TopSelectionTabs.SELECTED_STRUCTURE;
            if(!TopSelectionTabs.isArraysLoaded)TopSelectionTabs.loadStructureLists();
            if(selectedStructure!=null){
                ImageIcon icon=FileHandler.getIconFile("Types/"+selectedStructure.getStructureTypeID()+"_64.png");
                Image resized=icon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                selectedIcon.setIcon(new ImageIcon(resized));
                selectedLabel.setText("<HTML><center>"+selectedStructure.getStructureName()+"</center></HTML>");
                MainInterface.selectedStructureType.setText("Structure Type: "+selectedStructure.getStructureType());
                MainInterface.selectedStructureLocation.setText("Structure Type: "+selectedStructure.getSecLoc());
                DefaultComboBoxModel rig1Type=new DefaultComboBoxModel();
                DefaultComboBoxModel rig2Type=new DefaultComboBoxModel();
                DefaultComboBoxModel rig3Type=new DefaultComboBoxModel();
                if("Sotiyo".equals(selectedStructure.getStructureType())||"Keepstar".equals(selectedStructure.getStructureType())){
                    for(String group:TopSelectionTabs.RIGGROUPS_XL){
                        rig1Type.addElement(group);
                        rig2Type.addElement(group);
                        rig3Type.addElement(group);
                    }
                }else{
                    for(String group:TopSelectionTabs.RIGGROUPS){
                        rig1Type.addElement(group);
                        rig2Type.addElement(group);
                        rig3Type.addElement(group);
                    }
                }
                MainInterface.selectedRig1Type.setModel(rig1Type);
                MainInterface.selectedRig2Type.setModel(rig2Type);
                MainInterface.selectedRig3Type.setModel(rig3Type);
                
                
            }else{
                selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("UI/structurebrowser.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
                selectedLabel.setText("<HTML><center>Structures Tab</center></HTML>");
                MainInterface.selectedStructureType.setText("Structure Type: ");
                MainInterface.selectedStructureLocation.setText("Structure Type: ");
                MainInterface.selectedRig1Type.setModel(new DefaultComboBoxModel());
                MainInterface.selectedRig1Type.setSelectedIndex(-1);
                MainInterface.selectedRig1.setModel(new DefaultComboBoxModel());
                MainInterface.selectedRig1.setSelectedIndex(-1);
                MainInterface.selectedRig2Type.setModel(new DefaultComboBoxModel());
                MainInterface.selectedRig2Type.setSelectedIndex(-1);
                MainInterface.selectedRig2.setModel(new DefaultComboBoxModel());
                MainInterface.selectedRig2.setSelectedIndex(-1);
                MainInterface.selectedRig3Type.setModel(new DefaultComboBoxModel());
                MainInterface.selectedRig3Type.setSelectedIndex(-1);
                MainInterface.selectedRig3.setModel(new DefaultComboBoxModel());
                MainInterface.selectedRig3.setSelectedIndex(-1);
            }
        }
        if(topSelect==MainInterface.charactersTab){
            Character selectedCharacter=TopSelectionTabs.SELECTED_CHARACTER;
            if(selectedCharacter!=null){
                
            }else{
                selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("UI/charactersheet.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
                selectedLabel.setText("<HTML><center>Characters Tab</center></HTML>");
            }
        }
        if(topSelect==MainInterface.assetsTab){
            selectedIcon.setIcon(new ImageIcon(FileHandler.getIconFile("UI/assets.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
            selectedLabel.setText("<HTML><center>Assets Tab</center></HTML>");
        }
        Listeners.LISTENERS_ACTIVE=true;
    }
    
    static void updateSelectedJob() {
        Component indySelect=MainInterface.indyJobsTabs.getSelectedComponent();
        if(indySelect==MainInterface.manufacturingTab&&MainInterface.manufacturingTable.getSelectedRow()!=-1){
            IndyTabs.updateSelectedManufacturingJob();
        }
        if(indySelect==MainInterface.reactionsTab&&MainInterface.reactionsTable.getSelectedRow()!=-1){
            IndyTabs.updateSelectedReactionsJob();
        }
        if(indySelect==MainInterface.copyingTab&&MainInterface.copyingTable.getSelectedRow()!=-1){
            IndyTabs.updateSelectedCopyingJob();
        }
        if(indySelect==MainInterface.inventionTab&&MainInterface.inventionTable.getSelectedRow()!=-1){
            IndyTabs.updateSelectedInventionJob();
        }
        if(indySelect==MainInterface.researchTab&&MainInterface.researchTable.getSelectedRow()!=-1){
            IndyTabs.updateSelectedResearchJob();
        }
        DataArrayLists.saveData();
    }

    static void updateSelectedStructure() {
        
    }
    
}
