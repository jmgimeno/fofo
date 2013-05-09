package org.fofo.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * @author Mohamed, Anatoli
 */
public class CalendarLeagueGen extends CalendarGen{
        
    private FCalendar calendar;
    private int numTeams;
    
    public CalendarLeagueGen() {
        
        this.calendar = new FCalendar();
    }

    public CalendarLeagueGen(Competition competition) throws Exception {
        
        super(competition);
        this.numTeams = competition.getNumberOfTeams();
        this.calendar = new FCalendar();
    }     
   
    @Override
    public FCalendar CalculateCalendar() throws Exception{         
        
        List<Team> teams = getCompetition().getTeams();
        shuffleList(teams);
        
        //LinkedList -> perque actuen com a piles
        LinkedList<Team> locals = new LinkedList<Team>();
        LinkedList<Team> visitants = new LinkedList<Team>();

        //We split the teams into locals and visitants
        initAssignation(teams, locals, visitants);
     
        //Building the WM list
        boolean roundTwo = false;
        List<WeekMatches> wmList = new ArrayList<WeekMatches>();        
        for(int i=0; i<numTeams*2; i++){      //n-1 jornades per volta; 2 voltes en total
            if(i==numTeams) roundTwo = true;
            wmList.add(buildWeekMatches(locals,visitants,roundTwo));
            rotateAssignation(locals,visitants);
        }
        
        calendar.setCompetition(getCompetition());
        calendar.setWeekMatches(wmList);
        
        
        return calendar;
    }
    
    /* PRIVATE OPS */
    
    private void initAssignation(List<Team> teams, LinkedList<Team> locals, LinkedList<Team> visitants) {
        locals = new LinkedList<Team>(teams.subList(0, (numTeams / 2) - 1));
        visitants = new LinkedList<Team>(teams.subList(numTeams / 2, numTeams - 1));
    }
    
    private void rotateAssignation(LinkedList<Team> locals, LinkedList<Team> visitants) {
        //Deixem fixat el 1r equip de locals i rotem cap a la dreta
        
        Team teamToLocals = visitants.removeFirst();
        Team teamToVisitants = locals.removeLast();
        
        locals.add(1, teamToLocals);
        visitants.add(teamToVisitants);
    }

    private WeekMatches buildWeekMatches(LinkedList<Team> locals, LinkedList<Team> visitants, boolean roundTwo) throws Exception{
        WeekMatches wm = new WeekMatches("quin id???");
        
        for(int i=0; i<(numTeams/2)-1; i++){  // n/2 partits per jornada
            Team local = locals.get(i);
            Team visitant = visitants.get(i);
            Match m = buildMatch(local,visitant,roundTwo);
            wm.addMatch(m);
        }
        return wm;
    }
    
    private Match buildMatch(Team local, Team visitant, boolean roundTwo) {        
        Match m = roundTwo ? new Match(visitant,local) : new Match(local,visitant);
        return m;
    }
    
    private void shuffleList(List<Team> teams) {
        Collections.shuffle(teams);
    }   
 
    
}
