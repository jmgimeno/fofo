/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;

import java.util.ArrayList;
import java.util.List;
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
    
    private List<Match> weekMatches = new ArrayList<Match>();
    
    
    public WeekMatches(){
        this.weekMatchId = UUID.randomUUID().toString();   
    }
    
    public String getWeekMatchId(){
        return this.weekMatchId;
    }
    
    public void addMatch(Match m){
        if(!weekMatches.contains(m)){              
                this.weekMatches.add( m);
        }
    }
    
    public List<Match> getListOfWeekMatches(){
        return weekMatches;
    }
    
    public int getNumberOfMatchs(){
        return weekMatches.size();
    }
}
