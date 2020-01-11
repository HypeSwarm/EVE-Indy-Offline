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
public class IconIDs {
    private static final ArrayList<Icon> ICONS=new ArrayList<>();
    
    public void yamlSDE() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading IconIDs.yaml");
        Object data=FileHandler.loadYAMLFile("SDE/iconIDs");
        Object[] iconIDs=FileHandler.getKeySet(data);
        Object[] icons=FileHandler.getValuesSet(data);
        int currentEntry=1;
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+iconIDs.length+"\n");
        for(Object icon:icons){
            MainLauncher.LOADING.addLoadingText("Parsing IconID: "+currentEntry+"/"+iconIDs.length,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)iconIDs.length)*100);
            ICONS.add(new Icon(icon,(int)iconIDs[currentEntry-1]));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
        writeJSONFile();
    }
    
    public void jsonSDE() throws FileNotFoundException, IOException{
        MainLauncher.LOADING.addLoadingText("Loading IconIDs.json");
        JsonNode nodes=FileHandler.loadJSONFile("SDE/iconIDs");
        int entries=nodes.size();
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+entries+"\n");
        int currentEntry=1;
        for(JsonNode node:nodes){
            MainLauncher.LOADING.addLoadingText("Parsing IconID: "+currentEntry+"/"+entries,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)entries)*100);
            ICONS.add(new Icon(node));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
    }
    
    public void writeJSONFile() throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        String json=mapper.writeValueAsString(ICONS);
        FileHandler.writeJSONFile(json,"SDE/iconIDs");
    }
    
    public String getIconPath(int iconID){
        for(Icon icon:ICONS){
            if(iconID==icon.getIconID()){
                return icon.getIconFile();
            }
        }
        return null;
    }
}

class Icon{
    private int iconID;
    private String iconFile;

    public int getIconID() {
        return iconID;
    }

    public String getIconFile() {
        return iconFile;
    }

    public Icon(JsonNode json) throws IOException{
        try{
            iconID=json.get("iconID").intValue();
        }catch(NullPointerException e){
            iconID=0;
        }
        try{
            iconFile=json.get("iconFile").textValue();
        }catch(NullPointerException e){
            iconFile=null;
        }
    }
    
    public Icon(Object obj, int id){
        iconID=id;
        iconFile=null;
        Object[] keys=FileHandler.getKeySet(obj);
        Object[] values=FileHandler.getValuesSet(obj);
        for(int i=0;i<keys.length;i++){
            switch(String.valueOf(keys[i])){
                case "iconFile":
                    String s=String.valueOf(values[i]);
                    if(s.indexOf("/icons/")>0){
                        iconFile=s.substring(s.indexOf("/icons/")+7).trim();
                    }else{
                        iconFile=s.substring(s.indexOf("/Icons/")+7).trim();
                    }
                    break;
                default:;
            }
        }
    }
}