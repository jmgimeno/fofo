package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.fofo.entity.*;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class CalendarDAOImpl implements CalendarDAO {

    EntityManager em;
    TeamDAO td;

    @Override
    public TeamDAO getTd() {
        return td;
    }

    @Override
    public void setTd(TeamDAO td) {
        this.td = td;
    }

    @Override
    public EntityManager getEm() {
        return em;
    }

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * Add one Calendar to DAO
     *
     * @param cal: The Calendar to add.
     */
    @Override
    public void addCalendar(FCalendar cal) throws IncorrectTeamException, PersistException {

        try {
            
            em.getTransaction().begin();      
            
            for (int i = 0; i < cal.getNumOfWeekMatches(); i++) {

                addWeekMatches(cal.getWeekMatch(i));
            }

            em.persist(cal);
           
          associateCalendarToWeekMatches(cal);
             
            em.getTransaction().commit();

        } catch (IncorrectTeamException ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public FCalendar findFCalendarByCompetitionName(String name)  throws PersistException{
        FCalendar cal = null; 
        Competition comp = null;
        try{
            em.getTransaction().begin();            
            comp = (Competition) em.find (Competition.class,name);
            
            cal = (FCalendar) em.find (FCalendar.class,comp.getFCalendar().getIdFCalendar());
            em.getTransaction().commit();             
        }
        catch (PersistenceException e){
            throw new PersistException();
        }
        finally{
            if (em.isOpen()) em.close();
        }
        return cal;
    }
    
    /*
     * 
     * PRIVATE OPERATIONS
     * 
     * 
     */

    
       
    /**
     * Add one WeekMatch to DAO.
     *
     * @param wm: The WeekMatch to add.
     * @throws IncorrectTeamException
     */
    private void addWeekMatches(WeekMatch wm) throws IncorrectTeamException, PersistException  {

        for (int i = 0; i < wm.getNumberOfMatchs(); i++) {
            addMatch(wm.getListOfWeekMatches().get(i));
        }
        em.persist(wm);
    }

    /**
     * Add one Match to DAO.
     *
     * @param match: The Match to add.
     * @throws IncorrectTeamException
     */
    private void addMatch(Match match) throws IncorrectTeamException, PersistException {
       /*Team home = (Team) em.find(Team.class, match.getHome().getName());
        Team visitor = (Team) em.find(Team.class, match.getVisitor().getName());

        if (home != null && visitor != null){*/
         
        if (em.find(Team.class, match.getHome().getName())!=null
                      && em.find(Team.class, match.getVisitor().getName())!=null){      
            
            em.persist(match);

        } else {
            throw new IncorrectTeamException();
            
        }
    }
//WOULD IT BE A GOOD CHOICE TO DECLARE THE CALENDAR RELATIONSHIPS AS CASCADE???


    
    private void associateCalendarToWeekMatches(FCalendar cal){
            
            for (int i = 0; i < cal.getNumOfWeekMatches(); i++) {

                cal.getWeekMatch(i).setCalendar(cal);
            }
    }
    
}
