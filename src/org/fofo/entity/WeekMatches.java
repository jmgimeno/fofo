/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author oriol i Jordi
 */

@Entity
@Table (name="WeekMatches")
public class WeekMatches {
    
    @Id
    @Column (name="idWeekMatch")
    private String weekMatchId;
    
    @ManyToOne
    FCalendar calendar;
    
    private Map<String, Match> weekMatches = new HashMap<String,Match>(); 
    private Map<String, Team> teams = new HashMap<String, Team>();
    
    
    public WeekMatches(String id){
        this.weekMatchId = UUID.randomUUID().toString();   
    }
    
    public String getWeekMatchId(){
        return this.weekMatchId;
    }
    
    public void addMatch(Match m) throws NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{
        if(!weekMatches.containsKey(m.getIdMatch())){
            if(teamPlayOnlyOneMatch(m)){                
                this.weekMatches.put(m.getIdMatch(), m);
            }
            else{
                throw new TeamCanPlayOnlyOneMatchForAWeekException();
            }
        }
        else{
            throw new NonUniqueIdException();
        }
    }
    
    public Boolean teamPlayOnlyOneMatch(Match m){
        if(!teams.containsKey(m.getLocal().getName()) && 
            !teams.containsKey(m.getVisitant().getName())){
             teams.put(m.getLocal().getName(), m.getLocal());
             teams.put(m.getVisitant().getName(), m.getVisitant());
             return true;
         }
        else{
            return false;
        }
        
        
    }
    public Match getMatch(String id) throws UnknownMatchException{
        if(weekMatches.containsKey(id)){
            return weekMatches.get(id);            
        }
        else{
            throw new UnknownMatchException();
        }        
    }
    
    public List<Match> getListOfWeekMatches(){
        return new ArrayList<Match>(weekMatches.values());
    }
    
    public int getNumberOfMatchs(){
        return weekMatches.size();
    }
}
