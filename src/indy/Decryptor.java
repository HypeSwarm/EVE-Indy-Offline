/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public class Decryptor {
    public static ArrayList<Decryptor> DECRYPTORS=loadDecryptors();
    private final int itemID;
    private final String name;
    private final double probabilityMod;
    private final int runsMod;
    private final int meMod;
    private final int teMod;

    public int getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public double getProbabilityMod() {
        return probabilityMod;
    }

    public int getRunsMod() {
        return runsMod;
    }

    public int getMeMod() {
        return meMod;
    }

    public int getTeMod() {
        return teMod;
    }
    

    public Decryptor(int itemID, double probabilityMod, int runsMod, int meMod, int teMod) {
        this.itemID = itemID;
        this.name = SDEDatabase.TYPE_IDS.getTypeName(itemID);
        this.probabilityMod = probabilityMod;
        this.runsMod = runsMod;
        this.meMod = meMod;
        this.teMod = teMod;
    }
    
    Decryptor(JsonNode json) {
        this(json.get("itemID").intValue(),json.get("probabilityMod").doubleValue(),json.get("runsMod").intValue(),json.get("meMod").intValue(),json.get("teMod").intValue());
    }
    
    public static ArrayList<Decryptor> loadDecryptors(){
        ArrayList<Decryptor> decryptors=new ArrayList<>();
        decryptors.add(new Decryptor(34201,1.2,1,2,10));
        decryptors.add(new Decryptor(34202,1.8,5,-1,4));
        decryptors.add(new Decryptor(34203,0.6,9,-2,2));
        decryptors.add(new Decryptor(34207,1.9,2,1,-2));
        decryptors.add(new Decryptor(34208,0.9,7,2,0));
        decryptors.add(new Decryptor(34204,1.5,3,1,-2));
        decryptors.add(new Decryptor(34205,1.1,0,3,6));
        decryptors.add(new Decryptor(34206,1.0,2,1,8));
        return decryptors;
    }
    
    public static DefaultComboBoxModel getDecryptorModel(){
        DefaultComboBoxModel decryptors = new DefaultComboBoxModel();
        decryptors.addElement("None");
        DECRYPTORS.stream().forEach((decryptor) -> {
            decryptors.addElement(decryptor);
        });
        return decryptors;
    }

    @Override
    public String toString() {
        return name;
    }
}
