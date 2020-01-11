/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indy;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.atomic.AtomicInteger;
import sde.SDEDatabase;

/**
 *
 * @author HypeSwarm
 */
public class JobCopying extends Job{
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private final int categoryID;
    private final int groupID;
    
    public JobCopying(int blueprintID, int structureID, int runs, int numBlueprints) {
        super(COUNT.incrementAndGet(),blueprintID, structureID,5, runs,numBlueprints);
        this.groupID=SDEDatabase.TYPE_IDS.getGroupID(blueprintID);
        this.categoryID=SDEDatabase.GROUP_IDS.getCategoryID(groupID);
        this.durationModify();
    }

    JobCopying(JsonNode json){
        this(json.get("blueprintID").intValue(),json.get("structureID").intValue(),json.get("runs").intValue(),json.get("blueprintsUsed").intValue());
    }
    
    @Override
    public void durationModify() {
        Structure structure=DataArrayLists.getStructureByID(this.structureID);
        this.durationModified=(int)(this.duration*structure.structureTEBonus(categoryID, groupID,5))*runs*blueprintsUsed;
    }
    
    @Override
    public Object[] tableData() {
        return new Object[]{
            super.jobID,
            super.getBlueprintName(),
            super.getStructureName(),
            super.runs,
            super.blueprintsUsed,
            "<HTML><center>"+super.ModifiedDurationToString()+"</center></HTML>",
            super.doubleToCurrency(super.installFee)
        };
    }
}
