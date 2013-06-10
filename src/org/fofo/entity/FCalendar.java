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
import javax.persistence.FetchType;

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
    
    @OneToMany (mappedBy="calendar", fetch=FetchType.EAGER)
    private List<WeekMatch> weekMatches;
    
    
    
    /**
     *
     */
    public FCalendar(){
        this.competition = null;
        this.weekMatches = new ArrayList<WeekMatch>();  
        this.idFCalendar = UUID.randomUUID().toString();
    }
       
    /**
     *
     * @param c
     */
    public FCalendar(Competition c){
        this.competition = c;
        this.weekMatches = new ArrayList<WeekMatch>();
        this.idFCalendar = UUID.randomUUID().toString();
          //ABANS NO S'AGEGIA L'IDENTIFICADOR!!!!!
    
    }   
    
    /**
     *
     * @param c
     */
    public void setCompetition(Competition c){
        this.competition = c;
    }
    
    /**
     *
     * @param wm
     */
    public void setWeekMatches(List<WeekMatch> wm){
        this.weekMatches = wm;
    }
    
    /**
     *
     * @return
     */
    public String getIdFCalendar(){
        return this.idFCalendar;
    }
    
    /**
     *
     * @return
     */
    public List<WeekMatch> getAllWeekMatches() {
        return weekMatches;
    }
    
    /**
     *
     * @param index
     * @return
     */
    public WeekMatch getWeekMatch(int index){
        return weekMatches.get(index);
    }
    
    /**
     *
     * @return
     */
    public int getNumOfWeekMatches(){
        return weekMatches.size();
    }
    
    /**
     *
     * @return
     */
    public Competition getCompetition(){
        return competition;
    }  
    
    public String toString(){
        
        return "Calendar id: "+this.idFCalendar+ "Calendar competition: "+this.competition+
                "Calendar week matches:"+weekMatches;
        
    }
    
    public boolean equals(Object obj){
        
        if (! (obj instanceof FCalendar) ) return false;
        
        FCalendar cal = (FCalendar) obj;
        
        return this.idFCalendar.equals (cal.idFCalendar) &&
               this.weekMatches.size() == cal.weekMatches.size() &&
                this.weekMatches.containsAll(cal.weekMatches); 
    }
    
}
