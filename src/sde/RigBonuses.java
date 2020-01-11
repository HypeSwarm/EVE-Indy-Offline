/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import gui.MainLauncher;
import java.util.ArrayList;

/**
 *
 * @author HypeSwarm
 */
public class RigBonuses {
    private static final ArrayList<RigBonus> RIG_BONUSES=new ArrayList<>();
    
    public void yamlSDE() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading rigBonuses.csv");
        Path file=Paths.get("SDE/rigBonuses.csv");
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: 439"+"\n");
        int currentEntry=1;
        try(BufferedReader br=Files.newBufferedReader(file,StandardCharsets.US_ASCII)){
            String line=br.readLine();
            while(line!=null){
                MainLauncher.LOADING.addLoadingText("Parsing Rigbonus: "+currentEntry+"/439",false);
                MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/439.0)*100);
                String[] sData=line.split(",");
                RIG_BONUSES.add(new RigBonus(Integer.parseInt(sData[0]),Integer.parseInt(sData[1]),Integer.parseInt(sData[2]),Boolean.parseBoolean(sData[3]),Boolean.parseBoolean(sData[4]),Boolean.parseBoolean(sData[5])));
                currentEntry++;
                line=br.readLine();
            }
            br.close();
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
        writeJSONFile();
    }
    
    public void jsonSDE() throws FileNotFoundException, IOException{
        MainLauncher.LOADING.addLoadingText("Loading RigBonuses.json");
        JsonNode nodes=FileHandler.loadJSONFile("SDE/rigBonuses");
        int entries=nodes.size();
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+entries+"\n");
        int currentEntry=1;
        for(JsonNode node:nodes){
            MainLauncher.LOADING.addLoadingText("Parsing RigBonuses: "+currentEntry+"/"+entries,false);
            MainLauncher.LOADING.updateLoadingBars(((double)(currentEntry)/(double)entries)*100);
            RIG_BONUSES.add(new RigBonus(node));
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
    }
    
    public void writeJSONFile() throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        String json=mapper.writeValueAsString(RIG_BONUSES);
        FileHandler.writeJSONFile(json,"SDE/rigBonuses");
    }
    
    public boolean isMEBonused(int rigID, int categoryID, int groupID){
        for(RigBonus bonus:RIG_BONUSES){
            if(rigID==bonus.getRigID()){
                if(bonus.getCategoryID()==categoryID||bonus.getGroupID()==groupID){
                    return bonus.isMEBonused();
                }
            }
        }
        return false;
    }
    
    public boolean isTEBonused(int rigID, int categoryID, int groupID){
        for(RigBonus bonus:RIG_BONUSES){
            if(rigID==bonus.getRigID()){
                if(bonus.getCategoryID()==categoryID||bonus.getGroupID()==groupID){
                    return bonus.isTEBonused();
                }
            }
        }
        return false;
    }
    
    public boolean isCOSTBonused(int rigID, int categoryID, int groupID){
        for(RigBonus bonus:RIG_BONUSES){
            if(rigID==bonus.getRigID()){
                if(bonus.getCategoryID()==categoryID||bonus.getGroupID()==groupID){
                    return bonus.isCOSTBonused();
                }
            }
        }
        return false;
    }
}

class RigBonus{
    private int rigID;
    private int categoryID;
    private int groupID;
    private boolean MEBonused;
    private boolean TEBonused;
    private boolean COSTBonused;

    public int getRigID() {
        return rigID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getGroupID() {
        return groupID;
    }

    public boolean isMEBonused() {
        return MEBonused;
    }

    public boolean isTEBonused() {
        return TEBonused;
    }

    public boolean isCOSTBonused() {
        return COSTBonused;
    }
    
    public RigBonus(JsonNode json) throws IOException{
        try{
            rigID=json.get("rigID").intValue();
        }catch(NullPointerException e){
            rigID=0;
        }
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
            MEBonused=json.get("mebonused").booleanValue();
        }catch(NullPointerException e){
            MEBonused=false;
        }
        try{
            TEBonused=json.get("tebonused").booleanValue();
        }catch(NullPointerException e){
            TEBonused=false;
        }
        try{
            COSTBonused=json.get("costbonused").booleanValue();
        }catch(NullPointerException e){
            COSTBonused=false;
        }
    }

    public RigBonus(int rigID,int groupID,int categoryID,boolean isMEBonused,boolean isTEBonused,boolean isCOSTBonused) {
        this.rigID = rigID;
        this.categoryID = categoryID;
        this.groupID = groupID;
        this.MEBonused = isMEBonused;
        this.TEBonused = isTEBonused;
        this.COSTBonused = isCOSTBonused;
    }
}