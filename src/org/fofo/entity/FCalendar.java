/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fofo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
        if(diffBetwenDates(new Date(),c.getInici()) < 7) //minim 7 dies per crear calendari
            throw new Exception(); //ESCOLLIR UN NOM ADIENT
        
        calendar = new ArrayList<WeekMatches>();
        competition = c;
        numTeams = competition.getTeams().size();
        
        if(competition.getType()==Type.LEAGUE){
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
        //1a meitat: loclas:
        //2a meitat: visitants
        locals = (LinkedList<Team>) teams.subList(0, (numTeams/2)-1);
        visitants = (LinkedList<Team>) teams.subList(numTeams/2, numTeams-1);
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
        date = setDate();
        
        return new Match(local,visitant,date);
    }     

    private Date setDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static int diffBetwenDates(Date actual, Date prev) {
        return (int) (actual.getTime() - prev.getTime())
                      / (24*60*60*1000); //ms per dia   
    }
    
    /*Jordio i Oriol*/
    private void manageCup(){
        
    }   
    
    

}
