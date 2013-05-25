package org.fofo.services.management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.fofo.entity.*;
import org.joda.time.DateTime;

/**
 * @author Mohamed, Anatoli
 */
public class CalendarLeagueGen extends CalendarGen{        
 
    private List<WeekMatch> wmList = new ArrayList<WeekMatch>();
    
    /* Vbles declarades com a atributs >> codi més net i llegible */
    private int numTeams;
    private LinkedList<Team> homes; //LinkedList >> com un cua; ens interessa per fer les rotacions
    private LinkedList<Team> visitors;
    
  
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
    
    public List<WeekMatch> getWeekMatchesByRound(int numRound){
        List<WeekMatch> list = null;
        int n = numTeams-1;
        
        if(roundOne(numRound)){
            list = new ArrayList<WeekMatch>(this.wmList.subList(0, n));
        }else if(roundTwo(numRound)){
            list = new ArrayList<WeekMatch>(this.wmList.subList(n, n*2));
        }
        
        return list;
    }
    
    
    /* PRIVATE OPS */
    
    private void initAssignation(List<Team> teams) {
        homes = new LinkedList<Team>(teams.subList(0, numTeams / 2 ));
        visitors = new LinkedList<Team>(teams.subList(numTeams / 2, numTeams ));
    }
    
    private void rotateAssignation() {
        //Deixem fixat el 1r equip de locals i rotem cap a la dreta
        
        Team teamToHomes = visitors.removeFirst();
        Team teamToVisitors = homes.removeLast();
        
        homes.add(1, teamToHomes);
        visitors.add(teamToVisitors);
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

    private WeekMatch buildWeekMatches(int numRound){
        WeekMatch wm = new WeekMatch();
        
        for(int i=0; i<numTeams/2; i++){  // n/2 partits per jornada
            Team home = homes.get(i);
            Team visitor = visitors.get(i);
            Match m = buildMatch(home,visitor,numRound);
            wm.addMatch(m);
        }
        return wm;
    }
    
    private Match buildMatch(Team home, Team visitor, int numRound) {        
        Match m = null;
        
        if(roundOne(numRound)){
           m = new Match(home,visitor);
        }else if(roundTwo(numRound)){
            m = new Match(visitor,home);
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
