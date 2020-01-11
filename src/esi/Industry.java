/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esi;

import java.util.Date;

/**
 *
 * @author HypeSwarm
 */
public class Industry {
   private final int jobID;
   private final int activityID;
   private final int blueprintID;
   private final int blueprintTypeID;
   private final int outputTypeID;
   private final int installerID;
   private final int stationID;
   private final double installCost;
   private final int runs;
   private final int licencedRuns;
   private final int duration;
   private final Date startDate;
   private final Date endDate;
   private final Date pauseDate;
   private final String status;

    public int getJobID() {
        return jobID;
    }

    public int getActivityID() {
        return activityID;
    }

    public int getBlueprintID() {
        return blueprintID;
    }

    public int getBlueprintTypeID() {
        return blueprintTypeID;
    }

    public int getOutputTypeID() {
        return outputTypeID;
    }

    public int getInstallerID() {
        return installerID;
    }

    public int getStationID() {
        return stationID;
    }

    public double getInstallCost() {
        return installCost;
    }

    public int getRuns() {
        return runs;
    }

    public int getLicencedRuns() {
        return licencedRuns;
    }

    public int getDuration() {
        return duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getPauseDate() {
        return pauseDate;
    }

    public String getStatus() {
        return status;
    }

    public Industry(int jobID, int activityID, int blueprintID, int blueprintTypeID, int outputTypeID, int installerID, int stationID, double installCost, int runs, int licencedRuns, int duration, Date startDate, Date endDate, Date pauseDate, String status) {
        this.jobID = jobID;
        this.activityID = activityID;
        this.blueprintID = blueprintID;
        this.blueprintTypeID = blueprintTypeID;
        this.outputTypeID = outputTypeID;
        this.installerID = installerID;
        this.stationID = stationID;
        this.installCost = installCost;
        this.runs = runs;
        this.licencedRuns = licencedRuns;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pauseDate = pauseDate;
        this.status = status;
    }
}
