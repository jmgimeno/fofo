/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.services.management;

import java.util.Calendar;
import java.util.List;
import org.fofo.dao.ClubDAO;
import org.fofo.entity.Category;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.Type;

/**
 *
 * @author josepma
 */
public class ManagementService {
    
    ClubDAO clubDao;

    void addCompetition(Competition comp) throws IncorrectCompetitionData,
            IncorrectTypeData, IncorrectMinNumberOfTeams, IncorrectMaxNumberOfTeams,
            IncorrectDate{
        checkForExceptions(comp);
        List<Club> clubs = clubDao.getClubs();
        for(Club c : clubs){
            sendEmail(c);
        }
        
        //THIS IS THE OPERATION TO IMPLEMENT.....
        
    }
    
    private void sendEmail(Club c){
        //to be implemented...
    }
    
    private boolean isPowerOfTwo(int value){
        return (value != 0) && ((value &(value-1)) == 0);
    }
    
    private boolean isValidCategory(Category cat){
        if (cat.equals(Category.FEMALE) || cat.equals(Category.MALE) 
                || cat.equals(Category.VETERAN)){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean validMinTeamsInLeague(int teams){
        if(teams>=2 && teams%2 != 0) return true;
        else return false;
    }
    
    private boolean validMinTeamsInCup(int teams){
        if(validMinTeamsInLeague(teams) && isPowerOfTwo(teams)) return true;
        else return false;
    }
    
    private boolean validMaxTeamsInLeague(int teams){
        if(teams<=20 && teams%2 != 0) return true;
        else return false;
    }
    
    private boolean validMaxTeamsInCup(int teams){
        if(teams<=64 && isPowerOfTwo(teams)) return true;
        else return false;
    }
    
    private boolean isValidType(Type type){
        if(type.equals(Type.CUP) || type.equals(Type.LEAGUE)) return true;
        else return false;
    }
    
    private void checkForExceptions(Competition comp) throws IncorrectCompetitionData,
            IncorrectTypeData, IncorrectMinNumberOfTeams, IncorrectMaxNumberOfTeams,
            IncorrectDate{
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 15);
        if (!isValidCategory(comp.getCategory())) throw new IncorrectCompetitionData();
        if (!isValidType(comp.getType())) throw new IncorrectTypeData();
        if (comp.getInici().before(cal.getTime())) throw new IncorrectDate();
        if (comp.getType().equals(Type.LEAGUE)){
            if (!validMinTeamsInLeague(comp.getMinTeams())) throw new IncorrectMinNumberOfTeams();
            if (!validMaxTeamsInLeague(comp.getMaxTeams())) throw new IncorrectMaxNumberOfTeams();
        }else if (comp.getType().equals(Type.CUP)){
            if (!validMinTeamsInCup(comp.getMinTeams())) throw new IncorrectMinNumberOfTeams();
            if (!validMaxTeamsInCup(comp.getMaxTeams())) throw new IncorrectMaxNumberOfTeams();
        }
    }
}
