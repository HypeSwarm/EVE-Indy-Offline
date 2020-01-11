/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.BlueprintTree.SortableObject;
import indy.DataArrayLists;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author HypeSwarm
 */
public class Listeners {
    static boolean LISTENERS_ACTIVE=true;
    static boolean SEARCH_UPDATING=false;
    
    static void loadListeners(){
        //Top Tabs Selection Listeners
        MainInterface.selectionTabs.addChangeListener(SwitchInfoTabs());
        MainInterface.selectionTabs.addComponentListener(AdjustMainIndyTableVerticle());
        MainInterface.indyJobsTabs.addChangeListener(SwitchInfoTabs());
        MainInterface.structuresTable.getSelectionModel().addListSelectionListener(TopTabSelectListener());
        
        //Indy Sub Tab Selection and Resize Listeners
        MainInterface.manufacturingTable.getParent().addComponentListener(AdjustIndyTablesHorizontal());
        MainInterface.reactionsTable.getParent().addComponentListener(AdjustIndyTablesHorizontal());
        MainInterface.copyingTable.getParent().addComponentListener(AdjustIndyTablesHorizontal());
        MainInterface.inventionTable.getParent().addComponentListener(AdjustIndyTablesHorizontal());
        MainInterface.researchTable.getParent().addComponentListener(AdjustIndyTablesHorizontal());
        MainInterface.manufacturingTable.getSelectionModel().addListSelectionListener(IndyTablesSelectListener());
        MainInterface.reactionsTable.getSelectionModel().addListSelectionListener(IndyTablesSelectListener());
        MainInterface.copyingTable.getSelectionModel().addListSelectionListener(IndyTablesSelectListener());
        MainInterface.inventionTable.getSelectionModel().addListSelectionListener(IndyTablesSelectListener());
        MainInterface.researchTable.getSelectionModel().addListSelectionListener(IndyTablesSelectListener());
        
        //Info Panel Listeneres
            //Info Manufacturing
            MainInterface.deleteManuJob.addActionListener(DeleteJobButton());
            MainInterface.manuSelectedStructure.addActionListener(UpdateSelectedJobListener());
            MainInterface.manuSelectedRuns.addActionListener(UpdateSelectedJobListener());
            MainInterface.manuSelectedBlueprintNo.addActionListener(UpdateSelectedJobListener());
            MainInterface.manuSelectedME.addActionListener(UpdateSelectedJobListener());
            MainInterface.manuSelectedTE.addActionListener(UpdateSelectedJobListener());
            
            //Info Reactions
            MainInterface.deleteReactionJob.addActionListener(DeleteJobButton());
            MainInterface.reactionSelectedStructure.addActionListener(UpdateSelectedJobListener());
            MainInterface.reactionSelectedRuns.addActionListener(UpdateSelectedJobListener());
            MainInterface.reactionSelectedCardNo.addActionListener(UpdateSelectedJobListener());
            
            //Info Copying
            MainInterface.deleteCopyingJob.addActionListener(DeleteJobButton());
            MainInterface.copyingSelectedStructure.addActionListener(UpdateSelectedJobListener());
            MainInterface.copyingSelectedRuns.addActionListener(UpdateSelectedJobListener());
            MainInterface.copyingSelectedBlueprintNo.addActionListener(UpdateSelectedJobListener());
            
            //Info Invention
            MainInterface.deleteInventionJob.addActionListener(DeleteJobButton());
            MainInterface.inventionSelectedStructure.addActionListener(UpdateSelectedJobListener());
            MainInterface.inventionSelectedOutcome.addActionListener(UpdateSelectedJobListener());
            MainInterface.inventionSelectedDecryptor.addActionListener(UpdateSelectedJobListener());
            MainInterface.inventionSelectedRuns.addActionListener(UpdateSelectedJobListener());
            
            //Info Research
            MainInterface.deleteResearchJob.addActionListener(DeleteJobButton());
            MainInterface.researchSelectedStructure.addActionListener(UpdateSelectedJobListener());
            MainInterface.researchSelectedStartME.addActionListener(UpdateSelectedJobListener());
            MainInterface.researchSelectedTargetME.addActionListener(UpdateSelectedJobListener());
            MainInterface.researchSelectedStartTE.addActionListener(UpdateSelectedJobListener());
            MainInterface.researchSelectedTargetTE.addActionListener(UpdateSelectedJobListener());
            
            //Info Components
            
            
            //Info Structures
            MainInterface.deleteStructure.addActionListener(DeleteStructureButton());
            
            //Info Characters

            
            //Info Assets
            

        //Blueprints Tree and Search Listeners
        MainInterface.blueprintTree.addMouseListener(BlueprintTreeDoubleClick());
        MainInterface.blueprintSearch.addItemListener(SearchBlueprintSelect());
        JComboBox search = MainInterface.blueprintSearch;
        final JTextComponent textInput=(JTextComponent)search.getEditor().getEditorComponent();
        textInput.getDocument().addDocumentListener(SeachTextListener(search,textInput));
        textInput.addFocusListener(SearchFocus(search,textInput));
    }
    
    private static ChangeListener SwitchInfoTabs(){
        return new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                InfoPanel.changeTab();
            }
        };
    }

    private static ListSelectionListener IndyTablesSelectListener(){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()&&LISTENERS_ACTIVE){
                    IndyTabs.UpdateSelectedJob();
                }
            }
        };
    }
    
    private static ListSelectionListener TopTabSelectListener(){
        return new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()&&LISTENERS_ACTIVE){
                    TopSelectionTabs.updateSelectedTabs();
                }
            }
        };
    }
    
    private static ActionListener UpdateSelectedJobListener(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(LISTENERS_ACTIVE){
                    InfoPanel.updateSelectedJob();
                }
            }
        };
    }

    private static ActionListener DeleteJobButton(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                IndyTabs.deleteSelectedJob();
                DataArrayLists.saveData();
            }
        };
    }

    private static ActionListener DeleteStructureButton(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TopSelectionTabs.deleteStructure();
                DataArrayLists.saveData();
            }
        };
    }

    private static ActionListener DeleteCharacterButton(){
        return new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e){
              TopSelectionTabs.deleteCharacter();
          }
        };
    }

    private static ComponentAdapter AdjustIndyTablesHorizontal(){
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Layouts.tableAutoResize();
            }
        };
    }

    private static ComponentAdapter AdjustMainIndyTableVerticle(){
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height;
                if(MainInterface.manufacturingTable.getParent().getHeight()>MainInterface.manufacturingTable.getRowCount()*MainInterface.manufacturingTable.getRowHeight()){
                    height=MainInterface.manufacturingTable.getParent().getHeight();
                }else{
                    height=MainInterface.manufacturingTable.getRowCount()*MainInterface.manufacturingTable.getRowHeight();
                }
                MainInterface.manufacturingTable.setPreferredSize(new Dimension(MainInterface.manufacturingTable.getPreferredSize().width,height));
            }
        };
    }

    private static MouseAdapter BlueprintTreeDoubleClick(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row=MainInterface.blueprintTree.getRowForLocation(e.getX(),e.getY());
                if(e.getClickCount()==2&&row!=-1){
                    DefaultMutableTreeNode node=(DefaultMutableTreeNode)MainInterface.blueprintTree.getLastSelectedPathComponent();
                    if(node==null)return;
                    if(node.getChildCount()==0){
                        SortableObject selected=(SortableObject)node.getUserObject();
                        if(new WindowAddJob().newJobWindow(selected.getBlueprint())){
                            IndyTabs.loadIndyTabs();
                            DataArrayLists.saveData();
                        }
                    }
                }
            }
        };
    }

    private static ItemListener SearchBlueprintSelect() {
        return new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                JComboBox search=MainInterface.blueprintSearch;
                if(search.getSelectedItem() instanceof SortableObject&&e.getStateChange() == ItemEvent.SELECTED&&!SEARCH_UPDATING){
                    SortableObject selected=(SortableObject)search.getSelectedItem();
                    search.removeAllItems();
                    if(new WindowAddJob().newJobWindow(selected.getBlueprint())){
                        IndyTabs.loadIndyTabs();
                        DataArrayLists.saveData();
                    }
                }
            }
        };
    }

    private static DocumentListener SeachTextListener(JComboBox search,JTextComponent textInput) {
        return new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}

            public void update(){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        SEARCH_UPDATING=true;
                        ArrayList<SortableObject> founds =new ArrayList<>(BlueprintTree.searchBlueprints.search(textInput.getText()));
                        Set<String> foundSet=new HashSet<>();
                        founds.stream().forEach((blueprint) -> {
                            foundSet.add(blueprint.getBlueprintName().toLowerCase());
                        });
                        search.setEditable(false);
                        search.removeAllItems();
                        if(!foundSet.contains(textInput.getText().toLowerCase())){
                            search.addItem(textInput.getText());
                        }
                        founds.stream().forEach((blueprint) -> {
                            search.addItem(blueprint);
                        });
                        search.setEditable(true);
                        search.setPopupVisible(true);
                        textInput.requestFocus();
                        SEARCH_UPDATING=false;
                    }
                });
            }
        };
    }

    private static FocusListener SearchFocus(JComboBox search, JTextComponent textInput) {
        return new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                if(textInput.getText().length()>0){
                    search.setPopupVisible(true);
                    textInput.setCaretPosition(textInput.getText().length());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        };
    }
}
