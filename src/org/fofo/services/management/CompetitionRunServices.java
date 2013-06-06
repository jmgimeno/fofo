package org.fofo.services.management;

import java.util.List;
import org.fofo.dao.*;
import org.fofo.entity.*;
import org.fofo.utils.Classification;
import org.fofo.utils.Classification.InfoClassTeam;

/**
 *
 * @author Jordi Niubo i Oriol Capell
 */
public class CompetitionRunServices {
    private RefereeDAO refereeDao;
    private CalendarDAO calendarDao;
    private MatchDAO matchDao;
    
    private List<Referee> listReferee;
    
    /**
     * 
     */
    public CompetitionRunServices(){
    }
    
    /**
     * Set calendarDAO
     * @param calDao
     */
    public void setCalendarDao(CalendarDAO calDao){
        this.calendarDao = calDao;
    }
    /**
     * 
     * @return calendarDao
     */
    public CalendarDAO getCalendarDao(){
        return calendarDao;
    }
    
    /**
     * Set refereeDAO
     * @param referee
     */
    public void setRefereeDao(RefereeDAO referee){
        this.refereeDao = referee;
    }
    /**
     * 
     * @return refereeDao
     */
    public RefereeDAO getRefereeDao(){
        return refereeDao;
    }
    
    /**
     * Set matchDao
     * @param matchDao
     */
    public void setMatchDao(MatchDAO matchDao){
        this.matchDao = matchDao;
    }
    /**
     * 
     * @return matchDao
     */
    public MatchDAO getMatchDao(){
        return matchDao;
    }    
    
    
    /**
     * Assign referees for each match in comp
     * @param comp
     * @throws InvalidRequisitsException
     * @throws PersistException
     * @throws CompetitionWithoutFCalendarException
     * @throws Exception
     */
    public void assignRefereesToCompetition(Competition comp) throws InvalidRequisitsException,
                              PersistException, CompetitionWithoutFCalendarException, Exception{
        checkForDAOS();
        FCalendar calendar = calendarDao.findFCalendarByCompetitionName(comp.getName());
        if(calendar==null) throw new CompetitionWithoutFCalendarException();
        
        List<WeekMatch> listWeekMatch = calendar.getAllWeekMatches();
        
        checkIfHaveEnoughReferees(listWeekMatch);
        
        assignReferees(listWeekMatch);                
    }
    
    /**
     * Create the Classification of comp and return it
     * @param comp
     * @return classification
     */
    public Classification getClassificationOfCompetition(Competition comp){
        Classification classification = new Classification();
               
        return classification;
    }
    
    /*
   * PRIVATE OPS
   * 
   * 
   */
    private void checkForDAOS() throws InvalidRequisitsException{
        if(refereeDao == null ||
           calendarDao == null ||
           matchDao == null ) throw new InvalidRequisitsException();
    }

    private void checkIfHaveEnoughReferees(List<WeekMatch> listWeekMatch) throws Exception {
        int maxNumMatchs = 0;
        int numMatch;
        if(listWeekMatch.isEmpty()) throw new IncorrectFCalendarException();
        
        for(WeekMatch wm : listWeekMatch){
            numMatch = wm.getNumberOfMatchs();
            if(numMatch<=0) throw new PersistException();
            if(numMatch>maxNumMatchs) maxNumMatchs = numMatch;
        }
        
        listReferee = refereeDao.getAllReferees();      
        if(maxNumMatchs > listReferee.size()) throw new InsuficientRefereesException();
    }

    private void assignReferees(List<WeekMatch> listWeekMatch) throws PersistException {
        for(WeekMatch wm : listWeekMatch){
            assignRefereesToEachMach(wm);
        }
    }

    private void assignRefereesToEachMach(WeekMatch wm) throws PersistException {
        List<Match> listMatch= wm.getMatchs();
        
        for(int i=0; i<listMatch.size(); i++){          
            matchDao.addRefereeToMatch(listMatch.get(i).getIdMatch(),
                                         listReferee.get(i).getNif());   
        }
    }
    
}
