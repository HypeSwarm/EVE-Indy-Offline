/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import sde.FileHandler;

/**
 *
 * @author HypeSwarm
 */
public class ESIPullData {
    static final ArrayList<AssetsCharacter> ASSETS_CHARACTER=new ArrayList<>();
    static final ArrayList<AssetsCorp> ASSETS_CORP=new ArrayList<>();
    static final ArrayList<IndustryCharacter> INDUSTRY_CHARACTER=new ArrayList<>();
    static final ArrayList<IndustryCorp> INDUSTRY_CORP=new ArrayList<>();
    static final ArrayList<SystemIndex> SYSTEM_INDEX=new ArrayList<>();
    static final ArrayList<AdjustedPrice> ADJUSTED_PRICE=new ArrayList<>();
    static final ArrayList<MarketPrices> MARKET_PRICES=new ArrayList<>();
    
    public static void saveESIData() throws JsonProcessingException, IOException{
        ArrayList[] data={
            ASSETS_CHARACTER,
            ASSETS_CORP,
            INDUSTRY_CHARACTER,
            INDUSTRY_CORP,
            SYSTEM_INDEX,
            ADJUSTED_PRICE,
            MARKET_PRICES
        };
        String json = new ObjectMapper().writeValueAsString(data);
        FileHandler.writeJSONFile(json,"data/ESIPullSaves");
    }
    
    /*public static void loadSavedESIData() throws IOException{
        if(FileHandler.checkExistingJSONFiles("data/ESIPullSaves")){
            JsonNode dataArray=FileHandler.loadJSONFile("data/ESIPullSaves");
            for(JsonNode assetsCharacter:dataArray.get(0)){
                ASSETS_CHARACTER.add(new AssetsCharacter(assetsCharacter));
            }
            for(JsonNode assetsCorp:dataArray.get(1)){
                ASSETS_CORP.add(new AssetsCorp(assetsCorp));
            }
            for(JsonNode industryCharacter:dataArray.get(2)){
                INDUSTRY_CHARACTER.add(new IndustryCharacter(industryCharacter));
            }
            for(JsonNode industryCorp:dataArray.get(3)){
                INDUSTRY_CORP.add(new IndustryCorp(industryCorp));
            }
            for(JsonNode systemIndex:dataArray.get(4)){
                SYSTEM_INDEX.add(new SystemIndex(systemIndex));
            }
            for(JsonNode adjustedPrice:dataArray.get(5)){
                ADJUSTED_PRICE.add(new AdjustedPrice(adjustedPrice));
            }
            for(JsonNode marketPrices:dataArray.get(5)){
                MARKET_PRICES.add(new MarketPrices(marketPrices));
            }
        }
    }*/
}
