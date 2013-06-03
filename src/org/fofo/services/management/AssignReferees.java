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
    
    
    public void assignRefereesToCompetition(Competition comp) throws InvalidRequisitsException, PersistException, Exception{
        checkForDAOS();
        FCalendar calendar = calendarDao.findFCalendarByCompetitionName(comp.getName());
        if(calendar==null) throw new PersistException();
        
        List<WeekMatch> listWeekMatch = calendar.getAllWeekMatches();
        
        checkIfHaveEnoughReferees(listWeekMatch);
        
        assignReferees(listWeekMatch);                
    }

    private void checkForDAOS() throws InvalidRequisitsException{
        if(refereeDao == null ||
           calendarDao == null ||
           matchDao == null ) throw new InvalidRequisitsException();
    }

    private void checkIfHaveEnoughReferees(List<WeekMatch> listWeekMatch) throws Exception {
        int maxNumMatchs = 0;
        int numMatch;
        if(listWeekMatch.isEmpty()) throw new PersistException();
        
        for(WeekMatch wm : listWeekMatch){
            numMatch = wm.getNumberOfMatchs();
            if(numMatch<=0) throw new PersistException();
            if(numMatch>maxNumMatchs) maxNumMatchs = numMatch;
        }
        
        listReferee = refereeDao.getAllReferees();      
        if(maxNumMatchs > listReferee.size()) throw new InsuficientRefereesException();
    }

    private void assignReferees(List<WeekMatch> listWeekMatch) {
        for(WeekMatch wm : listWeekMatch){
            assignRefereesToEachMach(wm);
        }
    }

    private void assignRefereesToEachMach(WeekMatch wm) {
        List<Match> listMatch= wm.getMatchs();
        
        for(int i=0; i<listMatch.size(); i++){          
            matchDao.addRefereeToMatch(listMatch.get(i).getIdMatch(),
                                         listReferee.get(i).getNif());   
        }
    }
    
}
