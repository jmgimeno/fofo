/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author oriol i Jordi
 */
public class WeekMatches {
    
     private Map<String, Match> weekMatches = new HashMap<String,Match>();
    private String weekMatchId;
    
    public WeekMatches(String id){
        this.weekMatchId = id;        
    }
    
    public String getWeekMatchId(){
        return this.weekMatchId;
    }
    
    public void addMatch(Match m) throws NonUniqueIdException{
        if(!weekMatches.containsKey(m.getId())){        
            this.weekMatches.put(m.getId(), m);
        }
        else{
            throw new NonUniqueIdException();
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
