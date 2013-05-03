/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import org.fofo.entity.Competition;
import org.fofo.entity.Type;

/**
 *
 * @author josepma
 */
public class ManagementService {

    void addCompetition(Competition comp) throws IncorrectCompetitionData,
            IncorrectTypeData, IncorrectMinNumberOfTeams, IncorrectMaxNumberOfTeams{
        
        if (comp.getCategory()== null) throw new IncorrectCompetitionData();
        if (comp.getType() == null) throw new IncorrectTypeData();
        if (comp.getMinTeams()<2) throw new IncorrectMinNumberOfTeams();
        if (comp.getMinTeams()%2 != 0) throw new IncorrectMinNumberOfTeams();
        if (comp.getMaxTeams()%2 != 0) throw new IncorrectMaxNumberOfTeams();
        if (comp.getType() == Type.LEAGUE){
            if (comp.getMaxTeams()>20) throw new IncorrectMaxNumberOfTeams();
        }else if (comp.getType() == Type.CUP){
            if (comp.getMaxTeams()>64) throw new IncorrectMaxNumberOfTeams();
            if (isPowerOfTwo(comp.getMinTeams()) == false) throw new IncorrectMinNumberOfTeams();
            if (isPowerOfTwo(comp.getMaxTeams()) == false) throw new IncorrectMaxNumberOfTeams();
        }
        
        //THIS IS THE OPERATION TO IMPLEMENT.....
        
    }
    
    private boolean isPowerOfTwo(int value){
        return (value != 0) && ((value &(value-1)) == 0);
    }
    
    
}
