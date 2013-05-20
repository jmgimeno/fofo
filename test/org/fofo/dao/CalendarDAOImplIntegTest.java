/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.WeekMatches;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
public class CalendarDAOImplIntegTest {

    EntityManager em;
    EntityTransaction transaction;
    CalendarDAOImpl calDAO;
    Match match1, match2, match3, match4;
    WeekMatches wm1, wm2;
    FCalendar cal;

    @Before
    public void setUp() throws Exception {
        calDAO = new CalendarDAOImpl();
        calDAO.setEm(em);

        wm1 = new WeekMatches();
        wm2 = new WeekMatches();

        match1 = new Match();
        match2 = new Match();
        match3 = new Match();
        match4 = new Match();

        cal = new FCalendar();
    }

    /**
     * One Calendar cal, with just 1 wm and 1 match.
     *
     * @throws Exception
     */
    //@Test
    public void testAdditionOfJustOneMatch() throws Exception {
        wm1.addMatch(match1);

        cal.getAllWeekMatches().add(wm1);

        calDAO.addCalendar(cal);
    }

    /**
     *
     * Various matches in one WM.
     */
    //@Test
    public void testAdditionOfVariousMatchesOneWM() throws Exception {
        wm1.addMatch(match1);
        wm1.addMatch(match2);
        wm1.addMatch(match3);
        wm1.addMatch(match4);

        cal.getAllWeekMatches().add(wm1);

        calDAO.addCalendar(cal);
    }

    /**
     *
     * Various WM.
     */
    //@Test
    public void testAddVariousWeekMatches() throws Exception {
        wm1.addMatch(match1);
        wm1.addMatch(match2);
        wm2.addMatch(match3);
        wm2.addMatch(match4);

        cal.getAllWeekMatches().add(wm1);
        cal.getAllWeekMatches().add(wm2);

        calDAO.addCalendar(cal);
    }
}
