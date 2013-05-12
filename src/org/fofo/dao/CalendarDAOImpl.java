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

    public void addCalendar(FCalendar cal) {
        
        em.getTransaction().begin();
        for (int i = 0; i < cal.getNumOfWeekMatches(); i++) {
            addWeekMatches(cal.getAllWeekMatches().get(i));
        }
        em.persist(cal);
        em.getTransaction().commit();
    }

    private void addWeekMatches(WeekMatches wm) {
        
        for (int i = 0; i < wm.getListOfWeekMatches().size(); i++) {
            addMatch(wm.getListOfWeekMatches().get(i));
        }
        em.persist(wm);
    }

    private void addMatch(Match match) {
        em.persist(match);
    }

    Object get(int i,WeekMatches wm) {
        
        return wm.getListOfWeekMatches().get(i);       
    }



}
