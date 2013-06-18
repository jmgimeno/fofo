package org.fofo.services.management;

import org.fofo.services.management.exception.InvalidRequisitsException;
import org.fofo.services.management.exception.TeamCanPlayOnlyOneMatchForAWeekException;
import org.fofo.services.management.exception.UnknownCompetitionTypeException;
import java.util.*;
import org.fofo.entity.Category;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.Team;
import org.fofo.entity.WeekMatch;

/**
 *
 * @author jnp2
 */
public class CalendarCupGen extends CalendarGen{
    private List<WeekMatch> weekMatches ; 

    /**
     *
     */
    public CalendarCupGen() {
        weekMatches = new ArrayList<WeekMatch>();
    }

    /**
     *
     * @param competition
     * @return
     * @throws InvalidRequisitsException
     * @throws TeamCanPlayOnlyOneMatchForAWeekException
     * @throws UnknownCompetitionTypeException
     * @throws Exception
     */
    @Override
    public FCalendar calculateCalendar(Competition competition) throws InvalidRequisitsException,
                       TeamCanPlayOnlyOneMatchForAWeekException, UnknownCompetitionTypeException, Exception{ 
        checkRequirements(competition);
        generateRoundN(1,competition.getTeams());
        
        FCalendar calendar = new FCalendar(competition);
        calendar.setWeekMatches(weekMatches);
        calendar.setCompetition(competition);  //AFEGIT!!!!
        return calendar;      
    }
    
    private void generateRoundN(int numRound, List<Team> listTeam) throws TeamCanPlayOnlyOneMatchForAWeekException {
        WeekMatch weekMatch = new WeekMatch();  
        
        Iterator itr = listTeam.iterator();
        int nId = 1;
        while(itr.hasNext()) {            
            Team team1 = (Team) itr.next();
            Team team2 = (Team) itr.next();
            weekMatch.addMatch(new Match(team1, team2));
            nId++;
        }    
         
        this.weekMatches.add(weekMatch); 
        if(weekMatch.getNumberOfMatchs() > 1) generateRoundN(numRound+1,teamsParticipatingInRoundN(numRound));        
    }

    private List<Team> teamsParticipatingInRoundN(int numRound) {
        
        List<Team> listTeam  = new ArrayList<Team>();         
        WeekMatch weekMatch = weekMatches.get(numRound-1);       
        List<Match> matches = weekMatch.getListOfWeekMatches();  
        Club club = new Club();
        club.setName("Imaginary club");        
        club.setEmail("email@email.com");
        for(int i=1; i<=matches.size();i++){
            Team team = new Team("Winer match "+i+" of round "+numRound,club, Category.MALE);
            listTeam.add(team);            
        }   
      
        return listTeam;
    }
    

    
    
}
