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
    
    public CalendarCup(Competition competition) throws InvalidRequisitsException{
        this.competition = competition;
        if(!minimDaysPassed()) throw new InvalidRequisitsException();
        if(!teamsRequired())throw new InvalidRequisitsException();     
        if(!isPotencyOfTwo())throw new InvalidRequisitsException();
        generateFirstRound();
        generateRound(2);
    
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

    private void generateFirstRound() {
        WeekMatches weekMatch = new WeekMatches("WeekMatch number 1 ");
        List<Team> listTeam = competition.getTeams();
        
        Iterator itr = listTeam.iterator();
        while(itr.hasNext()) {
            Team team1 = (Team) itr.next();
            Team team2 = (Team) itr.next();
            weekMatch.addMatch(new Match(team1, team2, "--"));
//What String id?
        }
        this.weekMatches.put(1, weekMatch);
    }

    private void generateRound(int numRound) {
         WeekMatches weekMatch = new WeekMatches("WeekMatch number "+numRound); 
         List<Team> listTeam = competition.getTeams();

         
         
         if(numRound<numWM) generateRound(numRound++);
    }
    
    
    
    
}
