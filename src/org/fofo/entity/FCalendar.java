package org.fofo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Anatoli, Mohamedl, Jordi, Oriol
 */
public class FCalendar {
    
    private Competition competition;
    private List<WeekMatches> calendar;
    String idFCalendar;
    
    public FCalendar(){
        this.competition = null;
        this.calendar = new ArrayList<WeekMatches>();  
        this.idFCalendar = UUID.randomUUID().toString();
    }
       
    public FCalendar(Competition c){
        this.competition = c;
        this.calendar = new ArrayList<WeekMatches>();
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
    
    public WeekMatches getWeekMatch(int index){
        return calendar.get(index);
    }
    
    public int getNumOfWeekMatches(){
        return calendar.size();
    }
    
    public Competition getCompetition(){
        return competition;
    }
        
    
    
}
