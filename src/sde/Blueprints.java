/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sde;

import character.Skills;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import indy.Materials;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.MainLauncher;
import indy.InventionOutcome;
import java.io.FileNotFoundException;

/**
 *
 * @author HypeSwarm
 */
public final class Blueprints {
    private static final ArrayList<Blueprint> BLUEPRINT_DATA=new ArrayList();
    
    public void yamlSDE() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading Blueprints.yaml");
        Object data=FileHandler.loadYAMLFile("SDE/blueprints");
        Object[] blueprints=FileHandler.getValuesSet(data);
        int currentEntry=1;
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+blueprints.length+"\n");
        for(Object blueprint:blueprints){
            MainLauncher.LOADING.addLoadingText("Parsing Blueprint: "+currentEntry+"/"+blueprints.length,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)blueprints.length)*100);
            BLUEPRINT_DATA.add(new Blueprint(blueprint));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
        writeJSONFile();
    }
    
    public void jsonSDE() throws FileNotFoundException, IOException{
        MainLauncher.LOADING.addLoadingText("Loading Blueprints.json");
        JsonNode nodes=FileHandler.loadJSONFile("SDE/blueprints");
        int entries=nodes.size();
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+entries+"\n");
        int currentEntry=1;
        for(JsonNode node:nodes){
            MainLauncher.LOADING.addLoadingText("Parsing Blueprint: "+currentEntry+"/"+entries,false);
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)entries)*100);
            BLUEPRINT_DATA.add(new Blueprint(node));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete");
    }
    
    private void writeJSONFile() throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        String json=mapper.writeValueAsString(BLUEPRINT_DATA);
        FileHandler.writeJSONFile(json,"SDE/blueprints");
    }
    
    public boolean isBlueprintOutcome(int outputID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(BP.getActivities().getManufacturing()!=null){
                if(BP.getActivities().getManufacturing().getProductTypeID()==outputID){
                    return true;
                }
            }
            if(BP.getActivities().getReaction()!=null){
                if(BP.getActivities().getReaction().getProductTypeID()==outputID){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Blueprint getBlueprintByOutputID(int outputID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(BP.getActivities().getManufacturing()!=null){
                if(BP.getActivities().getManufacturing().getProductTypeID()==outputID){
                    return BP;
                }
            }
            if(BP.getActivities().getReaction()!=null){
                if(BP.getActivities().getReaction().getProductTypeID()==outputID){
                    return BP;
                }
            }
        }
        return null;
    }
    
    public ArrayList getActivityIDs(int blueprintID){
        ArrayList<Integer>ids=new ArrayList<>();
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                Activities activity=BP.getActivities();
                if(activity.getManufacturing()!=null){
                    ids.add(1);
                }
                if(activity.getResearchTime()!=0){
                    ids.add(2);
                }
                if(activity.getCopyingTime()!=0){
                    ids.add(5);
                }
                if(activity.getInvention()!=null){
                    ids.add(8);
                }
                if(activity.getReaction()!=null){
                    ids.add(11);
                }
                return ids;
            }
        }
        return ids;
    }
    
    public ArrayList<Materials> getIndyMaterials(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getManufacturing().getMaterials();
            }
        }
        return null;
    }
    
    public ArrayList<Materials> getReactionMaterials(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getReaction().getMaterials();
            }
        }
        return null;
    }
    
    public ArrayList<Materials> getInventionMaterials(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getInvention().getMaterials();
            }
        }
        return null;
    }

    public int getIndyOutputID(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                if(BP.getActivities().getManufacturing()!=null){
                    return BP.getActivities().getManufacturing().getProductTypeID();
                }
                return 0;
            }
        }
        return 0;
    }
    
    public int getReactionOutputID(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                if(BP.getActivities().getReaction()!=null){
                    return BP.getActivities().getReaction().getProductTypeID();
                }
            }
            return 0;
        }
        return 0;
    }
    
    public ArrayList getInventionOutputs(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                if(BP.getActivities().getInvention()!=null){
                    return BP.getActivities().getInvention().getOutcomes();
                }
                return null;
            }
        }
        return null;
    }
    
    public int getIndyOutputQuantity(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getManufacturing().getProductQuantity();
            }
        }
        return 0;
    }
    
    public int getReactionOutputQuantity(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getReaction().getProductQuantity();
            }
        }
        return 0;
    }
    
    public int getIndyDuration(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getManufacturing().getProductionTime();
            }
        }
        return 0;
    }
    
    public int getResearchDuration(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getResearchTime();
            }
        }
        return 0;
    }
    
    public int getCopyDuration(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getCopyingTime();
            }
        }
        return 0;
    }
    
    public int getInventionDuration(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getInvention().getProductionTime();
            }
        }
        return 0;
    }
    
    public int getReactionDuration(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getReaction().getProductionTime();
            }
        }
        return 0;
    }
    
    public int getMaxRuns(int blueprintID){
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP.getMaxProductionLimit();
            }
        }
        return 0;
    }

    public Blueprint getBlueprintByID(int blueprintID) {
        for(Blueprint BP:BLUEPRINT_DATA){
            if(blueprintID==BP.getBluePrintID()){
                return BP;
            }
        }
        return null;
    }

    public class Blueprint{
        private Activities activities;
        private int bluePrintID;
        private int maxProductionLimit;

        public Activities getActivities() {
            return activities;
        }

        public int getBluePrintID() {
            return bluePrintID;
        }

        public int getMaxProductionLimit() {
            return maxProductionLimit;
        }

        public Blueprint(JsonNode json) throws IOException{
            try{
                activities=new Activities(json.get("activities"));
            }catch(NullPointerException e){
                activities=null;
            }
            try{
                bluePrintID=json.get("bluePrintID").intValue();
            }catch(NullPointerException e){
                bluePrintID=0;
            }
            try{
            maxProductionLimit=json.get("maxProductionLimit").intValue();
            }catch(NullPointerException e){
                maxProductionLimit=0;
            }
        }

        public Blueprint(Object obj){
            activities = null;
            bluePrintID = 0;
            maxProductionLimit = 0;
            Object[] keys=FileHandler.getKeySet(obj);
            Object[] values=FileHandler.getValuesSet(obj);
            for(int i=0;i<keys.length;i++){
                switch(String.valueOf(keys[i])){
                    case "activities":
                        activities=new Activities(values[i]);
                        break;
                    case "blueprintTypeID":
                        bluePrintID=(Integer)values[i];
                        break;
                    case "maxProductionLimit":
                        maxProductionLimit=(Integer)values[i];
                        break;
                    default:;
                }
            }
        }

        @Override
        public String toString() {
            return SDEDatabase.TYPE_IDS.getTypeName(bluePrintID);
        }
        
    }
    
    public class Activities{
        private int copyingTime;
        private Invention invention;
        private Manufacturing manufacturing;
        private int researchTime;
        private Reaction reaction;

        public int getCopyingTime() {
            return copyingTime;
        }

        public Invention getInvention() {
            return invention;
        }

        public Manufacturing getManufacturing() {
            return manufacturing;
        }

        public int getResearchTime() {
            return researchTime;
        }

        public Reaction getReaction() {
            return reaction;
        }


        public Activities(JsonNode json) throws IOException{
            try{
                copyingTime=json.get("copyingTime").intValue();
            }catch(NullPointerException e){
                copyingTime=0;
            }
            if(!"null".equals(json.get("invention").toString())){
                invention=new Invention(json.get("invention"));
            }else{
                invention=null;
            }
            if(!"null".equals(json.get("manufacturing").toString())){
                manufacturing=new Manufacturing(json.get("manufacturing"));
            }else{
                manufacturing=null;
            }
            try{
                researchTime=json.get("researchTime").intValue();
            }catch(NullPointerException e){
                researchTime=0;
            }
            if(!"null".equals(json.get("reaction").toString())){
                reaction=new Reaction(json.get("reaction"));
            }else{
                reaction=null;
            }

        }


        public Activities(Object obj){
            copyingTime = 0;
            invention = null;
            manufacturing = null;
            researchTime = 0;
            reaction = null;
            Object[] keys=FileHandler.getKeySet(obj);
            Object[] values=FileHandler.getValuesSet(obj);
            for(int i=0;i<keys.length;i++){
                switch(String.valueOf(keys[i])){
                    case "copying":
                        Object[] copyKeys=FileHandler.getKeySet(values[i]);
                        int t=0;
                        for(Object copyKey:copyKeys){
                            if(copyKey.equals("time")){
                                copyingTime=(Integer)FileHandler.getValuesSet(values[i])[t];
                            }
                            t++;
                        }
                        break;
                    case "invention":
                        invention=new Invention(values[i]);
                        break;
                    case "manufacturing":
                        manufacturing=new Manufacturing(values[i]);
                        break;
                    case "research_material":
                        Object[] researchKeys=FileHandler.getKeySet(values[i]);
                        t=0;
                        for(Object researchKey:researchKeys){
                            if(researchKey.equals("time")){
                                researchTime=(Integer)FileHandler.getValuesSet(values[i])[t];
                            }
                            t++;
                        }
                        break;
                    case "reaction":
                        reaction=new Reaction(values[i]);
                        break;
                    default:;
                }
            }
        }
    }

    public class Manufacturing{
        private ArrayList<Materials> materials;
        private int productTypeID;
        private int productQuantity;
        private int productionTime;

        public ArrayList<Materials> getMaterials() {
            return materials;
        }

        public int getProductTypeID() {
            return productTypeID;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public int getProductionTime() {
            return productionTime;
        }

        public Manufacturing(JsonNode json) throws IOException{
            try{
                JsonNode mats=json.get("materials");
                materials=new ArrayList<>();
                for(JsonNode mat:mats){
                    materials.add(new Materials(mat));
                }
            }catch(NullPointerException e){
                materials=null;
            }
            try{
                productTypeID=json.get("productTypeID").intValue();
            }catch(NullPointerException e){
                productTypeID=0;
            }
            try{
                productQuantity=json.get("productQuantity").intValue();
            }catch(NullPointerException e){
                productQuantity=0;
            }
            try{
                productionTime=json.get("productionTime").intValue();
            }catch(NullPointerException e){
                productionTime=0;
            }
        }

        public Manufacturing(Object obj){
            Object[] keys=FileHandler.getKeySet(obj);
            Object[] values=FileHandler.getValuesSet(obj);
            ArrayList array;
            Object[] parseValues;
            for(int i=0;i<keys.length;i++){
                switch(String.valueOf(keys[i])){
                    case "materials":
                        array=(ArrayList)values[i];
                        materials=new ArrayList<>();
                        for(Object mats:array){
                            parseValues=FileHandler.getValuesSet(mats);
                            materials.add(new Materials((Integer)parseValues[1],(Integer)parseValues[0]));
                        }
                        break;
                    case "products":
                        array=(ArrayList)values[i];
                        parseValues=FileHandler.getValuesSet((Object)array.get(0));
                        productQuantity=(Integer)parseValues[0];
                        productTypeID=(Integer)parseValues[1];
                        break;
                    case "time":
                        productionTime=(Integer)values[i];
                        break;
                    default:;
                }
            }
        }
    }

    public class Reaction{
        private ArrayList<Materials> materials;
        private int productTypeID;
        private int productQuantity;
        private int productionTime;

        public ArrayList<Materials> getMaterials() {
            return materials;
        }

        public int getProductTypeID() {
            return productTypeID;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public int getProductionTime() {
            return productionTime;
        }

        public Reaction(JsonNode json) throws IOException{
            try{
                JsonNode mats=json.get("materials");
                materials=new ArrayList<>();
                for(JsonNode mat:mats){
                    materials.add(new Materials(mat));
                }
            }catch(NullPointerException e){
                materials=null;
            }
            try{
                productTypeID=json.get("productTypeID").intValue();
            }catch(NullPointerException e){
                productTypeID=0;
            }
            try{
                productQuantity=json.get("productQuantity").intValue();
            }catch(NullPointerException e){
                productQuantity=0;
            }
            try{
                productionTime=json.get("productionTime").intValue();
            }catch(NullPointerException e){
                productionTime=0;
            }
        }

        public Reaction(Object obj){
            Object[] keys=FileHandler.getKeySet(obj);
            Object[] values=FileHandler.getValuesSet(obj);
            ArrayList array;
            Object[] parseValues;
            for(int i=0;i<keys.length;i++){
                switch(String.valueOf(keys[i])){
                    case "materials":
                        array=(ArrayList)values[i];
                        materials=new ArrayList<>();
                        for(Object mats:array){
                            parseValues=FileHandler.getValuesSet(mats);
                            materials.add(new Materials((Integer)parseValues[1],(Integer)parseValues[0]));
                        }
                        break;
                    case "products":
                        array=(ArrayList)values[i];
                        parseValues=FileHandler.getValuesSet((Object)array.get(0));
                        productQuantity=(Integer)parseValues[0];
                        productTypeID=(Integer)parseValues[1];
                        break;
                    case "time":
                        productionTime=(Integer)values[i];
                        break;
                    default:;
                }
            }
        }
    }

    public class Invention{
        private ArrayList<Materials> materials;
        private ArrayList<Skills> skills;
        private ArrayList<InventionOutcome> outcomes;
        private int productionTime;

        public ArrayList<Materials> getMaterials() {
            return materials;
        }

        public ArrayList<Skills> getSkills() {
            return skills;
        }

        public ArrayList<InventionOutcome> getOutcomes() {
            return outcomes;
        }

        public int getProductionTime() {
            return productionTime;
        }

        public Invention(JsonNode json) throws IOException{
            try{
                JsonNode mats=json.get("materials");
                materials=new ArrayList<>();
                for(JsonNode mat:mats){
                    materials.add(new Materials(mat));
                }
            }catch(NullPointerException e){
                materials=null;
            }
            try{
                JsonNode jsonSkills=json.get("skills");
                skills=new ArrayList<>();
                for(JsonNode skill:jsonSkills){
                    skills.add(new Skills(skill));
                }
            }catch(NullPointerException e){
                skills=null;
            }
            try{
                JsonNode jsonOutcomes=json.get("outcomes");
                outcomes=new ArrayList<>();
                for(JsonNode outcome:jsonOutcomes){
                    outcomes.add(new InventionOutcome(outcome));
                }
            }catch(NullPointerException e){
                outcomes=null;
            }
            try{
                productionTime=json.get("productionTime").intValue();

            }catch(NullPointerException e){
                productionTime=0;
            }
        }

        public Invention(Object obj){
            Object[] keys=FileHandler.getKeySet(obj);
            Object[] values=FileHandler.getValuesSet(obj);
            ArrayList array;
            Object[] parseValues;
            for(int i=0;i<keys.length;i++){
                switch(String.valueOf(keys[i])){
                    case "materials":
                        array=(ArrayList)values[i];
                        materials=new ArrayList<>();
                        for(Object mat:array){
                            parseValues=FileHandler.getValuesSet(mat);
                            materials.add(new Materials((Integer)parseValues[1],(Integer)parseValues[0]));
                        }
                        break;
                    case "products":
                        array=(ArrayList)values[i];
                        outcomes=new ArrayList<>();
                        for(Object outcome:array){
                            parseValues=FileHandler.getValuesSet(outcome);
                            if(parseValues.length==3){
                                outcomes.add(new InventionOutcome((Integer)parseValues[2],(Integer)parseValues[1],(Double)parseValues[0]));
                            }else{
                                outcomes.add(new InventionOutcome((Integer)parseValues[1],(Integer)parseValues[0],0));
                            }
                        }
                        break;
                    case "skills":
                        array=(ArrayList)values[i];
                        skills=new ArrayList<>();
                        for(Object skill:array){
                            parseValues=FileHandler.getValuesSet(skill);
                            skills.add(new Skills((Integer)parseValues[1],(Integer)parseValues[0]));
                        }
                        break;
                    case"time":
                        productionTime=(Integer)values[i];
                    default:;
                }
            }
        }
    }
}