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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToMany;

/**
 * @author oriol i Jordi
 */

@Entity
@Table (name="WeekMatch")
public class WeekMatch {
    
    @Id
    @Column (name="idWeekMatch")
    private String weekMatchId;
    
    @ManyToOne
    FCalendar calendar;
    
    @OneToMany (fetch=FetchType.EAGER)
    private List<Match> Matchs = new ArrayList<Match>();
    
    
    public WeekMatch(){
        this.weekMatchId = UUID.randomUUID().toString();   
        
    }
    
    public String getWeekMatchId(){
        return this.weekMatchId;
    }
    
    public void addMatch(Match m){
        if(!Matchs.contains(m)){              
                this.Matchs.add( m);
        }
    }
    
    public List<Match> getListOfWeekMatches(){
        return Matchs;
    }
    
    public int getNumberOfMatchs(){
        return Matchs.size();
    }
    
    public String toString() {
        return "Week match id: "+weekMatchId+" Matches: "+Matchs;
        
    }
    
    public boolean equals(Object obj){
        if (! (obj instanceof WeekMatch) ) return false;
        
        WeekMatch wm = (WeekMatch) obj;
        
        return this.Matchs.size() == wm.Matchs.size() && this.Matchs.containsAll(wm.Matchs);
    }
}
