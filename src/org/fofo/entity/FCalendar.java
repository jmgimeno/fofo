/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fofo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Anatoli, Mohamed
 */
public class FCalendar {
    
    private Competition competition;
    private List<WeekMatches> calendar;
    private List<Team> locals;
    private List<Team> visitants;
    
    public FCalendar(Competition c) throws Exception{
        if(diffBetwenDates(new Date(),c.getInici()) < 7) //minim 7 dies per crear calendari
            throw new Exception(); //ESCOLLIR UN NOM ADIENT
        
        calendar = new ArrayList();
        competition = c;
        
        if(competition.getType()==Type.LEAGUE)
            manageLeague();
        else if(competition.getType()==Type.CUP)
            manageCup();
        else
            throw new Exception(); //ESCOLLIR UN NOM ADIENT
        
        
        
    }
    
    private void manageLeague(){
        List<Team> tmp = competition.getTeams();
        
        //--->metode per remenar la llista<---
        
        initAssignation(tmp);
        //...
    }
    
    private void initAssignation(List<Team> teams) {
        int size = teams.size();
        
        locals = teams.subList(0, (size/2)-1);
        visitants = teams.subList(size/2, size-1);
    }

    private static int diffBetwenDates(Date actual, Date prev) {
        return (int) (actual.getTime() - prev.getTime())
                      / (24*60*60*1000); //ms per dia   
    }
    
    
    
    /*Jordio i Oriol*/
    private void manageCup(){
        
    }   
    
    

}
