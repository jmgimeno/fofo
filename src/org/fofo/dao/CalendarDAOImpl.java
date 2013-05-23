package org.fofo.dao;

import javax.persistence.EntityManager;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.WeekMatch;
import org.fofo.entity.Team;

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

        em.getTransaction().begin();

        try {

            for (int i = 0; i < cal.getNumOfWeekMatches(); i++) {

                addWeekMatches(cal.getWeekMatch(0));
            }

            em.persist(cal);
            em.getTransaction().commit();

        } catch (IncorrectTeamException ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    //PRIVATE FUNCTIONS
    /**
     * Add one WeekMatch to DAO.
     *
     * @param wm: The WeekMatch to add.
     * @throws IncorrectTeamException
     */
    public void addWeekMatches(WeekMatch wm) throws PersistException, IncorrectTeamException {

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
    public void addMatch(Match match) throws IncorrectTeamException, PersistException {
        //if (td.getTeams() != null && td.findTeam(match.getLocal()) && td.findTeam(match.getVisitant())) {
System.out.println("ADD MATCH FUNCTION");
        //Treure td.getTeams...
        if (td.getTeams() != null && em.find(Team.class, match.getLocal().getName())!=null
                                  && em.find(Team.class, match.getVisitant().getName())!=null){                    
            System.out.println("IF");
            em.persist(match);

        } else {
            System.out.println("Else");
            throw new IncorrectTeamException();
            
        }
    }
//WOULD IT BE A GOOD CHOICE TO DECLARE THE CALENDAR RELATIONSHIPS AS CASCADE???
}