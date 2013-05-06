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
    //private int numTeams;
    
    public FCalendar(){
        competition = null;
        calendar = new ArrayList<WeekMatches>();
        
    }
    
    public FCalendar(Competition c, List<WeekMatches> wm){
        this.competition = c;
        this.calendar = wm;
    }
    
    public FCalendar createCalendar(Competition c) throws Exception{
        
        //return CalendarGen(c).getCalendar(); //Si CalendarGen es classe abstracta 
        //Si es inteficie:
        if(c.getType()==Type.LEAGUE)
            return CalendarLeagueGen.getCalendar();
        else if(c.getType()==Type.CUP)
            return CalendarCupGen.getCalendar();
        else
            throw new UnknownCompetitionTypeException();
        
    }
    
    public void setCompetition(Competition c){
        this.competition = c;
    }
    
    public void setWeekMatches(List<WeekMatches> wm){
        this.calendar = wm;
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
        
    
    
}
