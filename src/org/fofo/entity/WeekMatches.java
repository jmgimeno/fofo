/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oriol
 */
public class WeekMatches {
    
    private Map<String, Match> weekMatches = new HashMap<String,Match>();
    
    public void addMatch(String id, Match m){
        weekMatches.put(id, m);
    }
    
    public Match getMatch(String id) throws UnknownMatchException{
        if(weekMatches.containsKey(id)){
            return weekMatches.get(id);            
        }
        else{
            throw new UnknownMatchException();
        }        
    }
}
