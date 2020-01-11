/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esi;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Date;

/**
 *
 * @author HypeSwarm
 */
public class IndustryCorp extends Industry{

    public IndustryCorp(int jobID, int activityID, int blueprintID, int blueprintTypeID, int outputTypeID, int installerID, int stationID, double installCost, int runs, int licencedRuns, int duration, Date startDate, Date endDate, Date pauseDate, String status) {
        super(jobID, activityID, blueprintID, blueprintTypeID, outputTypeID, installerID, stationID, installCost, runs, licencedRuns, duration, startDate, endDate, pauseDate, status);
    }    
}
