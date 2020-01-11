/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JTable;

/**
 *
 * @author HypeSwarm
 */
public class Layouts{    
    static void loadSpecialLayouts(){
        MainInterface.infoSwitcher.setLayout(new CardLayout());
        Manufacturing.setup();
        Reaction.setup();
        Copying.setup();
        Research.setup();
        Invention.setup();
        Structure.setup();
        tableAutoResize();
    }

    static void tableAutoResize(){
        int width=MainInterface.selectionTabs.getWidth();
        if(width<Layouts.Manufacturing.manufacturingTableHorizontalMin()){
            MainInterface.manufacturingTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }else{
            MainInterface.manufacturingTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }
        if(width<Layouts.Reaction.reactionTableHorizontalMin()){
            MainInterface.reactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }else{
            MainInterface.reactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }
        if(width<Layouts.Copying.copyTableHorizontalMin()){
            MainInterface.copyingTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }else{
            MainInterface.copyingTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }
        if(width<Layouts.Research.researchTableHorizontalMin()){
            MainInterface.researchTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }else{
            MainInterface.researchTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }
        if(width<Layouts.Invention.inventionTableHorizontalMin()){
            MainInterface.inventionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }else{
            MainInterface.inventionTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }
    }
    
    static class Manufacturing{
        private static final Object[][] MAIN_MANUFACTURING_TABLE_SETTINGS={
            {45,45,false},
            {200,null,true},
            {175,null,true},
            {75,75,false},
            {75,75,false},
            {75,75,false},
            {65,65,false},
            {65,65,false},
            {100,100,false},
            {100,125,false},
            {100,125,false},
            {100,125,false}
        };

        private static final Object[][] SUB_MANUFACTURING_TABLE_SETTINGS={
            {50,null,true},
            {85,85,false},
            {85,85,false},
            {105,105,false}
        };

        private static void setup(){
            JTable manufacturingTable=MainInterface.manufacturingTable;
            JTable allJobMatsTable=MainInterface.allJobMatsTable;
            JTable singleJobMatsTable=MainInterface.singleJobMatsTable;
            manufacturingTable.getTableHeader().setPreferredSize(new Dimension(manufacturingTableHorizontalMin(),30));
            allJobMatsTable.getTableHeader().setPreferredSize(new Dimension(allJobMatsTable.getWidth(),30));
            singleJobMatsTable.getTableHeader().setPreferredSize(new Dimension(singleJobMatsTable.getWidth(),30));
            for(int i=0;i<manufacturingTable.getColumnCount();i++){
                manufacturingTable.getColumnModel().getColumn(i).setMinWidth((int)MAIN_MANUFACTURING_TABLE_SETTINGS[i][0]);
                if(MAIN_MANUFACTURING_TABLE_SETTINGS[i][1]!=null){
                    manufacturingTable.getColumnModel().getColumn(i).setMaxWidth((int)MAIN_MANUFACTURING_TABLE_SETTINGS[i][1]);
                }
                manufacturingTable.getColumnModel().getColumn(i).setResizable((boolean)MAIN_MANUFACTURING_TABLE_SETTINGS[i][2]);
            }
            for(int i=0;i<allJobMatsTable.getColumnCount();i++){
                allJobMatsTable.getColumnModel().getColumn(i).setMinWidth((int)SUB_MANUFACTURING_TABLE_SETTINGS[i][0]);
                singleJobMatsTable.getColumnModel().getColumn(i).setMinWidth((int)SUB_MANUFACTURING_TABLE_SETTINGS[i][0]);
                if(SUB_MANUFACTURING_TABLE_SETTINGS[i][1]!=null){
                    allJobMatsTable.getColumnModel().getColumn(i).setMaxWidth((int)SUB_MANUFACTURING_TABLE_SETTINGS[i][1]);
                    singleJobMatsTable.getColumnModel().getColumn(i).setMaxWidth((int)SUB_MANUFACTURING_TABLE_SETTINGS[i][1]);
                }
                allJobMatsTable.getColumnModel().getColumn(i).setResizable((boolean)SUB_MANUFACTURING_TABLE_SETTINGS[i][2]);
                singleJobMatsTable.getColumnModel().getColumn(i).setResizable((boolean)SUB_MANUFACTURING_TABLE_SETTINGS[i][2]);
            }
        }

        static void updateManufacturingTableMinSize(){
            MainInterface.manufacturingTable.setPreferredSize(new Dimension(manufacturingTableHorizontalMin(),MainInterface.manufacturingTable.getRowHeight()*MainInterface.manufacturingTable.getRowCount()));
        }

        static int manufacturingTableHorizontalMin(){
            int min=0;
            for(Object[] minSetting:MAIN_MANUFACTURING_TABLE_SETTINGS){
                min +=(int)minSetting[0];
            }
            return min;
        }
    }

    static class Reaction {
        private static final Object[][] REACTION_TABLE_SETTINGS={
            {45,45,false},
            {200,null,true},
            {175,null,true},
            {75,75,false},
            {75,75,false},
            {65,65,false},
            {100,100,false},
            {100,125,false},
            {100,125,false},
            {100,125,false}
        };
        
        private static void setup(){
            JTable reactionTable=MainInterface.reactionsTable;
            reactionTable.getTableHeader().setPreferredSize(new Dimension(reactionTableHorizontalMin(),30));
            for(int i=0;i<reactionTable.getColumnCount();i++){
                reactionTable.getColumnModel().getColumn(i).setMinWidth((int)REACTION_TABLE_SETTINGS[i][0]);
                if(REACTION_TABLE_SETTINGS[i][1]!=null){
                    reactionTable.getColumnModel().getColumn(i).setMaxWidth((int)REACTION_TABLE_SETTINGS[i][1]);
                }
               reactionTable.getColumnModel().getColumn(i).setResizable((boolean)REACTION_TABLE_SETTINGS[i][2]);
            }
        }
        
        static void updateReactionTableMinSize(){
            MainInterface.reactionsTable.setPreferredSize(new Dimension(reactionTableHorizontalMin(),MainInterface.reactionsTable.getRowHeight()*MainInterface.reactionsTable.getRowCount()));
        }
        
        static int reactionTableHorizontalMin(){
            int min=0;
            for(Object[] minSetting:REACTION_TABLE_SETTINGS){
                min +=(int)minSetting[0];
            }
            return min;
        }
    }
    
    static class Copying{
        private static final Object[][] COPYING_TABLE_SETTINGS={
            {45,45,false},
            {200,null,true},
            {175,null,true},
            {125,125,false},
            {125,125,false},
            {100,100,false},
            {100,125,false},
        };
        
        private static void setup(){
            JTable copyingTable=MainInterface.copyingTable;
            copyingTable.getTableHeader().setPreferredSize(new Dimension(copyTableHorizontalMin(),30));
            for(int i=0;i<copyingTable.getColumnCount();i++){
                copyingTable.getColumnModel().getColumn(i).setMinWidth((int)COPYING_TABLE_SETTINGS[i][0]);
                if(COPYING_TABLE_SETTINGS[i][1]!=null){
                    copyingTable.getColumnModel().getColumn(i).setMaxWidth((int)COPYING_TABLE_SETTINGS[i][1]);
                }
               copyingTable.getColumnModel().getColumn(i).setResizable((boolean)COPYING_TABLE_SETTINGS[i][2]);
            }
        }
        
        static void updateCopyTableMinSize(){
            MainInterface.copyingTable.setPreferredSize(new Dimension(copyTableHorizontalMin(),MainInterface.copyingTable.getRowHeight()*MainInterface.copyingTable.getRowCount()));
        }
        
        static int copyTableHorizontalMin(){
            int min=0;
            for(Object[] minSetting:COPYING_TABLE_SETTINGS){
                min +=(int)minSetting[0];
            }
            return min;
        }
    }
    
    static class Invention{
        private static final Object[][] INVENTION_TABLE_SETTINGS={
            {45,45,false},
            {200,null,true},
            {150,null,true},
            {175,175,false},
            {150,150,false},
            {75,75,false},
            {100,125,false},
            {75,75,false},
            {65,65,false},
            {65,65,false},
            {65,65,false},
            {75,75,false}
        };
        
        private static void setup(){
            JTable inventionTable=MainInterface.inventionTable;
            inventionTable.getTableHeader().setPreferredSize(new Dimension(inventionTableHorizontalMin(),30));
            for(int i=0;i<inventionTable.getColumnCount();i++){
                inventionTable.getColumnModel().getColumn(i).setMinWidth((int)INVENTION_TABLE_SETTINGS[i][0]);
                if(INVENTION_TABLE_SETTINGS[i][1]!=null){
                    inventionTable.getColumnModel().getColumn(i).setMaxWidth((int)INVENTION_TABLE_SETTINGS[i][1]);
                }
               inventionTable.getColumnModel().getColumn(i).setResizable((boolean)INVENTION_TABLE_SETTINGS[i][2]);
            }
        }
        
        static void updateInventionTableMinSize(){
            MainInterface.inventionTable.setPreferredSize(new Dimension(inventionTableHorizontalMin(),MainInterface.inventionTable.getRowHeight()*MainInterface.inventionTable.getRowCount()));
        }
        
        static int inventionTableHorizontalMin(){
            int min=0;
            for(Object[] minSetting:INVENTION_TABLE_SETTINGS){
                min +=(int)minSetting[0];
            }
            return min;
        }
    }
    
    static class Research{
        private static final Object[][] RESEARCH_TABLE_SETTINGS={
            {45,45,false},
            {200,null,true},
            {175,null,true},
            {75,75,false},
            {75,75,false},
            {100,125,false},
            {75,75,false},
            {75,75,false},
            {100,125,false}
        };
        
        private static void setup(){
            JTable researchTable=MainInterface.researchTable;
            researchTable.getTableHeader().setPreferredSize(new Dimension(researchTableHorizontalMin(),30));
            for(int i=0;i<researchTable.getColumnCount();i++){
                researchTable.getColumnModel().getColumn(i).setMinWidth((int)RESEARCH_TABLE_SETTINGS[i][0]);
                if(RESEARCH_TABLE_SETTINGS[i][1]!=null){
                    researchTable.getColumnModel().getColumn(i).setMaxWidth((int)RESEARCH_TABLE_SETTINGS[i][1]);
                }
               researchTable.getColumnModel().getColumn(i).setResizable((boolean)RESEARCH_TABLE_SETTINGS[i][2]);
            }
        }
        
        static void updateResearchTableMinSize(){
            MainInterface.researchTable.setPreferredSize(new Dimension(researchTableHorizontalMin(),MainInterface.researchTable.getRowHeight()*MainInterface.researchTable.getRowCount()));
        }
        
        static int researchTableHorizontalMin(){
            int min=0;
            for(Object[] minSetting:RESEARCH_TABLE_SETTINGS){
                min +=(int)minSetting[0];
            }
            return min;
        }
    }

    static class Structure {
        private static final Object[][] STRUCTURE_TABLE_SETTINGS={
            {65,65,false},
            {125,null,true},
            {75,75,true},
            {75,75,false},
            {250,null,false},
            {250,null,false},
            {250,null,false},
            {45,45,false}
        };
        
        private static void setup(){
            JTable copyingTable=MainInterface.structuresTable;
            copyingTable.getTableHeader().setPreferredSize(new Dimension(structureTableHorizontalMin(),30));
            for(int i=0;i<copyingTable.getColumnCount();i++){
                copyingTable.getColumnModel().getColumn(i).setMinWidth((int)STRUCTURE_TABLE_SETTINGS[i][0]);
                if(STRUCTURE_TABLE_SETTINGS[i][1]!=null){
                    copyingTable.getColumnModel().getColumn(i).setMaxWidth((int)STRUCTURE_TABLE_SETTINGS[i][1]);
                }
               copyingTable.getColumnModel().getColumn(i).setResizable((boolean)STRUCTURE_TABLE_SETTINGS[i][2]);
            }
        }
        
        static void updateStructuresTableMinSize() {
            MainInterface.structuresTable.setPreferredSize(new Dimension(structureTableHorizontalMin(),MainInterface.structuresTable.getRowHeight()*MainInterface.structuresTable.getRowCount()));
        }
        
        static int structureTableHorizontalMin(){
            int min=0;
            for(Object[] minSetting:STRUCTURE_TABLE_SETTINGS){
                min +=(int)minSetting[0];
            }
            return min;
        }
    }
}
