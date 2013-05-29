package org.fofo.services.management;

import java.util.List;
import org.fofo.dao.*;
import org.fofo.entity.*;

/**
 *
 * @author jnp2
 */
public class AssignReferees {
    private RefereeDAO refereeDao;
    private CalendarDAO calendarDao;
    private MatchDAO matchDao;
    
    private Competition competition;
    private List<Referee> listReferee;
    
    public AssignReferees(){
    }
    
    public void setCalendarDao(CalendarDAO calDao){
        this.calendarDao = calDao;
    }
    public CalendarDAO getCalendarDao(){
        return calendarDao;
    }
    
    public void setRefereeDao(RefereeDAO referee){
        this.refereeDao = referee;
    }
    public RefereeDAO getRefereeDao(){
        return refereeDao;
    }
    
    public void setMatchDao(MatchDAO matchDao){
        this.matchDao = matchDao;
    }
    public MatchDAO getMatchDao(){
        return matchDao;
    }    
    
    
    public void assignRefereesToCompetition(Competition comp){

        
    }
    
}
