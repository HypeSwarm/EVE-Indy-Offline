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
public class CategoryIDs {
    private static final ArrayList<Category> CATEGORIES=new ArrayList<>();
    
    public void yamlSDE() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading CategoryIDs.yaml");
        Object data=FileHandler.loadYAMLFile("SDE/categoryIDs");
        Object[] categoryIDs=FileHandler.getKeySet(data);
        Object[] categories=FileHandler.getValuesSet(data);
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+categories.length);
        int currentEntry=1;
        for(Object cetegory:categories){
            MainLauncher.LOADING.addLoadingText("Parsing Category: "+currentEntry+"/"+categories.length,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)categories.length)*100);
            CATEGORIES.add(new Category(cetegory,(int)categoryIDs[currentEntry-1]));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
        writeJSONFile();
    }
    
     public void jsonSDE() throws FileNotFoundException, IOException{
        MainLauncher.LOADING.addLoadingText("Loading CategotyIDs.json");
        JsonNode nodes=FileHandler.loadJSONFile("SDE/categoryIDs");
        int entries=nodes.size();
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+entries+"\n");
        int currentEntry=1;
        for(JsonNode node:nodes){
            MainLauncher.LOADING.addLoadingText("Parsing Category: "+currentEntry+"/"+entries,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)entries)*100);
            CATEGORIES.add(new Category(node));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
    }
    
    public void writeJSONFile() throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        String json=mapper.writeValueAsString(CATEGORIES);
        FileHandler.writeJSONFile(json,"SDE/categoryIDs");
    }
    
    public String getCategotyName(int categoryID){
        for(Category category:CATEGORIES){
            if(categoryID==category.getCategoryID()){
                return category.getName();
            }
        }
        return null;
    }
    
    public boolean isCategoryPublished(int categoryID){
        for(Category category:CATEGORIES){
            if(categoryID==category.getCategoryID()){
                return category.isPublicshed();
            }
        }
        return false;
    }
}

class Category{
    private int categoryID;
    private String name;
    private boolean published;

    public int getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public boolean isPublicshed() {
        return published;
    }
    
    public Category(JsonNode json) throws IOException{
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

    public Category(Object obj, int id) {
        categoryID =id;
        name = null;
        published = false;
        Object[] keys=FileHandler.getKeySet(obj);
        Object[] values=FileHandler.getValuesSet(obj);
        for(int i=0;i<keys.length;i++){
            switch(String.valueOf(keys[i])){
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