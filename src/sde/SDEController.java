package sde;


import gui.MainLauncher;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HypeSwarm
 */
public final class SDEController {
    //s
    private static final String DEFAULT_SDE="No Data";
    private static final String CURRENT_VERSION_FILE="SDE/SDEVersion.txt";
    private static final String[] SDE_FILES_REQUIRED={
        "blueprints",
        "categoryIDs",
        "groupIDs",
        "typeIDs",
        "ramActivities",
        "rigBonuses",
        "iconIDs",
        "marketGroups"
    };
    private static String currentSDE;
    private String latestSDE;
    
    
    //Getters
    public String getCurrentSDE() {
        return currentSDE;
    }

    public String getLatestSDE() {
        return latestSDE;
    }

    public static String[] getSDE_FILES_REQUIRED() {
        return SDE_FILES_REQUIRED;
    }
    
    public SDEController() throws IOException{
        MainLauncher.LOADING.addLoadingText("Loading current SDE version...");
        SDEController.currentSDE=FileHandler.currentSDE(CURRENT_VERSION_FILE);
        MainLauncher.LOADING.addLoadingText("Current SDE version: "+SDEController.currentSDE.split(".zip")[0]+"\n");
        if(checkForUpdate()){
            MainLauncher.LOADING.addLoadingText("Newer SDE Version Available");
            int option=JOptionPane.showConfirmDialog(null,new JLabel("<HTML><center>Do you wish to download latest SDE Files?</center></html>"),"Newer SDE",JOptionPane.OK_CANCEL_OPTION);
            if(option==JOptionPane.OK_OPTION){
                this.updateSDEFiles();
            }else{
                MainLauncher.LOADING.addLoadingText("\nSkipping SDE Download");
            }
        }else{
            MainLauncher.LOADING.addLoadingText("Current SDE is lastest\n");
        }
        MainLauncher.LOADING.addLoadingText("Loading SDE Database");
        this.loadSDEData();
    }
    
    //Check if current SDEController is outdated
    public boolean checkForUpdate() throws IOException{
        MainLauncher.LOADING.addLoadingText("Checking for new SDE version...");
        pingLatestSDE();
        if(latestSDE.equals(DEFAULT_SDE))return false;
        return !this.latestSDE.equals(currentSDE);
    }
    
    
    //Get latest SDE version off CCP
    public void pingLatestSDE() throws IOException{
        //Ping CCP for lastest SDEController Version
        MainLauncher.LOADING.addLoadingText("Pinging...https://developers.eveonline.com/resource/resources");
        try{
            Document doc=Jsoup.connect("https://developers.eveonline.com/resource/resources").get();
            this.latestSDE=doc.select("#page > div.mainContent.resources > div > div.col-two-third > div > ul:nth-child(5) > li:nth-child(2) > a").text();
        }catch(IOException e){
            //If Ping Unsuccessful
            MainLauncher.LOADING.addLoadingText("Error unable to get lastest SDE version info");
            latestSDE=SDEController.DEFAULT_SDE;
        }
    }
    
    public void updateSDEFiles() throws IOException{
        MainLauncher.LOADING.addLoadingText("Downloading "+latestSDE);
        if(FileHandler.downloadLatestSDE(latestSDE)){
            MainLauncher.LOADING.addLoadingText("\nUnpacking SDE Files");
            FileHandler.unpackSDE(latestSDE, SDE_FILES_REQUIRED);
            FileHandler.updateVersionFiles(latestSDE,CURRENT_VERSION_FILE);
        }else{
            MainLauncher.LOADING.addLoadingText("\nSDE Update FAILED using current files");
        }
    }
    
    public void loadSDEData() throws IOException{
        if(FileHandler.checkExistingJSONFiles("SDE/blueprints")){
            MainLauncher.LOADING.addLoadingText("\nOpening blueprints.json");
            SDEDatabase.BLUEPRINTS.jsonSDE();
        }else{
            MainLauncher.LOADING.addLoadingText("\nOpening blueprints.yaml");
            SDEDatabase.BLUEPRINTS.yamlSDE();
        }
        if(FileHandler.checkExistingJSONFiles("SDE/rigBonuses")){
            MainLauncher.LOADING.addLoadingText("\nOpening rigBonuses.json");
            SDEDatabase.RIG_BONUSES.jsonSDE();
        }else{
            MainLauncher.LOADING.addLoadingText("\nOpening rigBonuses.yaml");
            SDEDatabase.RIG_BONUSES.yamlSDE();
        }
        if(FileHandler.checkExistingJSONFiles("SDE/ramActivities")){
            MainLauncher.LOADING.addLoadingText("\nOpening ramActivities.json");
            SDEDatabase.RAM_ACTIVITIES.jsonSDE();
        }else{
            MainLauncher.LOADING.addLoadingText("\nOpening ramActivities.yaml");
            SDEDatabase.RAM_ACTIVITIES.yamlSDE();
        }
        if(FileHandler.checkExistingJSONFiles("SDE/categoryIDs")){
            MainLauncher.LOADING.addLoadingText("\nOpening categoryIDs.json");
            SDEDatabase.CATEGORY_IDS.jsonSDE();
        }else{
            MainLauncher.LOADING.addLoadingText("\nOpening categoryIDs.yaml");
            SDEDatabase.CATEGORY_IDS.yamlSDE();
        }
        if(FileHandler.checkExistingJSONFiles("SDE/groupIDs")){
            MainLauncher.LOADING.addLoadingText("\nOpening groupIDs.json");
            SDEDatabase.GROUP_IDS.jsonSDE();
        }else{
            MainLauncher.LOADING.addLoadingText("\nOpening groupIDs.yaml");
            SDEDatabase.GROUP_IDS.yamlSDE();
        }
        if(FileHandler.checkExistingJSONFiles("SDE/typeIDs")){
            MainLauncher.LOADING.addLoadingText("\nOpening typeIDs.json");
            SDEDatabase.TYPE_IDS.jsonSDE();
        }else{
            MainLauncher.LOADING.addLoadingText("\nOpening typeIDs.yaml");
            SDEDatabase.TYPE_IDS.yamlSDE();
        }
        if(FileHandler.checkExistingJSONFiles("SDE/iconIDs")){
            MainLauncher.LOADING.addLoadingText("\nOpening iconIDs.json");
            SDEDatabase.ICON_IDS.jsonSDE();
        }else{
            MainLauncher.LOADING.addLoadingText("\nOpening iconIDs.yaml");
            SDEDatabase.ICON_IDS.yamlSDE();
        }
        if(FileHandler.checkExistingJSONFiles("SDE/marketGroups")){
            MainLauncher.LOADING.addLoadingText("\nOpening marketGroups.json");
            SDEDatabase.MARKET_GROUPS.jsonSDE();
        }else{
            MainLauncher.LOADING.addLoadingText("\nOpening marketGroups.yaml");
            SDEDatabase.MARKET_GROUPS.yamlSDE();
        }
    }
}
