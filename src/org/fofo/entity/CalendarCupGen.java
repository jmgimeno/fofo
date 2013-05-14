package org.fofo.entity;

import java.util.*;

/**
 *
 * @author jnp2
 */
public class CalendarCupGen extends CalendarGen{   
    private Competition competition;  
    private List<WeekMatches> weekMatches ; 

    public CalendarCupGen(Competition competition) {
        weekMatches = new ArrayList<WeekMatches>();
        this.competition = competition;
    }

    @Override
    public FCalendar calculateCalendar() throws InvalidRequisitsException,
                       TeamCanPlayOnlyOneMatchForAWeekException, UnknownCompetitionTypeException, Exception{ 
        checkRequirements(competition);
        generateRoundN(1,competition.getTeams());
        
        FCalendar calendar = new FCalendar(competition);
        calendar.setWeekMatches(weekMatches);
        
        return calendar;      
    }
    
    private void generateRoundN(int numRound, List<Team> listTeam) throws TeamCanPlayOnlyOneMatchForAWeekException {
        WeekMatches weekMatch = new WeekMatches();  
        
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
        WeekMatches weekMatch = weekMatches.get(numRound-1);       
        List<Match> matches = weekMatch.getListOfWeekMatches();    
        for(int i=1; i<=matches.size();i++){
            Team team = new Team("Winer match "+i+" of round "+numRound);
            listTeam.add(team);            
        }   
      
        return listTeam;
    }
    

    
    
}
