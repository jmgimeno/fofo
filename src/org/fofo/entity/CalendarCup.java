package org.fofo.entity;

import java.util.*;
import org.fofo.entity.Competition;
import org.fofo.entity.InvalidRequisitsException;
import org.fofo.entity.Team;

/**
 *
 * @author Jordi i Oriol
 */
public class CalendarCup {
    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisecons/day 
    final Date init = new Date();
    private final Competition competition;
    int numWM;
    Map<Integer,WeekMatches> weekMatches = new HashMap<Integer,WeekMatches>();
    
    public CalendarCup(Competition competition) throws InvalidRequisitsException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{
        this.competition = competition;
        if(!minimDaysPassed()) throw new InvalidRequisitsException();       
        if(!teamsRequired())throw new InvalidRequisitsException();     
        if(!isPotencyOfTwo())throw new InvalidRequisitsException();
      
        generateRoundN(1,competition.getTeams());    
    }
    
    private boolean minimDaysPassed(){    
        long difference = 
             (init.getTime() - competition.getInici().getTime() )/MILLSECS_PER_DAY;
        if(difference < 7) return false;   
        return true;
    }    

    private boolean teamsRequired(){
        List<Team> list = competition.getTeams();      
        if(list.size()>=competition.getMinTeams() && 
           list.size()<=competition.getMaxTeams()){
            return true;
        }
        return false;            
    }    
    
    private boolean isPotencyOfTwo(){ 
        int num = competition.getTeams().size();
        int n = 0;
        while (num >= 2) {
            if (num % 2 != 0) {
                return false;
            }
            n++;
            num /= 2;
        }
        this.numWM = n;
        return true;
    }

    public int getNumWeekMatches() {
        return numWM;
    }

    public Map<Integer, WeekMatches> getWeekMatches() {
        return weekMatches;
    }

    private void generateRoundN(int numRound, List<Team> listTeam) throws NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException {
        WeekMatches weekMatch = new WeekMatches("WeekMatch number "+numRound);
      
        Iterator itr = listTeam.iterator();
        int nId = 1;
        while(itr.hasNext()) {            
            Team team1 = (Team) itr.next();
            Team team2 = (Team) itr.next();
            weekMatch.addMatch(new Match(team1, team2, new Date())); //li heu de assignar una data, i no un id
            nId++;
        }    
         
        this.weekMatches.put(numRound, weekMatch);
        if(numRound<numWM) generateRoundN(numRound+1,teamsParticipatingInRoundN(numRound));        
    }

    private List<Team> teamsParticipatingInRoundN(int numRound) {
        List<Team> listTeam  = new ArrayList<Team>();
        WeekMatches weekMatch = weekMatches.get(numRound);
        List<Match> matches = weekMatch.getListOfWeekMatches();
        for(Match match : matches){
            Team team = new Team("Winer "+match.getId()+" round "+numRound);
            listTeam.add(team);            
        }        
        return listTeam;
    }
    
    
    
    
}
