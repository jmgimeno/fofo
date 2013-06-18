package org.fofo.dao;

import org.fofo.dao.exception.PersistException;
import org.fofo.dao.exception.IncorrectTeamException;
import javax.persistence.EntityManager;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public interface CalendarDAO {

    /**
     *
     * @param cal
     * @throws IncorrectTeamException
     * @throws PersistException
     */
    void addCalendar(FCalendar cal) throws IncorrectTeamException, PersistException;

    /**
     *
     * @return
     */
    TeamDAO getTd();

    /**
     *
     * @param td
     */
    void setTd(TeamDAO td);

    /**
     *
     * @return
     */
    EntityManager getEm();

    /**
     *
     * @param em
     */
    void setEm(EntityManager em);

    /**
     *
     * @param name
     * @return
     * @throws PersistException
     */
    FCalendar findFCalendarByCompetitionName(String name) throws PersistException;
}
