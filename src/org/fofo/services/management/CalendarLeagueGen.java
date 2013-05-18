package org.fofo.services.management;

import org.fofo.services.management.CalendarGen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.fofo.entity.Competition;
import org.fofo.entity.CompetitionLeague;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.Team;
import org.fofo.entity.WeekMatches;
import org.joda.time.DateTime;

/**
 * @author Mohamed, Anatoli
 */
public class CalendarLeagueGen extends CalendarGen{        
 
    private List<WeekMatches> wmList = new ArrayList<WeekMatches>();
    
    /* Vbles declarades com a atributs >> codi més net i llegible */
    private int numTeams;
    private LinkedList<Team> locals; //LinkedList >> com un cua; ens interessa per fer les rotacions
    private LinkedList<Team> visitants;
    
  
    @Override
    public FCalendar calculateCalendar(Competition competition) throws Exception{     
        numTeams = competition.getNumberOfTeams();
        
        //Checking requirements
        checkRequirements((CompetitionLeague) competition);
        if(!teamsArePair())
            throw new NumberOfTeamsException("This LEAGUE competition has not "
                    + "a PAIR number of teams");
        
        
        List<Team> teams = competition.getTeams();
        shuffleList(teams);

        //We split the teams into locals and visitants
        initAssignation(teams); 
        
        //Building the WM list       
        buildRound(1);
        buildRound(2);
        
         
        FCalendar cal = new FCalendar();
        cal.setCompetition(competition);
        cal.setWeekMatches(wmList);
        return cal;
    }    
    
    public List<WeekMatches> getWeekMatchesByRound(int numRound){
        List<WeekMatches> list = null;
        int n = numTeams-1;
        
        if(roundOne(numRound)){
            list = new ArrayList<WeekMatches>(this.wmList.subList(0, n));
        }else if(roundTwo(numRound)){
            list = new ArrayList<WeekMatches>(this.wmList.subList(n, n*2));
        }
        
        return list;
    }
    
    
    /* PRIVATE OPS */
    
    private void initAssignation(List<Team> teams) {
        locals = new LinkedList<Team>(teams.subList(0, numTeams / 2 ));
        visitants = new LinkedList<Team>(teams.subList(numTeams / 2, numTeams ));
    }
    
    private void rotateAssignation() {
        //Deixem fixat el 1r equip de locals i rotem cap a la dreta
        
        Team teamToLocals = visitants.removeFirst();
        Team teamToVisitants = locals.removeLast();
        
        locals.add(1, teamToLocals);
        visitants.add(teamToVisitants);
    }
    
    private void buildRound(int numRound) {
        
        int n = numTeams-1;        
        int begin = (n * numRound) - n;           
        int end = n * numRound;
            
        for(int i=begin; i<end; i++){          
            wmList.add(buildWeekMatches(numRound));
            rotateAssignation();
        }
    }

    private WeekMatches buildWeekMatches(int numRound){
        WeekMatches wm = new WeekMatches();
        
        for(int i=0; i<numTeams/2; i++){  // n/2 partits per jornada
            Team local = locals.get(i);
            Team visitant = visitants.get(i);
            Match m = buildMatch(local,visitant,numRound);
            wm.addMatch(m);
        }
        return wm;
    }
    
    private Match buildMatch(Team local, Team visitant, int numRound) {        
        Match m = null;
        
        if(roundOne(numRound)){
           m = new Match(local,visitant);
        }else if(roundTwo(numRound)){
            m = new Match(visitant,local);
        }

        return m;
    }
    
    private void shuffleList(List<Team> teams) {
        Collections.shuffle(teams);
    }   
    
    private boolean teamsArePair() throws Exception {
        return numTeams % 2 == 0;
    }
    
    private boolean roundOne(int round){
        return round == 1;
    }
    
    private boolean roundTwo(int round){
        return round == 2;
    }

 
}
