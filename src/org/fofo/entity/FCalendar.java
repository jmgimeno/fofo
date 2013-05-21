package org.fofo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

/**
 *
 * @author Anatoli, Mohamedl, Jordi, Oriol
 */

@Entity
@Table (name="FCalendar")
public class FCalendar {
    
    @Id
    @Column (name="idFCalendar")
    String idFCalendar;
    
    @JoinColumn (name="Competition",
                  referencedColumnName="NAME")
   @OneToOne
    private Competition competition;
    
    @OneToMany (mappedBy="calendar")
    private List<WeekMatches> weekMatches;
    
    
   
    public FCalendar(){
        this.competition = null;
        this.weekMatches = new ArrayList<WeekMatches>();  
        this.idFCalendar = UUID.randomUUID().toString();
    }
       
    public FCalendar(Competition c){
        this.competition = c;
        this.weekMatches = new ArrayList<WeekMatches>();
    }   
    
    public void setCompetition(Competition c){
        this.competition = c;
    }
    
    public void setWeekMatches(List<WeekMatches> wm){
        this.weekMatches = wm;
    }
    
    public List<WeekMatches> getAllWeekMatches() {
        return weekMatches;
    }
    
    public WeekMatches getWeekMatch(int index){
        return weekMatches.get(index);
    }
    
    public int getNumOfWeekMatches(){
        return weekMatches.size();
    }
    
    public Competition getCompetition(){
        return competition;
    }  
    
}
