/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sde;

import com.fasterxml.jackson.databind.JsonNode;
import indy.Materials;
import java.util.ArrayList;

/**
 *
 * @author HypeSwarm
 */
public class Reprocessing {
    private static final ArrayList<Ore> ORE_DATABASE=new ArrayList<>();
    
    public static void yamlSDE(){
        
    }
    
    public static void jsonSDE(){
        
    }
    
    public static int getReproUnits(int typeID){
        for(Ore ore:ORE_DATABASE){
            if(typeID==ore.getTypeID()){
                return ore.getReproUnits();
            }
        }
        return 0;
    }
    
    public static ArrayList getReproMaterials(int typeID){
        for(Ore ore:ORE_DATABASE){
            if(typeID==ore.getTypeID()){
                return ore.getReproMaterials();
            }
        }
        return null;
    }
    
    public static boolean isReprocessable(int typeID){
        for(Ore ore:ORE_DATABASE){
            if(typeID==ore.getTypeID()){
                return true;
            }
        }
        return false;
    }
}

class Ore{
    private int typeID;
    private int reproUnits;
    private ArrayList<Materials> reproMaterials;

    public int getTypeID() {
        return typeID;
    }

    public int getReproUnits() {
        return reproUnits;
    }

    public ArrayList<Materials> getReproMaterials() {
        return reproMaterials;
    }
    
    public Ore(JsonNode json){
        try{
            typeID=json.get("typeID").asInt();
        }catch(NullPointerException e){
            typeID=0;
        }
        try{
            reproUnits=json.get("reproUnits").asInt();
        }catch(NullPointerException e){
            reproUnits=0;
        }
        try{
            JsonNode materials=json.get("reproMaterials");
            reproMaterials=new ArrayList<>();
            for(JsonNode mat:materials){
                reproMaterials.add(new Materials(mat));
            }
        }catch(NullPointerException e){
            reproMaterials=null;
        }
    }
    
    public Ore(Object obj){
        
    }
}