/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sde;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.MainLauncher;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.ImageIcon;
import org.yaml.snakeyaml.Yaml;


/**
 *
 * @author HypeSwarm
 */
public final class FileHandler {
    
    //Gets the variable names of an object
    static Object[] getKeySet(Object obj){
        Map<String, Object> map=(Map<String, Object>)obj;
        return map.keySet().toArray();
    }
    
    //Gets the variable values of an object
    static  Object[] getValuesSet(Object obj){
        Map<String, Object> map=(Map<String, Object>)obj;
        return map.values().toArray();
    }
    
    //Loads .yaml file and returns contents as object
    static Object loadYAMLFile(String fileName) throws FileNotFoundException, IOException{
        return new Yaml().load(new FileInputStream(fileName+".yaml"));
    }
    
    //Loads .json file and returns contents as JsonNode
    public static JsonNode loadJSONFile(String fileName) throws IOException {
        return new ObjectMapper().readTree(new File(fileName+".json"));
    }
    
    //Write Json Stringified data to .json file
    public static void writeJSONFile(String json,String fileName) throws IOException{
        File file =new File(fileName+".json");
        FileOutputStream fos = new FileOutputStream(file);
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            bw.write(json);
        }
    }
    
    //Load current SDEController version number from file
    static String currentSDE(String fileLoc) throws FileNotFoundException, IOException{
        String version="No SDE File";
        //Load existing Version File
        try(FileReader file = new FileReader(fileLoc)) {
            BufferedReader buffer = new BufferedReader(file);
            version=buffer.readLine();
            file.close();
        }catch(FileNotFoundException e){
            //If no file is found
            MainLauncher.LOADING.addLoadingText("No SDE Version File Found");
            MainLauncher.LOADING.addLoadingText("Creating New File with Default");
            //Create New file
            File newFile=new File(fileLoc);
            try (FileWriter file = new FileWriter(newFile)) {
                file.write(version);
                file.close();
            }
        }
        return version;
    }
    
    //Download SDE.zip file
    static boolean downloadLatestSDE(String latestSDE) throws MalformedURLException, IOException{
        //Open stream to CCP's SDE zip
        ReadableByteChannel readChannel = Channels.newChannel(new URL("https://cdn1.eveonline.com/data/sde/tranquility/"+latestSDE).openStream());
        //Try downlodaing zip file
        try(FileOutputStream file = new FileOutputStream("SDE/"+latestSDE)) {
            FileChannel writeChannel = file.getChannel();
            writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
        }catch(MalformedURLException e){
            MainLauncher.LOADING.addLoadingText("Error Downloading SDE files");
            return false;
        }catch(IOException e){
            MainLauncher.LOADING.addLoadingText("Error Downloading SDE files");
            return false;
        }
        //Return True if downloading successfull
        MainLauncher.LOADING.addLoadingText("Download Complete");
        return true;
    }
    
    //Unpack SDEController File
    static void unpackSDE(String latestSDE, String[] filesNeeded) throws FileNotFoundException, IOException{
        //File Location
        String sourceZip="SDE/"+latestSDE;
        //Unzip Buffer
        byte[] buffer = new byte[4096];
        try ( //Import Zip Entries
                ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceZip))) {
            ZipEntry ze=zis.getNextEntry();
            //Loop thru all files and unpack
            while(ze!=null){
                String filename=ze.getName();
                File newFile=new File("SDE/"+filename);
                String packedFile=newFile.getAbsoluteFile().getName();
                if(FileHandler.needFile(packedFile,filesNeeded)){
                    MainLauncher.LOADING.addLoadingText("UnZipping File: "+ newFile.getAbsoluteFile().getName());
                    try (FileOutputStream fos = new FileOutputStream("SDE/"+packedFile)) {
                        int len;
                        while((len=zis.read(buffer))>0){
                            fos.write(buffer,0,len);
                        }
                    }
                }else{
                    MainLauncher.LOADING.addLoadingText("Skipping File: "+newFile.getAbsoluteFile().getName(),false);
                }
                ze=zis.getNextEntry();
            }
            zis.closeEntry();
        }
        MainLauncher.LOADING.addLoadingText("Unpacking Done");
        
        //Delete Downloaded SDE.zip and outdated .json files
        Files.delete(Paths.get(sourceZip));
        for (String filesNeeded1 : filesNeeded) {
            Files.deleteIfExists(Paths.get("SDE/"+filesNeeded1+".json"));
        }
    }
    
    //Update the SDEVersion.txt to latest string
    static void updateVersionFiles(String newVersion,String fileLoc) throws IOException{
        try (FileWriter file = new FileWriter(new File(fileLoc))) {
            file.write(newVersion);
        }
        
    }
    
    //Check if packed file is needed
    private static boolean needFile(String fileName, String[] filesNeeded){
        for(String filesNeeded1 : filesNeeded) {
            if(fileName.equalsIgnoreCase(filesNeeded1+".yaml")) {
                return true;
            }
        }
        return false;
    }
    
    //Check if Json file exists
    public static boolean checkExistingJSONFiles(String fileName){
        return new File(fileName+".json").exists();
    }
    
    //Check if Image file exists
    public static boolean checkIconExists(String icon){
        return new File("Icons/"+icon).exists();
    }
    
    //Open Image File
    public static ImageIcon getIconFile(String icon){
        if(checkIconExists(icon)){
            return new ImageIcon("Icons/"+icon);
        }else{
            return new ImageIcon("Icons/0.png");
        }
    }
}