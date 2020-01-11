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
public class GroupIDs{
    private static final ArrayList<Group> GROUPS=new ArrayList<>();
    
    public void yamlSDE() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading GroupIDs.yaml");
        Object data=FileHandler.loadYAMLFile("SDE/groupIDs");
        Object[] groupIDs=FileHandler.getKeySet(data);
        Object[] groups=FileHandler.getValuesSet(data);
        int currentEntry=1;
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+groupIDs.length+"\n");
        for(Object group:groups){
            MainLauncher.LOADING.addLoadingText("Parsing Group: "+currentEntry+"/"+groupIDs.length,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)groupIDs.length)*100);
            GROUPS.add(new Group(group,(Integer)groupIDs[currentEntry-1]));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
        writeJSONFile();
    }
    
    public void jsonSDE() throws FileNotFoundException, IOException{
        MainLauncher.LOADING.addLoadingText("Loading GroupIDs.json");
        JsonNode nodes=FileHandler.loadJSONFile("SDE/groupIDs");
        int entries=nodes.size();
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+entries+"\n");
        int currentEntry=1;
        for(JsonNode node:nodes){
            MainLauncher.LOADING.addLoadingText("Parsing Group: "+currentEntry+"/"+entries,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)entries)*100);
            GROUPS.add(new Group(node));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
    }
    
    public void writeJSONFile() throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        String json=mapper.writeValueAsString(GROUPS);
        FileHandler.writeJSONFile(json,"SDE/groupIDs");
    }
    
    public String getGroupName(int groupID){
        for(Group group:GROUPS){
            if(groupID==group.getGroupID()){
                return group.getName();
            }
        }
        return null;
    }

    public int getCategoryID(int groupID){
        for(Group group:GROUPS){
            if(groupID==group.getGroupID()){
                return group.getCategoryID();
            }
        }
        return 0;
    }
    
    public boolean isGroupPublished(int groupID){
        for(Group group:GROUPS){
            if(groupID==group.getGroupID()){
                return group.isPublished();
            }
        }
        return false;
    }
    
    public ArrayList getStructureRiqGroupIDs(){
        ArrayList<Integer> structureGroups = new ArrayList<>();
        for (Group g:GROUPS){
            if(g.isPublished()&&g.getName().contains("Rig")&&g.getName().contains("Structure")&&g.getName().contains(" - ")){
                structureGroups.add(g.getGroupID());
            }
        }
        return structureGroups;
    }

    public int getGroupID(String groupName){
        for(Group group:GROUPS){
            if(groupName.equals(group.getName())){
                return group.getGroupID();
            }
        }
        return 0;
    }
    
    public class Group{
        private int groupID;
        private int categoryID;
        private String name;
        private boolean published;

        public int getGroupID() {
            return groupID;
        }

        public int getCategoryID() {
            return categoryID;
        }

        public String getName() {
            return name;
        }

        public boolean isPublished() {
            return published;
        }

        public Group(JsonNode json) throws IOException{
            try{
                groupID=json.get("groupID").intValue();
            }catch(NullPointerException e){
                groupID=0;
            }
            try{
                categoryID=json.get("categoryID").intValue();
            }catch(NullPointerException e){
                categoryID=0;
            }
            try{
                name=json.get("name").toString();
            }catch(NullPointerException e){
                name=null;
            }
            try{
                published=json.get("published").booleanValue();
            }catch(NullPointerException e){
                published=false;
            }
        }

        public Group(Object obj,int id) {
            FileHandler file=new FileHandler();
            groupID =id;
            categoryID = 0;
            name = null;
            published = false;
            Object[] keys=FileHandler.getKeySet(obj);
            Object[] values=FileHandler.getValuesSet(obj);
            for(int i=0;i<keys.length;i++){
                switch(String.valueOf(keys[i])){
                    case"categoryID":
                        categoryID=(Integer)values[i];
                        break;
                    case"name":
                        Object[]parseKeys=FileHandler.getKeySet(values[i]);
                        Object[]parseValues=FileHandler.getValuesSet(values[i]);
                        for(int i1=0;i1<parseKeys.length;i1++){
                            if("en".equals(String.valueOf(parseKeys[i1]))){
                                name=String.valueOf(parseValues[i1]);
                            }
                        }
                        break;
                    case"published":
                        published=Boolean.valueOf(String.valueOf(values[i]));
                        break;
                    default:;
                }
            }
        }
    }
}
