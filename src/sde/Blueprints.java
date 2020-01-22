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
    //ArrayList that stores all data loaded from Blueprints SDE File
    private static final ArrayList<Blueprint> BLUEPRINT_DATA=new ArrayList();
    
    //Loads blueprints.yaml into ArrayList (Slower)
    //Only runs when .json is missing or newer SDE files have been downloaded
    public void yamlSDE() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading Blueprints.yaml"); //Update loading window
        Object data=FileHandler.loadYAMLFile("SDE/blueprints"); //Open file and load into object
        Object[] blueprints=FileHandler.getValuesSet(data); //Split each yaml entry into an array
        int currentEntry=1;
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+blueprints.length+"\n"); //Update loading window with total entries
        //Loop over each yaml entry
        for(Object blueprint:blueprints){
            MainLauncher.LOADING.addLoadingText("Parsing Blueprint: "+currentEntry+"/"+blueprints.length,false); //Update loading window with current entry
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)blueprints.length)*100); //Update loading window % bars
            //Add new Blueprint from the data of the yaml entry
            BLUEPRINT_DATA.add(new Blueprint(blueprint)); 
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete"); //Update loading window
        //Save ArrayList to .json file so next loading is faster
        writeJSONFile();
    }
    
    //Loads blueprints.json into ArrayList (Faster)
    public void jsonSDE() throws FileNotFoundException, IOException{
        MainLauncher.LOADING.addLoadingText("Loading Blueprints.json"); //Update loading window
        JsonNode nodes=FileHandler.loadJSONFile("SDE/blueprints"); //Open file and load into Json Node
        int entries=nodes.size();
        MainLauncher.LOADING.addLoadingText("Entries for Parsing: "+entries+"\n"); //Update loading window with total entries
        int currentEntry=1;
        //Loop over each json entry
        for(JsonNode node:nodes){
            MainLauncher.LOADING.addLoadingText("Parsing Blueprint: "+currentEntry+"/"+entries,false); //Update loading window with current entry
            MainLauncher.LOADING.updateLoadingBars(((double)currentEntry/(double)entries)*100); //Update loading window % bars
            //Add new Blueprint from the data of the json entry
            BLUEPRINT_DATA.add(new Blueprint(node));
            currentEntry++;
        }
        MainLauncher.LOADING.addLoadingText("Parsing Complete"); //Update loading window
    }
    
    //Write ArrayList to .json file
    private void writeJSONFile() throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        String json=mapper.writeValueAsString(BLUEPRINT_DATA); //Convert whole ArrayList to Json String
        FileHandler.writeJSONFile(json,"SDE/blueprints"); //Wtite .json file
    }
    
    
    //Check if an Item can be built from a Blueprint
    public boolean isBlueprintOutcome(int itemID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Check if blueprint has a Manufacturing Part
            if(BP.getActivities().getManufacturing()!=null){
                //Check if the outputID matches and return if True
                if(BP.getActivities().getManufacturing().getProductTypeID()==itemID){
                    return true;
                }
            }
            //Check if blueprint has a Reactions Part
            if(BP.getActivities().getReaction()!=null){
                //Check if the outputID matches and return if True
                if(BP.getActivities().getReaction().getProductTypeID()==itemID){
                    return true;
                }
            }
        }
        return false; //Return false if nothing matched
    }
    
    //Gets Blueprint based of the outputID
    public Blueprint getBlueprintByOutputID(int outputID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Check if blueprint has a Manufacturing Part
            if(BP.getActivities().getManufacturing()!=null){
                //Check if the outputID matches and return blueprint if True
                if(BP.getActivities().getManufacturing().getProductTypeID()==outputID){
                    return BP;
                }
            }
            //Check if blueprint has a Reactions Part
            if(BP.getActivities().getReaction()!=null){
                //Check if the outputID matches and return blueprint if True
                if(BP.getActivities().getReaction().getProductTypeID()==outputID){
                    return BP;
                }
            }
        }
        return null; //Return null if nothing matched (Technically should never hit this return if isBlueprintOutcome() check is done first)
    }
    
    
    //Returns a list if Industry IDs that the Blueprint can do
    public ArrayList getActivityIDs(int blueprintID){
        ArrayList<Integer>ids=new ArrayList<>(); //New list for all IDs
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID
            if(blueprintID==BP.getBluePrintID()){
                Activities activity=BP.getActivities(); //Pull the Activities Class from the bluerint
                //Check if blueprint has a Manufacturing Part
                if(activity.getManufacturing()!=null){
                    ids.add(1); //ID 1 = Manufactoring
                }
                //Check if blueprint has a Research Part
                if(activity.getResearchTime()!=0){
                    ids.add(2); //ID 2 = Research
                }
                //Check if blueprint has a Copying Part
                if(activity.getCopyingTime()!=0){
                    ids.add(5); //ID 5 = Copying
                }
                //Check if blueprint has an Invention Part
                if(activity.getInvention()!=null){
                    ids.add(8); //ID 8 = Invention
                }
                //Check if blueprint has a Reaction Part
                if(activity.getReaction()!=null){
                    ids.add(11); //ID 11 = Reaction
                }
                return ids; //Return the List of Industry IDs (Breaks out of Loop)
            }
        }
        return null; //Return Null if no match (Technically shouldn't be hit)
    }
    
    //Gets the Materials List of a Manufactoring Job
    public ArrayList<Materials> getManufactoringMaterials(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return the Marerials List
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getManufacturing().getMaterials();
            }
        }
        return null; //Return Null if no match (Technically shouldn't be hit)
    }
    
    //Gets the Materials List of a Reaction Job
    public ArrayList<Materials> getReactionMaterials(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return the Marerials List
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getReaction().getMaterials();
            }
        }
        return null; //Return Null if no match (Technically shouldn't be hit)
    }
    
    //Gets the Materials List of an Invention Job
    public ArrayList<Materials> getInventionMaterials(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return the Marerials List
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getInvention().getMaterials();
            }
        }
        return null; //Return Null if no match (Technically shouldn't be hit)
    }

    //Gets the Manufactoring Output ID of a Blueprint
    public int getManufactoringOutputID(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return outputID
            if(blueprintID==BP.getBluePrintID()){
                if(BP.getActivities().getManufacturing()!=null){
                    return BP.getActivities().getManufacturing().getProductTypeID();
                }else{
                    return 0; //Return 0 if blueprint has no Manufactoring Part (Technically shouldn't be hit)
                }
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }
    
    //Gets the Reaction Output ID of a Blueprint
    public int getReactionOutputID(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return outputID
            if(blueprintID==BP.getBluePrintID()){
                if(BP.getActivities().getReaction()!=null){
                    return BP.getActivities().getReaction().getProductTypeID();
                }
            }else{
                return 0; //Return 0 if blueprint has no Reaction Part (Technically shouldn't be hit)
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }
    
    //Gets the Invention Outcome IDs of a Blueprint
    public ArrayList getInventionOutputs(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return outcomes
            if(blueprintID==BP.getBluePrintID()){
                if(BP.getActivities().getInvention()!=null){
                    return BP.getActivities().getInvention().getOutcomes();
                }else{
                    return null; //Return Null if blueprint has no Invention Part (Technically shouldn't be hit)
                }
            }
        }
        return null; //Return Null if no match (Technically shouldn't be hit)
    }
    
    //Get the Manufactoring Output Quantity of a Blueprint
    public int getManufactoringOutputQuantity(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return Manufactoring Product Quantity
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getManufacturing().getProductQuantity();
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }
    
    //Get the Reaction Output Quantity of a Blueprint
    public int getReactionOutputQuantity(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return Reaction Product Quantity
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getReaction().getProductQuantity();
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }
    
    //Get the Manufactoring Duration of a Blueprint
    public int getManufactoringDuration(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return Manufactoring Duration
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getManufacturing().getProductionTime();
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }
    
    //Get the Reseach Duration of a Blueprint
    public int getResearchDuration(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return Research Duration
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getResearchTime();
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }
    
    //Get the Copying Duration of a Blueprint
    public int getCopyingDuration(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return Copying Duration
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getCopyingTime();
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }
    
    //Get the Invention Duration of a Blueprint
    public int getInventionDuration(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return Invention Duration
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getInvention().getProductionTime();
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }
    
    //Get the Reaction Duration of a Blueprint
    public int getReactionDuration(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return Reaction Duration
            if(blueprintID==BP.getBluePrintID()){
                return BP.getActivities().getReaction().getProductionTime();
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }
    
    //Get the Max BPC runs of a Blueprint
    public int getMaxRuns(int blueprintID){
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return Max BPC Runs
            if(blueprintID==BP.getBluePrintID()){
                return BP.getMaxProductionLimit();
            }
        }
        return 0; //Return 0 if no match (Technically shouldn't be hit)
    }

    //Finds the Blueprint based on blueprintID
    public Blueprint getBlueprintByID(int blueprintID) {
        //Loop over whole ArrayList
        for(Blueprint BP:BLUEPRINT_DATA){
            //Finds the blueprint that matches the ID and return
            if(blueprintID==BP.getBluePrintID()){
                return BP;
            }
        }
        return null; //Return null if no match (Technically shouldn't be hit)
    }

    //Main Data Class for the Static ArrayList
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

        //Load Data from Json Entry
        public Blueprint(JsonNode json) throws IOException{
            try{
                activities=new Activities(json.get("activities")); //If there is a Node then create new Activities and pass Json Node
            }catch(NullPointerException e){
                activities=null; //If no matching node set default value
            }
            try{
                bluePrintID=json.get("bluePrintID").intValue(); //If there is a Node then set Value
            }catch(NullPointerException e){
                bluePrintID=0; //If no matching node set default value
            }
            try{
                maxProductionLimit=json.get("maxProductionLimit").intValue(); //If there is a Node then set Value
            }catch(NullPointerException e){
                maxProductionLimit=0; //If no matching node set default value
            }
        }

        //Load Data from Yaml Entry
        public Blueprint(Object obj){
            //Set default values
            activities = null;
            bluePrintID = 0;
            maxProductionLimit = 0;
            Object[] keys=FileHandler.getKeySet(obj); //Get the Data Keys from the Entry
            Object[] values=FileHandler.getValuesSet(obj); //Get the Data Values from the Entry
            //For each Data Key
            for(int i=0;i<keys.length;i++){
                //Match Key to a case
                switch(String.valueOf(keys[i])){
                    case "activities": //If this case then create new Activities and pass assigned Data Value to it
                        activities=new Activities(values[i]);
                        break;
                    case "blueprintTypeID": //If this case then set value
                        bluePrintID=(Integer)values[i];
                        break;
                    case "maxProductionLimit": //If this case then set value
                        maxProductionLimit=(Integer)values[i];
                        break;
                    default:;
                }
            }
        }

        //Overide toString to only return the Name of the Blueprint
        @Override
        public String toString() {
            //Get the name of the Blueprint from the TypeIDs Database and return
            return SDEDatabase.TYPE_IDS.getTypeName(bluePrintID);
        }
        
    }
    
    //Activities Sub Data Class
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

        //Load Data from passed Json Node
        public Activities(JsonNode json) throws IOException{
            try{
                copyingTime=json.get("copyingTime").intValue(); //If there is a Node then set Value
            }catch(NullPointerException e){
                copyingTime=0; //If no matching node set default value
            }
            if(!"null".equals(json.get("invention").toString())){ //Chech if node is not null
                invention=new Invention(json.get("invention")); //Create new Invention and pass Json Node
            }else{
                invention=null; //If node value is null then set null
            }
            if(!"null".equals(json.get("manufacturing").toString())){ //Chech if node is not null
                manufacturing=new Manufacturing(json.get("manufacturing")); //Create new Manufactoring and pass Json Node
            }else{
                manufacturing=null; //If node value is null then set null
            }
            try{
                researchTime=json.get("researchTime").intValue(); //If there is a Node then set Value
            }catch(NullPointerException e){
                researchTime=0; //If no matching node set default value
            }
            if(!"null".equals(json.get("reaction").toString())){  //Chech if node is not null
                reaction=new Reaction(json.get("reaction")); //Create new Reaction and pass Json Node
            }else{
                reaction=null; //If node value is null then set null
            }
        }

        //Load Data from passed Key Value
        public Activities(Object obj){
            //Set Default Values
            copyingTime = 0;
            invention = null;
            manufacturing = null;
            researchTime = 0;
            reaction = null;
            Object[] keys=FileHandler.getKeySet(obj); //Get the Data Keys from the Key Value
            Object[] values=FileHandler.getValuesSet(obj); //Get the Data Values from the Key Value
            //For each Data Key
            for(int i=0;i<keys.length;i++){
                //Match Key to a case
                switch(String.valueOf(keys[i])){
                    case "copying": //If this case then find sub key and value
                        Object[] copyKeys=FileHandler.getKeySet(values[i]); //Get sub Key Set
                        int t=0;
                        //Loop over Sub Keys
                        for(Object copyKey:copyKeys){
                            if(copyKey.equals("time")){ //If key is matched then set Value
                                copyingTime=(Integer)FileHandler.getValuesSet(values[i])[t];
                            }
                            t++;
                        }
                        break;
                    case "invention": //If this case then create new Invention and pass assigned Data Value to it
                        invention=new Invention(values[i]);
                        break;
                    case "manufacturing": //If this case then create new Activities and pass assigned Data Value to it
                        manufacturing=new Manufacturing(values[i]);
                        break;
                    case "research_material": //If this case then find sub key and value
                        Object[] researchKeys=FileHandler.getKeySet(values[i]); //Get sub Key Set
                        t=0;
                        //Loop over Sub Keys
                        for(Object researchKey:researchKeys){
                            if(researchKey.equals("time")){ //If key is matched then set Value
                                researchTime=(Integer)FileHandler.getValuesSet(values[i])[t];
                            }
                            t++;
                        }
                        break;
                    case "reaction": //If this case then create new Activities and pass assigned Data Value to it
                        reaction=new Reaction(values[i]);
                        break;
                    default:;
                }
            }
        }
    }
    
    //Activity Manufacturing Sub Data Class
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

        //Load Data from passed Json Node
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

        //Load Data from passed Key Value
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

    //Activity Reaction Sub Data Class
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

        //Load Data from passed Json Node
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

        //Load Data from passed Key Value
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

    //Activity Invention Sub Data Class
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

        //Load Data from passed Json Node
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