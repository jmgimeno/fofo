package org.fofo.dao;

import javax.persistence.EntityManager;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.WeekMatches;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class CalendarDAOImpl implements CalendarDAO {

    EntityManager em;
    TeamDAO td;

    @Override
    public void addCalendar(FCalendar cal) {
        em.getTransaction().begin();
        for (int i = 0; i < cal.getNumOfWeekMatches(); i++) {
            try {
                addWeekMatches(cal.getWeekMatch(i));
            } catch (IncorrectTeamException ex) {
            }
        }
        em.persist(cal);
        em.getTransaction().commit();
    }

    @Override
    public boolean findCalendar(FCalendar cal) {
        return true;
    }

    private void addWeekMatches(WeekMatches wm) throws IncorrectTeamException {
        for (int i = 0; i < wm.getNumberOfMatchs(); i++) {
            addMatch(wm.getListOfWeekMatches().get(i));
        }
        em.persist(wm);
    }

    private void addMatch(Match match) throws IncorrectTeamException {
        if (td.findTeam(match.getLocal()) && td.findTeam(match.getVisitant())) {
            em.persist(match);
        } else {
            throw new IncorrectTeamException();
        }
    }

    //WOULD IT BE A GOOD CHOICE TO DECLARE THE CALENDAR RELATIONSHIPS AS CASCADE???
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
