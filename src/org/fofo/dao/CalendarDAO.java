package org.fofo.dao;

import org.fofo.entity.FCalendar;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public interface CalendarDAO {
    void addCalendar(FCalendar cal);
    boolean findCalendar(FCalendar cal);
}
