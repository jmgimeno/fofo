/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fofo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Anatoli, Mohamed
 */
public class FCalendar {
    
    private Competition competition;
    private List<WeekMatches> calendar;
    private int numTeams;
    private LinkedList<Team> locals;   //LinkedList perque es comporta com una pila
    private LinkedList<Team> visitants;
    
    public FCalendar(Competition c) throws Exception{
        
        competition = c;
        numTeams = competition.getNumberOfTeams();
        
        checkSevenDaysMinAfterCompetition();        
        checkNumOfTeamsIsInLimits();
        
        calendar = new ArrayList<WeekMatches>();        
        
        if(competition.getType()==Type.LEAGUE){
            checkNumOfTeamsIsPair();
            manageLeague();
        }else if(competition.getType()==Type.CUP){
            manageCup();
        }else
            throw new UnknownCompetitionTypeException(); 
        
        
        
    }
    
    private void manageLeague() throws Exception{
        List<Team> tmp = competition.getTeams();
        
        //--->metode per remenar la llista<---
        
        initAssignation(tmp);
        //1a volta
        for(int i=0; i<numTeams; i++){  //n-1 jornades per volta
            WeekMatches wm = buildWeekMatches();
            calendar.add(wm);
            rotateAssignation();            
        }
    }
    
    private void initAssignation(List<Team> teams) {
        //1a meitat: loclas
        //2a meitat: visitants        
        
        locals.addAll(teams.subList(0, (numTeams/2)-1));
        visitants.addAll(teams.subList(numTeams/2, numTeams-1));
    }
    
    private void rotateAssignation() {
        //Deixem fixat el 1r equip de locals i rotem cap a la dreta
        
        Team teamToLocals = visitants.removeFirst();
        Team teamToVisitants = locals.removeLast();
        
        locals.add(1, teamToLocals);
        visitants.add(teamToVisitants);
        
    }  

    private WeekMatches buildWeekMatches() throws Exception{
        WeekMatches wm = null;
        
        for(int i=0; i<numTeams/2; i++){  // n/2 partits per jornada
            Match m = buildMatch(i);
            wm.addMatch(m);
        }
        return wm;
    }
    
    private Match buildMatch(int index) {
        Team local = locals.get(index);
        Team visitant = visitants.get(index);
        
        Date date = new Date();
        //date = assignDate();
        
        return new Match(local,visitant,date);
    }     

    private Date assignDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
    
    public List<WeekMatches> getAllWeekMatches() {
        return calendar;
    }
    
    public WeekMatches getWeekMatches(int index){
        return calendar.get(index);
    }
    
    public int getNumOfWeekMatches(){
        return calendar.size();
    }

    public List<Team> getLocals() {
        return locals;
    }

    public List<Team> getVisitants() {
        return visitants;
    }     

    private void checkSevenDaysMinAfterCompetition() throws Exception {
        DateTime actual = new DateTime();
        DateTime compDate = new DateTime(competition.getInici()); 
        if((actual.getDayOfYear() - compDate.getDayOfYear()) < 7)
            throw new MinimumDaysException("It must be a diference of 7 days as "
                    + "minimum betwen Calendar creation and Competition creation"); 
    }

    private void checkNumOfTeamsIsPair() throws Exception {
        if(numTeams%2 != 0)
            throw new NumberOfTeamsException("This LEAGUE competition has not "
                    + "a PAIR number of teams"); 
    }

    private void checkNumOfTeamsIsInLimits() throws Exception {
        if(numTeams < competition.getMinTeams() 
        && numTeams > competition.getMaxTeams())
            throw new NumberOfTeamsException("This LEAGUE competition has not "
                    + "the right number of teams betwen "
                    +competition.getMinTeams()+" and "+competition.getMaxTeams()); 
    }
    
    /*Jordio i Oriol*/
    private void manageCup(){
        
    } 

}
