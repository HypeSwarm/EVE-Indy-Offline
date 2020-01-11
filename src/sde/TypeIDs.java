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
public class TypeIDs {
    private static final ArrayList<Type> TYPES=new ArrayList<>();
    
    public void yamlSDE() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading TypeIDs.yaml");
        Object data=FileHandler.loadYAMLFile("SDE/typeIDs");
        Object[] typeIDs=FileHandler.getKeySet(data);
        Object[] types=FileHandler.getValuesSet(data);
        int currentEntry=1;
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+typeIDs.length+"\n");
        for(Object type:types){
            MainLauncher.LOADING.addLoadingText("Parsing TypeID: "+currentEntry+"/"+typeIDs.length,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)typeIDs.length)*100);
            TYPES.add(new Type(type,(Integer)typeIDs[currentEntry-1]));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
        writeJSONFile();
    }
    
    public void jsonSDE() throws FileNotFoundException, IOException{
        MainLauncher.LOADING.addLoadingText("Loading TypeIDs.json");
        JsonNode nodes=FileHandler.loadJSONFile("SDE/typeIDs");
        int entries=nodes.size();
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+entries+"\n");
        int currentEntry=1;
        for(JsonNode node:nodes){
            MainLauncher.LOADING.addLoadingText("Parsing TypeIDs: "+currentEntry+"/"+entries,false);
            MainLauncher.LOADING.updateLoadingBars(((double)(currentEntry)/(double)entries)*100);
            TYPES.add(new Type(node));
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
    }
    
    public void writeJSONFile() throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        String json=mapper.writeValueAsString(TYPES);
        FileHandler.writeJSONFile(json,"SDE/typeIDs");
    }
    
        
    public String getTypeName(int typeID){
        for(Type type:TYPES){
            if(typeID==type.getTypeID()){
                return type.getName();
            }
        }
        return null;
    }
    
    public int getGroupID(int typeID){
        for(Type type:TYPES){
            if(typeID==type.getTypeID()){
                return type.getGroupID();
            }
        }
        return 0;
    }
    
    public int getMarketGroupID(int typeID){
        for(Type type:TYPES){
            if(typeID==type.getTypeID()){
                return type.getMarketGroupID();
            }
        }
        return 0;
    }
    
    public int getMetaGroup(int typeID) {
        for(Type type:TYPES){
            if(type.getTypeID()==typeID&&type.isPublished()){
                return type.getMetaGroupID();
            }
        }
        return 0;
    }
    
    public int getVariationParentID(int typeID){
        for(Type type:TYPES){
            if(typeID==type.getTypeID()){
                return type.getVariationParentTypeID();
            }
        }
        return 0;
    }
    
    public boolean isPublished(int typeID){
        for(Type type:TYPES){
            if(type.getTypeID()==typeID){
                return type.isPublished();
            }
        }
        return false;
    }

    public double getVolume(int typeID) {
        for(Type type:TYPES){
            if(type.getTypeID()==typeID&&type.isPublished()){
                return type.getVolume();
            }
        }
        return 0;
    }
    
    public int getIconID(int typeID){
        for(Type type:TYPES){
            if(typeID==type.getTypeID()){
                return type.getIconID();
            }
        }
        return 0;
    }
    
    public int getTypeID(String typeName){
        for(Type type:TYPES){
            if(typeName==null?type.getName()==null:typeName.equals(type.getName())){
                return type.getTypeID();
            }
        }
        return 0;
    }
    
    public ArrayList getByMarketGroup(int groupID){
        ArrayList<Integer> ids=new ArrayList<>();
        for(Type type:TYPES){
            if(type.getMarketGroupID()==groupID&&type.isPublished()){
                ids.add(type.getTypeID());
            }
        }
        return ids;
    }
    
    public ArrayList getByGroupID(int groupID){
        ArrayList<String> names=new ArrayList<>();
        for(Type type:TYPES){
            if(type.getGroupID()==groupID&&type.isPublished()){
                names.add(type.getName());
            }
        }
        return names;
    }
}

class Type{
    private int typeID;
    private int groupID;
    private int iconID;
    private int metaGroupID;
    private int marketGroupID;
    private int variationParentTypeID;
    private String name;
    private double volume;
    private boolean published;

    public int getTypeID() {
        return typeID;
    }

    public int getGroupID() {
        return groupID;
    }

    public int getIconID() {
        return iconID;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public boolean isPublished() {
        return published;
    }

    public int getMetaGroupID() {
        return metaGroupID;
    }

    public int getMarketGroupID() {
        return marketGroupID;
    }

    public int getVariationParentTypeID() {
        return variationParentTypeID;
    }
    
    
    public Type(JsonNode json) throws IOException{
        try{
            typeID=json.get("typeID").intValue();
        }catch(NullPointerException e){
            typeID=0;
        }
        try{
            groupID=json.get("groupID").intValue();
        }catch(NullPointerException e){
            groupID=0;
        }
        try{
            iconID=json.get("iconID").intValue();
        }catch(NullPointerException e){
            iconID=0;
        }
        try{
            metaGroupID=json.get("metaGroupID").intValue();
        }catch(NullPointerException e){
            metaGroupID=0;
        }
        try{
            marketGroupID=json.get("marketGroupID").intValue();
        }catch(NullPointerException e){
            marketGroupID=0;
        }
        try{
            variationParentTypeID=json.get("variationParentTypeID").intValue();
        }catch(NullPointerException e){
            variationParentTypeID=0;
        }
        try{
            name=json.get("name").textValue();
        }catch(NullPointerException e){
            name=null;
        }
        try{
            volume=json.get("volume").intValue();
        }catch(NullPointerException e){
            volume=0;
        }
        try{
            published=json.get("published").booleanValue();
        }catch(NullPointerException e){
            published=false;
        }
    }

    public Type(Object obj,int id) {
        typeID =id;
        groupID = 0;
        iconID = 0;
        metaGroupID=0;
        marketGroupID=0;
        variationParentTypeID=0;
        name = null;
        volume = 0;
        published = false;
        Object[] keys=FileHandler.getKeySet(obj);
        Object[] values=FileHandler.getValuesSet(obj);
        for(int i=0;i<keys.length;i++){
            switch(String.valueOf(keys[i])){
                case "groupID":
                    groupID=(Integer)values[i];
                    break;
                case "iconID":
                    iconID=(Integer)values[i];
                    break;
                case "metaGroupID":
                    metaGroupID=(Integer)values[i];
                    break;
                case "marketGroupID":
                    marketGroupID=(Integer)values[i];
                    break;
                case "variationParentTypeID":
                    variationParentTypeID=(Integer)values[i];
                    break;
                case "name":
                    Object[]parseKeys=FileHandler.getKeySet(values[i]);
                    Object[]parseValues=FileHandler.getValuesSet(values[i]);
                    for(int i1=0;i1<parseKeys.length;i1++){
                        if("en".equals(String.valueOf(parseKeys[i1]))){
                            name=String.valueOf(parseValues[i1]);
                        }
                    }
                    break;
                case "volume":
                    volume=Double.valueOf(String.valueOf(values[i]));
                    break;
                case "published":
                    published=Boolean.valueOf(String.valueOf(values[i]));
                    break;
                default:;
            }
        }
    }
}
