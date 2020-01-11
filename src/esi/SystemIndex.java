/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esi;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author HypeSwarm
 */
public class SystemIndex {
    private final int systemID;
    private final double indexManufacturing;
    private final double indexCopying;
    private final double indexMEResearch;
    private final double indexTEResearch;
    private final double indexInvension;
    private final double indexReaction;

    public int getSystemID() {
        return systemID;
    }

    public double getIndexManufacturing() {
        return indexManufacturing;
    }

    public double getIndexCopying() {
        return indexCopying;
    }

    public double getIndexMEResearch() {
        return indexMEResearch;
    }

    public double getIndexTEResearch() {
        return indexTEResearch;
    }

    public double getIndexInvension() {
        return indexInvension;
    }

    public double getIndexReaction() {
        return indexReaction;
    }
    
    public SystemIndex(int systemID, double indexManufacturing, double indexCopying, double indexMEResearch, double indexTEResearch, double indexInvension, double indexReaction) {
        this.systemID = systemID;
        this.indexManufacturing = indexManufacturing;
        this.indexCopying = indexCopying;
        this.indexMEResearch = indexMEResearch;
        this.indexTEResearch = indexTEResearch;
        this.indexInvension = indexInvension;
        this.indexReaction = indexReaction;
    }
    
    SystemIndex(JsonNode json) {
        this(json.get("systemID").intValue(),json.get("indexMaufanuturing").asDouble(),json.get("indexCopying").asDouble(),json.get("indexMEResearch").asDouble(),json.get("indexTEResearch").asDouble(),json.get("indexInvension").asDouble(),json.get("indexReaction").asDouble());
    }
}
