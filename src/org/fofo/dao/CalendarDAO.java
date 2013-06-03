package org.fofo.dao;

import javax.persistence.EntityManager;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public interface CalendarDAO {

    void addCalendar(FCalendar cal) throws IncorrectTeamException, PersistException;

    TeamDAO getTd();

    void setTd(TeamDAO td);

    EntityManager getEm();

    void setEm(EntityManager em);

    FCalendar findFCalendarByCompetitionName(String name) throws PersistException;

    Match findMatchById(String id) throws PersistException;
}
