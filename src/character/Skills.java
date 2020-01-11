/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package character;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

/**
 *
 * @author HypeSwarm
 */
public class Skills {
    private int skillID;
    private int level;

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public Skills(JsonNode json) throws IOException{
        this.skillID=json.get("typeID").intValue();
        this.level=json.get("level").intValue();
    }
    
    public Skills(int skillID,int level){
        this.skillID=skillID;
        this.level=level;
    }
}
