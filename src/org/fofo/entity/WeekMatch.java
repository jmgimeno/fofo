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
import javax.persistence.JoinColumn;

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
    @JoinColumn(name="CALENDAR_ID", referencedColumnName="idFCalendar")          
    FCalendar calendar;
    
    @OneToMany (fetch=FetchType.EAGER)
    private List<Match> Matchs = new ArrayList<Match>();
    
    
    /**
     *
     */
    public WeekMatch(){
        this.weekMatchId = UUID.randomUUID().toString();   
        
    }
    
    /**
     *
     * @return
     */
    public String getWeekMatchId(){
        return this.weekMatchId;
    }
    
    /**
     *
     * @param m
     */
    public void addMatch(Match m){
        if(!Matchs.contains(m)){              
                this.Matchs.add( m);
        }
    }
    
    /**
     *
     * @return
     */
    public List<Match> getListOfWeekMatches(){
        return Matchs;
    }
    
    /**
     *
     * @return
     */
    public int getNumberOfMatchs(){
        return Matchs.size();
    }

    /**
     *
     * @return
     */
    public FCalendar getCalendar() {
        return calendar;
    }

    /**
     *
     * @param calendar
     */
    public void setCalendar(FCalendar calendar) {
        this.calendar = calendar;
    }

    /**
     *
     * @return
     */
    public List<Match> getMatchs() {
        return Matchs;
    }

    /**
     *
     * @param Matchs
     */
    public void setMatchs(List<Match> Matchs) {
        this.Matchs = Matchs;
    }
    
    
    
    public String toString() {
        
        String idcal = "";
        
        if (calendar != null) idcal = calendar.getIdFCalendar();
        
        return "Week match id: "+weekMatchId+"Week match of calendar: "+ idcal+ "Matches: "+Matchs;
        
    }
    
    public boolean equals(Object obj){
        if (! (obj instanceof WeekMatch) ) return false;
        
        WeekMatch wm = (WeekMatch) obj;
        
        return this.Matchs.size() == wm.Matchs.size() && this.Matchs.containsAll(wm.Matchs);
    }
}
