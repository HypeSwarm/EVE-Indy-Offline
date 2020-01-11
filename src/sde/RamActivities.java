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
import java.util.ArrayList;
import gui.MainLauncher;

/**
 *
 * @author HypeSwarm
 */
public class RamActivities{
    private static final ArrayList<Activity> ACTIVITIES=new ArrayList<>();
    
    public void yamlSDE() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading RamActivities.yaml");
        ArrayList activities=(ArrayList) FileHandler.loadYAMLFile("SDE/ramActivities");
        int currentEntry=1;
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+activities.size()+"\n");
        for(Object acticity:activities){
            MainLauncher.LOADING.addLoadingText("Parsing RamActivities: "+currentEntry+"/"+activities.size(),false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)activities.size())*100);
            ACTIVITIES.add(new Activity(acticity));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
        writeJSONFile();
    }
    
    public void jsonSDE() throws FileNotFoundException, IOException{
        MainLauncher.LOADING.addLoadingText("Loading RamActivities.json");
        JsonNode nodes=FileHandler.loadJSONFile("SDE/ramActivities");
        int entries=nodes.size();
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+entries+"\n");
        int currentEntry=1;
        for(JsonNode node:nodes){
            MainLauncher.LOADING.addLoadingText("Parsing RamActivities: "+currentEntry+"/"+entries,false);
            MainLauncher.LOADING.updateLoadingBars(((double)(currentEntry)/(double)entries)*100);
            ACTIVITIES.add(new Activity(node));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
    }
    
    public void writeJSONFile() throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        String json=mapper.writeValueAsString(ACTIVITIES);
        FileHandler.writeJSONFile(json,"SDE/ramActivities");
    }
    
    public String getActivityName(int activityID){
        for(Activity activity:ACTIVITIES){
            if(activityID==activity.getActivityID()){
                return activity.getActivityName();
            }
        }
        return null;
    }
    
    public boolean isActivityPublished(int activityID){
        for(Activity activity:ACTIVITIES){
            if(activityID==activity.getActivityID()){
                return activity.isPublished();
            }
        }
        return false;
    }
}

class Activity{
    private int activityID;
    private String activityName;
    private boolean published;

    public int getActivityID() {
        return activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public boolean isPublished() {
        return published;
    }
    
    public Activity(JsonNode json) throws IOException{
        try{
            activityID=json.get("activityID").intValue();
        }catch(NullPointerException e){
            activityID=0;
        }
        try{
            activityName=json.get("activityName").textValue();
        }catch(NullPointerException e){
            activityName=null;
        }
        try{
            published=json.get("published").booleanValue();
        }catch(NullPointerException e){
            published=false;
        }
    }

    public Activity(Object obj) {
        activityID = 0;
        activityName = null;
        published = false;
        Object[] keys=FileHandler.getKeySet(obj);
        Object[] values=FileHandler.getValuesSet(obj);
        for(int i=0;i<keys.length;i++){
            switch(String.valueOf(keys[i])){
                case "activityID":
                    activityID=(Integer)values[i];
                    break;
                case "activityName":
                    activityName=String.valueOf(values[i]).replace("\"","");
                    break;
                case "published":
                    published=Boolean.valueOf(String.valueOf(values[i]));
                    break;
                default:;    
            }
        }
    }
    
}