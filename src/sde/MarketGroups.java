/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import gui.MainLauncher;
import java.util.ArrayList;

/**
 *
 * @author HypeSwarm
 */
public class MarketGroups {
    private static final ArrayList<MarketGroup> MARKET_GROUPS=new ArrayList<>();
    
    public void yamlSDE() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading MarketGroups.yaml");
        Object data=FileHandler.loadYAMLFile("SDE/marketGroups");
        Object[] marketIDs=FileHandler.getKeySet(data);
        Object[] marketGroups=FileHandler.getValuesSet(data);
        int currentEntry=1;
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+marketIDs.length+"\n");
        for(Object group:marketGroups){
            MainLauncher.LOADING.addLoadingText("Parsing MarketGroup: "+currentEntry+"/"+marketIDs.length,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)marketIDs.length)*100);
            MARKET_GROUPS.add(new MarketGroup(group,(int)marketIDs[currentEntry-1]));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
        writeJSONFile();
    }
    
    public void jsonSDE() throws FileNotFoundException, IOException{
        MainLauncher.LOADING.addLoadingText("Loading MarketGroups.json");
        JsonNode nodes=FileHandler.loadJSONFile("SDE/marketGroups");
        int entries=nodes.size();
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+entries+"\n");
        int currentEntry=1;
        for(JsonNode node:nodes){
            MainLauncher.LOADING.addLoadingText("Parsing MarketGroups: "+currentEntry+"/"+entries,false);
            MainLauncher.LOADING.updateLoadingBars(((double)(currentEntry)/(double)entries)*100);
            MARKET_GROUPS.add(new MarketGroup(node));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
    }
    
    public void writeJSONFile() throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        String json=mapper.writeValueAsString(MARKET_GROUPS);
        FileHandler.writeJSONFile(json,"SDE/marketGroups");
    }

    public int getParentGroup(int groupID){
        for(MarketGroup group:MARKET_GROUPS){
            if(groupID==group.getGroupID()){
                return group.getParentGroupID();
            }
        }
        return 0;
    }
    
    public int getIconID(int groupID){
        for(MarketGroup group:MARKET_GROUPS){
            if(group.getGroupID()==groupID){
                return group.getIconID();
            }
        }
        return 0;
    }
    
    public String getGroupName(int groupID){
        for(MarketGroup group:MARKET_GROUPS){
            if(group.getGroupID()==groupID){
                return group.getName();
            }
        }
        return null;
    }
    
    public int getGroupID(String groupName){
        for(MarketGroup group:MARKET_GROUPS){
            if(groupName==null?group.getName()==null:groupName.equals(group.getName())){
                return group.getGroupID();
            }
        }
        return 0;
    }
    
    public ArrayList getSubMarketGroups(int groupID){
        ArrayList<MarketGroup> marketGroups=new ArrayList<>();
        for(MarketGroup marketGroup:MARKET_GROUPS){
            if(marketGroup.getParentGroupID()==groupID){
                marketGroups.add(marketGroup);
            }
        }
        return marketGroups;
    }

    public class MarketGroup{
        private int groupID;
        private int iconID;
        private int parentGroupID;
        private String name;

        public int getGroupID() {
            return groupID;
        }

        public int getIconID() {
            return iconID;
        }

        public int getParentGroupID() {
            return parentGroupID;
        }

        public String getName() {
            return name;
        }

        public MarketGroup(JsonNode json) throws IOException{
            try{
                groupID=json.get("groupID").intValue();
            }catch(NullPointerException e){
                groupID=0;
            }
            try{
                name=json.get("name").textValue();
            }catch(NullPointerException e){
                name=null;
            }
            try{
                iconID=json.get("iconID").intValue();
            }catch(NullPointerException e){
                iconID=0;
            }
            try{
                parentGroupID=json.get("parentGroupID").intValue();
            }catch(NullPointerException e){
                parentGroupID=0;
            }
        }

        public MarketGroup(Object obj,int id){
            groupID = id;
            iconID = 0;
            parentGroupID=0;
            name = null;
            Object[] keys=FileHandler.getKeySet(obj);
            Object[] values=FileHandler.getValuesSet(obj);
            for(int i=0;i<keys.length;i++){
                switch(String.valueOf(keys[i])){
                    case "iconID":
                        iconID=(Integer)values[i];
                        break;
                    case "nameID":
                        Object[]parseKeys=FileHandler.getKeySet(values[i]);
                        Object[]parseValues=FileHandler.getValuesSet(values[i]);
                        for(int i1=0;i1<parseKeys.length;i1++){
                            if("en".equals(String.valueOf(parseKeys[i1]))){
                                name=String.valueOf(parseValues[i1]).replace("\"","");
                            }
                        }
                        break;
                    case "parentGroupID":
                        parentGroupID=(Integer)values[i];
                        break;
                    default:;
                }
            }
        }
    }
}