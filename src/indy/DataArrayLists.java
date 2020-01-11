/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import sde.FileHandler;

/**
 *
 * @author HypeSwarm
 */
public class DataArrayLists {
    public static final ArrayList<Structure> STRUCTURES=new ArrayList<>();
    public static final ArrayList<JobManufacturing> JOBS_MANUFACTURING=new ArrayList<>();
    public static final ArrayList<JobReaction> JOBS_REACTION=new ArrayList<>();
    public static final ArrayList<JobCopying> JOBS_COPYING=new ArrayList<>();
    public static final ArrayList<JobInvention> JOBS_INVENTION=new ArrayList<>();
    public static final ArrayList<JobResearch> JOBS_RESEARCH=new ArrayList<>();
    public static final ArrayList<Materials> ALL_MATS=new ArrayList<>();
    
    //ArrayList Get By Index
    public static Structure getStructure(int index){
        return STRUCTURES.get(index);
    }

    public static JobManufacturing getIndyJob(int index){
        return JOBS_MANUFACTURING.get(index);
    }
    
    public static JobReaction getReactionJob(int index){
        return JOBS_REACTION.get(index);
    }
    
    public static JobCopying getCopyingJob(int index){
        return JOBS_COPYING.get(index);
    }
    
    public static JobInvention getInventionJob(int index){
        return JOBS_INVENTION.get(index);
    }
    
    public static JobResearch getResearchJob(int index){
        return JOBS_RESEARCH.get(index);
    }
    
    //Get Structure By ID
    public static Structure getStructureByID(int structureID){
        for(Structure structure:STRUCTURES){
            if(structureID==structure.getStructureID()){
                return structure;
            }
        }
        return null;
    }
    
    //Add to ArrayList
    public static void addStructure(Structure structure){
        STRUCTURES.add(structure);
    }
    
    public static void addIndyJob(JobManufacturing jobIndy) {
        JOBS_MANUFACTURING.add(jobIndy);
    }
    
    public static void addReactionJob(JobReaction jobReaction) {
        JOBS_REACTION.add(jobReaction);
    }

    public static void addCopyingJob(JobCopying jobCopying) {
        JOBS_COPYING.add(jobCopying);
    }

    public static void addInventionJob(JobInvention jobInvention) {
        JOBS_INVENTION.add(jobInvention);
    }

    public static void addResearchJob(JobResearch jobResearch) {
        JOBS_RESEARCH.add(jobResearch);
    }
    
    //Remove from ArrayList
    public static void removeStructure(int index){
        STRUCTURES.remove(index);
    }
    
    public static void removeManufacturingJob(int index){
        JOBS_MANUFACTURING.remove(index);
    }
    
    public static void removeReactionJob(int index) {
        JOBS_REACTION.remove(index);
    }

    public static void removeCopyingJob(int index) {
        JOBS_COPYING.remove(index);
    }

    public static void removeInventionJob(int index) {
        JOBS_INVENTION.remove(index);
    }

    public static void removeResearchJob(int index) {
        JOBS_RESEARCH.remove(index);
    }
    
        
    public static void setAllMats(){
        ALL_MATS.clear();
        for(JobManufacturing job:JOBS_MANUFACTURING){
            for(Materials mat:job.getModifiedMaterials()){
                if(ALL_MATS.isEmpty()){
                    ALL_MATS.add(new Materials(mat.getTypeID(),mat.getQuantity()));
                }else{
                    boolean added=false;
                    for(Materials upMat:ALL_MATS){
                        if(upMat.getTypeID()==mat.getTypeID()){
                            upMat.setQuantity(upMat.getQuantity()+mat.getQuantity());
                            added=true;
                            
                        }
                    }
                    if(!(added))ALL_MATS.add(new Materials(mat.getTypeID(),mat.getQuantity()));
                }
            }
        }
        Collections.sort(ALL_MATS);
    }

    public static int getStructureJobIndex(int index) {
        int structureID=JOBS_MANUFACTURING.get(index).getStructureID();
        return getStructureIndex(structureID);
    }
    
    public static int getStructureIndex(int structureID){
        for(Structure s:STRUCTURES){
            if(s.getStructureID()==structureID){
                return STRUCTURES.indexOf(s);
            }
        }
        return 0;
    }
    
    public static void saveData(){
        ArrayList[] data={
            STRUCTURES,
            JOBS_MANUFACTURING,
            JOBS_REACTION,
            JOBS_COPYING,
            JOBS_INVENTION,
            JOBS_RESEARCH
        };
        try {
            String json = new ObjectMapper().writeValueAsString(data);
            FileHandler.writeJSONFile(json,"data/DataLists");
        } catch (Exception e){}
    }

    public static void loadData() throws IOException{
        if(FileHandler.checkExistingJSONFiles("data/DataLists")){
            JsonNode dataArray=FileHandler.loadJSONFile("data/DataLists");
            for(JsonNode structure:dataArray.get(0)){
                STRUCTURES.add(new Structure(structure));
            }
            for(JsonNode indyJob:dataArray.get(1)){
                JOBS_MANUFACTURING.add(new JobManufacturing(indyJob));
            }
            for(JsonNode reactionJob:dataArray.get(2)){
                JOBS_REACTION.add(new JobReaction(reactionJob));
            }
            for(JsonNode copyingJob:dataArray.get(3)){
                JOBS_COPYING.add(new JobCopying(copyingJob));
            }
            for(JsonNode inventionJob:dataArray.get(4)){
                JOBS_INVENTION.add(new JobInvention(inventionJob));
            }
            for(JsonNode researchJob:dataArray.get(5)){
                JOBS_RESEARCH.add(new JobResearch(researchJob));
            }
        }
    }
}
